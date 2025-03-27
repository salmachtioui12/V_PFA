package com.cwa.crudspringboot.controller;


import com.cwa.crudspringboot.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Assurez-vous que c'est le bon port frontend
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            passwordResetService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Si cet email existe, un lien a été envoyé.");
        } catch (Exception e) {
            // Log l'erreur en interne
            System.err.println("Erreur lors de l'envoi du email: " + e.getMessage());
            return ResponseEntity.ok("Si cet email existe, un lien a été envoyé.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
}