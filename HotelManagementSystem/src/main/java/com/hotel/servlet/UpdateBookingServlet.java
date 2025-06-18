package com.hotel.servlet;

import com.hotel.dao.RoomDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

public class UpdateBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        Date fromDate = Date.valueOf(request.getParameter("fromDate"));
        Date toDate = Date.valueOf(request.getParameter("toDate"));

        RoomDAO dao = new RoomDAO();
        boolean updated = dao.updateBookingDates(roomId, fromDate, toDate); // ✅ update in rooms table

        if (updated) {
            response.sendRedirect("manage_bookings.jsp");
        } else {
            request.setAttribute("errorMessage", "Update failed.");
            request.getRequestDispatcher("manage_bookings.jsp").forward(request, response);
        }
    }
}
