package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("/profile");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("confirm_password");

        // this is being called from the helper java in the util which is checking if the username and email are valid options
        boolean validUsername = Helper.validUsername(username);
        boolean validEmail = Helper.validEmail(email);

        // this is being called from the helper java in the util which is checking if the email and username have not being used.
       boolean usernameNotExist = Helper.usernameNotExist(username);
       boolean emailNotExist = Helper.emailNotExist(email);

       // this are messages for valid usernames and emails
        String usernameValid = "<script>alert('This Username is not a valid option, try again');</script>";
        String emailValid = "<script>alert('This Email is not a valid option, try again');</script>";
        String emailNUsernameValid = "<script>alert('The username and email already exist with another user, try again');</script>";

        // this is checking if our email and username are valid
        if(!validUsername && !validEmail){
            request.setAttribute("emailNUsernameValid", emailNUsernameValid);
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } else if (!validEmail){
            request.setAttribute("username", username);
            request.setAttribute("emailValid", emailValid);
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } else if(!validUsername){
            request.setAttribute("email", email);
            request.setAttribute("usernameValid", usernameValid);
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }


       // this are our alert messages for existing email and users
        String usernameExistMessage = "<script>alert('The Username already exists, try again');</script>";
        String emailExistMessage = "<script>alert('The email already exist with another user, try again');</script>";
        String emaiNUsernameExistMessage = "<script>alert('The username and email already exist with another user, try again');</script>";

        // this is checking if the email an the username already exist on out datatabase
        if (!usernameNotExist && !emailNotExist) {
            request.setAttribute("emaiNUsernameExistMessage", emaiNUsernameExistMessage);
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } else if (!emailNotExist) {
            request.setAttribute("username", username);
            request.setAttribute("emailExistMessage", emailExistMessage);
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } else {
            request.setAttribute("email", email);
            request.setAttribute("usernameExistMessage", usernameExistMessage);
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }

        boolean inputHasErrors =
                username.isEmpty()
                        || email.isEmpty()
                        || password.isEmpty()
                        || (!password.equals(passwordConfirmation));

        if (inputHasErrors) {
            response.sendRedirect("/register");
            return;
        }
        User user = new User(username, email, password);
        DaoFactory.getUsersDao().insert(user);
        response.sendRedirect("/login");
    }
}