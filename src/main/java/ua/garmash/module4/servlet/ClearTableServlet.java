package ua.garmash.module4.servlet;

import ua.garmash.module4.dao.DetailDao;
import ua.garmash.module4.service.DetailFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ClearServlet", value = "/race/clear")
public class ClearTableServlet extends HttpServlet {
    private static DetailDao detailDao;

    @Override
    public void init() throws ServletException {
        super.init();
        detailDao = new DetailDao();
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!DetailFactory.producingInProgress) {
            detailDao.deleteAll();
            PrintWriter responseBody = resp.getWriter();
            resp.setContentType("text/html");
            responseBody.println("<h1 align=\"center\" style=\"color:#ff0000\">Clearing!</h1>");
            responseBody.println("<div style=\"text-align:center\">" +
                    "<INPUT TYPE=\"button\" VALUE=\"Back\" style=\"width:100px;height:25px\" onClick=\"history.go(-1);\">" +
                    "</div>");
            responseBody.close();
        } else {
            req.getRequestDispatcher("/wait.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }
}
