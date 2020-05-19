package ru.job4j.springboot.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.springboot.model.Link;

import java.util.List;
import java.util.Set;

/**
 * Class LinksRepository.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 18.05.2020
 */
@Repository
public interface LinkRepository extends CrudRepository<Link, Integer> {

    /**
     *
     * @param code, .
     * @return .
     */
    Link findByCode(String code);

    /**
     *
     * @param id, the Site id.
     * @return .
     */
    List<Link> findBySiteId(@Param("id") int id);

    /**
     *
     * @param count, .
     * @param id,    .
     */
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Link l set l.count = :count + 1 where l.id = :id")
    void updateLink(@Param("count") int count, @Param("id") int id);

}