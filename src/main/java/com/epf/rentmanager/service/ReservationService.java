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

    /**
     * Création d'une réservation
     * @param reservation la réservation créée
     * @return l'identifiant de la réservation
     * @throws ServiceException
     */
    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    /**
     * Modification d'une réservation
     * @param reservation l'identifiant de la réservation modifiée
     * @return le nombre de lignes mises à jour dans la table
     * @throws ServiceException
     */
    public long edit(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.edit(reservation);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    /**
     * Suppression d'une réservation
     * @param id l'identifiant de la réservation supprimée
     * @return le nombre de lignes mises à jour dans la table
     * @throws ServiceException
     */
    public long delete(int id) throws ServiceException {
        try {
            return reservationDao.delete(id);
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    /**
     * Recherche d'une réservation avec son identifiant
     * @param id l'identifiant de la réservation recherchée
     * @return la réservation recherchée
     * @throws ServiceException
     */
    public Reservation findById(long id) throws ServiceException {
        if (id <= 0) {
            throw new ServiceException("L'id est inférieur ou égal à 0");
        } else {
            try {
                return reservationDao.findById(id);
            } catch(DaoException e){
                e.printStackTrace();
                throw new ServiceException();
            }
        }
    }

    /**
     * Recherche des réservations d'un client avec son identifiant
     * @param clientId l'identifiant du client de la réservation recherchée
     * @return la liste des réservations du client
     * @throws ServiceException
     */
    public List<Reservation> findResaByClientId(long clientId) throws ServiceException {
        if(clientId<=0){
            throw new ServiceException("L'id est inférieur ou égal à 0");
        } else {
            try {
                return reservationDao.findResaByClientId(clientId);
            } catch(DaoException e){
                e.printStackTrace();
                throw new ServiceException();
            }
        }
    }

    /**
     * Recherche des réservations d'une voiture avec son identifiant
     * @param vehicleId l'identifiant de la voiture de la réservation recherchée
     * @return la liste des réservations de la voiture
     * @throws ServiceException
     */
    public List<Reservation> findResaByVehicleId(long vehicleId) throws ServiceException {
        if(vehicleId<=0){
            throw new ServiceException("L'id est inférieur ou égal à 0");
        } else {
            try {
                return reservationDao.findResaByVehicleId(vehicleId);
            } catch(DaoException e){
                e.printStackTrace();
                throw new ServiceException();
            }
        }

    }

    /**
     * Recherche de toutes les réservations
     * @return une liste de toutes les réservations
     * @throws ServiceException
     */
    public List<Reservation> findAll() throws ServiceException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        try {
            reservations = reservationDao.findAll();
            for(Reservation reservation : reservations){
                Client client = clientService.findById(reservation.getClient_id());
                reservation.setClient(client);
                Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
                reservation.setVehicle(vehicle);
            }
            return reservations;
        } catch(DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    /**
     * Compte le nombre de réservations
     * @return le nombre de réservations
     * @throws ServiceException
     */
    public long count() throws ServiceException {
        try{
            return reservationDao.count();
        } catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

}
