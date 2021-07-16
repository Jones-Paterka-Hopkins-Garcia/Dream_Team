package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.AdCategory;
import com.codeup.adlister.models.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.codeup.adlister.util.Helper.formatReturnAddress;

@WebServlet(name = "controllers.CreateAdServlet", urlPatterns = "/ads/create")
public class CreateAdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            String address = formatReturnAddress(request.getRequestURL(),request.getQueryString());
            response.sendRedirect("/login?returnTo=" + address);
            return;
        }

        request.setAttribute("categories", DaoFactory.getCategoriesDao().all());

        request.getRequestDispatcher("/WEB-INF/ads/create.jsp")
            .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Session expires after 20 minutes on most servers
        //so if someone were to login, go to the create ads page
        //then fill out the form and then sit there and waits 20 minutes without submitting
        //since the session on most servers expires after 20 minutes, the session variable user
        //will now be null and the next line will either throw an error or give
        //you a user of null or empty which will be problematic a few lines down
        //when you try to call user.getId() since that will not exist
        //and then your code will throw an error.

        //you could check to see if the session is still active in a similar fashion to how you check in the
        //doGet above, but then keep in mind, when the user is sent back to the login, they will
        //lose whatever information was still in the form
        //this is often solved by using a pop up window for login, or by storing the form fields in session
        //of cookie when the redirect happens on the post instead of the get
        User user = (User) request.getSession().getAttribute("user");
        Ad ad = new Ad(
            user.getId(),
            request.getParameter("title"),
                request.getParameter("description")
        );
        String[] cats =request.getParameterValues("categories");
        long newAdId = DaoFactory.getAdsDao().insert(ad);
        if(cats.length>0) {
            for (String c : cats) {
                AdCategory ac = new AdCategory(newAdId, Long.parseLong(c));
                DaoFactory.getAdsCategoriesDao().insert(ac);
            }
        }
        response.sendRedirect("/ads");
    }
}
