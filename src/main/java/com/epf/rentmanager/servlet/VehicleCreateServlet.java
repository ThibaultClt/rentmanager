package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends VehicleListServlet {


    private static final long serialVersionUID = 1L;

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            final Vehicle vehicle = new Vehicle();
            String constructeur = request.getParameter("manufacturer");
            int  nb_places = Integer.parseInt(request.getParameter("seats"));
            vehicle.setConstructeur(constructeur);
            vehicle.setNb_places(nb_places);
            if(Vehicle.hasEnoughPlace(vehicle)){
                vehicleService.create(vehicle);
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
