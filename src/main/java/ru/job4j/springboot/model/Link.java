package ru.job4j.springboot.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class Link.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 18.05.2020
 */
@Getter
@Setter
@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false, updatable = false)
    @BatchSize(size = 10)
    private Site site;

    private String url;

    private String code;

    private int count;

    public Link() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link url1 = (Link) o;
        return id == url1.id
                && count == url1.count
                && site.equals(url1.site)
                && url.equals(url1.url)
                && code.equals(url1.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }
}
