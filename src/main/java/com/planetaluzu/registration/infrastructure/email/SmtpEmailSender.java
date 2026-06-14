package com.planetaluzu.registration.infrastructure.email;

import com.planetaluzu.common.exception.ExternalServiceException;
import com.planetaluzu.registration.application.port.out.EmailSender;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Recover;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

    private static final String EMAIL_BG = "#140014";
    private static final String EMAIL_PANEL = "#2a0d2d";
    private static final String EMAIL_PANEL_DARK = "#120012";
    private static final String EMAIL_ACCENT = "#ff00c8";
    private static final String EMAIL_ACCENT_SECONDARY = "#ff6a00";
    private static final String EMAIL_TEXT = "#ffffff";
    private static final String EMAIL_MUTED = "#d8c5da";
    private static final String EMAIL_BORDER = "#5e1a66";
    private static final String REVOLUT_PAYMENT_URL = "https://revolut.me/planeta_luzu";
    private static final String WEBSITE_URL = "https://planeta-luzu.pl";
    private static final String CONTACT_EMAIL = "planetaluzu.sd@gmail.com";

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void generateReservationEmail(
            String email,
            String firstName,
            String lastName,
            String reservationId,
            LocalDateTime expiresAt,
            BigDecimal amount) {

        try {
            String paymentTitle = firstName + " " + lastName + " - Planeta Luzu";

            String formattedExpiresAt = expiresAt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Potwierdzenie rezerwacji słuchawek");

            String htmlContent = "<html><body style='margin:0; background-color:" + EMAIL_BG + "; color:" + EMAIL_TEXT + "; font-family:Inter, Arial, sans-serif; padding:24px;'>"
                    + "<div style='max-width:600px; margin:auto; background-color:" + EMAIL_PANEL + "; border:1px solid " + EMAIL_BORDER + "; border-radius:8px; padding:28px; box-shadow:0 16px 38px rgba(255,0,200,0.18);'>"
                    + "<p style='margin:0 0 10px; color:" + EMAIL_ACCENT_SECONDARY + "; font-size:12px; font-weight:800; text-transform:uppercase;'>Planeta Luzu</p>"
                    + "<h2 style='margin:0 0 18px; color:" + EMAIL_TEXT + "; font-size:28px; line-height:1.1;'>Cześć " + firstName + "</h2>"
                    + "<p style='color:" + EMAIL_MUTED + "; line-height:1.6;'>Twoja rezerwacja słuchawek o numerze <strong style='color:" + EMAIL_ACCENT + ";'>" + reservationId + "</strong> została pomyślnie zarejestrowana.</p>"
                    + "<p style='color:" + EMAIL_MUTED + "; line-height:1.6;'>Aby potwierdzić ją, prosimy o dokonanie opłaty rezerwacyjnej w wysokości <strong style='color:" + EMAIL_TEXT + ";'>"
                    + String.format("%.2f", amount) + " zł</strong> w ciągu 24 godzin (do <strong style='color:" + EMAIL_ACCENT_SECONDARY + ";'>" + formattedExpiresAt + "</strong>).</p>"
                    + "<div style='margin:22px 0; padding:18px; background-color:" + EMAIL_PANEL_DARK + "; border:1px solid " + EMAIL_BORDER + "; border-radius:8px;'>"
                    + "<p style='margin:0 0 8px; color:" + EMAIL_MUTED + ";'><strong style='color:" + EMAIL_TEXT + ";'>Tytuł płatności:</strong> " + paymentTitle + "</p>"
                    + "<p style='margin:0 0 8px; color:" + EMAIL_MUTED + ";'><strong style='color:" + EMAIL_TEXT + ";'>Revtag:</strong> <a href='" + REVOLUT_PAYMENT_URL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>@planeta_luzu</a></p>"
                    + "<p style='margin:0; color:" + EMAIL_MUTED + ";'><strong style='color:" + EMAIL_TEXT + ";'>Numer konta:</strong> 62291000060000000002224734</p>"
                    + "</div>"
                    + "<p style='color:" + EMAIL_MUTED + ";'>Dzięki! - <strong style='color:" + EMAIL_TEXT + ";'>PlanetaLuzu</strong></p>"
                    + "<hr style='border:0; border-top:1px solid " + EMAIL_BORDER + "; margin:24px 0 16px;'/>"
                    + "<p style='font-size:12px; line-height:1.5; color:" + EMAIL_MUTED + ";'>&copy; 2025 PlanetaLuzu.<br/>"
                    + "<a href='" + WEBSITE_URL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Odwiedź naszą stronę</a> | "
                    + "<a href='mailto:" + CONTACT_EMAIL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Kontakt</a></p>"
                    + "</div></body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new ExternalServiceException(
                    "RESERVATION_EMAIL_SEND_FAILED",
                    "Nie udało się wysłać maila z potwierdzeniem rezerwacji.",
                    e
            );
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
            String mailUsername = CONTACT_EMAIL;
            String htmlContent = "<html><body style='margin:0; background-color:" + EMAIL_BG + "; color:" + EMAIL_TEXT + "; font-family:Inter, Arial, sans-serif; padding:24px;'>"
                    + "<div style='max-width:600px; margin:auto; background-color:" + EMAIL_PANEL + "; border:1px solid " + EMAIL_BORDER + "; border-radius:8px; padding:28px; text-align:center; box-shadow:0 16px 38px rgba(255,0,200,0.18);'>"
                    + "<img src='cid:logo' alt='PlanetaLuzu Logo' style='width:150px; margin-bottom:20px;'/>"
                    + "<p style='margin:0 0 10px; color:" + EMAIL_ACCENT_SECONDARY + "; font-size:12px; font-weight:800; text-transform:uppercase;'>Rezerwacja potwierdzona</p>"
                    + "<h2 style='margin:0 0 18px; color:" + EMAIL_TEXT + "; font-size:28px; line-height:1.1;'>Cześć " + firstName + "</h2>"
                    + "<p style='color:" + EMAIL_MUTED + "; line-height:1.6;'>Twoja opłata rezerwacji słuchawek została potwierdzona. Poniżej znajdziesz kod QR potrzebny do odbioru słuchawek:</p>"
                    + "<p style='color:" + EMAIL_MUTED + ";'><strong style='color:" + EMAIL_TEXT + ";'>Kod rezerwacji:</strong> <span style='color:" + EMAIL_ACCENT + "; font-weight:900;'>" + qrToken + "</span></p>"
                    + "<div style='display:inline-block; margin:20px 0; padding:16px; background-color:" + EMAIL_TEXT + "; border-radius:8px; box-shadow:0 16px 38px rgba(0,0,0,0.28);'>"
                    + "<img src='cid:qrCode' alt='QR Code' style='display:block;'/>"
                    + "</div>"
                    + "<p style='color:" + EMAIL_MUTED + ";'>DOZO! - <strong style='color:" + EMAIL_TEXT + ";'>PlanetaLuzu</strong></p>"
                    + "<hr style='border:0; border-top:1px solid " + EMAIL_BORDER + "; margin:24px 0 16px;'/>"
                    + "<p style='font-size:12px; line-height:1.5; color:" + EMAIL_MUTED + ";'>&copy; 2025 PlanetaLuzu.<br/>"
                    + "<a href='" + WEBSITE_URL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Odwiedź naszą stronę</a> | "
                    + "<a href='mailto:" + mailUsername + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Kontakt</a></p>"
                    + "</div></body></html>";

            helper.setText(htmlContent, true); // true = HTML

            // Dodanie logo inline (logo.png w src/main/resources/static/)
            helper.addInline("logo", new ClassPathResource("static/logo.png"));

            // Dodanie kodu QR inline
            helper.addInline("qrCode", new ByteArrayResource(qrImage), "image/png");

            mailSender.send(message);

        } catch (Exception e) {
            throw new ExternalServiceException(
                    "TICKET_EMAIL_SEND_FAILED",
                    "Nie udało się wysłać maila z biletem.",
                    e
            );
        }
    }

    @Override
    @Async
    public void sendReservationExpirationWarning(
            String email, String firstName, String lastName, String reservationId, LocalDateTime expiresAt) {
        try {
            String formattedExpiresAt = expiresAt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Rezerwacja Planeta Luzu wkrótce przepadnie");

            String htmlContent = "<html><body style='margin:0; background-color:" + EMAIL_BG + "; color:" + EMAIL_TEXT + "; font-family:Inter, Arial, sans-serif; padding:24px;'>"
                    + "<div style='max-width:600px; margin:auto; background-color:" + EMAIL_PANEL + "; border:1px solid " + EMAIL_BORDER + "; border-radius:8px; padding:28px; box-shadow:0 16px 38px rgba(255,0,200,0.18);'>"
                    + "<p style='margin:0 0 10px; color:" + EMAIL_ACCENT_SECONDARY + "; font-size:12px; font-weight:800; text-transform:uppercase;'>Mniej niż 12 godzin</p>"
                    + "<h2 style='margin:0 0 18px; color:" + EMAIL_TEXT + "; font-size:28px; line-height:1.1;'>Cześć " + firstName + "</h2>"
                    + "<p style='color:" + EMAIL_MUTED + "; line-height:1.6;'>Twoja rezerwacja słuchawek o numerze <strong style='color:" + EMAIL_ACCENT + ";'>" + reservationId + "</strong> nadal czeka na potwierdzenie płatności.</p>"
                    + "<p style='color:" + EMAIL_MUTED + "; line-height:1.6;'>Zostało mniej niż 12 godzin. Jeśli płatność nie zostanie potwierdzona do <strong style='color:" + EMAIL_ACCENT_SECONDARY + ";'>" + formattedExpiresAt + "</strong>, rezerwacja przepadnie.</p>"
                    + "<div style='margin:22px 0; padding:18px; background-color:" + EMAIL_PANEL_DARK + "; border:1px solid " + EMAIL_BORDER + "; border-radius:8px;'>"
                    + "<p style='margin:0 0 8px; color:" + EMAIL_MUTED + ";'><strong style='color:" + EMAIL_TEXT + ";'>Revtag:</strong> <a href='" + REVOLUT_PAYMENT_URL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>@planeta_luzu</a></p>"
                    + "<p style='margin:0; color:" + EMAIL_MUTED + ";'><strong style='color:" + EMAIL_TEXT + ";'>Numer konta:</strong> 62291000060000000002224734</p>"
                    + "</div>"
                    + "<p style='color:" + EMAIL_MUTED + ";'>Dzięki! - <strong style='color:" + EMAIL_TEXT + ";'>PlanetaLuzu</strong></p>"
                    + "<hr style='border:0; border-top:1px solid " + EMAIL_BORDER + "; margin:24px 0 16px;'/>"
                    + "<p style='font-size:12px; line-height:1.5; color:" + EMAIL_MUTED + ";'>&copy; 2025 PlanetaLuzu.<br/>"
                    + "<a href='" + WEBSITE_URL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Odwiedź naszą stronę</a> | "
                    + "<a href='mailto:" + CONTACT_EMAIL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Kontakt</a></p>"
                    + "</div></body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new ExternalServiceException(
                    "RESERVATION_EXPIRATION_WARNING_EMAIL_SEND_FAILED",
                    "Nie udało się wysłać maila z przypomnieniem o wygasającej rezerwacji.",
                    e
            );
        }
    }

    @Override
    @Async
    public void sendReservationExpired(String email, String firstName, String lastName, String reservationId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Rezerwacja Planeta Luzu przepadła");

            String htmlContent = "<html><body style='margin:0; background-color:" + EMAIL_BG + "; color:" + EMAIL_TEXT + "; font-family:Inter, Arial, sans-serif; padding:24px;'>"
                    + "<div style='max-width:600px; margin:auto; background-color:" + EMAIL_PANEL + "; border:1px solid " + EMAIL_BORDER + "; border-radius:8px; padding:28px; box-shadow:0 16px 38px rgba(255,0,200,0.18);'>"
                    + "<p style='margin:0 0 10px; color:" + EMAIL_ACCENT_SECONDARY + "; font-size:12px; font-weight:800; text-transform:uppercase;'>Rezerwacja usunięta</p>"
                    + "<h2 style='margin:0 0 18px; color:" + EMAIL_TEXT + "; font-size:28px; line-height:1.1;'>Cześć " + firstName + "</h2>"
                    + "<p style='color:" + EMAIL_MUTED + "; line-height:1.6;'>Twoja rezerwacja słuchawek o numerze <strong style='color:" + EMAIL_ACCENT + ";'>" + reservationId + "</strong> nie została opłacona w wyznaczonym czasie i została usunięta.</p>"
                    + "<p style='color:" + EMAIL_MUTED + "; line-height:1.6;'>Jeśli nadal chcesz zarezerwować słuchawki, wypełnij formularz rejestracyjny ponownie.</p>"
                    + "<p style='color:" + EMAIL_MUTED + ";'>Do zobaczenia! - <strong style='color:" + EMAIL_TEXT + ";'>PlanetaLuzu</strong></p>"
                    + "<hr style='border:0; border-top:1px solid " + EMAIL_BORDER + "; margin:24px 0 16px;'/>"
                    + "<p style='font-size:12px; line-height:1.5; color:" + EMAIL_MUTED + ";'>&copy; 2025 PlanetaLuzu.<br/>"
                    + "<a href='" + WEBSITE_URL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Odwiedź naszą stronę</a> | "
                    + "<a href='mailto:" + CONTACT_EMAIL + "' style='color:" + EMAIL_ACCENT + "; text-decoration:none; font-weight:800;'>Kontakt</a></p>"
                    + "</div></body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new ExternalServiceException(
                    "RESERVATION_EXPIRED_EMAIL_SEND_FAILED",
                    "Nie udało się wysłać maila o usunięciu rezerwacji.",
                    e
            );
        }
    }

    @Recover
    public void recover(RuntimeException e, String email, String qrToken) {
        System.err.println("Nie udało się wysłać maila po 3 próbach: " + email);
    }
}
