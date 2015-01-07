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

/**
 * Created by lingdongdong on 15/1/4.
 * 管理用户的一个服务
 */
public class UserService {

    // 保存当前登录的用户信息
    public static User loginUser = null;

    // 判断当前是否有用户登录
    public static boolean isLogin = false;

    public static User getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(User user) {
        loginUser = user;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        UserService.isLogin = isLogin;
    }

    /**
     * 判断数据库中是否有此用户
     * @param username 需要判断的用户名
     * @return 如果存在当前用户，则返回true， 否则返回false
     */
    public static boolean hasUser(String username) {
        UserManageClient userManage = new UserManageClient();
        userManage.sendData("Exist " + username);
        String response = userManage.response().split(" ")[0];
        userManage.closeTheConnection();

        if (response.equalsIgnoreCase("+TRUE")) {
            return true;
        }

        return false;
    }

    /**
     * 对用户进行身份的验证，判断用户名和密码是否合法
     * @param username 需要验证的用户
     * @param password 需要验证的用户密码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean authUser(String username, String password) {
        UserManageClient userManage = new UserManageClient();
        userManage.sendData("Auth " + username + ":" + password);
        String response = userManage.response().split(" ")[0];
        userManage.closeTheConnection();

        if (response.equalsIgnoreCase("+TRUE")) {
            isLogin = true;
            loginUser = new User(username, password);
            return true;
        }

        return false;
    }

    /**
     * 注册新用户
     * @param username 需要注册的新用户名
     * @param password 需要注册的新用户密码
     * @return 注册成功返回true， 注册失败返回false
     */
    public static boolean regUser(String username, String password) {
        UserManageClient userManage = new UserManageClient();
        userManage.sendData("Reg " + username + ":" + password);
        String response = userManage.response().split(" ")[0];
        userManage.closeTheConnection();

        if (response.equalsIgnoreCase("+TRUE")) {
            return true;
        }

        return false;
    }

    /**
     * 退出登录
     */
    public static void logout() {
        loginUser = null;
        isLogin = false;
    }

    public static void main(String[] args) {
        UserService.regUser("xxx", "123456");
    }
}
