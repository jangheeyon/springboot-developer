package org.example.springbootdeveloper.event;

import lombok.Getter;
import org.example.springbootdeveloper.domain.Article;
@Getter
public class BlogCreatedEvent {

    private final Article article;

    public BlogCreatedEvent(Article article) {
        this.article = article;
    }
}
