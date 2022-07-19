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
import java.util.Arrays;

@WebServlet(name = "ItemStatsServlet", urlPatterns = {"/stats/*"})
public class ItemStatsServlet extends HttpServlet {
    private DetailDao detailDao;

    @Override
    public void init() throws ServletException {
        super.init();
        detailDao = new DetailDao();
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        detailDao = new DetailDao();
        String idToSearch = req.getPathInfo().replaceAll("[^0-9]", "");
        Detail detail = detailDao.getById(Long.parseLong(idToSearch));

        req.setAttribute("detailInfo", new ArrayList<>(Arrays.asList(detail)));
        getServletContext().getRequestDispatcher("/showItemInfo.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }
}
