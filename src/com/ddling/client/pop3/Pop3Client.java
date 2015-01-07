package com.ddling.client.pop3;

import com.ddling.client.mailmanage.Mail;
import com.ddling.client.usermanage.User;
import com.ddling.client.utils.Constants;
import com.ddling.client.utils.LoggerFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lingdongdong on 15/1/5.
 */
public class Pop3Client {

    private int port = Constants.LOCAL_POP3_SERVER_PORT;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Socket socket = null;
    private User authUser = null;
    private int SO_TIME_OUT = 30000;
    private static final String RESPONSE_OK = "+OK";

    public static Logger logger = LoggerFactory.getLogger(Pop3Client.class);

    public Pop3Client(int port) {
        this.port = port;
        initializeThePOP3Client();
    }

    public Pop3Client(User user) {
        this.authUser = user;
        initializeThePOP3Client();
    }

    private void initializeThePOP3Client() {
        try {
            socket = new Socket(Constants.LOCAL_POP3_SERVER_ADDRESS, port);
            socket.setSoTimeout(SO_TIME_OUT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            authLogin();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String pop3Stat() throws IOException {

        sendData("stat");

        String statResponse = getResponse();

        String response = getCommand(statResponse);

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("stat fail");
        }

        quit();

        return statResponse;
    }

    public String pop3List() throws IOException {

        sendData("list");

        String listResponse = getResponse();

        String response = getCommand(listResponse);

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("list fail");
        }

        String mail_list = readBuffer();

        quit();

        return mail_list;
    }

    public String pop3Slist() throws IOException {

        sendData("slist");

        String listResponse = getResponse();

        String response = getCommand(listResponse);

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("rlist fail");
        }

        String mail_list = readBuffer();

        quit();

        return mail_list;
    }

    public String pop3Retr(int mail_id) throws IOException {

        sendData("retr " + mail_id);

        String response = getCommand(getResponse());

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("retr fail");
        }

        String mail_content = readBuffer();

        quit();

        return mail_content;
    }

    public String pop3Setr(int mail_id) throws IOException {

        sendData("setr " + mail_id);

        String response = getCommand(getResponse());

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("setr fail");
        }

        String mail_content = readBuffer();

        quit();

        return mail_content;
    }

    public boolean pop3Dele(int mail_id) throws IOException {

        sendData("dele " + mail_id);

        String response = getCommand(getResponse());

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            return false;
        }

        quit();

        return true;
    }

    private void authLogin() throws IOException{
        String data = "user " + authUser.getUsername();
        sendData(data);

        String response = getCommand(getResponse());

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("Response fail!");
        }

        data = "pass " + authUser.getPassword();
        sendData(data);

        response = getCommand(getResponse());

        getResponse();

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("Can not log on");
        }
    }

    /**
     * 发送字符串给邮件服务器
     * @param s 要发送的字符串
     */
    private void sendData(String s) {
        out.println(s);
        out.flush();
    }

    /**
     * 得到服务器的响应信息
     * @return
     */
    private String getResponse() {

        String line = "";
        try {
            line = in.readLine();
        } catch (IOException e) {
            logger.error(e);
        }

        logger.debug(line);
        return line;
    }

    private String readBuffer() {

        StringBuffer stringBuffer = new StringBuffer();
        String line = "";

        try {
            while (!(line = in.readLine()).equalsIgnoreCase(".")) {
                stringBuffer.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    private void quit() throws IOException{

        sendData("quit");

        String response = getCommand(getResponse());

        if (!response.equalsIgnoreCase(RESPONSE_OK)) {
            throw new IOException("quit fail");
        }

        close();
    }

    private void close() {
        if (socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCommand(String str) {
        String cmd = str.toLowerCase().split(" ")[0].toUpperCase();
        return cmd;
    }

    public static void main(String[] args) {
        User user = new User("ddl", "123456");
        Pop3Client client = new Pop3Client(user);
        try {
            String mail_content = client.pop3Retr(1);
            Mail mail = Pop3Handler.handleRetrString(mail_content);
            System.out.println(mail.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
