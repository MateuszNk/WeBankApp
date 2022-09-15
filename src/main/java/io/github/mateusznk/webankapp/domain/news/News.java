package io.github.mateusznk.webankapp.domain.news;

import java.time.LocalDateTime;

public class News {
    private final Integer id;
    private final String title;
    private final String url;
    private final String description;
    private final LocalDateTime dateAdded;

    public News(Integer id,
                String title,
                String url,
                String description,
                LocalDateTime dateAdded) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
        this.dateAdded = dateAdded;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getDescription() { return description; }
    public LocalDateTime getDateAdded() { return dateAdded; }
}
