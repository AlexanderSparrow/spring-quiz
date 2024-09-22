package ru.yandex.practicum.quiz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.quiz.config.QuizConfig;
import ru.yandex.practicum.quiz.model.Question;
import ru.yandex.practicum.quiz.model.QuizLog;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private final Scanner input;
    private final QuizLog quizLogger;
    private final List<Question> questions;

    public ConsoleUI(@Value("${spring-quiz.title:\"Неназванный тест\"}") String title,
                     QuizConfig quizConfig) {
        this.questions = quizConfig.getQuestions();
        this.input = new Scanner(System.in);
        this.quizLogger = new QuizLog(questions.size());
    }
    public QuizLog startQuiz() {
        System.out.println("\nЗдравствуйте, приступаем к тесту \"Тест по Spring Framework\"!\n");

        for (int questionIdx = 0; questionIdx < questions.size(); questionIdx++) {
            Question question = questions.get(questionIdx);
            processQuestion(questionIdx+1, question);
        }
        System.out.println("\n");
        return quizLogger;
    }
    private void processQuestion(int questionNumber, Question question) {

        for(int attemptIdx = 0; attemptIdx < question.getAttempts(); attemptIdx++) {
            System.out.println("\n");
            askQuestion(questionNumber, question, attemptIdx);

            int answerNumber = getAnswer(questionNumber, question);

            if (question.getCorrectAnswerNumber() == answerNumber) {
                break;
            } else {
                if(attemptIdx+1 < question.getAttempts()) {
                    System.out.println("К сожалению ваш ответ неверный, но вы можете попробовать еще раз");
                }
            }
        }
    }

    private void askQuestion(int questionNumber, Question question, int attemptIdx) {
        System.out.printf("Вопрос %d (попытка: %d/%d): %s\n",
                questionNumber, attemptIdx + 1, question.getAttempts(), question.getText());

        for (int optionIdx = 0; optionIdx < question.getOptions().size(); optionIdx++) {
            String option = question.getOptions().get(optionIdx);
            System.out.printf("%d.\t%s\n", optionIdx + 1, option);
        }
    }

    private int getAnswer(int questionNumber, Question question) {
        System.out.print("\nВведите номер правильного ответа или 0 для выхода: ");

        int answerNumber = input.nextInt();

        if (answerNumber == 0) {
            System.exit(0);
        }

        quizLogger.logAnswer(questionNumber, question, answerNumber);
        return answerNumber;
    }
}
