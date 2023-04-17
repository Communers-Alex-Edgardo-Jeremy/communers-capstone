package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.Services.PasswordGenerator;
import com.codeup.Capstone_Communers.Services.UserService;
import com.codeup.Capstone_Communers.Services.Utility;
import com.codeup.Capstone_Communers.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @GetMapping("/forgot/password")
    public String showForgotPasswordForm() {
        return "users/forgotPasswordForm";
    }

    @PostMapping("/forgot/password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = PasswordGenerator.generateRandomPassword(30);
        try {
            System.out.println("ready to updateResetPasswordToken");
            userService.updateResetPasswordToken(token, email);
            System.out.println("ready to getSiteURL");
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset/password?token=" + token;
            System.out.println("ready to send");
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (MessagingException ex) {
            System.out.println("Messaging Exception occured");
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException ex){
            System.out.println("UnsupportedEncodingException occured");
            model.addAttribute("error", ex.getMessage());
        } catch (Exception e) {
            System.out.println("runtime exception occured");
            System.out.println((e.getMessage()));
        }

        return "/users/forgotPasswordForm";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("ucommuners39@gmail.com", "commUnity");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }


    @GetMapping("/reset/password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        String user = String.valueOf(UserService.getByResetPasswordToken(token));
        model.addAttribute("token", token);

        model.addAttribute("error", true);
        model.addAttribute("message", "Invalid Token");
        return "/users/forgotPasswordForm";

    }

    @PostMapping("/reset/password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = UserService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "/users/forgotPasswordForm";
        } else {
            userService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "/users/forgotPasswordForm";
    }
}