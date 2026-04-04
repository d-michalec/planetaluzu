package com.planetaluzu.user.infrastructure.email;

import com.planetaluzu.user.application.port.out.EmailSender;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Recover;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void generateReservationEmail(
            String email, String firstName, String lastName, String reservationId, LocalDateTime expiresAt) {

        try {
            double amount = 150;
            String paymentTitle = firstName + " " + lastName + " - Planeta Luzu";

            String formattedExpiresAt = expiresAt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Potwierdzenie rezerwacji słuchawek");

            String htmlContent = "<html><body style='background-color:#111; color:#fff; font-family:Arial, sans-serif; padding:20px;'>"
                    + "<div style='max-width:600px; margin:auto; background-color:#111; padding:20px;'>"
                    + "<h2>Cześć " + firstName + "</h2>"
                    + "<p>Twoja rezerwacja słuchawek o numerze <strong>" + reservationId + "</strong> została pomyślnie zarejestrowana.</p>"
                    + "<p>Aby potwierdzić ją, prosimy o dokonanie opłaty rezerwacyjnej w wysokości <strong>"
                    + String.format("%.2f", amount) + " zł</strong> w ciągu 12 godzin (do <strong>" + formattedExpiresAt + "</strong>).</p>"
                    + "<p><strong>Tytuł płatności:</strong> " + paymentTitle + "</p>"
                    + "<p><strong>Revtag:</strong> @planeta_luzu</p>"
                    + "<p><strong>Numer konta:</strong> 62291000060000000002224734</p>"
                    + "<p>🗿 Dzięki! – <strong>PlanetaLuzu</strong></p>"
                    + "<hr style='border-color:#333;'/>"
                    + "<p style='font-size:12px; color:#aaa;'>&copy; 2025 PlanetaLuzu.<br/>"
                    + "<a href='#' style='color:#FFD700; text-decoration:none;'>Odwiedź naszą stronę</a> | "
                    + "<a href='mailto:test@gmail.com' style='color:#FFD700; text-decoration:none;'>Kontakt</a></p>"
                    + "</div></body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Nie udało się wysłać maila z potwierdzeniem rezerwacji", e);
        }
    }

    @Override
    @Async
    public void sendReservationConfirmation(String email, String firstName, String lastName, String qrToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Potwierdzenie rezerwacji słuchawek");

            // Generowanie kodu QR
            byte[] qrImage = QrCodeGenerator.generate(qrToken);

            // HTML maila
            String mailUsername = "test@gmail.com";
            String htmlContent = "<html><body style='background-color:#111; color:#fff; font-family:Arial, sans-serif; padding:20px;'>"
                    + "<div style='max-width:600px; margin:auto; background-color:#111; padding:20px; text-align:center;'>"
                    + "<img src='cid:logo' alt='PlanetaLuzu Logo' style='width:150px; margin-bottom:20px;'/>"
                    + "<h2 style='color:#FFD700;'>Cześć " + firstName + "</h2>"
                    + "<p>Twoja opłata rezerwacji słuchawek została potwierdzona. Poniżej znajdziesz kod QR potrzebny do odbioru słuchawek:</p>"
                    + "<p><strong>Kod rezerwacji:</strong> <span style='color:#FFD700;'>" + qrToken + "</span></p>"
                    + "<img src='cid:qrCode' alt='QR Code' style='margin:20px 0;'/>"
                    + "<p>🔒 DOZO! – <strong>PlanetaLuzu</strong></p>"
                    + "<p style='font-size:12px; color:#aaa;'>&copy; 2025 PlanetaLuzu.<br/>"
                    + "<a href='#' style='color:#FFD700; text-decoration:none;'>Odwiedź naszą stronę</a> | "
                    + "<a href='mailto:" + mailUsername + "' style='color:#FFD700; text-decoration:none;'>Kontakt</a></p>"
                    + "</div></body></html>";

            helper.setText(htmlContent, true); // true = HTML

            // Dodanie logo inline (logo.png w src/main/resources/static/)
//            helper.addInline("logo", new ClassPathResource("static/logo.png"));

            // Dodanie kodu QR inline
            helper.addInline("qrCode", new ByteArrayResource(qrImage), "image/png");

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Nie udało się wysłać maila z potwierdzeniem rezerwacji", e);
        }
    }

    @Recover
    public void recover(RuntimeException e, String email, String qrToken) {
        System.err.println("Nie udało się wysłać maila po 3 próbach: " + email);
    }
}