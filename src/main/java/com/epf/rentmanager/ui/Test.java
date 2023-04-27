package com.epf.rentmanager.ui;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.AppConfiguration;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Test {

    public static void main(String[] args){

        Client legalUser = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2010,01,01));
        System.out.println(Client.isLegal(legalUser));

    }




}
