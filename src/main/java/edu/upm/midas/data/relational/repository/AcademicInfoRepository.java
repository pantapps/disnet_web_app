package edu.upm.midas.data.relational.repository;
import edu.upm.midas.data.relational.entities.disnetdb.AcademicInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 20/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className AcademicInfoRepository
 * @see
 */
public interface AcademicInfoRepository {

    AcademicInfo findById(int academicId);

    Object[] findByIdNative(int academicId);//por academicId
    
    List<AcademicInfo> findAllQuery();

    List<Object[]> findAllNative();

    void persist(AcademicInfo academicInfo);
    
    int insertNative(String institution, int country, String occupation, String interest);
    
    boolean deleteById(int academicId);

    void delete(AcademicInfo academicInfo);

    AcademicInfo update(AcademicInfo academicInfo);

    int updateByIdQuery(AcademicInfo academicInfo);
    
}
