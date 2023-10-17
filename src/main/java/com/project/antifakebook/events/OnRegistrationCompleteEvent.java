package com.project.antifakebook.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
@SuppressWarnings("serial")
    public class OnRegistrationCompleteEvent extends ApplicationEvent {

        private final String appUrl;
        private final Locale locale;
        private final Long userId;



    public OnRegistrationCompleteEvent(final Long userId, final Locale locale, final String appUrl) {
            super(userId);
            this.userId = userId;
            this.locale = locale;
            this.appUrl = appUrl;
        }
}


