package ua.garmash.module4.servlet;

import ua.garmash.module4.dao.DetailDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SummaryStatsServlet", urlPatterns = {"/stats"})
public class SummaryStatsServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Long> resultArray = new ArrayList<>(3);
        DetailDao detailDao = new DetailDao();
        detailDao.getSummary().forEach(result -> {
            for (int i = 0; i < 3; i++) {
                resultArray.add((Long) result[i]);
            }
        });

        PrintWriter responseBody = resp.getWriter();
        resp.setContentType("text/html");
        responseBody.println("<h1 align=\"center\" style=\"color:#ff0000\">Summary statistic</h1>");
        responseBody.println("<h1 align=\"center\"> </h1>");
        responseBody.println(String.format("<h2 align=\"center\" style=\"color:#00ff00\">Number of manufactured details: %d </h2>",
                resultArray.get(0)));
        if (resultArray.get(1) != null) {
            responseBody.println(String.format("<h2 align=\"center\" style=\"color:#ff8800\">Number of broken chips: %d</h2>",
                    resultArray.get(1)));
        }
        if (resultArray.get(2) != null) {
            responseBody.println(String.format("<h2 align=\"center\" style=\"color:#55aaff\">Produced fuel: %d</h2>",
                    resultArray.get(2)));
        }
        responseBody.println("<div style=\"text-align:center\">" +
                "<INPUT TYPE=\"button\" VALUE=\"Back\" onClick=\"history.go(-1);\">" +
                "</div>");
        responseBody.close();


//        Detail detail = detailDao.getById(1);
/*        req.setAttribute("infoList", infoList);
        getServletContext().getRequestDispatcher("/showList.jsp").forward(req, resp);*/
    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }
}
