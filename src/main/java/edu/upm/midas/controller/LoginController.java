package edu.upm.midas.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.upm.midas.data.relational.entities.disnetdb.Person;
import edu.upm.midas.data.relational.entities.disnetdb.PersonToken;
import edu.upm.midas.data.relational.service.CountryService;
import edu.upm.midas.data.relational.service.LogQuery_Service;
import edu.upm.midas.data.relational.service.PersonService;
import edu.upm.midas.data.relational.service.PersonTokenService;
import edu.upm.midas.data.relational.service.helper.PersonHelper;
import edu.upm.midas.email.component.EmailHtmlSender;
import edu.upm.midas.email.model.EmailStatus;
import edu.upm.midas.email.service.EmailService;
import edu.upm.midas.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by gerardo on 21/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className LoginController
 * @see
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private PersonHelper personHelper;
    @Autowired
    private PersonService personService;
    @Autowired
    private LogQuery_Service logQuery_service;
    @Autowired
    private PersonTokenService personTokenService;
    @Autowired
    private CountryService countryService;


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String userRegister(Model model){
        model.addAttribute("countries", countryService.findAll());
        return "user/registration";
    }


    @RequestMapping(value = "/login")
    public String userLogin(Model model){
        return "user/login";
    }


    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String userRecovery(Model model){
        return "user/forgot";
    }


    @RequestMapping(value="/client/home", method = RequestMethod.GET)
    public ModelAndView home(HttpSession sesion){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        Person user = personService.findById( auth.getName() );
        PersonToken token = personTokenService.findByPersonId( auth.getName() );
        //<editor-fold desc="VARIABLES DE SESION">
        sesion.setAttribute("person", user);
        sesion.setAttribute("token", token.getToken());
        //</editor-fold>
        //System.out.println(user.toString());
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getPersonId() + ")");
        modelAndView.addObject("email", user.getPersonId());
        modelAndView.addObject("user", user);
        modelAndView.addObject("token", token.getToken());
        modelAndView.addObject("countries", countryService.findAll());
        //modelAndView.addObject("transactions", transactionHistories);
        modelAndView.addObject("clientMessage","Content Available Only for DISNET clients");
        modelAndView.setViewName("user/client/home");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserRegistrationForm userRegistrationForm, BindingResult bindingResult, Device device) throws JsonProcessingException {
        System.out.println(userRegistrationForm.toString());
        ModelAndView modelAndView = new ModelAndView();

        Person userExists = personHelper.findByEmailAndStatusOK( userRegistrationForm.getEmail() );

        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {//System.out.println("que pasa" + bindingResult.toString());
            modelAndView.addObject("countries", countryService.findAll());
            modelAndView.setViewName("user/registration");
        } else {
            //PONER VALIDACIÖN PARA CACHAR ERRORES Y MOSTRARLOS
            try {
                if (personHelper.saveNewUser(userRegistrationForm, device)) {
                    System.out.println("BIEN");
                    modelAndView.addObject("successMessage", "User has been registered successfully. Please verify your email for complete the registration.");
                    modelAndView.addObject("user", userRegistrationForm);
                    modelAndView.setViewName("user/confirmation");
                } else {
                    System.out.println("MAL");
                    modelAndView.addObject("errorMessage", "Problems registering user");
                    modelAndView.addObject("user", userRegistrationForm);
                    modelAndView.setViewName("user/confirmation");
                }
            }catch (Exception e){
                System.out.println("MUY MAL");
                modelAndView.addObject("errorMessage", "Problems registering user");
                modelAndView.addObject("user", userRegistrationForm);
                modelAndView.setViewName("user/confirmation");
            }
        }
        return modelAndView;
    }


    }
