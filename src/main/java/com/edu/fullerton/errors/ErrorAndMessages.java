package com.edu.fullerton.errors;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "errMsg", eager = true)
@RequestScoped
public class ErrorAndMessages {
	private ArrayList<String> error;
	private ArrayList<String> messages;

	public ErrorAndMessages() {
		this.error = new ArrayList<String>();
		this.messages = new ArrayList<String>();
	}

	public ArrayList<String> getError() {
		return error;
	}

	public void setError(ArrayList<String> error) {
		this.error = error;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}

	public void addError(String error) {
		this.error.add(error);
	}

	public static void serErrorsAndMessages(HttpServletRequest req, ErrorAndMessages em) {
		req.setAttribute("infoMessage", em.getMessages());
		req.setAttribute("errorMessage", em.getError());		
	}
}
