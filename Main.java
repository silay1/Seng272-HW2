import gui.MainFrame;
import javax.swing.*;

/**
 * Entry point – ISO 15939 Measurement Process Simulator
 * Implementation V1
 *
 * Compile: javac -d out -sourcepath src src/Main.java
 * Run:     java -cp out Main
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
