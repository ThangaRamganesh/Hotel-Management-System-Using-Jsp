package com.hotel.servlet;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddRoomServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("price"));
        String status = request.getParameter("status");

        Room room = new Room();
        room.setType(type);
        room.setPrice(price);
        room.setStatus(status);

        RoomDAO dao = new RoomDAO();
        boolean success = dao.insertRoom(room);

        if (success) {
            response.sendRedirect("admin_dashboard.jsp");
        } else {
            request.setAttribute("errorMessage", "Failed to add room.");
            request.getRequestDispatcher("admin-add-room.jsp").forward(request, response);
        }
    }
}
