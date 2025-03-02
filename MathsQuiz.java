package com.mathsquiz;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MathsQuiz extends Application {
    private int num1, num2, correctAnswer, score = 0, questionCount = 0;
    private Label questionLabel, scoreLabel;
    private TextField answerField;
    private Button submitButton, nextButton;
    private final Random random = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        questionLabel = new Label();
        scoreLabel = new Label("Score: 0");
        answerField = new TextField();
        submitButton = new Button("Submit");
        nextButton = new Button("Next");

        generateQuestion();

        submitButton.setOnAction(e -> checkAnswer());
        nextButton.setOnAction(e -> generateQuestion());

        VBox root = new VBox(10, questionLabel, answerField, submitButton, nextButton, scoreLabel);
        root.setStyle("-fx-padding: 20px; -fx-font-size: 16px;");

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.setTitle("Math Quiz");
        primaryStage.show();
    }

    private void generateQuestion() {
        if (questionCount >= 10) {
            showFinalResult();
            return;
        }

        num1 = random.nextInt(10) + 1;
        num2 = random.nextInt(10) + 1;
        int operation = random.nextInt(3);  // 0: +, 1: -, 2: *

        switch (operation) {
            case 0:
                correctAnswer = num1 + num2;
                questionLabel.setText(num1 + " + " + num2 + " = ?");
                break;
            case 1:
                correctAnswer = num1 - num2;
                questionLabel.setText(num1 + " - " + num2 + " = ?");
                break;
            case 2:
                correctAnswer = num1 * num2;
                questionLabel.setText(num1 + " * " + num2 + " = ?");
                break;
        }
        answerField.clear();
        questionCount++;
    }

    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerField.getText());
            if (userAnswer == correctAnswer) {
                score++;
                showSnackbar("Correct!", Alert.AlertType.INFORMATION);
            } else {
                showSnackbar("Wrong! The correct answer was " + correctAnswer, Alert.AlertType.ERROR);
            }
            scoreLabel.setText("Score: " + score + "/" + questionCount);
            nextButton.fire();  // Auto-trigger next question
        } catch (NumberFormatException e) {
            showSnackbar("Enter a valid number!", Alert.AlertType.WARNING);
        }
    }

    private void showSnackbar(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    private void showFinalResult() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Over");
        alert.setHeaderText("Your Final Score");
        alert.setContentText("You scored " + score + " out of 10!");
        alert.showAndWait();

        // Reset game
        score = 0;
        questionCount = 0;
        scoreLabel.setText("Score: 0");
        generateQuestion();
    }
}
