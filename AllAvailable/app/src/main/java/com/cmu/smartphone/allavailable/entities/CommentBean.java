package com.cmu.smartphone.allavailable.entities;

import java.io.Serializable;

/**
 * CommentBean Class
 *
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class CommentBean implements Serializable {
    private int commentId;
    private String date;
    private String time;
    private String title;
    private String content;
    private String userId;
    private int roomId;
    private String imagePath;

    /**
     * Get the comment's id
     *
     * @return the comment's id
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Set the comment's id
     *
     * @param commentId the id of the comment
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
     * @param date the date of the comment
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
     * @param time the time of the comment
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
     * @param content the content of the comment
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the user's id of the comment
     *
     * @return the user's id of the comment
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set the user's id of the comment
     *
     * @param userId the user's id of the comment
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the room's id
     *
     * @return the room's id
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Set the room's id
     *
     * @param roomId the given room's id
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @param imagePath the image path of the comment
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
