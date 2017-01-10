package com.edu.fullerton.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.edu.fullerton.bean.PostsBean;

public interface DAO {
	
	Boolean storeUserIntoDB(String userName, String password);
	
	Boolean checkUserValid(String userName, String password);
	
	ArrayList<PostsBean> fetchAllUsersPost();
	
	Boolean fetchMyPosts(String userName);
}
