package com.urban.backend.shared.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    public void sendResetPasswordEmail(String to, String username, String code) throws MessagingException {
        String content = generateResetPasswordEmailContent(username, code);

        sendEmail(
                to,
                "Resetowanie hasła - Jodo Gym",
                content
        );
    }

    @Async
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            if (to == null || to.isBlank() || !isValidEmailAddress(to)) {
                throw new IllegalArgumentException("Invalid email address: " + to);
            }

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setFrom("no-reply@jodo-gym.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MessagingException("Failed to send email to " + to, e);
        } catch (MailSendException e) {
            throw new MailSendException("Failed to send email to " + to + ": " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid email address: " + to, e);
        }
    }

    private String generateResetPasswordEmailContent(String username, String code) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/reset-password.html");
            String template;
            try (InputStream inputStream = resource.getInputStream()) {
                template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            template = template
                    .replace("{{username}}", username)
                    .replace("{{verification_code}}", code);

            return template;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }

    private boolean isValidEmailAddress(String to) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return to.matches(regex);
    }
}
