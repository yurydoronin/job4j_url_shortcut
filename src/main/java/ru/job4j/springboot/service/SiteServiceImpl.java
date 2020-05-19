package ru.job4j.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.springboot.model.Site;
import ru.job4j.springboot.repository.SiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository repo;

    @Override
    public Site findById(int id) {
        return this.repo.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format(
                        "Сайт %s не найден. Проверьте данные запроса", id)));
    }

    @Override
    public List<Site> getAll() {
        List<Site> sites = new ArrayList<>();
        this.repo.findAll().forEach(sites::add);
        return sites;
    }

    @Override
    public Site save(Site site) {
        return this.repo.save(site);
    }
}
