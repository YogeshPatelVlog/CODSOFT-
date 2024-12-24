import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String questionText;
    String[] options;
    int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

public class QuizApp {
    private static List<Question> questions = new ArrayList<>();
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static final int TIME_LIMIT = 10; // Time limit for each question in seconds
    private static List<Boolean> answers = new ArrayList<>(); // To track correct/incorrect answers

    public static void main(String[] args) {
        // Initialize quiz questions
        initializeQuestions();

        // Start the quiz
        startQuiz();
    }

    private static void initializeQuestions() {
        questions.add(new Question("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 2));
        questions.add(new Question("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Saturn"}, 1));
        questions.add(new Question("What is the largest ocean on Earth?", new String[]{"1. Atlantic Ocean", "2. Indian Ocean", "3. Arctic Ocean", "4. Pacific Ocean"}, 3));
        questions.add(new Question("Who wrote 'Hamlet'?", new String[]{"1. Charles Dickens", "2. Mark Twain", "3. William Shakespeare", "4. Leo Tolstoy"}, 2));
        questions.add(new Question("What is the chemical symbol for water?", new String[]{"1. H2O", "2. O2", "3. CO2", "4. NaCl"}, 0));
    }

    private static void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        for (currentQuestionIndex = 0; currentQuestionIndex < questions.size(); currentQuestionIndex++) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            System.out.println("Question " + (currentQuestionIndex + 1) + ": " + currentQuestion.questionText);
            for (String option : currentQuestion.options) {
                System.out.println(option);
            }

            // Start timer for the question
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! Moving to the next question.");
                    // Automatically move to the next question if time runs out
                    answers.add(false); // Mark as incorrect if time runs out
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        startQuiz();
                    } else {
                        displayResult();
                    }
                }
            }, TIME_LIMIT * 1000); // Convert seconds to milliseconds

            // Get user answer
            System.out.print("Your answer (1-4): ");
            int userAnswer = scanner.nextInt() - 1; // Convert to 0-based index

            // Cancel the timer if the user answers in time
            timer.cancel();

            // Check if the answer is correct
            if (userAnswer == currentQuestion.correctAnswerIndex) {
                score++;
                answers.add(true); // Mark as correct
            } else {
                answers.add(false); // Mark as incorrect
            }
        }

        // Display the result after all questions
        displayResult();
    }

    private static void displayResult() {
        System.out.println("\nQuiz Finished!");
        System.out.println("Your score: " + score + "/" + questions.size());
        System.out.println("Summary of your answers:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ": " + (answers.get(i) ? "Correct" : "Incorrect"));
        }
        System.out.println("Thank you for participating!");
    }
}