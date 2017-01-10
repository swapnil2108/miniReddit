package com.edu.fullerton.bean;

import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.edu.fullerton.dao.UserDao;
import com.edu.fullerton.errors.ErrorAndMessages;

@ManagedBean(name = "postsBean", eager = true)
@RequestScoped
public class PostsBean {
	
	private String post;
	private String postUserName;
	private ArrayList<CommentsBean> comments;
	
	public PostsBean(){}
	
	public PostsBean(String post,String postUserName,ArrayList<CommentsBean> comments){
		this.post=post;
		this.postUserName=postUserName;
		this.comments=comments;
	}	
	
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getpostUserName() {
		return postUserName;
	}
	public void setpostUserName(String postUserName) {
		this.postUserName = postUserName;
	}
	public ArrayList<CommentsBean> getComments() {
		return comments;
	}
	public void setComments(ArrayList<CommentsBean> comments) {
		this.comments = comments;
	}
	
	public ArrayList<PostsBean> fetchAllPosts(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		ErrorAndMessages em = new ErrorAndMessages();
		UserDao obj = new UserDao();
		if (obj.getConnection() != null) {
			return obj.fetchAllUsersPost();
		} else {
			em.addError("Server Down!! database Connection Failed");
			ErrorAndMessages.serErrorsAndMessages(req, em);
			return null;
		}
	}
	
	public ArrayList<PostsBean> storeThisPostInDB(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		this.postUserName = req.getParameter("postUserName").trim();
		ErrorAndMessages em = new ErrorAndMessages();
		UserDao obj = new UserDao();
		if (obj.getConnection() != null) {			
			Boolean stored = obj.storeThisPostInDB(postUserName,post);
			if (null != stored && stored == true) {
				em.addMessage("Post Posted successfully");
			} else if (null != stored && stored == false) {
				em.addError("Server Down!! database Connection Failed");
			} else {
				em.addError("Sorry Somebody already took that Posts");
			}
		} else {
			em.addError("Server Down!! database Connection Failed");
			return null;
		}
		ErrorAndMessages.serErrorsAndMessages(req, em);
		return fetchAllPosts();
	}
}
