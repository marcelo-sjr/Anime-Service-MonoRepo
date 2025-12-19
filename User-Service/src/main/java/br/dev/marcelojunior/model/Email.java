package br.dev.marcelojunior.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Locale;
import java.util.regex.Pattern;

@Embeddable
@Getter
public class Email {

    @Column(nullable = false, unique = true,name = "user_email")
    private String email;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+([._%+-][A-Za-z0-9]+)*@([A-Za-z0-9]+(-[A-Za-z0-9]+)*\\.)+[A-Za-z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    protected Email(){}

    public Email(String email){
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("invalid email address: " + email);
        }
        this.email = email.toLowerCase(Locale.ROOT);
    }

    private boolean isEmailValid(String email){
        return PATTERN.matcher(email).matches();
    }
}