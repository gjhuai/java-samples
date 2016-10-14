package com.tmy.model;

import java.util.Date;

/**
 * 
 * @author Cliff
 */
public class User  {

	private String username;
	private Date time;

	public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    public User(String username) {
		this.username = username;
		time = new Date();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

//    @Override
//    public String getName() {
//        return getUsername();
//    }
}
