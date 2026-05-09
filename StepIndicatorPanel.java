package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a step breadcrumb at the top of the wizard.
 * Active step is highlighted; completed steps show a check mark.
 */
public class StepIndicatorPanel extends JPanel {

    private static final String[] LABELS = {"Profile","Define","Plan","Collect","Analyse"};
    private int currentStep = 0;

    public StepIndicatorPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));
        setPreferredSize(new Dimension(800, 72));
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w      = getWidth();
        int cx     = w / 2;
        int startX = cx - (LABELS.length * 120) / 2;
        int r      = 16;
        int cy     = getHeight() / 2 - 8;
        int labelY = cy + r + 14;

        for (int i = 0; i < LABELS.length; i++) {
            int x = startX + i * 120 + 60;

            if (i > 0) {
                int prevX = startX + (i - 1) * 120 + 60;
                g2.setColor(i <= currentStep ? UITheme.PRIMARY : UITheme.BORDER_COLOR);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(prevX + r, cy + r, x - r, cy + r);
            }

            Color fill = (i < currentStep) ? UITheme.SUCCESS
                       : (i == currentStep) ? UITheme.PRIMARY
                       : UITheme.BORDER_COLOR;
            g2.setColor(fill);
            g2.fillOval(x - r, cy, r * 2, r * 2);

            g2.setColor(Color.WHITE);
            if (i < currentStep) {
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String ck = "\u2714";
                g2.drawString(ck, x - fm.stringWidth(ck) / 2, cy + r + fm.getAscent() / 2 - 1);
            } else {
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                String num = String.valueOf(i + 1);
                g2.drawString(num, x - fm.stringWidth(num) / 2, cy + r + fm.getAscent() / 2 - 1);
            }

            Font lf = (i == currentStep)
                    ? new Font("Segoe UI", Font.BOLD, 11)
                    : new Font("Segoe UI", Font.PLAIN, 11);
            g2.setFont(lf);
            g2.setColor(i == currentStep ? UITheme.PRIMARY
                    : i < currentStep ? UITheme.SUCCESS
                    : UITheme.TEXT_MUTED);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(LABELS[i], x - fm.stringWidth(LABELS[i]) / 2, labelY);
        }
        g2.dispose();
    }
}
