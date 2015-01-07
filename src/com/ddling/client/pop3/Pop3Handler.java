package com.ddling.client.pop3;

import com.ddling.client.mailmanage.Mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingdongdong on 15/1/5.
 */
public class Pop3Handler {

    private static int mail_num = 0;
    private static int all_mail_bytes = 0;
    private static ArrayList<Map<String, Integer>> mail_bytes_map = null;

    public Pop3Handler() {
        mail_bytes_map = new ArrayList<Map<String, Integer>>();
    }

    public static int getMail_num() {
        return mail_num;
    }

    public static int getAll_mail_bytes() {
        return all_mail_bytes;
    }

    public static ArrayList<Map<String, Integer>> getMail_bytes_map() {
        return mail_bytes_map;
    }

    public static void handleStatString(String string) {

        String[] args = string.split(" ");

        if (args.length < 3) {
            mail_num = 0;
            all_mail_bytes = 0;
            return;
        }

        mail_num = Integer.parseInt(args[1]);
        all_mail_bytes = Integer.parseInt(args[2]);
    }

    public static ArrayList<Integer> handleListString(String string) {

        String[] lines = string.split("\n");
        ArrayList<Integer> mail_ids = new ArrayList<Integer>();

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split(" ");

            if (line.length == 2) {
                int mail_id = Integer.parseInt(line[0]);
                int mail_bytes = Integer.parseInt(line[0]);
                mail_ids.add(mail_id);
            }
        }

        return mail_ids;
    }

    public static Mail handleRetrString(String content) {

        Mail mail = new Mail();

        String[] contents = content.split("\n");

        for (int i = 0; i < contents.length; i++) {
            String[] line = contents[i].split(":");
            if (line.length == 2) {
                String arg0 = line[0];
                String arg1 = line[1];
                if (arg0.equalsIgnoreCase("from")) {
                    mail.setMail_from(arg1);
                }

                if (arg0.equalsIgnoreCase("to")) {
                    mail.setMail_to(arg1);
                }

                if (arg0.equalsIgnoreCase("subject")) {
                    mail.setSubject(arg1);
                }

                if (arg0.equalsIgnoreCase("content")) {
                    mail.setContent(arg1);
                }

                if (arg0.equalsIgnoreCase("sendTime")) {
                    mail.setSendTime(arg1);
                }
            }
        }

        return mail;
    }
}
