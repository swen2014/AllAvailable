package com.cmu.qiuoffer.Exception;

/**
 * Custome exception class
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
}
