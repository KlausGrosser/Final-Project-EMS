package com.finalproject.util.email;


import com.finalproject.dto.RegistrationUserDTO;
import com.finalproject.model.entity.User;
import com.finalproject.model.service.AbsenceService;
import com.finalproject.model.service.UserService;
import com.finalproject.util.token.ConfirmationToken;
import com.finalproject.util.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final Logger LOGGER  = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final EmailValidator emailValidator;
    private final EmailTemplateEngine emailTemplateEngine;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;
    private final AbsenceService absenceService;

    @Override
    @Async
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("emsdevtestmail@gmail.com");
            mailSender.send(mimeMessage);

        } catch (MessagingException e){
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Scheduled(cron="40 23 * * * MON-FRI")
    public void sendEmailToSupervisorWithAbsentEmployees(){
        for(Map.Entry<User, List<User>> entry : absenceService.getEmployeesAndSupervisorsRelation().entrySet()){

            List<String> emailAddresses = new ArrayList<>();

            for(User user : entry.getValue()){
                emailAddresses.add(user.getUsername());
            }

            this.sendEmailToMultipleAddresses(
                    emailAddresses,
                    emailTemplateEngine.buildAbsenteeismWarningForSupervisor(
                            entry.getValue(),
                            entry.getKey().getFirstName(),
                            "http://localhost:8080/absence/sendWarningToEmployees"),
                    "Unusual absenteeism");
        }



    }

    public void sendWarningToAbsentEmployees(){
        User currentUser = userService.getCurrentUser();

        List<User> absentEmployees = new ArrayList<>();

        if(currentUser.isSupervisorRole()){
            absentEmployees = absenceService.getAssignedEmployees(currentUser);
        }

        this.sendEmailToMultipleAddresses(
                absenceService.getEmailsFromAbsentEmployees(absentEmployees),
                emailTemplateEngine.buildAbsentEmployeeWarningEmail(),
                "Unusual absenteeism"
        );

    }

    public void createNewUserAndSendRegistrationMail(RegistrationUserDTO user) {
        //email should be validated
        if(!emailValidator
                .test(user.getUsername())){
            throw new IllegalStateException(
                    "invalid email"
            );
        }
        //signing the user up and receiving the token back
        User newUser = userService.createUser(user);

        String token = confirmationTokenService.createConfirmationToken(newUser);

        // create a confirmation link to be sent to the user email
        String link = "http://localhost:8080/confirm?token=" + token;
        //Send Email
        this.send(
                user.getUsername(),
                emailTemplateEngine.buildRegisterEmail(
                        user.getFirstName(),
                        user.getUsername(),
                        user.getPassword(),
                        link
                )
        );
    }

    @Transactional
    public String confirmToken(String token) {
        // search for the token
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(
                        ()-> new IllegalStateException("token not found")
                );
        // check if the token is already confirmed or not
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("token is confirmed");
        }
        // check if the token expires at time after now or not
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token is expired");
        }
        // set the confirmed time of the token to now
        confirmationTokenService.setConfirmedAt(token);
        // set the user that holds this token to enabled
        userService.enableUser(
                confirmationToken.getUser().getUsername());
//        confirmationToken.getUser().setEnabled(true);
        userService.save(confirmationToken.getUser());
        return "confirmed";
    }

    public void sendEmailToMultipleAddresses(List<String> emailAddresses, String emailBody, String subject){
        String[] addresses = emailAddresses.toArray(new String[0]);

        if(!emailAddresses.isEmpty()){
            try{
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper =
                        new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setText(emailBody, true);
                helper.setTo(addresses);
                helper.setSubject(subject);
                helper.setFrom("emsdevtestmail@gmail.com");
                mailSender.send(mimeMessage);

            } catch (MessagingException e){
                LOGGER.error("failed to send email", e);
                throw new IllegalStateException("failed to send email");
            }
        }




    }
}
