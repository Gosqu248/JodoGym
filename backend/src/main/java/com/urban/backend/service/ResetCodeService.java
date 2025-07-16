package com.urban.backend.service;

import com.urban.backend.model.ResetCode;
import com.urban.backend.model.User;
import com.urban.backend.repository.ResetCodeRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ResetCodeService {
    private final ResetCodeRepository repository;
    private final EmailService emailService;

    public void generateAndSendResetCode(User user) throws MessagingException {
        ResetCode resetCode = ensureResetCodeExists(user);
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);

        resetCode.setCode(String.valueOf(code));
        resetCode.setCodeExpiration(Instant.now().plusSeconds(300)); // Code valid for 5 minutes
        repository.save(resetCode);
        String username = user.getUserInfo() != null ? user.getUserInfo().getFirstName() : user.getEmail();

        emailService.sendResetPasswordEmail(user.getEmail(), username, resetCode.getCode());
    }

    public boolean verifyResetCode(User user, String code) {
        ResetCode resetCode = user.getResetCode();
        if (resetCode == null || resetCode.getCode() == null || resetCode.getCodeExpiration() == null) {
            return false;
        }

        return resetCode.getCode().equals(code) && Instant.now().isBefore(resetCode.getCodeExpiration());
    }

    private ResetCode ensureResetCodeExists(User user) {
        ResetCode resetCode = user.getResetCode();
        if (resetCode == null) {
            resetCode = new ResetCode();
            resetCode.setUser(user);
            user.setResetCode(resetCode);
        }
        return resetCode;
    }
}
