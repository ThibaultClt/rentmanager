package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/details")
public class DetailsUser extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ClientService clientService = ClientService.getInstance();
    private VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }
}
