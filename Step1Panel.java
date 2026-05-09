package gui;

import model.AppState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Step 1 – Profile
 * Collects username, school, and session name.
 */
public class Step1Panel extends JPanel implements StepPanelInterface {

    private final JTextField tfUsername;
    private final JTextField tfSchool;
    private final JTextField tfSession;

    public Step1Panel() {
        setBackground(UITheme.BG);
        setLayout(new GridBagLayout());

        JPanel card = UITheme.cardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(460, 340));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets  = new Insets(6, 4, 6, 4);
        gc.fill    = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel title = new JLabel("User Profile");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT);
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 2;
        gc.insets = new Insets(0, 4, 4, 4);
        card.add(title, gc);

        JLabel sub = UITheme.mutedLabel("Enter your information to begin the session.");
        gc.gridy = 1;
        gc.insets = new Insets(0, 4, 20, 4);
        card.add(sub, gc);

        gc.gridwidth = 1;
        gc.insets    = new Insets(6, 4, 2, 4);

        gc.gridx = 0; gc.gridy = 2; gc.weightx = 0.35;
        card.add(label("Username *"), gc);
        tfUsername = UITheme.styledField(18);
        gc.gridx = 1; gc.weightx = 0.65;
        card.add(tfUsername, gc);

        gc.gridx = 0; gc.gridy = 3; gc.weightx = 0.35;
        card.add(label("School *"), gc);
        tfSchool = UITheme.styledField(18);
        gc.gridx = 1; gc.weightx = 0.65;
        card.add(tfSchool, gc);

        gc.gridx = 0; gc.gridy = 4; gc.weightx = 0.35;
        card.add(label("Session Name *"), gc);
        tfSession = UITheme.styledField(18);
        gc.gridx = 1; gc.weightx = 0.65;
        card.add(tfSession, gc);

        JLabel hint = UITheme.mutedLabel("* All fields are required.");
        gc.gridx = 0; gc.gridy = 5; gc.gridwidth = 2;
        gc.insets = new Insets(16, 4, 0, 4);
        card.add(hint, gc);

        add(card);
    }

    private static JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(UITheme.FONT_BODY);
        l.setForeground(UITheme.TEXT);
        return l;
    }

    @Override
    public void refresh(AppState s) {
        if (s.getUsername()    != null) tfUsername.setText(s.getUsername());
        if (s.getSchool()      != null) tfSchool.setText(s.getSchool());
        if (s.getSessionName() != null) tfSession.setText(s.getSessionName());
    }

    @Override
    public String validateStep() {
        if (tfUsername.getText().trim().isEmpty())
            return "Please enter your username to continue.";
        if (tfSchool.getText().trim().isEmpty())
            return "Please enter your school name to continue.";
        if (tfSession.getText().trim().isEmpty())
            return "Please enter a session name to continue.";
        return null;
    }

    @Override
    public void collectData(AppState s) {
        s.setUsername(tfUsername.getText().trim());
        s.setSchool(tfSchool.getText().trim());
        s.setSessionName(tfSession.getText().trim());
    }
}
