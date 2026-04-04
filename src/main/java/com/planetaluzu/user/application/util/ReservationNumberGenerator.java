package com.planetaluzu.user.application.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ReservationNumberGenerator {

    private static final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RANDOM_CODE_LENGTH = 4;
    private static final Random random = new Random();

    public static String generateReservationNumber(String firstName, String lastName) {
        String initials = getInitials(firstName, lastName);

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String randomCode = generateRandomCode();

        return initials + "-" + date + "-" + randomCode;
    }

    private static String getInitials(String firstName, String lastName) {
        char firstInitial = firstName != null && !firstName.isEmpty() ? firstName.charAt(0) : 'X';
        char lastInitial = lastName != null && !lastName.isEmpty() ? lastName.charAt(0) : 'X';
        return ("" + firstInitial + lastInitial).toUpperCase();
    }

    private static String generateRandomCode() {
        StringBuilder sb = new StringBuilder(ReservationNumberGenerator.RANDOM_CODE_LENGTH);
        for (int i = 0; i < ReservationNumberGenerator.RANDOM_CODE_LENGTH; i++) {
            sb.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
}