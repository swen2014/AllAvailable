/**
 * 
 */
package com.cmu.qiuoffer.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The tool helps parsing the JSON format
 * 
 * @author Xi Wang
 * @version 1.0
 */
public class JsonHelper {
	/**
	 * Create JSON String according to the given String
	 * 
	 * @param key
	 *            JSON head
	 * @param value
	 *            JSON value
	 */
	public static String createJsonString(Object value) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(value);
	}
}
