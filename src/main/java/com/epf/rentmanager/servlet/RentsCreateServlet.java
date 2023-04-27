package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/create")
public class RentsCreateServlet extends ReservationListServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("vehicles", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());
        } catch (ServiceException e){
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            final Reservation reservation = new Reservation();
            int id_voiture = Integer.parseInt(request.getParameter("car"));
            int id_client = Integer.parseInt(request.getParameter("client"));
            LocalDate début = LocalDate.parse(request.getParameter("begin"));
            LocalDate fin = LocalDate.parse(request.getParameter("end"));
            reservation.setVehicle_id(id_voiture);
            reservation.setClient_id(id_client);
            reservation.setDebut(début);
            reservation.setFin(fin);

            if(Reservation.isBookedMore7Days(reservation) == false && Reservation.isDateOk(reservation, reservationService)){
                reservationService.create(reservation);
                response.sendRedirect("/rentmanager/rents");
            }
            if(Reservation.isBookedMore7Days(reservation)){
                String error_message = "La réservation du véhicule ne peut pas excéder 7 jours \n";
                response.getWriter().write(error_message);
            }
            if(Reservation.isDateOk(reservation, reservationService) == false){
                String error_message = "Le véhicule choisi est déjà réservé pendant ces dates \n";
                response.getWriter().write(error_message);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        }
}
