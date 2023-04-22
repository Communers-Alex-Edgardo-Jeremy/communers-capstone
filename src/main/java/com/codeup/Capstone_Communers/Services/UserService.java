package com.codeup.Capstone_Communers.Services;


import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;



@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService service;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    //    The processRegister() method handles submission of the registration form
    @PostMapping("/process_register")
    public String processRegister(User user, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        service.register(user, getSiteURL(request));
//        return "redirect:/login";
        return "redirect:/successfulSignUp";
    }

//    registration process for sending a verification link to the user’s email.
    public void register(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepo.save(user);

        sendVerificationEmail(user, siteURL);
    }

//    This method will send email to user (captured from the signup form), with the verification hyperlink            includes the verification code. The value of siteURL is sent from the controller.
    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("ucommuners39@gmail.com", "CommUners");
        helper.setTo(user.getEmail());

        String subject = "Please verify your registration";

        String content = "Dear" + user.getFirst_name() + ",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"" + siteURL + "\">Verify email</a></h3>"
                + "Thank you,<br>"
                + "CommUners!";

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        content = content.replace("http://localhost:8080/process_register", verifyURL);

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);

    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    //    User Account Verification Functionality
    public boolean verify(String verificationCode) {
        User user = userRepo.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);

            return true;
        }
    }

    public void updateResetPasswordToken(String token, String email) throws Exception {

        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            throw new Exception("Could not find any user with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepo.save(user);
    }

}