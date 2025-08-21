package com.jobspring.jobspring.service;

import com.jobspring.jobspring.entity.Account;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final TokenService tokenService;
    private final SecureRandom secureRandom;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendValidationMail(Account account) throws MessagingException {
        var token = generateToken(6);
        tokenService.save(token, account.getId());
        sendMail(
                account.getEmail(),
                EmailTemplateName.ACTIVATION_MAIL,
                token,
                "Verification code for JobSpring Account"
        );
    }

    private void sendMail(
            String to,
            EmailTemplateName emailTemplateName,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName;
        if(emailTemplateName == null){templateName = "common/activation-mail";}
        else {templateName = emailTemplateName.getFile();}

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String,Object> properties = new HashMap<>();
        properties.put("email",to);
        properties.put("activationCode",activationCode);
        Context thymeleafContext =  new Context();
        thymeleafContext.setVariables(properties);

        mimeMessageHelper.setFrom("siddharthmsingh2001@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setText(templateEngine.process(templateName, thymeleafContext), true);
        mailSender.send(mimeMessage);
    }

    private String generateToken(int length){
        String characters = "0123456789";
        StringBuilder tokenBuilder = new StringBuilder();
        String token;
        do{
            tokenBuilder.setLength(0);
            for(int i=0; i<length; i++){
                int randomIndex = secureRandom.nextInt(characters.length());
                tokenBuilder.append(characters.charAt(randomIndex));
            }
            token = tokenBuilder.toString();
        } while(tokenService.existsByToken(token));
        return token;
    }

    @Getter
    public enum EmailTemplateName{
        ACTIVATION_MAIL("common/activation-mail");
        private final String file;
        EmailTemplateName(String file){
            this.file = file;
        }
    }

}
