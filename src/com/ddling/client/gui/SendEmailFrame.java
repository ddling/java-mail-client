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

import com.ddling.client.mailmanage.Mail;
import com.ddling.client.usermanage.User;
import com.ddling.client.usermanage.UserService;
import com.ddling.client.smtp.SmtpThread;
import com.ddling.client.utils.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lingdongdong on 15/1/4.
 */
public class SendEmailFrame {

    private User authUser = null;
    private String receiver = "";
    private String subject = "";
    private String content = "";
    private String sender = "";
    private String reply = "";

    private JFrame sendEmailFrame = null;

    private JLabel backgroundJL;

    private JLabel receiverJL = null;
    private JLabel subjectJL = null;
    private JLabel senderJL = null;

    private JButton sendEmailJB = null;

    private JTextField receiverJTL = null;
    private JTextField subjectJTL = null;
    private JTextField senderJTL = null;

    private JTextArea emailContantJTA = null;

    public SendEmailFrame(){
        if (UserService.getLoginUser() != null) {
            authUser = UserService.getLoginUser();
            sender = authUser.getUsername() + "@" + Constants.LOCAL_ADDRESS;
        }
        initSendEmailFrame();
    }

    public SendEmailFrame(String reply) {
        this.reply = reply;
        if (UserService.getLoginUser() != null) {
            authUser = UserService.getLoginUser();
            sender = authUser.getUsername() + "@" + Constants.LOCAL_ADDRESS;
        }
        initSendEmailFrame();
    }

    private void initSendEmailFrame() {

        sendEmailFrame = new JFrame();

        final Container container = sendEmailFrame.getContentPane();

        receiverJL = new JLabel("收件人：");
        receiverJL.setBounds(50, 10, 100, 50);

        if (!reply.equalsIgnoreCase("")) {
            System.out.println(reply.length());
            receiverJTL = new JTextField();
            receiverJTL.setText(reply.substring(0, reply.length() - 1));
            System.out.println(receiverJTL.getText());
        } else {
            receiverJTL = new JTextField();
        }
        receiverJTL.setBounds(160, 10, 400, 50);
        receiverJTL.setBackground(new Color(238, 238, 238));
        receiverJTL.setBorder(new EmptyBorder(0, 0, 0, 0));

        subjectJL = new JLabel("主题：");
        subjectJL.setBounds(50, 70, 100, 50);

        subjectJTL = new JTextField();
        subjectJTL.setBounds(160, 70, 400, 50);
        subjectJTL.setBackground(new Color(238, 238, 238));
        subjectJTL.setBorder(new EmptyBorder(0, 0, 0, 0));

        senderJL = new JLabel("发件人：");
        senderJL.setBounds(50, 130, 100, 50);

        senderJTL = new JTextField(sender);
        senderJTL.setBounds(160, 130, 400, 50);
        senderJTL.setBackground(new Color(238, 238, 238));
        senderJTL.setBorder(new EmptyBorder(0, 0, 0, 0));

        JTextField line = new JTextField();
        line.setBounds(0, 200, 800, 1);

        emailContantJTA = new JTextArea();
        emailContantJTA.setBounds(50, 220, 700, 340);

        sendEmailJB = new JButton("发送");
        sendEmailJB.setBounds(650, 580, 100, 40);
        sendEmailJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                receiver = receiverJTL.getText();
                System.out.println(receiver + "sss");

                subject  = subjectJTL.getText();
                content  = emailContantJTA.getText();

                if (receiver.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(
                            sendEmailFrame,
                            "收件人不能为空",
                            "Mail problem",
                            JOptionPane.ERROR_MESSAGE
                    );
                    receiverJTL.grabFocus();
                    return;
                }

                if (!isEmailAdressFormat(receiver)) {
                    JOptionPane.showMessageDialog(
                            sendEmailFrame,
                            "收件人邮箱格式有误",
                            "Mail problem",
                            JOptionPane.ERROR_MESSAGE
                    );
                    receiverJTL.grabFocus();
                    return;
                }

                if (!isEmailAdressFormat(sender)) {
                    JOptionPane.showMessageDialog(
                            sendEmailFrame,
                            "发件人邮箱格式有误",
                            "Mail problem",
                            JOptionPane.ERROR_MESSAGE
                    );
                    senderJTL.grabFocus();
                    return;
                }

                if (subject.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(
                            sendEmailFrame,
                            "邮件主题不能为空",
                            "Mail problem",
                            JOptionPane.ERROR_MESSAGE
                    );
                    subjectJTL.grabFocus();
                    return;
                }

                if (content.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(
                            sendEmailFrame,
                            "邮件内容不能为空",
                            "Mail problem",
                            JOptionPane.ERROR_MESSAGE
                    );
                    emailContantJTA.grabFocus();
                    return;
                }

                Mail mail = new Mail(sender, receiver, subject, content);

                new SmtpThread(mail).run();

                JOptionPane.showMessageDialog(
                        sendEmailFrame,
                        "你的邮件已经发往服务器，静候处理",
                        "邮件发送成功",
                        JOptionPane.INFORMATION_MESSAGE
                );

                sendEmailFrame.dispose();
            }
        });

        JLabel tmp = new JLabel();

        container.add(receiverJL);
        container.add(receiverJTL);

        container.add(subjectJL);
        container.add(subjectJTL);

        container.add(senderJL);
        container.add(senderJTL);

        container.add(line);
        container.add(emailContantJTA);
        container.add(sendEmailJB);

        container.add(tmp);

        sendEmailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sendEmailFrame.setBounds(200, 0, 800, 650);
        sendEmailFrame.setVisible(true);
        sendEmailFrame.setResizable(false);
    }

    private boolean isEmailAdressFormat(String email){
        boolean isExist = false;

        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        if(b) {
            isExist=true;
        }
        return isExist;
    }
}
