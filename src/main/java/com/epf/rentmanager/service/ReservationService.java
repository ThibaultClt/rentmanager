package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;

    private ReservationService() {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long edit(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.edit(reservation);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long delete(int id) throws ServiceException {
        try {
            return reservationDao.delete(id);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public Reservation findById(long id) throws ServiceException {
        if (id <= 0) {
            throw new ServiceException("L'id est inférieur ou égal à 0");
        }
        try {
            return reservationDao.findById(id);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByClientId(long clientId) throws ServiceException {
        if(clientId<=0){
            throw new ServiceException("L'id est inférieur ou égal à 0");
        }
        try {
            return reservationDao.findResaByClientId(clientId);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByVehicleId(long vehicleId) throws ServiceException {
        if(vehicleId<=0){
            throw new ServiceException("L'id est inférieur ou égal à 0");
        }
        try {
            return reservationDao.findResaByVehicleId(vehicleId);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        try {
            reservations = reservationDao.findAll();
            for(int i =0; i<reservations.size(); i++){
                Client client = clientService.findById(reservations.get(i).getClient_id());
                reservations.get(i).setClient(client);
                Vehicle vehicle = vehicleService.findById(reservations.get(i).getVehicle_id());
                reservations.get(i).setVehicle(vehicle);
            }
            return reservations;
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long count() throws ServiceException {
        try{
            return reservationDao.count();
        } catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

}
