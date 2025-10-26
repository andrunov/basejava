package com.urise.webapp.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("message");
        String errorMessage;

        switch (message) {
            case "immutable":
                errorMessage = "Зарезервированные резюме нельзя менять";
                break;
            case "general":
                errorMessage = "Что-то пошло не так";
                // Здесь можно добавить логику для показа деталей ошибки
                break;
            default:
                errorMessage = "Неизвестная ошибка";
        }

        request.setAttribute("errorMessage", errorMessage);
        // Для отладки - можно временно выводить все исключения
        Throwable error = (Throwable) request.getSession().getAttribute("error");
        if (error != null) {
            StringWriter sw = new StringWriter();
            error.printStackTrace(new PrintWriter(sw));
            request.setAttribute("stackTrace", sw.toString());
        }

        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }
}
