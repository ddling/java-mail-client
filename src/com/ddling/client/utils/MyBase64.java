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

package com.ddling.client.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by lingdongdong on 15/1/4.
 * 对Base64类的一个整合
 */
public class MyBase64 {

    /**
     * 对字符串进行Base64的解码
     * @param str 要解码的字符串
     * @return 解码后的字符串
     */
    public static String decodeStr(String str) {
        Base64 base64 = new Base64();
        byte[] debytes = base64.decodeBase64(new String(str).getBytes());
        return new String(debytes);
    }

    /**
     * 对字符串进行Base64的编码
     * @param str 要编码的字符串
     * @return 编码后的字符串
     */
    public static String encodeStr(String str) {
        Base64 base64 = new Base64();
        byte[] enbytes = base64.encodeBase64Chunked(str.getBytes());
        return new String(enbytes);
    }
}
