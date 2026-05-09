package gui;

import model.AppState;
import model.Metric;
import model.Scenario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Step 3 – Plan Measurement (read-only)
 * Displays dimensions and metrics of the selected scenario.
 */
public class Step3Panel extends JPanel implements StepPanelInterface {

    private final JLabel      lblScenario = new JLabel();
    private final JPanel      contentArea = new JPanel();
    private final JScrollPane scrollPane;

    public Step3Panel() {
        setBackground(UITheme.BG);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));
        JLabel title = new JLabel("Plan Measurement");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT);
        header.add(title, BorderLayout.NORTH);
        lblScenario.setFont(UITheme.FONT_BODY);
        lblScenario.setForeground(UITheme.TEXT_MUTED);
        header.add(lblScenario, BorderLayout.CENTER);
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

            String[] cols = {"Metric", "Coefficient", "Direction", "Range", "Unit"};
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };

            for (Metric m : dim.getMetrics()) {
                model.addRow(new Object[]{
                        m.getName(),
                        String.valueOf(m.getCoefficient()),
                        m.getDirectionLabel(),
                        m.getRangeLabel(),
                        m.getUnit()
                });
            }

            JTable table = new JTable(model);
            UITheme.styleTable(table);
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(
                        JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                    super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                    if (!sel) setBackground(row % 2 == 0 ? Color.WHITE : UITheme.ROW_ALT);
                    setBorder(new EmptyBorder(0, 8, 0, 8));
                    return this;
                }
            });

            int[] widths = {200, 100, 120, 100, 100};
            for (int i = 0; i < widths.length; i++)
                table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

            JScrollPane sp = new JScrollPane(table);
            sp.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));
            int h = table.getRowHeight() * (dim.getMetrics().size() + 1) + 4;
            sp.setPreferredSize(new Dimension(620, h));
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
            lblScenario.setText("Scenario: " + sc.getName() + "  |  Type: " + sc.getQualityType() + "  |  Mode: " + sc.getMode());
            buildContent(sc);
        }
    }

    @Override public String validateStep()          { return null; }
    @Override public void   collectData(AppState s) { }
}
