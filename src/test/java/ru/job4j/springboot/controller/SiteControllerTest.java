package ru.job4j.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.springboot.Application;
import ru.job4j.springboot.model.Link;
import ru.job4j.springboot.model.Site;
import ru.job4j.springboot.service.LinkService;
import ru.job4j.springboot.service.SiteService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.07.2020
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@WithMockUser
class SiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkService linkService;

    @MockBean
    private SiteService siteService;

    @Autowired
    ObjectMapper jo;

    @Test
    public void siteRegistration() throws Exception {
        final Site site = new Site();
        site.setId(4);
        site.setLogin("NEW");
        site.setPassword("123");
        this.mockMvc.perform(post("/rest/registration")
                .contentType(MediaType.APPLICATION_JSON).content(jo.writeValueAsString(site)))
                .andDo(print())
                .andExpect(status().isCreated());
        ArgumentCaptor<Site> argument = ArgumentCaptor.forClass(Site.class);
        verify(siteService).save(argument.capture());
        assertEquals("login", "NEW", argument.getValue().getLogin());
    }

    @Test
    public void getSiteById() throws Exception {
        final Site site = new Site();
        site.setId(4);
        site.setLogin("NEW");
        site.setPassword("123");
        when(siteService.findById(4)).thenReturn(site);
        this.mockMvc.perform(get("/rest/site/{id}", 4))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getListOfSites() throws Exception {
        final Site site = new Site();
        site.setId(4);
        site.setLogin("NEW");
        site.setPassword("123");
        when(siteService.getAll()).thenReturn(List.of(site));
        this.mockMvc.perform(get("/rest/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[0].login").value("NEW"))
                .andExpect(jsonPath("$[0].password").value("123"));
    }

    @Test
    public void convert() throws Exception {
        final Link link = new Link();
        link.setId(1);
        link.setUrl("https://mail.ru/news/");
        link.setCode("d1d14ea");
        link.setCount(3);
        this.mockMvc.perform(post("/rest/convert")
                .contentType(MediaType.APPLICATION_JSON).content(jo.writeValueAsString(link)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        ArgumentCaptor<Link> argument = ArgumentCaptor.forClass(Link.class);
        verify(linkService).save(argument.capture());
        assertEquals("url", "https://mail.ru/news/", argument.getValue().getUrl());
    }

    @Disabled
    @Test
    public void redirect() throws Exception {
        final Link link = new Link();
        link.setId(1);
        link.setUrl("https://mail.ru/news/");
        link.setCode("d1d14ea");
        link.setCount(3);
        when(linkService.findBySiteId(1)).thenReturn(List.of(link));
        this.mockMvc.perform(get("/rest/redirect/{code}", "d1d14ea"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].url").value("https://mail.ru/news/"));
    }

    @Test
    public void getStatOfSites() throws Exception {
        final Link link = new Link();
        link.setId(1);
        link.setUrl("https://mail.ru/news/");
        link.setCode("1234567");
        link.setCount(3);
        when(linkService.findBySiteId(1)).thenReturn(List.of(link));
        this.mockMvc.perform(get("/rest/statistic").param("siteId", String.valueOf(1)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}