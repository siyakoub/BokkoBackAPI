package com.msyconseil.bokkobackapi.utils;

import com.msyconseil.bokkobackapi.utils.BCrypt.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public Utils(){

    }
    public static String hash(String text) {
        if (text == null) {
            return null;
        } else {
            return BCrypt.hashpw(text, BCrypt.gensalt(12));
        }
    }
    public static boolean compare(String clearText, String hash) {
        return BCrypt.checkpw(clearText, hash);
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString, String pattern) {
        if (dateTimeString != null && !dateTimeString.trim().equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(dateTimeString, formatter);
        } else {
            return null;
        }
    }
    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        return convertStringToLocalDateTime(dateTimeString, "yyyy-MM-dd HH:mm:ss");
    }
    public static LocalDate convertStringToLocalDate(String dateString, String pattern) {
        if (dateString != null && !dateString.trim().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(dateString, formatter);
        } else {
            return null;
        }
    }
    public static LocalDate convertStringToLocalDate(String dateString) {
        if (dateString != null && !dateString.trim().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date;
        } else {
            return null;
        }
    }
    public static String formatLocalDateTime(LocalDateTime date) {
        if (date == null) {
            return "";
        } else {
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return date.format(dtFormatter);
        }
    }
    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return "";
        } else {
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(dtFormatter);
        }
    }
    public static boolean matchByPattern(String text, String stringPattern) {
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
    public static String removeAccent(String src) {
        return Normalizer.normalize(src, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    public byte[] convertImageToBytes(MultipartFile image) throws IOException {
        return image.getBytes();
    }
    public ByteArrayInputStream convertBytesToImage(byte[] imageInBytes) {
        return new ByteArrayInputStream(imageInBytes);
    }
}
