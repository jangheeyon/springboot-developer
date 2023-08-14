package org.example.springbootdeveloper.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootdeveloper.domain.Article;
import org.example.springbootdeveloper.domain.Notification;
import org.example.springbootdeveloper.domain.NotificationType;
import org.example.springbootdeveloper.repository.MemberRepository;
import org.example.springbootdeveloper.repository.NotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class BlogEventListener {

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleBlogCreatedEvent(BlogCreatedEvent blogCreatedEvent) {
        Article article = blogCreatedEvent.getArticle();
        log.info(article.getTitle() + " is created.");
        saveNotification(article);
        //db에 Notification 정보 저장
    }

    private void saveNotification(Article article){
        notificationRepository.save(Notification.from(
                article.getTitle(),
                false,
                LocalDateTime.now(),
                article.getContent(),
                NotificationType.BLOG_CREATED
        ));
    }
}
