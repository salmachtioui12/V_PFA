package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.entity.User;
import com.cwa.crudspringboot.exception.InvalidTokenException;
import com.cwa.crudspringboot.exception.UserNotFoundException;
import com.cwa.crudspringboot.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final String jwtSecret;

    public PasswordResetService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JavaMailSender mailSender,
            @Value("${app.secret-key}") String jwtSecret) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.jwtSecret = jwtSecret;
    }

    public void sendPasswordResetEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("Aucun utilisateur trouvé avec l'email: " + email);
        }

        String token = generateResetToken(user);
        saveResetToken(user, token);
        sendResetEmail(user.getEmail(), token);
    }
    public void resetPassword(String token, String newPassword) {
        User user = validateResetToken(token);
        updateUserPassword(user, newPassword);
    }

    private String generateResetToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 heure
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private void saveResetToken(User user, String token) {
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiry(new Date(System.currentTimeMillis() + 3600000));
        userRepository.save(user);
    }

    private void sendResetEmail(String email, String token) {
        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Réinitialisation de mot de passe");
        message.setText("Cliquez sur ce lien pour réinitialiser votre mot de passe : " + resetLink);

        mailSender.send(message);
    }

    private User validateResetToken(String token) {
        Optional<User> userOptional = userRepository.findByResetPasswordToken(token);

        if (userOptional.isEmpty()) {
            throw new InvalidTokenException("Token de réinitialisation invalide");
        }

        User user = userOptional.get();

        if (user.getResetPasswordTokenExpiry().before(new Date())) {
            throw new InvalidTokenException("Le token de réinitialisation a expiré");
        }

        return user;
    }

    private void updateUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        userRepository.save(user);
    }
}