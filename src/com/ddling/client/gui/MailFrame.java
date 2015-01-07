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
import com.ddling.client.mailmanage.MailManage;
import com.ddling.client.usermanage.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by lingdongdong on 14/12/30.
 */
public class MailFrame {

    private JFrame mailFrame;

    private JScrollPane jPanel_center;
    private JTextArea mail_content;

    private JList mail_list = null;
    private DefaultListModel mail_list_model = null;
    private MailManage.MAIL_ROLE mail_role = MailManage.MAIL_ROLE.RECEIVER_EMAIL;

    private JLabel backgroundJL;

    public MailFrame() {
        initMailFrame();
    }

    private void initMailFrame() {
        mailFrame = new JFrame();

        Container container = mailFrame.getContentPane();

        JLabel mainIcon = new JLabel();
        ImageIcon imageIcon = new ImageIcon("images/main.png");
        mainIcon.setBounds(10, 10, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        mainIcon.setIcon(imageIcon);

        JLabel welcome = new JLabel("欢迎您!");
        welcome.setBounds(160, 30, 110, 50);

        JLabel username = new JLabel(UserService.getLoginUser().getUsername());
        username.setBounds(160, 70, 110, 50);

        ImageIcon sendIcon = new ImageIcon("images/sendEmail.png");
        JButton sendBtn = new JButton("写信", sendIcon);
        sendBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        sendBtn.setBounds(10, 160, 220, sendIcon.getIconHeight() + 20);
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SendEmailFrame();
            }
        });

        ImageIcon mailBoxIcon = new ImageIcon("images/mailbox.png");
        final JButton mailBoxBtn = new JButton("收件箱", mailBoxIcon);
        mailBoxBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        mailBoxBtn.setBounds(10, 240, 220, mailBoxIcon.getIconHeight() + 20);
        mailBoxBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTheEmailBox(MailManage.MAIL_ROLE.RECEIVER_EMAIL);
            }
        });

        ImageIcon mail_forward_icon = new ImageIcon("images/email_forward.png");
        final JButton mail_forward_btn = new JButton("发件箱", mail_forward_icon);
        mail_forward_btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        mail_forward_btn.setBounds(10, 320, 220, mailBoxIcon.getIconHeight() + 20);
        mail_forward_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTheEmailBox(MailManage.MAIL_ROLE.SENDER_EMAIL);
            }
        });

        mail_list = new JList();
        mail_list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                readEmail(e);
            }
        });
        mail_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList the_list = (JList)e.getSource();
                if (e.getClickCount() == 2) {
                    int index = getMailId((String)the_list.getSelectedValue());
                    operaterEmail(index);
                }
            }
        });
        mail_list_model = new DefaultListModel();
        mail_list.setModel(mail_list_model);
        mail_list.setFont(new Font("Arial", Font.BOLD, 20));
        mail_list.setCellRenderer(new ListCellRenderer());
        mail_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ImageIcon logoutIcon = new ImageIcon("images/logout.png");
        JButton logoutBtn = new JButton("退出登录", logoutIcon);
        logoutBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        logoutBtn.setBounds(10, 600, 220, logoutIcon.getIconHeight() + 20);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        jPanel_center = new JScrollPane(mail_list);
        jPanel_center.setBounds(250, 20, 250, 640);
        jPanel_center.setBorder(new EmptyBorder(0, 0, 0, 0));

        mail_content = new JTextArea();
        mail_content.setBounds(520, 20, 460, 640);
        mail_content.setEditable(false);

        JLabel tmp = new JLabel();

        container.add(mainIcon);
        container.add(welcome);
        container.add(username);
        container.add(sendBtn);
        container.add(mailBoxBtn);
        container.add(mail_forward_btn);
        container.add(logoutBtn);
        container.add(jPanel_center);
        container.add(mail_content);
        container.add(tmp);

        mailFrame.pack();
        mailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mailFrame.setBounds(200, 0, 1000, 700);
        mailFrame.setResizable(false);
        mailFrame.setVisible(true);
    }

    private void operaterEmail(int index) {
        Object[] opetions = {"退出", "回复邮件", "删除邮件"};
        int response = JOptionPane.showOptionDialog(
                mailFrame,
                "选择你的操作",
                "邮件操作",
                JOptionPane.YES_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opetions,
                opetions[0]
        );

        if (response == 2) {
            int deleteOrNot = JOptionPane.showConfirmDialog(
                    mailFrame,
                    "你确定要删除此邮件吗？",
                    "邮件操作",
                    JOptionPane.CANCEL_OPTION
            );

            if (deleteOrNot == 0) {
                deleteEmail(index);
            } else {
            }
        } else if (response == 1){
            replyEmail(index);
        } else {
            return;
        }
    }

    private void replyEmail(int mail_id) {
        MailManage mailManage = new MailManage();
        Mail mail = mailManage.getMailByID(mail_id, mail_role);
        String mail_from = mail.getMail_from();
        new SendEmailFrame(mail_from);
    }

    private void refreshTheEmailBox(MailManage.MAIL_ROLE mailRole) {
        MailManage mailManage = new MailManage();
        mail_role = mailRole;
        ArrayList<Integer> mail_ids = mailManage.getMailIds(mail_role);
        mail_list_model.clear();
        for (int i = 0; i < mail_ids.size(); i++) {
            mail_list_model.add(i, "邮件 " + mail_ids.get(i));
            mail_list.setModel(mail_list_model);
        }
    }

    private void logout() {
        UserService.setLoginUser(null);
        UserService.setIsLogin(false);
        mailFrame.dispose();
        new LoginFrame();
    }

    private void readEmail(ListSelectionEvent e) {
        MailManage mailManage = new MailManage();
        try {
            Mail mail = mailManage.getMailByID(getMailId((String) mail_list.getSelectedValue()), mail_role);

            String mail_from = mail.getMail_from();
            String rcpt_to = mail.getMail_to();
            String content = mail.getContent();
            String sendTime = mail.getSendTime();

            String present_mail_content = "\n\n";

            present_mail_content += "\t收件人：\t" + rcpt_to + "\n\n";
            present_mail_content += "\t发信人： \t" + mail_from + "\n\n";
            present_mail_content += "\t发信时间： \t" + sendTime + "\n\n";
            present_mail_content += "\t邮件正文： \t\n\n";
            present_mail_content += "\t" + content + "\n\n";

            mail_content.setText(present_mail_content);
        } catch (NullPointerException ex) {
            mail_content.setText("");
            return;
        }
    }

    private int getMailId(String str) {
        int len = str.split(" ").length;
        return Integer.parseInt(str.split(" ")[len - 1]);
    }

    private void deleteEmail(int mail_id) {
        MailManage mailManage = new MailManage();
        boolean deleteOK = mailManage.deleteMailById(mail_id);
        if (deleteOK) {
            JOptionPane.showMessageDialog(null, "删除成功");
        } else {
            JOptionPane.showMessageDialog(null, "删除失败");
        }

        refreshTheEmailBox(MailManage.MAIL_ROLE.RECEIVER_EMAIL);
        refreshTheEmailBox(MailManage.MAIL_ROLE.SENDER_EMAIL);
    }

    public static void main(String[] args) {
        new MailFrame();
    }
}
