package ru.job4j.springboot.service;

import ru.job4j.springboot.model.Site;

import java.util.List;

/**
 * Class LinkService.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 04.07.2020
 */
public interface SiteService {

    Site findById(int id);
    List<Site> getAll();
    Site save(Site site);
}
