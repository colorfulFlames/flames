package com.severalcircles.flames.ux.embeds.errors;

import com.severalcircles.flames.system.Flames;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class ErrorMessage {
    private  ErrorLevel level = ErrorLevel.STOP;
    private ErrorType type = ErrorType.UNSPECIFIED;
    private String message;
    private ResourceBundle resourceBundle;

    private String author;
    private String title;
    private String description;

    public ErrorMessage(ErrorLevel level, ErrorType type, String message, Locale locale) {
        this.level = level;
        this.type = type;
        this.message = message;
        this.resourceBundle = ResourceBundle.getBundle("ErrorMessage", locale);
    }
    public discord4j.core.spec.EmbedCreateSpec get() {
        switch (level) {
            case WARNING: author = resourceBundle.getString("warning"); break;
            case DANGER_STOP: author = resourceBundle.getString("dangerStop"); break;
            case DANGER_WARNING: author = resourceBundle.getString("dangerWarning"); break;
            case STOP:
            default: author = resourceBundle.getString("stop"); break;
        }
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
                .footer(Flames.commonResources.getString("flames"), Flames.gatewayDiscordClient.getSelf().block().getAvatarUrl())
                .timestamp(Instant.now())
                .build();
    }
}
