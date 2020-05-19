package ru.job4j.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.springboot.model.Site;
import ru.job4j.springboot.repository.SiteRepository;

import static java.util.Collections.emptyList;

/**
 * Class SiteDetailsServiceImpl.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 07.06.2020
 */
@Service
@RequiredArgsConstructor
public class SiteDetailsServiceImpl implements UserDetailsService {

    private final SiteRepository siteRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Site site = siteRepo.findByLogin(login);
        if (site == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(site.getLogin(), site.getPassword(), emptyList());
    }
}
