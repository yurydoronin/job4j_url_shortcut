package ru.job4j.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.springboot.model.Link;
import ru.job4j.springboot.repository.LinkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository repo;

    @Override
    public Link findByCode(String code) {
        return this.repo.findByCode(code);
    }

    @Override
    public List<Link> findBySiteId(int id) {
        return this.repo.findBySiteId(id);
    }

    @Override
    public void updateLink(int count, int id) {
        this.repo.updateLink(count, id);
    }

    @Override
    public Link save(Link link) {
        return this.repo.save(link);
    }
}
