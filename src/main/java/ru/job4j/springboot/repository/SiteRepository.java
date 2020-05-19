package ru.job4j.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.springboot.model.Site;

/**
 * Class SiteRepository.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 18.05.2020
 */
@Repository
public interface SiteRepository extends CrudRepository<Site, Integer> {

    Site findByLogin(String login);
}
