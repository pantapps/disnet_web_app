package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.disnetdb.Token;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public interface TokenRepository {

    Token findById(String token);

    Object[] findByIdNative(String token);

    List<Token> findAllQuery();

    List<Object[]> findAllNative();

    void persist(Token token);

    int insertNative(String token, String type, boolean enabled, long expire, String scope, Date date);

    boolean deleteById(String token);

    void delete(Token token);

    Token update(Token token);

    int updateEnabled(String token);

    int updateByIdQuery(Token token);

}
