package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Password;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "controllers.LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //This next line will tell you the longest amount of time a session can remain idle
        //before the server invalidates it / removes its data
        //int timeOut = request.getSession().getMaxInactiveInterval();
        if (request.getSession().getAttribute("user") != null) {

            response.sendRedirect("/profile");
            return;
        }
        request.setAttribute("returnTo", request.getParameter("returnTo"));
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = DaoFactory.getUsersDao().findByUsername(username);
        PrintWriter out = response.getWriter();

        String returnTo = request.getParameter("returnTo");
        if (user == null) {
            out.println("<script>");
            out.println("alert('Please enter a username and password');");
            out.println("window.location.replace('" + "/login" + "');");
            out.println("</script>");
            return;
        }

        boolean validAttempt = Password.check(password, user.getPassword());
        String wrongAlert = "<script>alert('Sorry, the username or password may be incorrect');</script>";

            if (validAttempt) {
                request.getSession().setAttribute("user", user);
                if(returnTo==null || returnTo.isEmpty()) {
                    response.sendRedirect("/profile");
                }
                else
                {
                    response.sendRedirect(returnTo);
                }
            }else {
                request.setAttribute("username", username);
                request.setAttribute("wrongAlert", wrongAlert);
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
    }
}
