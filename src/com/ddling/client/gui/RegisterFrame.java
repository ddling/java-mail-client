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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by lingdongdong on 14/12/15.
 */
public class RegisterFrame {

    private JFrame registerFrame;

    private JLabel backgroundJL;

    private JTextField usernameJTF;
    private JPasswordField passwordJTF;
    private JPasswordField confirmPasswordJTF;

    private JButton registerJB;
    private JButton cancelJB;

    public RegisterFrame() {
        initRegisterFrame();
    }

    private void initRegisterFrame() {
        registerFrame = new JFrame();
        Container container = registerFrame.getContentPane();

        // 背景图片
        backgroundJL = new JLabel();
        ImageIcon backgroundImgIcon = new ImageIcon("images/background.jpg");
        backgroundJL.setIcon(backgroundImgIcon);
        backgroundJL.setBounds(0, 0, backgroundImgIcon.getIconWidth(), backgroundImgIcon.getIconHeight());

        // Title
        Font font = new Font("Serif", Font.PLAIN, 30);
        JLabel titleTL = new JLabel();
        titleTL.setText("注册JavaMail账号");
        titleTL.setBounds(60, 60, 250, 40);
        titleTL.setFont(font);

        // 用户账号
        JLabel usernameJL = new JLabel();
        usernameJL.setBounds(60, 150, 80, 40);
        usernameJL.setText("用户账号*：");

        usernameJTF = new JTextField();
        usernameJTF.setBounds(140, 150, 220, 40);

        // 用户密码
        final JLabel passwordJL = new JLabel();
        passwordJL.setBounds(60, 200, 80, 40);
        passwordJL.setText("密码*：");

        passwordJTF = new JPasswordField();
        passwordJTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
//                System.out.println("key typed");
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println("key pressed");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int passwordCount = passwordJTF.getPassword().length;
                JLabel passwordLen1 = new JLabel();

                passwordLen1.setBounds(400, 208, 60, 20);
                passwordLen1.setHorizontalAlignment(SwingConstants.CENTER);
                passwordLen1.setOpaque(true);
                backgroundJL.add(passwordLen1);

                if (passwordCount <= 0) {
                }
                else if (passwordCount > 0 && passwordCount <= 6) {
                    passwordLen1.setText("弱");
                    passwordLen1.setBackground(Color.red);
                } else if (passwordCount > 6 && passwordCount < 10) {
                    passwordLen1.setText("中");
                    passwordLen1.setBackground(Color.yellow);
                } else {
                    passwordLen1.setText("强");
                    passwordLen1.setBackground(Color.green);
                }
            }
        });
        passwordJTF.setBounds(140, 200, 220, 40);

        // 用户密码
        final JLabel confirmPasswordJL = new JLabel();
        confirmPasswordJL.setBounds(60, 250, 80, 40);
        confirmPasswordJL.setText("确认密码*：");

        confirmPasswordJTF = new JPasswordField();
        confirmPasswordJTF.setBounds(140, 250, 220, 40);

        // 完成注册按钮
        registerJB = new JButton();
        registerJB.setText("完成注册");
        registerJB.setBounds(260, 300, 100, 40);
        registerJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameJTF.getText().toString();
                String pwd1 = new String(passwordJTF.getPassword());
                String pwd2 = new String(confirmPasswordJTF.getPassword());

                if (!pwd1.equals(pwd2)) {
                    JOptionPane.showMessageDialog(
                            registerFrame,
                            "The second password does not match the first password!",
                            "Password match fail!",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (UserService.hasUser(username)) {
                    JOptionPane.showMessageDialog(
                            registerFrame,
                            "The username has been used!",
                            "Same Username",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (UserService.regUser(username, pwd1)) {
                    JOptionPane.showMessageDialog(
                            registerFrame,
                            "Congratulations! you have registered to the mail",
                            "Register Done!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    new LoginFrame();
                    registerFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(
                            registerFrame,
                            "There has some problem for register",
                            "Register Fail!",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }
        });

        cancelJB = new JButton("取消");
        cancelJB.setBounds(142, 300, 100, 40);
        cancelJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFrame.dispose();
                new LoginFrame();
            }
        });

        backgroundJL.add(titleTL);
        backgroundJL.add(usernameJL);
        backgroundJL.add(usernameJTF);
        backgroundJL.add(passwordJL);
        backgroundJL.add(passwordJTF);
        backgroundJL.add(confirmPasswordJL);
        backgroundJL.add(confirmPasswordJTF);
        backgroundJL.add(registerJB);
        backgroundJL.add(cancelJB);

        container.add(backgroundJL);

        registerFrame.setBounds(0, 0, backgroundImgIcon.getIconWidth(), backgroundImgIcon.getIconHeight());
        registerFrame.pack();
        registerFrame.setLocation(200, 300);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setVisible(true);
    }
}
