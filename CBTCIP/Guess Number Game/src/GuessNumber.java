import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class GuessNumber {
    private static final int MAX_ATTEMPTS = 10;
    private static final int MAX_ROUNDS = 5;

    private int randomNumber;
    private int numberOfAttempts;
    private int currentRound;
    private int score;
    final Random random;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessNumber::new);
    }

    public GuessNumber() {
        random = new Random();
        initializeGame();
    }

    private void initializeGame() {
        currentRound = 0;
        score = 0;
        startNewRound();
    }

    private void startNewRound() {
        if (currentRound >= MAX_ROUNDS) {
            showMessage("Game Over! Your total score is: " + score);
            return;
        }

        currentRound++;
        randomNumber = random.nextInt(100) + 1;
        numberOfAttempts = 0;

        JFrame frame = new JFrame("Guess the Number");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter your guess (1-100):");
        JTextField textField = new JTextField();
        JButton guessButton = new JButton("Guess");
        JButton newRoundButton = new JButton("New Round");
        newRoundButton.setEnabled(false);

        guessButton.addActionListener(e -> {
            String userInput = textField.getText();
            try {
                int userGuess = Integer.parseInt(userInput);
                numberOfAttempts++;
                if (userGuess < randomNumber) {
                    showMessage("Too low! Try again.");
                } else if (userGuess > randomNumber) {
                    showMessage("Too high! Try again.");
                } else {
                    int points = calculatePoints();
                    score += points;
                    showMessage("Correct! You guessed the number in " + numberOfAttempts + " attempts.\nYou earned " + points + " points.");
                    newRoundButton.setEnabled(true);
                    guessButton.setEnabled(false);
                }

                if (numberOfAttempts >= MAX_ATTEMPTS && !newRoundButton.isEnabled()) {
                    showMessage("You've reached the maximum number of attempts. The number was: " + randomNumber);
                    newRoundButton.setEnabled(true);
                    guessButton.setEnabled(false);
                }

            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid number.");
            }
        });

        newRoundButton.addActionListener(e -> {
            frame.dispose();
            startNewRound();
        });

        JLabel footerLabel = new JLabel("Made by Ritika Dhanotiya");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(footerLabel, BorderLayout.SOUTH);

        JPanel panel = new JPanel();
        panel.setBackground(Color.cyan);
        panel.add(label);
        panel.add(textField);
        textField.setPreferredSize(new Dimension(150, 30));

        panel.add(guessButton);
        panel.add(newRoundButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private int calculatePoints() {
        return (MAX_ATTEMPTS - numberOfAttempts + 1) * 10;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}

