package gui;

import model.AppState;
import model.Metric;
import model.Scenario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Step 4 – Collect Data
 *
 * V1: Displays metric names, directions, ranges, and raw values.
 * Score calculation is NOT yet implemented (coming in V2).
 */
public class Step4Panel extends JPanel implements StepPanelInterface {

    private final JLabel      lblScenario = new JLabel();
    private final JPanel      contentArea = new JPanel();
    private final JScrollPane scrollPane;

    public Step4Panel() {
        setBackground(UITheme.BG);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel title = new JLabel("Collect Data");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT);
        header.add(title, BorderLayout.NORTH);

        lblScenario.setFont(UITheme.FONT_BODY);
        lblScenario.setForeground(UITheme.TEXT_MUTED);
        header.add(lblScenario, BorderLayout.CENTER);

        // TODO V2: Score calculation will be added here
        JLabel todo = UITheme.mutedLabel("Note: Score calculation will be implemented in the next version.");
        header.add(todo, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        contentArea.setBackground(UITheme.BG);
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBorder(new EmptyBorder(12, 24, 24, 24));

        scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void buildContent(Scenario scenario) {
        contentArea.removeAll();

        for (model.Dimension dim : scenario.getDimensions()) {
            JPanel dimRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            dimRow.setBackground(UITheme.BG);
            dimRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            dimRow.setAlignmentX(LEFT_ALIGNMENT);
            JLabel dimLbl = new JLabel(dim.getName() + "   (Coefficient: " + dim.getCoefficient() + ")");
            dimLbl.setFont(UITheme.FONT_H2);
            dimLbl.setForeground(UITheme.PRIMARY);
            dimRow.add(dimLbl);
            contentArea.add(dimRow);
            contentArea.add(Box.createVerticalStrut(6));

            // V1: No score column yet
            String[] cols = {"Metric", "Direction", "Range", "Value", "Unit"};
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };

            for (Metric m : dim.getMetrics()) {
                String valStr = (m.getValue() == Math.floor(m.getValue()))
                        ? String.valueOf((int) m.getValue())
                        : String.format("%.1f", m.getValue());

                model.addRow(new Object[]{
                        m.getName(),
                        m.getDirectionLabel(),
                        m.getRangeLabel(),
                        valStr,
                        m.getUnit()
                });
            }

            JTable table = new JTable(model);
            UITheme.styleTable(table);

            int[] widths = {200, 110, 100, 80, 100};
            for (int i = 0; i < widths.length; i++)
                table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

            JScrollPane sp = new JScrollPane(table);
            sp.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));
            int h = table.getRowHeight() * (dim.getMetrics().size() + 1) + 4;
            sp.setPreferredSize(new Dimension(640, h));
            sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, h));
            sp.setAlignmentX(LEFT_ALIGNMENT);
            contentArea.add(sp);
            contentArea.add(Box.createVerticalStrut(18));
        }

        contentArea.revalidate();
        contentArea.repaint();
        scrollPane.getVerticalScrollBar().setValue(0);
    }

    @Override
    public void refresh(AppState state) {
        Scenario sc = state.getSelectedScenario();
        if (sc != null) {
            lblScenario.setText("Scenario: " + sc.getName());
            buildContent(sc);
        }
    }

    @Override public String validateStep()          { return null; }
    @Override public void   collectData(AppState s) { }
}
