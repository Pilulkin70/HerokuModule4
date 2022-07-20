package ua.garmash.module4.servlet;

import ua.garmash.module4.dao.DetailDao;
import ua.garmash.module4.model.Detail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteItemServlet", urlPatterns = {"/delete/*"})
public class DeleteItemServlet extends HttpServlet {
    private static DetailDao detailDao;

    @Override
    public void init() throws ServletException {
        super.init();
        detailDao = new DetailDao();
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idToSearch = req.getPathInfo().replaceAll("\\D", "");
        Detail detail = detailDao.getById(Long.parseLong(idToSearch));
        detailDao.delete(detail);
        String homePath = req.getContextPath();
        req.setAttribute("idDeletedItem", idToSearch);
        req.setAttribute("homePath", homePath);
        getServletContext().getRequestDispatcher("/isdelete.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }
}
