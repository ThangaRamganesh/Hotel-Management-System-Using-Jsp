package com.hotel.servlet;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

public class UpdateRoomServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String type = request.getParameter("type");
            double price = Double.parseDouble(request.getParameter("price"));
            String status = request.getParameter("status");
            String bookingStatusDateStr = request.getParameter("bookingStatusDate");

            Room room = new Room();
            room.setId(id);
            room.setType(type);
            room.setPrice(price);
            room.setStatus(status);

            if (bookingStatusDateStr != null && !bookingStatusDateStr.isEmpty()) {
                try {
                    Date bookingStatusDate = Date.valueOf(bookingStatusDateStr);
                    room.setBookingStatusDate(bookingStatusDate);
                } catch (IllegalArgumentException e) {
                    room.setBookingStatusDate(null);
                }
            } else {
                room.setBookingStatusDate(null);
            }

            RoomDAO dao = new RoomDAO();
            boolean success = dao.updateRoom(room);

            if (success) {
                response.sendRedirect("view_rooms.jsp");
            } else {
                request.setAttribute("errorMessage", "Failed to update room.");
                request.setAttribute("room", room);
                request.getRequestDispatcher("edit-room.jsp?id=" + id).forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred.");
            request.getRequestDispatcher("edit-room.jsp").forward(request, response);
        }
    }
}
