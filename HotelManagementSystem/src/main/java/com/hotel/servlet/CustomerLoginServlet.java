package com.hotel.servlet;

import com.hotel.util.DBConnection;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CustomerLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM customers WHERE email=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("customerName", rs.getString("name"));
                session.setAttribute("customerEmail", rs.getString("email"));
                session.setAttribute("customerId", rs.getInt("id")); // Make sure 'id' exists
                response.sendRedirect("customer_dashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid customer login.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("customer_login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // See logs for the real error
            request.setAttribute("error", "Server error occurred.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer_login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
