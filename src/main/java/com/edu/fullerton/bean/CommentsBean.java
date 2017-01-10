package com.edu.fullerton.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.edu.fullerton.dao.UserDao;
import com.edu.fullerton.errors.ErrorAndMessages;

@ManagedBean(name = "commentBean", eager = true)
@RequestScoped
public class CommentsBean implements Serializable{
	private String comments;
	private String commentedBy;
	private String commentedOnPost;
	
	public CommentsBean(){}

	public CommentsBean(String userName, String comments2,String posts) {
		this.comments=comments2;
		this.commentedBy=userName;
		this.commentedOnPost=posts;
	}
	
	public String getCommentedOnPost() {
		return commentedOnPost;
	}
	public void setCommentedOnPost(String commentedOnPost) {
		this.commentedOnPost = commentedOnPost;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}
	
	public void storeThisCommentForThisPost(String commentedOnPost){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		ErrorAndMessages em = new ErrorAndMessages();
		if(req.getParameter("comments") != null ){
			this.comments = req.getParameter("comments");
		}
		if(this.comments != null && !this.comments.equals("")){
			this.commentedOnPost = commentedOnPost.trim();
			this.commentedBy = ((UserBean)req.getSession().getAttribute("userInsession")).getUserName();
			UserDao obj = new UserDao();
			if (obj.getConnection() != null) {			
				Boolean stored = obj.storeThisCommentForThisPost(comments,commentedOnPost,commentedBy);
				if (null != stored && stored == true) {
					em.addMessage("Post Posted successfully");
				} else if (null != stored && stored == false) {
					em.addError("Server Down!! database Connection Failed");
				} else {
					em.addError("Sorry Somebody already took that Posts");
				}
			} else {
				em.addError("Server Down!! database Connection Failed");
			}
		}else{
			em.addError("Please comment something to be pushed");
		}		
		ErrorAndMessages.serErrorsAndMessages(req, em);
	}
}
