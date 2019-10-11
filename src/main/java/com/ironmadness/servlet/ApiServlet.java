package com.ironmadness.servlet;

import com.ironmadness.repos.User_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api")
public class ApiServlet extends HttpServlet {

    @Value("${hostname}")
    private String hostname;

    @Autowired
    private User_Repo userRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nameApi = req.getParameter("name");

        String attributeApi = "apiname";

        HttpSession httpSession = req.getSession();

        String apiSessionWindow = (String) req.getSession().getAttribute(attributeApi);

        if(nameApi == null) {
            if(apiSessionWindow == "false") {
                resp.setContentType("text/plan");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(apiSessionWindow);
            }
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
}
