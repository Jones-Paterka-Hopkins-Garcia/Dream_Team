package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.codeup.adlister.util.Helper.formatReturnAddress;

@WebServlet(name = "DeleteAdServlet", urlPatterns = "/ads/delete")

public class DeleteAdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("user") == null) {
            String address = formatReturnAddress(request.getRequestURL(),request.getQueryString());
            response.sendRedirect("/login?returnTo=" + address);
            return;
        }
        Long id = Long.parseLong(request.getParameter("id"));
        PrintWriter out = response.getWriter();

//        out.println("<script>");
//        out.println("var userConfirm = Confirm('Are you sure you want to delete this ad?');" +
//                "if (userConfirm {" +
//                "        DaoFactory.getAdsDao().deleteAd(id);\n)");
//        out.println("window.location.replace('" + "/login" + "');");
//        out.println("</script>");

        DaoFactory.getAdsDao().deleteAd(id);
        response.sendRedirect("/profile");

    }
}

