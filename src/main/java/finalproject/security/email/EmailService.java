package finalproject.security.email;

import finalproject.models.Employee;
import finalproject.security.token.ConfirmationToken;
import finalproject.security.token.ConfirmationTokenService;
import finalproject.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final Logger LOGGER  = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final EmailValidator emailValidator;
    private final EmailTemplateEngine emailTemplateEngine;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmployeeService employeeService;

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

    public void registerMail(Employee employee) {
        //email should be validated
        if(!emailValidator
                .test(employee.getEmail())){
            throw new IllegalStateException(
                    "invalid email"
            );
        }
        //signing the user up and receiving the token back
        String token = employeeService.signUp(
                new Employee(
                        employee.getFName(),
                        employee.getLName(),
                        employee.getEmail(),
                        employee.getPassword(),
                        employee.getRole()
                )
        );
        // create a confirmation link to be sent to the user email
        String link = "http://localhost:8080/confirm?token=" + token;
        //Send Email
        this.send(
                employee.getEmail(),
                emailTemplateEngine.buildRegisterEmail(
                        employee.getFName(),
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
        employeeService.enableEmployee(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
