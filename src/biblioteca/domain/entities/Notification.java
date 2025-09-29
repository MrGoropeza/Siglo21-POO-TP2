package biblioteca.domain.entities;

import java.time.LocalDateTime;

import biblioteca.domain.enums.NotificationChannel;

public class Notification {
    private String id;
    private Member member;
    private String message;
    private NotificationChannel channel;
    private LocalDateTime sentAt;

    public Notification(String id, Member member, String message, NotificationChannel channel, LocalDateTime sentAt) {
        this.id = id;
        this.member = member;
        this.message = message;
        this.channel = channel;
        this.sentAt = sentAt;
    }

    public String getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getMessage() {
        return message;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
