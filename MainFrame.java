package gui;

import model.AppState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Main JFrame – hosts the 5-step CardLayout wizard.
 */
public class MainFrame extends JFrame {

    private final AppState            state   = new AppState();
    private int                       current = 0;
    private final StepPanelInterface[] steps;
    private final String[]             keys   = {"s1","s2","s3","s4","s5"};

    private final StepIndicatorPanel indicator;
    private final JPanel             cardHost;
    private final CardLayout         cardLayout;
    private final JButton            btnBack;
    private final JButton            btnNext;

    public MainFrame() {
        setTitle("ISO 15939 Measurement Process Simulator  –  V1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 620);
        setMinimumSize(new Dimension(700, 520));
        setLocationRelativeTo(null);

        Step1Panel s1 = new Step1Panel();
        Step2Panel s2 = new Step2Panel();
        Step3Panel s3 = new Step3Panel();
        Step4Panel s4 = new Step4Panel();
        Step5Panel s5 = new Step5Panel();
        steps = new StepPanelInterface[]{s1, s2, s3, s4, s5};

        indicator  = new StepIndicatorPanel();
        cardLayout = new CardLayout();
        cardHost   = new JPanel(cardLayout);
        cardHost.setBackground(UITheme.BG);
        cardHost.add(s1, keys[0]);
        cardHost.add(s2, keys[1]);
        cardHost.add(s3, keys[2]);
        cardHost.add(s4, keys[3]);
        cardHost.add(s5, keys[4]);

        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Color.WHITE);
        nav.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(1,0,0,0, UITheme.BORDER_COLOR),
                new EmptyBorder(10,24,10,24)));

        btnBack = UITheme.secondaryButton("\u2190  Back");
        btnNext = UITheme.primaryButton("Next  \u2192");

        JPanel lp = new JPanel(new FlowLayout(FlowLayout.LEFT,  0,0)); lp.setBackground(Color.WHITE); lp.add(btnBack);
        JPanel rp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0,0)); rp.setBackground(Color.WHITE); rp.add(btnNext);
        nav.add(lp, BorderLayout.WEST);
        nav.add(rp, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(indicator, BorderLayout.NORTH);
        add(cardHost,  BorderLayout.CENTER);
        add(nav,       BorderLayout.SOUTH);

        btnBack.addActionListener(e -> goBack());
        btnNext.addActionListener(e -> goNext());

        updateNav();
        steps[0].refresh(state);
    }

    private void goNext() {
        String err = steps[current].validateStep();
        if (err != null) {
            JOptionPane.showMessageDialog(this, err, "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        steps[current].collectData(state);
        if (current < steps.length - 1) {
            current++;
            steps[current].refresh(state);
            cardLayout.show(cardHost, keys[current]);
            indicator.setCurrentStep(current);
            updateNav();
        } else {
            int c = JOptionPane.showConfirmDialog(this,
                    "Session complete!\nWould you like to start a new session?",
                    "Finished", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (c == JOptionPane.YES_OPTION) restart();
        }
    }

    private void goBack() {
        if (current > 0) {
            current--;
            steps[current].refresh(state);
            cardLayout.show(cardHost, keys[current]);
            indicator.setCurrentStep(current);
            updateNav();
        }
    }

    private void restart() {
        state.setUsername(null); state.setSchool(null); state.setSessionName(null);
        state.setQualityType(null); state.setMode(null); state.setSelectedScenario(null);
        current = 0;
        steps[0].refresh(state);
        cardLayout.show(cardHost, keys[0]);
        indicator.setCurrentStep(0);
        updateNav();
    }

    private void updateNav() {
        btnBack.setEnabled(current > 0);
        btnBack.setForeground(current > 0 ? UITheme.PRIMARY : UITheme.TEXT_MUTED);
        boolean last = (current == steps.length - 1);
        btnNext.setText(last ? "\u2713  Finish" : "Next  \u2192");
    }
}
