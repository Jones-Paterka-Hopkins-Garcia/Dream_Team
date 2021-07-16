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

@WebServlet(name = "controllers.EditAdServlet", urlPatterns = "/ads/edit")
public class EditAdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        long adId = Long.parseLong(request.getParameter("id"));
        request.setAttribute("ad", DaoFactory.getAdsDao().showAd(adId));
        request.setAttribute("categories", DaoFactory.getCategoriesDao().all());
        request.setAttribute("selectedCategories", DaoFactory.getCategoriesDao().allCategoriesForAd(adId));

        request.getRequestDispatcher("/WEB-INF/ads/edit.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        Ad ad = new Ad(
                Long.parseLong(request.getParameter("id")),
                Long.parseLong(request.getParameter("user_id")),
                request.getParameter("title"),
                request.getParameter("description")
        );
        long newAdId = DaoFactory.getAdsDao().edit(ad);
        String[] cats =request.getParameterValues("categories");
        if(cats.length>0) {
            DaoFactory.getCategoriesDao().deleteCategoriesForAd(ad);
            for (String c : cats) {
                AdCategory ac = new AdCategory(newAdId, Long.parseLong(c));
                DaoFactory.getAdsCategoriesDao().insert(ac);
            }
        }
        response.sendRedirect("/ads");
    }
}
