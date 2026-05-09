package gui;

import model.AppState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Step 5 – Analyse
 *
 * V1: Placeholder panel. Full implementation coming in V2.
 * Will include: dimension weighted averages, radar chart, gap analysis.
 */
public class Step5Panel extends JPanel implements StepPanelInterface {

    private final JLabel lblUser = new JLabel();

    public Step5Panel() {
        setBackground(UITheme.BG);
        setLayout(new GridBagLayout());

        JPanel card = UITheme.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(500, 300));

        JLabel title = new JLabel("Analyse Results");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT);
        title.setAlignmentX(LEFT_ALIGNMENT);
        card.add(title);
        card.add(Box.createVerticalStrut(8));

        lblUser.setFont(UITheme.FONT_BODY);
        lblUser.setForeground(UITheme.TEXT_MUTED);
        lblUser.setAlignmentX(LEFT_ALIGNMENT);
        card.add(lblUser);
        card.add(Box.createVerticalStrut(24));

        // Placeholder items showing what will be added
        JPanel placeholder = new JPanel();
        placeholder.setLayout(new BoxLayout(placeholder, BoxLayout.Y_AXIS));
        placeholder.setBackground(new Color(241, 245, 249));
        placeholder.setBorder(new EmptyBorder(16, 16, 16, 16));
        placeholder.setAlignmentX(LEFT_ALIGNMENT);
        placeholder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JLabel coming = new JLabel("Coming in V2:");
        coming.setFont(UITheme.FONT_H3);
        coming.setForeground(UITheme.PRIMARY);
        placeholder.add(coming);
        placeholder.add(Box.createVerticalStrut(10));

        String[] items = {
            "  \u2022  Dimension-based weighted average scores",
            "  \u2022  Score (1\u20135) calculation with ISO 15939 formula",
            "  \u2022  Gap analysis (weakest dimension)",
            "  \u2022  Radar chart (bonus)"
        };
        for (String item : items) {
            JLabel l = new JLabel(item);
            l.setFont(UITheme.FONT_BODY);
            l.setForeground(UITheme.TEXT_MUTED);
            placeholder.add(l);
            placeholder.add(Box.createVerticalStrut(4));
        }

        card.add(placeholder);
        add(card);
    }

    @Override
    public void refresh(AppState state) {
        if (state.getUsername() != null) {
            lblUser.setText("Session: " + state.getSessionName()
                    + "  |  User: " + state.getUsername()
                    + "  |  Scenario: " + (state.getSelectedScenario() != null
                        ? state.getSelectedScenario().getName() : "-"));
        }
    }

    @Override public String validateStep()          { return null; }
    @Override public void   collectData(AppState s) { }
}
