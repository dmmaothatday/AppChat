package com.example.projectappchat.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EncryptedPasswordUtils {

    public static String encryptedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) throws IOException {
    /*    String password = "123";
        String encryptedPassword = encryptedPassword(password);

        System.out.println("Encrypted Password: " + encryptedPassword);

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean isPasswordMatches = bcrypt.matches(
                "123",
                "$2a$10$VH0B9FHS40gMcUgdXrs6mO.lI7VjbJbwtWgO08aS4yH49k0PVdwL."
        );


        if (isPasswordMatches) { // correct password
            System.out.println("true");
        } else { // Wrong Password
            System.out.println("false");
        }*/


        /*String imageUrl = "C:\\Windows-D\\PICTURE\\user_logo_default.jpg";

        String folder = "C:\\Windows-D\\W_Photo";

        byte[] user_logo = Files.readAllBytes(Paths.get("C:\\Windows-D\\PICTURE\\user_logo_default.jpg"));

        Path path = Paths.get(folder + "\\123"+".jpg");

        Files.write(path, user_logo);*/

        /*String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());


        Date date = Date.valueOf(timeStamp);

        System.out.println(date);*/

        /*LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);*/

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));


    }

}
