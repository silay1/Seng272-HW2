package gui;

import model.AppState;
import model.Scenario;
import model.ScenarioRepository;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;

/**
 * Step 2 – Define Quality Dimensions
 * Three cascading single-selection groups: quality type, mode, scenario.
 */
public class Step2Panel extends JPanel implements StepPanelInterface {

    private final ButtonGroup  bgQuality   = new ButtonGroup();
    private final JRadioButton rbProduct   = radio("Product Quality");
    private final JRadioButton rbProcess   = radio("Process Quality");

    private final ButtonGroup  bgMode      = new ButtonGroup();
    private final JRadioButton rbHealth    = radio("Health");
    private final JRadioButton rbEducation = radio("Education");

    private final ButtonGroup bgScenario  = new ButtonGroup();
    private final JPanel      scenarioInner;

    public Step2Panel() {
        setBackground(UITheme.BG);
        setLayout(new GridBagLayout());

        JPanel card = UITheme.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(560, 460));

        JLabel title = new JLabel("Define Quality Dimensions");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT);
        title.setAlignmentX(LEFT_ALIGNMENT);
        card.add(title);

        JLabel sub = UITheme.mutedLabel("Select quality type, mode, and scenario.");
        sub.setAlignmentX(LEFT_ALIGNMENT);
        card.add(sub);
        card.add(Box.createVerticalStrut(16));

        bgQuality.add(rbProduct);
        bgQuality.add(rbProcess);
        card.add(groupPanel("2a \u2014 Quality Type", new JComponent[]{rbProduct, rbProcess}));
        card.add(Box.createVerticalStrut(10));

        bgMode.add(rbHealth);
        bgMode.add(rbEducation);
        card.add(groupPanel("2b \u2014 Mode", new JComponent[]{rbHealth, rbEducation}));
        card.add(Box.createVerticalStrut(10));

        scenarioInner = new JPanel();
        scenarioInner.setLayout(new BoxLayout(scenarioInner, BoxLayout.Y_AXIS));
        scenarioInner.setBackground(UITheme.CARD);
        card.add(groupPanelDynamic("2c \u2014 Scenario", scenarioInner));

        add(card);

        ActionListener update = e -> rebuildScenarios();
        rbProduct.addActionListener(update);
        rbProcess.addActionListener(update);
        rbHealth.addActionListener(update);
        rbEducation.addActionListener(update);

        rbProduct.setSelected(true);
        rbHealth.setSelected(true);
        rebuildScenarios();
    }

    private void rebuildScenarios() {
        String qt   = rbProduct.isSelected() ? "Product" : "Process";
        String mode = rbHealth.isSelected()  ? "Health"  : "Education";
        List<Scenario> list = ScenarioRepository.getScenariosFor(qt, mode);

        scenarioInner.removeAll();
        Enumeration<AbstractButton> e = bgScenario.getElements();
        List<AbstractButton> old = new ArrayList<>();
        while (e.hasMoreElements()) old.add(e.nextElement());
        for (AbstractButton ab : old) bgScenario.remove(ab);

        if (list.isEmpty()) {
            JLabel none = UITheme.mutedLabel("No scenarios available yet.");
            none.setAlignmentX(LEFT_ALIGNMENT);
            scenarioInner.add(none);
        } else {
            for (Scenario sc : list) {
                JRadioButton rb = radio(sc.getName());
                rb.putClientProperty("scenario", sc);
                bgScenario.add(rb);
                rb.setAlignmentX(LEFT_ALIGNMENT);
                scenarioInner.add(rb);
                scenarioInner.add(Box.createVerticalStrut(4));
            }
        }
        scenarioInner.revalidate();
        scenarioInner.repaint();
    }

    private Scenario getSelectedScenario() {
        Enumeration<AbstractButton> e = bgScenario.getElements();
        while (e.hasMoreElements()) {
            AbstractButton ab = e.nextElement();
            if (ab.isSelected())
                return (Scenario) ((JRadioButton) ab).getClientProperty("scenario");
        }
        return null;
    }

    private static JRadioButton radio(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setFont(UITheme.FONT_BODY);
        rb.setBackground(UITheme.CARD);
        rb.setForeground(UITheme.TEXT);
        rb.setFocusPainted(false);
        return rb;
    }

    private static JPanel groupPanel(String title, JComponent[] buttons) {
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        for (JComponent b : buttons) {
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            inner.add(b);
            inner.add(Box.createVerticalStrut(2));
        }
        return groupPanelDynamic(title, inner);
    }

    private static JPanel groupPanelDynamic(String title, JPanel content) {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.CARD);
        outer.setAlignmentX(LEFT_ALIGNMENT);
        outer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        TitledBorder tb = BorderFactory.createTitledBorder(
                new LineBorder(UITheme.BORDER_COLOR, 1, true), title,
                TitledBorder.LEFT, TitledBorder.TOP, UITheme.FONT_H3, UITheme.PRIMARY);
        outer.setBorder(new CompoundBorder(tb, new EmptyBorder(4, 8, 8, 8)));
        outer.add(content, BorderLayout.CENTER);
        return outer;
    }

    @Override
    public void refresh(AppState state) {
        if ("Process".equals(state.getQualityType())) rbProcess.setSelected(true);
        else                                           rbProduct.setSelected(true);
        if ("Education".equals(state.getMode()))       rbEducation.setSelected(true);
        else                                           rbHealth.setSelected(true);
        rebuildScenarios();
    }

    @Override
    public String validateStep() {
        if (!rbProduct.isSelected() && !rbProcess.isSelected())
            return "Please select a quality type to continue.";
        if (!rbHealth.isSelected() && !rbEducation.isSelected())
            return "Please select a mode to continue.";
        if (getSelectedScenario() == null)
            return "Please select a scenario to continue.";
        return null;
    }

    @Override
    public void collectData(AppState state) {
        state.setQualityType(rbProduct.isSelected() ? "Product" : "Process");
        state.setMode(rbHealth.isSelected() ? "Health" : "Education");
        state.setSelectedScenario(getSelectedScenario());
    }
}
