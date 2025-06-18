package com.hotel.servlet;

import com.hotel.dao.BookingDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));

        BookingDAO dao = new BookingDAO();
        boolean deleted = dao.deleteBooking(bookingId, roomId);

        if (deleted) {
            response.sendRedirect("manage_bookings.jsp");
        } else {
            request.setAttribute("errorMessage", "Deletion failed.");
            request.getRequestDispatcher("manage_all_bookings.jsp").forward(request, response);
        }
    }
}
