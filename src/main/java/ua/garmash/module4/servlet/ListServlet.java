package ua.garmash.module4.servlet;

import ua.garmash.module4.dao.DetailDao;
import ua.garmash.module4.model.Detail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ListServlet", value = "/list")
public class ListServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Detail> infoList = new ArrayList<>();
        DetailDao detailDao= new DetailDao();
        infoList.addAll(detailDao.getAll());
        req.setAttribute("infoList", infoList);
        getServletContext().getRequestDispatcher("/showList.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }
}
