package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/create")
public class RentsCreate extends ReservationServlet {
    private static final long serialVersionUID = 1L;
    private ClientService clientService = ClientService.getInstance();
    private VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// traitement du formulaire (appel à la méthode de sauvegarde)
        try {
            final Reservation reservation = new Reservation();
            int voiture = Integer.parseInt(request.getParameter("car"));
            int client = Integer.parseInt(request.getParameter("client"));
            LocalDate début = LocalDate.parse(request.getParameter("begin"));
            LocalDate fin = LocalDate.parse(request.getParameter("end"));
            reservation.setVehicle_id(voiture);
            reservation.setClient_id(client);
            reservation.setDebut(début);
            reservation.setFin(fin);
            ReservationService.create(reservation);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
//        response.sendRedirect("/rentmanager/src/main/webapp/WEB-INF/views/users/create.jsp");
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }
}
