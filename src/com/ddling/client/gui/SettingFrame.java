/*
 * Copyright (C) 2014 lingdongdong
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ddling.client.gui;

import com.ddling.client.utils.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lingdongdong on 14/12/15.
 */
public class SettingFrame {

    private JFrame settingFrame;

    private JLabel backgroundJL;

    private JTextField addressJTF;
    private JTextField portJTF;

    private JButton saveJB;

    public SettingFrame() {
        initSettingFrame();
    }

    private void initSettingFrame() {
        settingFrame = new JFrame();
        Container container = settingFrame.getContentPane();

        // 背景图片
        backgroundJL = new JLabel();
        ImageIcon backgroundImgIcon = new ImageIcon("images/background.jpg");
        backgroundJL.setIcon(backgroundImgIcon);
        backgroundJL.setBounds(0, 0, backgroundImgIcon.getIconWidth(), backgroundImgIcon.getIconHeight());

        // Title
        Font font = new Font("Serif", Font.PLAIN, 30);
        JLabel titleTL = new JLabel();
        titleTL.setText("JavaMail 配置");
        titleTL.setBounds(60, 60, 250, 40);
        titleTL.setFont(font);

        // 服务器地址
        JLabel addressJL = new JLabel();
        addressJL.setBounds(60, 150, 80, 40);
        addressJL.setText("服务器地址：");

        addressJTF = new JTextField(Constants.LOCAL_SMTP_SERVER_ADDRESS);
        addressJTF.setBounds(140, 150, 220, 40);

        // 服务器端口
        JLabel portJL = new JLabel();
        portJL.setBounds(60, 200, 80, 40);
        portJL.setText("端口：");

        portJTF = new JTextField("" + Constants.LOCAL_SMTP_SERVER_PORT);
        portJTF.setBounds(140, 200, 220, 40);

        // 保存按钮
        saveJB = new JButton();
        saveJB.setText("保存");
        saveJB.setBounds(140, 250, 220, 40);

        backgroundJL.add(titleTL);
        backgroundJL.add(addressJTF);
        backgroundJL.add(addressJL);
        backgroundJL.add(portJL);
        backgroundJL.add(portJTF);
        backgroundJL.add(saveJB);

        container.add(backgroundJL);

        settingFrame.setBounds(0, 0, backgroundImgIcon.getIconWidth(), backgroundImgIcon.getIconHeight());
        settingFrame.pack();
        settingFrame.setLocation(200, 300);
        settingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingFrame.setVisible(true);
    }
}
