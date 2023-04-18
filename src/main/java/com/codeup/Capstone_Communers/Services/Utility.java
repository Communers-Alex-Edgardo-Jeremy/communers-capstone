package com.codeup.Capstone_Communers.Services;
import com.codeup.Capstone_Communers.models.Questionnaire;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public static String checkTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return now.format(formatter);
    }

    public static int getDayOfMonth(){
        try {
            return Integer.parseInt(checkTime().substring(8, 10));
        } catch (Exception e){
            return 0;
        }
    }

    public static String getQuote(Questionnaire questionnaire){
        String quote;
        switch (getDayOfMonth() % 3) {
            case 0:
                if(questionnaire.getAnswer_1().equals("Y")){
                    return quote = "\"Life isn't about waiting for the storm to pass, it's about learning to dance in the rain.\" - Vivian Greene";
                } else{
                    return quote = "\"Believe you can and you're halfway there.\" -Theodore Roosevelt";
                }
            case 1:
                if(questionnaire.getAnswer_2().equals("Y")){
                    return quote =  "\"Every season is one of becoming, but not always one of blooming. Be gracious with your ever-evolving self.\" - B. Oakman";
                } else{
                    return quote = "\"You are never too old to set another goal or to dream a new dream.\" - C.S. Lewis";
                }
            case 2:
                if(questionnaire.getAnswer_3().equals("Y")){
                    return quote = "\"The greatest discovery of my generation is that a human being can alter his life by altering his attitudes.\" - William James";
                } else{
                    return quote = "\"Change the way you look at things and the things you look at change.\" - Wayne Dyer";
                }
            default:
                return quote = "Seize the day!";
        }
    }

}