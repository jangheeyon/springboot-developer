package org.example.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="message", nullable = false)
    private String message;

    @Column(name="checked", nullable = false)
    private boolean checked;

//    @ManyToOne
//    private Member member;

    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public static Notification from(String title, boolean checked, LocalDateTime created, String message, NotificationType notificationType){
        Notification notification = new Notification();
        notification.title = title;
        notification.checked = checked;
        notification.created = created;
        notification.message = message;
        notification.notificationType = notificationType;
        return notification;
    }
}
