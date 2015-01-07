package com.ddling.client.mailmanage;

import com.ddling.client.usermanage.UserService;
import com.ddling.client.pop3.Pop3Client;
import com.ddling.client.pop3.Pop3Handler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lingdongdong on 15/1/6.
 */
public class MailManage {

    private Pop3Client pop3Client = null;
    public enum MAIL_ROLE {SENDER_EMAIL, RECEIVER_EMAIL};
    private ArrayList<Mail> email_for_sender = null;
    private ArrayList<Mail> email_for_receiver = null;

    public ArrayList<Mail> getEmail_for_receiver() {
        return email_for_receiver;
    }

    public ArrayList<Mail> getEmail_for_sender() {
        return email_for_sender;
    }

    public MailManage() {
        pop3Client = new Pop3Client(UserService.getLoginUser());
        email_for_sender = new ArrayList<Mail>();
        email_for_receiver = new ArrayList<Mail>();
    }

    public void initTheMailBox() {
        ArrayList<Integer> mail_ids_for_sender = new ArrayList<Integer>();
        ArrayList<Integer> mail_ids_for_receiver = new ArrayList<Integer>();
        mail_ids_for_sender = getMailIds(MAIL_ROLE.SENDER_EMAIL);
        mail_ids_for_receiver = getMailIds(MAIL_ROLE.RECEIVER_EMAIL);

        for (int i = 0; i < mail_ids_for_sender.size(); i++) {
            int mail_id = mail_ids_for_sender.get(i);
            Mail mail = getMailByID(mail_id, MAIL_ROLE.SENDER_EMAIL);
            email_for_sender.add(mail);
        }

        for (int j = 0; j < mail_ids_for_receiver.size(); j++) {
            int mail_id = mail_ids_for_receiver.get(j);
            Mail mail = getMailByID(mail_id, MAIL_ROLE.RECEIVER_EMAIL);
            email_for_receiver.add(mail);
        }
    }

    public ArrayList<Integer> getMailIds(MAIL_ROLE mail_role) {
        ArrayList<Integer> mail_ids = new ArrayList<Integer>();

        try {
            if (mail_role == MAIL_ROLE.SENDER_EMAIL) {
                String list = pop3Client.pop3Slist();
                mail_ids = Pop3Handler.handleListString(list);
            } else if (mail_role == MAIL_ROLE.RECEIVER_EMAIL) {
                String list = pop3Client.pop3List();
                mail_ids = Pop3Handler.handleListString(list);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mail_ids;
    }

    public Mail getMailByID(int mail_id, MAIL_ROLE mail_role) {
        Mail mail = new Mail();

        try {
            if (mail_role == MAIL_ROLE.SENDER_EMAIL) {
                String mailStr = pop3Client.pop3Setr(mail_id);
                mail = Pop3Handler.handleRetrString(mailStr);
            } else if (mail_role == MAIL_ROLE.RECEIVER_EMAIL) {
                String mailStr = pop3Client.pop3Retr(mail_id);
                mail = Pop3Handler.handleRetrString(mailStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mail;
    }

    public boolean deleteMailById(int mail_id) {

        boolean deleteOK = false;

        try {
            deleteOK = pop3Client.pop3Dele(mail_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deleteOK;
    }
}
