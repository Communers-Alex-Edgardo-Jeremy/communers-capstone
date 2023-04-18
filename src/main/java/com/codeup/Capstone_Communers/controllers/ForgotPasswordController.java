package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.Services.EmailService;
import com.codeup.Capstone_Communers.Services.PasswordGenerator;
import com.codeup.Capstone_Communers.Services.UserService;
import com.codeup.Capstone_Communers.Services.Utility;
import com.codeup.Capstone_Communers.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ForgotPasswordController {
//    @Autowired
//    private JavaMailSender mailSender;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private UserService userService;

    public ForgotPasswordController(EmailService emailService) {
        this.emailService = emailService;
    }


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
            String response = userService.updateResetPasswordToken(token, email);
            System.out.println("ready to getSiteURL");
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset/password?token=" + token;
            System.out.println("ready to send");
            emailService.prepareAndSend(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        }  catch (Exception e) {
            e.printStackTrace();
        }

        return "/users/forgotPasswordForm";
    }

//    public void sendEmail(String recipientEmail, String link)
//            throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {
////        jakarta.mail.internet.MimeMessage message = emailService.createMimeMessage();
////        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom("ucommuners39@gmail.com", "commUnity");
//        helper.setTo(recipientEmail);
//
//        String subject = "";
//
//        String content = "<p>Hello,</p>"
//                + "<p>You have requested to reset your password.</p>"
//                + "<p>Click the link below to change your password:</p>"
//                + "<p><a href=\"" + link + "\">Change my password</a></p>"
//                + "<br>"
//                + "<p>Ignore this email if you do remember your password, "
//                + "or you have not made the request.</p>";
//
//        helper.setSubject(subject);
//
//        helper.setText(content, true);
//
//        mailSender.send(message);
//    }


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