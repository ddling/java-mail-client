package com.ddling.client.gui;

import javax.swing.*;

/**
 * Created by lingdongdong on 15/1/5.
 */
public class ListModel extends AbstractListModel {

    String listItems[] = {};

    @Override
    public int getSize() {
        return listItems.length;
    }

    @Override
    public Object getElementAt(int index) {
        return listItems[index];
    }
}
