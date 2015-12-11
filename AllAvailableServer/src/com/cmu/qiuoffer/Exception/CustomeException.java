package com.cmu.qiuoffer.Exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Custome exception class
 * 
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class CustomeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int expNo;
	private String expMsg;

	public CustomeException(int no, String msg) {
		this.expNo = no;
		this.expMsg = msg;
	}

	public int getExpNo() {
		return expNo;
	}

	public void setExpNo(int expNo) {
		this.expNo = expNo;
	}

	public String getExpMsg() {
		return expMsg;
	}

	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}

	public String printErr() {
		return null;
	}

	/**
	 * Writing this Exception to a local log file
	 */
	public void logException() {
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(new Timestamp(date.getTime()));
		sb.append("\t");
		sb.append(this.toString());
		try {
			PrintWriter logOut = new PrintWriter(new BufferedWriter(
					new FileWriter("../../log/exception_log.txt", true)));
			logOut.println(sb.toString());
			logOut.close();
		} catch (IOException e) {
			System.out.println("Error: Writing to Log failed");
		}
	}

	/*
	 * Convert this Exception object to a String
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CustomeException occurs:");
		sb.append(expMsg);
		return sb.toString();
	}
}
