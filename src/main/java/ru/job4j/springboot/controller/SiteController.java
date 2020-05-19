package ru.job4j.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import ru.job4j.springboot.model.Link;
import ru.job4j.springboot.model.Site;
import ru.job4j.springboot.service.LinkService;
import ru.job4j.springboot.service.SiteService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class AppController.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 18.05.2020
 */
@RestController
@RequestMapping(path = "/rest")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;
    private final LinkService linkService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping(path = "/registration")
    public ResponseEntity<Site> reg(@RequestBody Site site) {
        site.setLogin(site.getLogin());
        site.setPassword(this.encoder.encode(site.getPassword()));
        return new ResponseEntity<>(this.siteService.save(site), HttpStatus.CREATED);
    }

    @GetMapping(path = "/site/{id}")
    public Site getSiteById(@PathVariable Integer id) {
        return this.siteService.findById(id);
    }

    @GetMapping(path = "/all")
    public List<Site> getAllSites() {
        return this.siteService.getAll();
    }

    @PostMapping(path = "/convert")
    public Link convert(@RequestBody Link link) {
        String md5 = DigestUtils.md5DigestAsHex(link.getUrl().getBytes());
        link.setCode(md5.substring(0, 7));
        return this.linkService.save(link);
    }

    @GetMapping(path = "/redirect/{code}")
    public String redirect(@PathVariable String code) {
        Link link = this.linkService.findByCode(code);
        this.linkService.updateLink(link.getCount(), link.getId());
        return String.format("HTTP CODE: %s, redirect:%s", HttpStatus.FOUND, link.getUrl());
    }

    @GetMapping(path = "/statistic")
    public Map<String, Integer> stat(@RequestParam int siteId) {
        List<Link> list = this.linkService.findBySiteId(siteId);
        return list.stream().collect(Collectors.toMap(Link::getUrl, Link::getCount));
    }
}
