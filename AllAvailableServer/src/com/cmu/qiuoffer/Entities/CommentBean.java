package com.cmu.qiuoffer.Entities;

/**
 * CommentBean Class
 * 
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class CommentBean {
	private int commentId;
	private String date;
	private String time;
	private String content;
	private String userId;
	private int roomId;
	private String imagePath;

	/**
	 * Get the comment id
	 * 
	 * @return the id of the comment
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * Set the comment id
	 * 
	 * @param commentId
	 *            the given comment id
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	/**
	 * Get the date of the comment
	 * 
	 * @return the date of the comment
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Set the date of the comment
	 * 
	 * @param date
	 *            the given date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Get the time of the comment
	 * 
	 * @return the time of the comment
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Set the time of the comment
	 * 
	 * @param time
	 *            the given time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Get the content of the comment
	 * 
	 * @return the content of the comment
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set the content of the comment
	 * 
	 * @param content
	 *            the content of the comment
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Get the user of the comment
	 * 
	 * @return the user id of the comment
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set the user of the comment
	 * 
	 * @param userId
	 *            the given user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Get the room id of the comment
	 * 
	 * @return the room id of the comment
	 */
	public int getRoomId() {
		return roomId;
	}

	/**
	 * Set the room id of the comment
	 * 
	 * @param roomId
	 *            the given room id
	 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	/**
	 * Get the image path of the comment
	 * 
	 * @return the image path of the comment
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Set the image path of the comment
	 * 
	 * @param imagePath
	 *            the given image path
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
