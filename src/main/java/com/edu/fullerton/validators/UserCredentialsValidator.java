package com.edu.fullerton.validators;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.edu.fullerton.bean.UserBean;
import com.edu.fullerton.dao.UserDao;
import com.edu.fullerton.errors.ErrorAndMessages;

@ManagedBean(name = "home", eager = true)
@SessionScoped
@FacesValidator("com.edu.fullerton.validators.UserCredentialsValidator")
public class UserCredentialsValidator {

	public UserCredentialsValidator() {
		System.out.println("Website Startedd");
	}

	public String getMessage() {
		return "hiiii";
	}

	public static void checkCredentials(String userName, String password, HttpServletRequest req, String action, ErrorAndMessages em) {
		if (userName == null || userName == "") {
			em.addError("User Name cannot be empty");
		} else if (password == null || password == "") {
			em.addError("Password cannot be empty");
		} else {
			Boolean ifSuccessfull = validateLoginAndSignUp(userName, password, em, action);
			if (ifSuccessfull)
				req.getSession().setAttribute("userInsession", new UserBean(userName, password));
		}
		ErrorAndMessages.serErrorsAndMessages(req, em);
	}

	private static Boolean validateLoginAndSignUp(String userName, String password, ErrorAndMessages em,
			String action) {
		UserDao obj = new UserDao();
		if (obj.getConnection() != null) {
			if (action.equals("signUp")) {
				Boolean stored = obj.storeUserIntoDB(userName, password);
				if (null != stored && stored == true) {
					em.addMessage("Sign Up successful");
					return true;
				} else if (null != stored && stored == false) {
					em.addError("Server Down!! database Connection Failed");
					return false;
				} else {
					em.addError("Sorry Somebody already took that username");
					return false;
				}
			} else {
				Boolean stored = obj.checkUserValid(userName, password);
				if (stored) {
					em.addMessage("Sign in Successfull. Welcome back.");
					return true;
				} else {
					em.addError("Looks like you mistyped your username or password.");
					return false;
				}
			}
		} else {
			em.addError("Server Down!! database Connection Failed");
			return false;
		}
	}

	public String getResult() {
		return null;
	}
}
