package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.disnetdb.PersonToken;
import edu.upm.midas.data.relational.entities.disnetdb.PersonTokenPK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public interface PersonTokenRepository {

    PersonToken findById(PersonTokenPK personTokenPK);

    PersonToken findByPersonId(String personId);

    Object[] findByIdNative(String personId, String token);

    List<PersonToken> findAllQuery();

    List<Object[]> findAllNative();

    void persist(PersonToken personToken);

    int insertNative(String personId, String token, boolean enabled, Date date);

    boolean deleteById(PersonTokenPK personTokenPK);

    void delete(PersonToken personToken);

    PersonToken update(PersonToken personToken);

    int updateEnabledNative(String personId, String token, boolean enabled, Date date, Date datetime);

    int updateByIdQuery(PersonToken personToken);

}
