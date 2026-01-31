package com.rutik.ems.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // DB auto increment

    @Column(name = "sender_id", nullable = false)
    private Integer senderId;

    @Column(name = "sender_role", nullable = false, length = 10)
    private String senderRole;   // ADMIN / USER

    @Column(name = "receiver_id", nullable = false)
    private Integer receiverId;

    @Column(name = "receiver_role", nullable = false, length = 10)
    private String receiverRole; // ADMIN / USER

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 20)
    private String status; // SENT, DELIVERED, READ

    public ChatMessage() {}

    public ChatMessage(Integer senderId, String senderRole,
                       Integer receiverId, String receiverRole,
                       String message) {
        this.senderId = senderId;
        this.senderRole = senderRole;
        this.receiverId = receiverId;
        this.receiverRole = receiverRole;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = "SENT";
    }

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getSenderId() { return senderId; }
    public void setSenderId(Integer senderId) { this.senderId = senderId; }

    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }

    public Integer getReceiverId() { return receiverId; }
    public void setReceiverId(Integer receiverId) { this.receiverId = receiverId; }

    public String getReceiverRole() { return receiverRole; }
    public void setReceiverRole(String receiverRole) { this.receiverRole = receiverRole; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Utility

    public boolean isSentBy(Integer id, String role) {
        return senderId.equals(id) && senderRole.equalsIgnoreCase(role);
    }

    public boolean isReceivedBy(Integer id, String role) {
        return receiverId.equals(id) && receiverRole.equalsIgnoreCase(role);
    }

    public boolean isUnread() {
        return !"READ".equalsIgnoreCase(status);
    }
}
