package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
