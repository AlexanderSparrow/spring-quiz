package ru.yandex.practicum.quiz.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter @Getter @ToString
@ConfigurationProperties("spring-quiz")
public class AppConfig {

    private String title;
    private ReportSettings report;

    @Setter @Getter @ToString
    public static class ReportSettings {
        private boolean enabled;
        private ReportMode mode;
        private ReportOutputSettings output;
    }

    @Setter @Getter @ToString
    public static class ReportOutputSettings {
        private ReportOutputMode mode;
        private String path;
    }

    public enum ReportMode {
        VERBOSE, CONCISE;
    }

    public enum ReportOutputMode {
        CONSOLE, FILE;
    }
}