package com.rutik.ems.controller;

import com.rutik.ems.model.ChatMessage;
import com.rutik.ems.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatRestController {

    @Autowired
    private ChatMessageRepository chatRepo;
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @RequestParam Integer id1,
            @RequestParam String role1,
            @RequestParam Integer id2,
            @RequestParam String role2) {

        return ResponseEntity.ok(
                chatRepo.getChatBetween(id1, role1, id2, role2)
        );
    }
    @GetMapping("/unread")
    public ResponseEntity<Long> getUnreadCount(
            @RequestParam Integer senderId,
            @RequestParam String senderRole,
            @RequestParam Integer receiverId,
            @RequestParam String receiverRole) {

        return ResponseEntity.ok(
                chatRepo.countUnread(senderId, senderRole, receiverId, receiverRole)
        );
    }
    @PutMapping("/read")
    public ResponseEntity<String> markAsRead(
            @RequestParam Integer senderId,
            @RequestParam String senderRole,
            @RequestParam Integer receiverId,
            @RequestParam String receiverRole) {

        int updated = chatRepo.markAsRead(
                senderId, senderRole, receiverId, receiverRole
        );

        return ResponseEntity.ok(updated + " messages marked as READ");
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteChatBetween(
            @RequestParam Integer id1,
            @RequestParam String role1,
            @RequestParam Integer id2,
            @RequestParam String role2) {

        int deleted = chatRepo.deleteChatBetween(
                id1, role1, id2, role2
        );

        return ResponseEntity.ok(deleted + " messages deleted");
    }
}
