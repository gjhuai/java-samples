package org.tubez.lang.net.transfer_object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 编程实现序列化的Student（sno,sname）对象在网络上的传输
 * @author chris.guan
 */
class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int sno;
	private String sname;

	public Student(int sno, String sname) {
		this.sno = sno;
		this.sname = sname;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	@Override
	public String toString() {
		return "学号:" + sno + ";姓名:" + sname;
	}

}

class MyClient extends Thread {
	@Override
	public void run() {
		try {
			Socket s = new Socket("localhost", 9999);
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			Student stu = (Student) ois.readObject();
			System.out.println("客户端程序收到服务器端程序传输过来的学生对象>> " + stu);
			ois.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class MyServer extends Thread {

	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(9999);
			Socket s = ss.accept();
			ObjectOutputStream ops = new ObjectOutputStream(s.getOutputStream());
			Student stu = new Student(1, "赵本山");
			ops.writeObject(stu);
			ops.close();
			s.close();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

public class TestTransferObject {
	public static void main(String[] args) {
		new MyServer().start();
		new MyClient().start();
	}
}
