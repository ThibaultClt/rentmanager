package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/cars/edit")
public class VehicleEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id").toString());
            request.setAttribute("vehicle", vehicleService.findById(id));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id").toString());
            final Vehicle vehicle = new Vehicle();
            String constructeur = request.getParameter("manufacturer");
            int nb_places = Integer.parseInt(request.getParameter("seats"));
            vehicle.setId(id);
            vehicle.setConstructeur(constructeur);
            vehicle.setNb_places(nb_places);
            if(Vehicle.hasEnoughPlace(vehicle)){

                vehicleService.edit(vehicle);
                response.sendRedirect("/rentmanager/cars");
            }
            else{
                String error_message = "La voiture doit avoir entre 2 et 9 places \n";
                response.getWriter().write(error_message);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
