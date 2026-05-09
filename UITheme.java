package gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Centralised colours, fonts, and factory helpers.
 */
public class UITheme {

    public static final Color PRIMARY      = new Color(63,  94, 251);
    public static final Color PRIMARY_LIGHT= new Color(219,227,255);
    public static final Color SUCCESS      = new Color(16, 185,129);
    public static final Color WARNING      = new Color(245,158, 11);
    public static final Color DANGER       = new Color(239, 68, 68);
    public static final Color BG           = new Color(248,250,252);
    public static final Color CARD         = Color.WHITE;
    public static final Color TEXT         = new Color( 15, 23, 42);
    public static final Color TEXT_MUTED   = new Color(100,116,139);
    public static final Color BORDER_COLOR = new Color(226,232,240);
    public static final Color TABLE_HEADER = new Color(241,245,249);
    public static final Color ROW_ALT      = new Color(248,250,252);

    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONT_H2     = new Font("Segoe UI", Font.BOLD,  16);
    public static final Font FONT_H3     = new Font("Segoe UI", Font.BOLD,  14);
    public static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD,  13);

    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setPreferredSize(new java.awt.Dimension(130, 38));
        return btn;
    }

    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(CARD);
        btn.setForeground(PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(PRIMARY, 1, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setPreferredSize(new java.awt.Dimension(130, 38));
        return btn;
    }

    public static JTextField styledField(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setFont(FONT_BODY);
        tf.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        return tf;
    }

    public static JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setBackground(CARD);
        p.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(16, 16, 16, 16)));
        return p;
    }

    public static JLabel mutedLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SMALL);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(32);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER_COLOR);
        table.setBackground(CARD);
        table.setSelectionBackground(PRIMARY_LIGHT);
        table.setSelectionForeground(TEXT);
        table.getTableHeader().setFont(FONT_H3);
        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setBorder(new LineBorder(BORDER_COLOR));
    }
}
