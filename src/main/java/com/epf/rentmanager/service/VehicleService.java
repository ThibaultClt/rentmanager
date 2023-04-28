package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	@Autowired
	VehicleDao vehicleDao;
	@Autowired
	private ReservationService reservationService;

	private VehicleService() {
		this.vehicleDao = vehicleDao;
	}

	/**
	 * Création d'un véhicule
	 * @param vehicle le véhicule créé
	 * @return l'identifiant du véhicule
	 * @throws ServiceException
	 */
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Modification d'un vehicule
	 * @param vehicle l'identifiant du vehicule modifié
	 * @return le nombre de lignes mises à jour dans la table
	 * @throws ServiceException
	 */
	public long edit(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.edit(vehicle);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Suppression d'un vehicule
	 * @param id l'identifiant du vehicule supprimé
	 * @return le nombre de lignes mises à jour dans la table
	 * @throws ServiceException
	 */
	public long delete(int id) throws ServiceException {
		try {
			List<Reservation> reservationsVehicle = reservationService.findResaByClientId(id);
			for(Reservation reservation : reservationsVehicle) {
				reservationService.delete(reservation.getId());
			}
			return vehicleDao.delete(id);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Recherche d'un vehicule avec son identifiant
	 * @param id l'identifiant du vehicule recherché
	 * @return le vehicule recherché
	 * @throws ServiceException
	 */
	public Vehicle findById(long id) throws ServiceException {
		if (id <= 0) {
			throw new ServiceException("L'id est inférieur ou égal à 0");
		} else {
			try {
				return vehicleDao.findById(id);
			} catch(DaoException e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	}

	/**
	 * Recherche de tous les vehicules
	 * @return une liste de tous les vehicules
	 * @throws ServiceException
	 */
	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Recherche des vehicules réservés par un client
	 * @param id l'identifiant du client qui a réservé la voiture
	 * @return une liste des vehicules recherchés
	 * @throws ServiceException
	 */
	public List<Vehicle> findByClientId(long id) throws ServiceException {
		if (id <= 0) {
			throw new ServiceException("L'id est inférieur ou égal à 0");
		} else {
			try {
				return vehicleDao.findByClientId(id);
			} catch(DaoException e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	}

	/**
	 * Compte le nombre de vehicules
	 * @return le nombre de vehicules
	 * @throws ServiceException
	 */
	public long count() throws ServiceException {
		try{
			return this.vehicleDao.count();
		} catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Compte le nombre de vehicules réservés par un client
	 * @return le nombre de vehicules
	 * @throws ServiceException
	 */
	public long countVehiclesClient(int id) throws ServiceException {
		try{
			return this.vehicleDao.countVehiclesClient(id);
		} catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
