package com.ddling.client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lingdongdong on 15/1/5.
 */
public class ListCellRenderer extends JLabel implements javax.swing.ListCellRenderer {

    public ListCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        if (value != null) {
            setText(value.toString());
            setIcon(new ImageIcon("images/notRead.png"));
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}
