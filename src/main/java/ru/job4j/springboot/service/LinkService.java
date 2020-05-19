package ru.job4j.springboot.service;

import ru.job4j.springboot.model.Link;

import java.util.List;

/**
 * Class LinkService.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 04.07.2020
 */
public interface LinkService {

    Link findByCode(String code);
    List<Link> findBySiteId(int id);
    void updateLink(int count, int id);
    Link save(Link link);
}
