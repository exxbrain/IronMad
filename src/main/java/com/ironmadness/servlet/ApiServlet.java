package com.ironmadness.servlet;

import com.ironmadness.repos.User_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api")
public class ApiServlet extends HttpServlet {

    @Value("${hostname}")
    private String hostname;

    @Autowired
    private User_Repo userRepository;

    //параметр по которому будем смотреть есть ли у нас узер или нет
    private static String apiUser = "true";

    public ApiServlet() {
    }

    public ApiServlet(String apiUser) {
        ApiServlet.apiUser = apiUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nameApi = req.getParameter("name");

        if(nameApi == null) {
            String greeting = getApiUser();
            resp.setContentType("text/plan");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(greeting);
        }else{
            if(userRepository.findByUsername(nameApi) != null){
                String greeting = "true";
                resp.setContentType("text/plan");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(greeting);
            }else{
                String greeting = "false";
                resp.setContentType("text/plan");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(greeting);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        ApiServlet.apiUser = apiUser;
    }
}
