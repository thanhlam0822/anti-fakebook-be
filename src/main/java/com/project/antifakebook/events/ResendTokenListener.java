package com.project.antifakebook.events;

import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.entity.VerificationTokenEntity;
import com.project.antifakebook.repository.VerifyTokenRepository;
import com.project.antifakebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class ResendTokenListener implements ApplicationListener<ResendTokenEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private VerifyTokenRepository verifyTokenRepository;
    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(final ResendTokenEvent event) {
        this.resendToken(event);

    }

    private void resendToken(final ResendTokenEvent event) {
        final Long userId = event.getUserId();
        UserEntity user = userService.updateVerificationTokenForUser(userId);
        VerificationTokenEntity token = verifyTokenRepository.getVerifyTokenByUserId(userId);
        final SimpleMailMessage email = constructEmailMessageResendToken(event, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessageResendToken(ResendTokenEvent event, UserEntity user, VerificationTokenEntity token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Resend Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/api/registration-confirm?token=" + token.getToken();
        final String message = messages.getMessage("message.resendToken", null, "Your resend token request successfully. To confirm your registration, please click on the below link.", event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}
