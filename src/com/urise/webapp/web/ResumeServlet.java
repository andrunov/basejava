package com.urise.webapp.web;

import com.urise.webapp.model.Resume;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        List<Resume> resumeList = new ArrayList<>();
        resumeList.add(new Resume("uuid1", "First Middle Last 1"));
        resumeList.add(new Resume("uuid2", "First Middle Last 3"));
        resumeList.add(new Resume("uuid3", "First Middle Last 3"));
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<h2>Resumes</h2>");
        htmlTable.append("<table>\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>Name</th>\n" +
                "  </tr>");
        for (Resume resume : resumeList) {
            htmlTable.append("<tr>");
            htmlTable.append("<td>");
            htmlTable.append(resume.getUuid());
            htmlTable.append("</td>");
            htmlTable.append("<td>");
            htmlTable.append(resume.getFullName());
            htmlTable.append("</td>");
            htmlTable.append("</tr>");
        }
        htmlTable.append("</table>");
        response.getWriter().write(htmlTable.toString());
    }
}
