package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class UserCreateServlet extends UserListServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// traitement du formulaire (appel à la méthode de sauvegarde)
        boolean textVisibility = false;
        try {
            final Client client = new Client();
            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate naissance = LocalDate.parse(request.getParameter("naissance"));
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setNaissance(naissance);

            if(Client.isLegal(client) == true && Client.isNameLongEnough(client)==true && Client.isMailValid(client,clientService)){
                clientService.create(client);
                response.sendRedirect("/rentmanager/users");
            }
            if(Client.isLegal(client)==false){
                String error_message = "Le client doit être majeur \n";
                response.getWriter().write(error_message);
            }
            if(Client.isNameLongEnough(client)==false){
                String error_message = "Le nom et le prénom doivent faire au moins 3 lettres \n";
                response.getWriter().write(error_message);
            }
            if(Client.isMailValid(client,clientService) == false){
                String error_message = "L'adresse mail renseignée est déjà utilisée \n";
                response.getWriter().write(error_message);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
//        response.sendRedirect("/rentmanager/src/main/webapp/WEB-INF/views/users/create.jsp");

    }
}
