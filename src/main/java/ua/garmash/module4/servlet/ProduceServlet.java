package ua.garmash.module4.servlet;

import ua.garmash.module4.config.HibernateFactoryUtil;
import ua.garmash.module4.service.DetailFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProduceServlet", value = "/race/start")
public class ProduceServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!DetailFactory.producingInProgress) {
            HibernateFactoryUtil.init();
            DetailFactory.produce();
            req.getRequestDispatcher("/done.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/wait.jsp").forward(req, resp);
        }

    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }
}
