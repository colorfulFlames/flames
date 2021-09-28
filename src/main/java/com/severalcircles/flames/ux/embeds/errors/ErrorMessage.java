package com.severalcircles.flames.ux.embeds.errors;

import com.severalcircles.flames.system.Flames;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class ErrorMessage {
    private  ErrorLevel level = ErrorLevel.STOP;
    private ErrorType type = ErrorType.UNSPECIFIED;
    private final ResourceBundle resourceBundle;

    @SuppressWarnings("unused")
    public ErrorMessage(ErrorLevel level, ErrorType type, String message, Locale locale) {
        this.level = level;
        this.type = type;
        this.resourceBundle = ResourceBundle.getBundle("ErrorMessage", locale);
    }
    @SuppressWarnings("unused")
    public discord4j.core.spec.EmbedCreateSpec get() {
        String author;
        switch (level) {
            case WARNING: author = resourceBundle.getString("warning"); break;
            case DANGER_STOP: author = resourceBundle.getString("dangerStop"); break;
            case DANGER_WARNING: author = resourceBundle.getString("dangerWarning"); break;
            case STOP:
            default: author = resourceBundle.getString("stop"); break;
        }
        String title;
        switch (type) {
            case USER: title = resourceBundle.getString("titleUser"); break;
            case DATABASE: title = resourceBundle.getString("titleDatabase"); break;
            case DISCORD: title = resourceBundle.getString("titleDiscord"); break;
            case UNSPECIFIED:
            default: title = resourceBundle.getString("titleUnspecified"); break;
        }
        return EmbedCreateSpec.builder()
                .author(EmbedCreateFields.Author.of(author, null, null))
                .title(title)
                .footer(Flames.commonResources.getString("flames"), Objects.requireNonNull(Flames.gatewayDiscordClient.getSelf().block()).getAvatarUrl())
                .timestamp(Instant.now())
                .build();
    }
}
