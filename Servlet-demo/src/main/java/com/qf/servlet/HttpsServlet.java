package com.qf.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wing
 * @create 2024/5/21
 */
@WebServlet("/hello")
public class HttpsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext s1 = this.getServletContext();
        ServletContext s2 = req.getServletContext();
        ServletContext s3 = req.getSession().getServletContext();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
