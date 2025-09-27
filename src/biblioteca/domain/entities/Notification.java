package biblioteca.domain.entities;

import java.time.LocalDateTime;

import biblioteca.domain.enums.NotificationChannel;

public class Notification {
    private String id;
    private String memberId;
    private String message;
    private NotificationChannel channel;
    private LocalDateTime sentAt;

    public Notification(String id, String memberId, String message, NotificationChannel channel, LocalDateTime sentAt) {
        this.id = id;
        this.memberId = memberId;
        this.message = message;
        this.channel = channel;
        this.sentAt = sentAt;
    }

    public String getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
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
