import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MCQExam extends JFrame {
    private JLabel questionLabel, timerLabel;
    private JRadioButton[] options;
    private JButton nextButton, submitButton, logoutButton;
    private ButtonGroup optionGroup;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private final Question[] questions;
    private Timer timer;
    private int timeRemaining = 300; // 5 minutes in seconds

    public MCQExam() {
        setTitle("MCQ Exam");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Timer setup
        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timerLabel, BorderLayout.NORTH);
        startTimer();

        // Questions setup
        questions = new Question[]{
                new Question("What is Java?", new String[]{"A programming language", "A car", "A city", "A fruit"}, 0),
                new Question("Which company developed Java?", new String[]{"Microsoft", "Sun Microsystems", "Google", "Apple"}, 1),
                new Question("What does JVM stand for?", new String[]{"Java Very Machine", "Java Verified Machine", "Java Virtual Machine", "Java Visual Machine"}, 2),
                new Question("Which method is the entry point in a Java program?", new String[]{"start()", "begin()", "main()", "run()"}, 2),
                new Question("What is bytecode in Java?", new String[]{"Machine code", "Code written in bytes", "Intermediate code", "Source code"}, 2),
                new Question("Which keyword is used to inherit a class in Java?", new String[]{"implements", "extends", "inherits", "uses"}, 1),
                new Question("What is the size of int in Java?", new String[]{"4 bytes", "2 bytes", "8 bytes", "1 byte"}, 0),
                new Question("Which of these is not a Java feature?", new String[]{"Object-oriented", "Platform-independent", "Secure", "Pointer"}, 3),
                new Question("Which package contains the Random class?", new String[]{"java.util", "java.io", "java.lang", "java.net"}, 0),
                new Question("What is the default value of a boolean variable in Java?", new String[]{"true", "false", "0", "null"}, 1)
        };

        // Question panel
        JPanel questionPanel = new JPanel(new GridLayout(6, 1));
        questionLabel = new JLabel();
        questionPanel.add(questionLabel);

        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            questionPanel.add(options[i]);
        }

        add(questionPanel, BorderLayout.CENTER);

        // Navigation panel
        JPanel navigationPanel = new JPanel();
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");
        logoutButton = new JButton("Logout");
        navigationPanel.add(nextButton);
        navigationPanel.add(submitButton);
        navigationPanel.add(logoutButton);
        add(navigationPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentQuestionIndex < questions.length) {
                    if (options[questions[currentQuestionIndex].getCorrectAnswerIndex()].isSelected()) {
                        score++;
                    }
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        displayQuestion(currentQuestionIndex);
                    } else {
                        nextButton.setEnabled(false);
                        submitButton.setEnabled(true);
                    }
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitExam();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(MCQExam.this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    dispose(); // Close the MCQ exam frame
                    new LoginPage().setVisible(true); // Reopen the login frame
                }
            }
        });

        submitButton.setEnabled(false);
        displayQuestion(currentQuestionIndex);
    }

    private void displayQuestion(int questionIndex) {
        Question question = questions[questionIndex];
        questionLabel.setText(question.getQuestion());
        String[] answers = question.getAnswers();
        optionGroup.clearSelection(); // Clear previous selection
        for (int i = 0; i < options.length; i++) {
            options[i].setText(answers[i]);
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                timerLabel.setText(String.format("Time remaining: %02d:%02d", minutes, seconds));

                if (timeRemaining <= 0) {
                    timer.stop();
                    submitExam();
                }
            }
        });
        timer.start();
    }

    private void submitExam() {
        JOptionPane.showMessageDialog(this, "Time's up! Your score is: " + score + "/" + questions.length);
        nextButton.setEnabled(false);
        submitButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MCQExam().setVisible(true);
            }
        });
    }
}

class Question {
    private final String question;
    private final String[] answers;
    private final int correctAnswerIndex;

    public Question(String question, String[] answers, int correctAnswerIndex) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}


