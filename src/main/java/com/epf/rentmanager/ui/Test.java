package com.epf.rentmanager.ui;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;
import java.util.List;

public class Test {

    public static void main(String[] args){
//        Client client = new Client("Thibault","Collet","tibo.collet@mail", LocalDate.now());
//        System.out.println(client.toString());
        long ID_QUE_JE_VEUX = 2;
        try{
            System.out.println(ReservationDao.getInstance().findResaByClientId(ID_QUE_JE_VEUX));
//        } catch (ServiceException e){
//            e.printStackTrace();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

}
