package com.cmu.qiuoffer.Dao;

import java.util.List;

import com.cmu.qiuoffer.Entities.CommentBean;

/**
 * CommentDao Class
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class CommentDao {
	
	/**
	 * Get all comments of a room
	 * @param roomId Room id
	 * @return All comments of a room in a list
	 */
	public List<CommentBean> getComments(int roomId) {
		return null;
	}
	
	/**
	 * Make a comment on a room
	 * @param userId 
	 * @param roomId
	 * @param content
	 * @param pic
	 * @return
	 */
	public boolean makeComment(String userId, int roomId, String content, String pic) {
		return false;
	}
}
