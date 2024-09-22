package ru.yandex.practicum.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.yandex.practicum.quiz.model.QuizLog;
import ru.yandex.practicum.quiz.service.ConsoleUI;
import ru.yandex.practicum.quiz.service.ReportGenerator;

@ConfigurationPropertiesScan
@SpringBootApplication
@RequiredArgsConstructor
public class QuizApp implements CommandLineRunner {
    private final ConsoleUI ui;
    private final ReportGenerator reportGenerator;

    public static void main(String[] args) {
        SpringApplication.run(QuizApp.class, args);
    }

    @Override
    public void run(String... args) {
        QuizLog log = ui.startQuiz();
        reportGenerator.generate(log);
    }
}