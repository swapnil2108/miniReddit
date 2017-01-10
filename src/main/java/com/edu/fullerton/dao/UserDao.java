package com.edu.fullerton.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.edu.fullerton.bean.CommentsBean;
import com.edu.fullerton.bean.PostsBean;
import com.edu.fullerton.errors.ErrorAndMessages;

public class UserDao extends RedditDao implements DAO{
	
	public UserDao(){
		super.createConnection();
	}

	public Boolean storeUserIntoDB(String userName, String password) {
		if (super.hasUsertable() >= 0) {
			try {
				PreparedStatement ps = con.prepareStatement("INSERT INTO ALLUSERS (USERNAME,PASSWORD) VALUES (?,?)");
				ps.setString(1,userName);
				ps.setString(2,password);
				ps.executeUpdate();
				return true;				
			} catch (SQLException e) {
				if(e instanceof SQLIntegrityConstraintViolationException){
					e.printStackTrace();
					return null;
				}else{
					e.printStackTrace();
					return false;
				}				
			}finally{try {
				con.close();
				System.out.println("connection closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}else{
			return false;
		}		
	}

	public Boolean checkUserValid(String userName, String password) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT PASSWORD FROM ALLUSERS WHERE USERNAME='"+userName+"'");
			ResultSet set = ps.executeQuery();
			String passwordReturned = "";
			while(set.next()){
				passwordReturned = set.getString("PASSWORD");
			}
			if(passwordReturned.trim().equals(password)){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{try {
			con.close();
			System.out.println("connection closed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}}
		return false;
	}

	public ArrayList<PostsBean> fetchAllUsersPost() {
		if(super.hasPostsTable() >= 0 && super.hasCommentsTable() >= 0){
			try {
				ArrayList<PostsBean> postList = new ArrayList<PostsBean>();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM USERSPOST");
				ResultSet set = ps.executeQuery();
				String userName = "";
				String posts = "";
				ArrayList<CommentsBean> comments;
				while(set.next()){
					userName = set.getString("USERNAME");
					posts = set.getString("POSTS");
					comments = fetchAllCommentsForThisPost(posts);
					postList.add(new PostsBean(posts, userName, comments));
				}
				return postList;				
			} catch (SQLException e) {
				if(e instanceof SQLIntegrityConstraintViolationException){
					e.printStackTrace();
					return null;
				}else{
					e.printStackTrace();
					return null;
				}				
			}finally{try {
				con.close();
				System.out.println("connection closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}else{
			return null;
		}
		
	}

	private ArrayList<CommentsBean> fetchAllCommentsForThisPost(String posts) {
		try{
			ArrayList<CommentsBean> commentsList = new ArrayList<CommentsBean>();
			PreparedStatement ps = con.prepareStatement("SELECT COMMENTEDUSER,COMMENTS FROM USERSCOMMENTS WHERE POSTS='"+posts+"'");
			ResultSet set = ps.executeQuery();
			String userName = "";
			String comments = "";
			while(set.next()){
				userName = set.getString("COMMENTEDUSER");
				comments = set.getString("COMMENTS");
				commentsList.add(new CommentsBean(userName,comments,posts));
			}			
			return commentsList;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}		
	}

	public Boolean fetchMyPosts(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean storeThisPostInDB(String postUserName, String post) {
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO USERSPOST(USERNAME,POSTS) VALUES(?,?);");
			ps.setString(1, postUserName);
			ps.setString(2, post);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				e.printStackTrace();
				return null;
			} else {
				e.printStackTrace();
				return false;
			}
		} finally {
			try {
				con.close();
				System.out.println("connection closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}

	public Boolean storeThisCommentForThisPost(String comments, String commentedOnPost, String commentedBy) {
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO USERSCOMMENTS (POSTS,COMMENTS,COMMENTEDUSER) VALUES (?,?,?);");
			ps.setString(1, commentedOnPost);
			ps.setString(2, comments);
			ps.setString(3, commentedBy);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				e.printStackTrace();
				return null;
			} else {
				e.printStackTrace();
				return false;
			}
		} finally {
			try {
				con.close();
				System.out.println("connection closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
}
