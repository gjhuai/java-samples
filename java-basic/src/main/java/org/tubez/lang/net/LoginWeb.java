package org.tubez.lang.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 本程序可以模拟web登录。向服务器端提交数据。 
 * 
 * 1、向服务器post多个参数时，如何做？ 
 * 2、如何取得一个连接的Cookie和sessionId?
 * 3、如何使用sessionId访问一个网站？
 * 
 * 第30行，关于POST多个参数到服务器端，可以参照下面代码：
 
         StringBuffer params = new StringBuffer("typeid=");
         params.append(args[0]).append("&");
         params.append("keyword=").append(args[1]);
         
         os.write(params.toString().getBytes("gb2312"))
 */
public class LoginWeb {

	public static void main(String args[]) throws IOException {

		URL url = new URL("http://localhost:8080/backgroundH/login.jsp");
		URL url1 = new URL("http://localhost:8080/backgroundH/execute.jsp");
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();

		// 设置允许output
		huc.setDoOutput(true);
		// 设置为post方式
		huc.setRequestMethod("POST");
		huc.setRequestProperty("user-agent", "mozilla/4.7 [en] (win98; i)");

		OutputStream os = huc.getOutputStream();
		// 多个参数的输出时，需要用&连接，并转换成bytes
		os.write("name=gaolei".getBytes("gbk"));
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				huc.getInputStream()));

		huc.connect();
		String line = br.readLine();

		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}

		// 取得cookie
		String cookieval = huc.getHeaderField("set-cookie");

		System.out.println(cookieval);
		String sessionid = null;

		// 取得cookie，这段代码对于不同网站不同，因为有的网站有多个session标识
		if (cookieval != null) {
			sessionid = cookieval.substring(0, cookieval.indexOf(";"));
		}

		huc.disconnect();
		huc = null;

		HttpURLConnection huc1 = (HttpURLConnection) url1.openConnection();
		// 使用sessionId访问一个网站
		huc1.setRequestProperty("cookie", sessionid);
		// 设置允许output
		huc1.setDoOutput(true);
		// 设置为post方式
		huc1.setRequestMethod("POST");
		huc1.setRequestProperty("user-agent", "mozilla/4.7 [en] (win98; i)");

		OutputStream os1 = huc1.getOutputStream();

		os1.write("value=1234567890".getBytes("gbk"));
		os1.close();

		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				huc1.getInputStream()));

		huc1.connect();
		line = br1.readLine();
		while (line != null) {
			System.out.println(line);
			line = br1.readLine();
		}
		huc1.disconnect();

	}
}
