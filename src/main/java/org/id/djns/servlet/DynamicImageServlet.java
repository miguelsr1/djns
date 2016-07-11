/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author misanchez
 */
public class DynamicImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file = request.getParameter("file");

        if (file != null && !file.isEmpty()) {
            try {
                BufferedInputStream in;
                in = new BufferedInputStream(new FileInputStream("D:\\fotos\\" + file));

                // Get image contents.
                byte[] bytes = new byte[in.available()];

                in.read(bytes);
                in.close();

                // Write image contents to response.
                response.getOutputStream().write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}