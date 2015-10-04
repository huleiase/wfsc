package com.wfsc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 编程实现借助飞信通道，免费发送短信
 * 
 * @author leo
 * @date Mar 25, 2010 1:43:50 PM
 */
public class Fetion {
	public static void main(String[] args) throws IOException {
//		String sUrl = "http://fetionapi.appspot.com/api/?";
		//http://api.foryes.net/fetion/?u=飞信手机号码&p=飞信手机密码&t=接收人号码&m=短信内容
//		String sUrl = "http://api.foryes.net/fetion/?";//?username=13812345678&password=123456&sendto=13512345678&message=短信内容
		String sUrl = "http://sms.api.bz/fetion.php?";//?username=13812345678&password=123456&sendto=13512345678&message=短信内容
		String fromNo = "15821754802";// 发送端手机号码
		String password = "wy982932";// 飞信登陆密码
		String toNo = "13917981044";// 接收手机号码
		String msg = "你好";// 发送内容
		sUrl += "username=" + fromNo + "&password=" + password + "&sendto=" + toNo + "&message=" + msg;
		System.out.println(sUrl);
		URL url = new URL(sUrl);
		URLConnection urlConn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
		String rets = "";
		if (in != null) {
			for (String s = ""; (s = in.readLine()) != null;) {
				rets = rets + s;
			}
		}
		in.close();
		System.out.println("Result : " + rets);
		System.out.println("发送成功");
	}
}
