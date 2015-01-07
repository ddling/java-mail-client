package com.ddling.client.smtp;

import com.ddling.client.mailmanage.Mail;

/**
 * Created by lingdongdong on 15/1/5.
 */
public class SmtpThread implements Runnable {

    private Mail mail = null;

    public SmtpThread(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void run() {
        SmtpClient smtpClient = new SmtpClient(mail);
        smtpClient.sendEmail();
    }

    public static void main(String[] args) {
        Mail mail = new Mail("465391062@qq.com", "ddling@127.0.0.1", "Hello", "This is a test");
        new SmtpThread(mail).run();
    }
}
