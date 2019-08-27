package com.zaloni.training;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zaloni.training.cookie.HttpCookie;
import com.zaloni.training.entity.User;
import com.zaloni.training.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns = {"/user-register", "/login", "/logout", "/user-list", "/user-edit/*", "/user-delete/*", "/dummy"})
public class UserServlet extends HttpServlet {
    private static String viewDir = "/jsp/";
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }

    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String jsp)  throws ServletException, IOException {
        request.getRequestDispatcher(viewDir + jsp).forward(request, response);
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        switch (servletPath) {
            case "/login":
                forwardToJsp(request, response, "user-login.jsp");
                break;
                
            case "/logout":
                HttpCookie.logout(response);
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUsername", null);
                session.setAttribute("isAdmin", false);
                response.sendRedirect(request.getContextPath() + "/login");
                break;
    
            case "/user-register":
                forwardToJsp(request, response, "user-register.jsp");
                break;
                
            case "/user-list":
                doUserList(request, response);
                break;
                
            case "/user-delete":
            case "/user-edit":
                System.out.println("servletPath:"+ servletPath);
                String pathInfo = request.getPathInfo();
                if (Pattern.matches("/[0-9]+", pathInfo)) {
                    int id = Integer.valueOf(pathInfo.substring(1));
                    UserService userService = new UserService();
                    User user = userService.getUser(id);
                    request.setAttribute("user", user);
                    forwardToJsp(request, response, servletPath.equals("/user-edit") ? "user-edit.jsp" : "user-delete.jsp");
                }
                else {
                    doDefaultMessage(request, response);
                }
                break;
            
            default:
                doDefaultMessage(request, response);
                break;
        }
	}

	private void doUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (HttpCookie.isLoggedIn(request, response, true)) {
            UserService userService = new UserService();
            String searchString = request.getParameter("search");
            request.setAttribute("userList", userService.getUsers(searchString));
            setMessage(request, "errorMessage");
            setMessage(request, "successMessage");
            
            forwardToJsp(request, response, "user-list.jsp");
        }
        else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
	}

    private void setMessage(HttpServletRequest request, String name) {
        HttpSession session = request.getSession();
        String value = (String) session.getAttribute(name);
        request.setAttribute(name, value == null ? "" : value);
        session.setAttribute(name, "");
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        switch (servletPath) {
            case "/user-register":
                doUserRegister(request, response);
                break;
                
            case "/user-list":
                doUserList(request, response);
                break;

            case "/user-edit":
                doUserUpdate(request, response);
                break;
                
            case "/user-delete":
                doUserDelete(request, response);
                break;

            case "/login":
                doUserLogin(request, response);
                break;

            default:
                doDefaultMessage(request, response);
                break;
        }
	}

	private void doUserDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        HttpSession session = request.getSession();
        if (id != 1) {
            UserService userService = new UserService();
            userService.deleteUser(id);
            session.setAttribute("successMessage", "User id:"+id+" deleted successfully.");
        }
        else {
            session.setAttribute("errorMessage", "Admin user can not be deleted");
        }
        response.sendRedirect(request.getContextPath() + "/user-list");
    }

    private void doUserUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int id = Integer.valueOf(request.getParameter("id"));
	    String username = request.getParameter("username");
	    String email = request.getParameter("email");
	    UserService userService = new UserService();
	    User user = userService.getUser(id);
        HttpSession session = request.getSession();
	    if (user != null && id != 1) {
	        user.setEmail(email);
	        user.setUsername(username);
	        userService.updateUser(user);
            session.setAttribute("successMessage", "User id:"+id+" updated successfully.");
	        response.sendRedirect(request.getContextPath() + "/user-list");
	    }
	    else {
	        String errorMessage = (id == 1) ? "Admin user is can not be updated" : "User id:"+id+" not found";
	        session.setAttribute("errorMessage", errorMessage);
	        response.sendRedirect(request.getContextPath() + "/user-list");
	    }
    }

    private void doUserLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserService userService = new UserService();
        if (userService.isValidCredential(email, password)) {
            User user = userService.getUserByEmail(email);
            HttpCookie.login(response);
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUsername", user.getUsername());
            boolean isAdmin = user.getId() == 1 ? true : false;
            session.setAttribute("isAdmin", isAdmin);
            response.sendRedirect(request.getContextPath() + "/user-list");
        }
        else {
            request.setAttribute("errorMessage", "Invalid user credential!");
            forwardToJsp(request, response, "user-login.jsp");
        }
	}
	
    protected void doUserRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
	    
	    String name = request.getParameter("name");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String confirmPassword = request.getParameter("confirmPassword");
	    String errorMessage = userService.getInvalidErrorMessage(name, email, password, confirmPassword);
	    if (errorMessage.isEmpty()) {
	        HttpSession session = request.getSession();
	        String loggedInUsername = (String) session.getAttribute("loggedInUsername");
	        userService.registerUser(email, password, name);
	        //request.setAttribute("email", email);
            //forwardToJsp(request, response, "user-register-success.jsp");
	        if (loggedInUsername == null) {
	            response.sendRedirect(request.getContextPath() + "/login");
	        }
	        else {
	            response.sendRedirect("user-list");
	        }
	    }
	    else {
	        request.setAttribute("name", name);
	        request.setAttribute("email", email);
	        request.setAttribute("password", password);
	        request.setAttribute("confirmPassword", confirmPassword);
	        request.setAttribute("errorMessage", errorMessage);
            forwardToJsp(request, response, "user-register.jsp");
	    }
	}

	protected void doDefaultMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Unhandled Page: ").append(request.getContextPath());
	}
	
	private int getIntValue(String s) {
	    int num;
        try {
            num = Integer.valueOf(s);
        }
        catch(NumberFormatException nfe) {
            num = 0;
        }
        return num;
	}
}
