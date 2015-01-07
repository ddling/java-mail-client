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

import com.ddling.client.usermanage.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lingdongdong on 14/12/15.
 */
public class LoginFrame {

    private JFrame loginFrame;

    private JLabel backgroundJL;

    private JTextField usernameJTF;
    private JPasswordField passwordJTF;

    private JButton registerJB;
    private JButton loginJB;

    public LoginFrame() {
        initLoginFrame();
    }

    private void initLoginFrame() {
        loginFrame = new JFrame();
        final Container container = loginFrame.getContentPane();

        // 背景图片
        backgroundJL = new JLabel();
        ImageIcon backgroundImgIcon = new ImageIcon("images/background.jpg");
        backgroundJL.setIcon(backgroundImgIcon);
        backgroundJL.setBounds(0, 0, backgroundImgIcon.getIconWidth(), backgroundImgIcon.getIconHeight());

        // Title
        Font font = new Font("Serif", Font.PLAIN, 30);
        JLabel titleTL = new JLabel();
        titleTL.setText("JavaMail");
        titleTL.setBounds(60, 60, 150, 40);
        titleTL.setFont(font);

        // 用户账号
        JLabel usernameJL = new JLabel();
        usernameJL.setBounds(60, 150, 80, 40);
        usernameJL.setText("用户账号：");

        usernameJTF = new JTextField();
        usernameJTF.setBounds(140, 150, 220, 40);

        // 用户密码
        JLabel passwordJL = new JLabel();
        passwordJL.setBounds(60, 200, 80, 40);
        passwordJL.setText("密码：");

        passwordJTF = new JPasswordField();
        passwordJTF.setBounds(140, 200, 220, 40);

        // 注册按钮
        registerJB = new JButton();
        registerJB.setText("注册");
        registerJB.setBounds(142, 250, 80, 40);
        registerJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();
                loginFrame.dispose();
            }
        });

        // 登陆按钮
        loginJB = new JButton();
        loginJB.setText("登陆");
        loginJB.setBounds(278, 250, 80, 40);
        loginJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 登陆
                String username = usernameJTF.getText().toString();
                String password = new String(passwordJTF.getPassword());

                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(
                            loginFrame,
                            "You should input your username and password!",
                            "Username or Password needed",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (UserService.authUser(username, password)) {
                    loginFrame.setVisible(false);
                    new MailFrame();
                } else {
                    JOptionPane.showMessageDialog(
                            loginFrame,
                            "Login Failed! Please check your username or password!",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // 设置按钮
        JButton settingJB = new JButton();
        settingJB.setBounds(60, 400, 40, 40);
        settingJB.setBorderPainted(false);
        settingJB.setIcon(new ImageIcon("images/setting_normal.png"));
        settingJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingFrame();
            }
        });

        backgroundJL.add(titleTL);
        backgroundJL.add(usernameJL);
        backgroundJL.add(usernameJTF);
        backgroundJL.add(passwordJL);
        backgroundJL.add(passwordJTF);
        backgroundJL.add(registerJB);
        backgroundJL.add(loginJB);
        backgroundJL.add(settingJB);

        container.add(backgroundJL);

        loginFrame.setBounds(0, 0, backgroundImgIcon.getIconWidth(), backgroundImgIcon.getIconHeight());
        loginFrame.setVisible(true);
        loginFrame.pack();
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
