package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.CommentBean;

/**
 * Define the comment operations
 * 
 * @author Dudaxi Huang
 * @version 1.0
 */
public interface CommentDAO {
	/**
	 * Get all comments of a room
	 * 
	 * @param roomId
	 *            Room id
	 * @return All comments of a room in a list
	 */
	public List<CommentBean> getComments(int roomId);

	/**
	 * Make a comment on a room
	 * 
	 * @param userId
	 * @param roomId
	 * @param content
	 * @param pic
	 * @return
	 */
	public boolean makeComment(String userId, int roomId, String content,
			String pic);
}
