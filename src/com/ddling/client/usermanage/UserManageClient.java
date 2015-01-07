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

package com.ddling.client.usermanage;

import com.ddling.client.utils.Constants;
import com.ddling.client.utils.LoggerFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lingdongdong on 15/1/4.
 */
public class UserManageClient {

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private int SO_TIME_OUT = 30000;

    public static Logger logger = LoggerFactory.getLogger(UserManageClient.class);

    /**
     * 初始化用户管理Socket
     */
    public UserManageClient() {
        try {
            socket = new Socket(Constants.MANAGE_SERVER_ADDRESS, Constants.MANAGE_SERVER_PORT);
            socket.setSoTimeout(SO_TIME_OUT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * 给用户管理服务器发送数据
     * @param str 要发送的数据
     */
    public void sendData(String str) {
        out.println(str);
        out.flush();
    }

    /**
     * 得到服务器的响应
     * @return 服务器的响应信息
     */
    public String response() {
        try {
            String response = in.readLine();
            return response;
        } catch (IOException e) {
            logger.error(e);
            return "";
        }
    }

    /**
     * 关闭用户管理Socket
     */
    public void closeTheConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error(e);
            }
            socket = null;
        }
    }
}
