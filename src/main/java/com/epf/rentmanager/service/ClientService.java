package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;
	@Autowired
	private ReservationService reservationService;

	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	/**
	 * Création d'un client
	 * @param client le client créé
	 * @return l'identifiant du client
	 * @throws ServiceException
	 */
	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Modification d'un client
	 * @param client l'identifiant du client modifié
	 * @return le nombre de lignes mises à jour dans la table
	 * @throws ServiceException
	 */
	public long edit(Client client) throws ServiceException {
		try {
			return clientDao.edit(client);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Suppression d'un client
	 * @param id l'identifiant du client supprimé
	 * @return le nombre de lignes mises à jour dans la table
	 * @throws ServiceException
	 */
	public long delete(int id) throws ServiceException {
		try {
			List<Reservation> reservationsClient = reservationService.findResaByClientId(id);
			for(Reservation reservation : reservationsClient) {
				reservationService.delete(reservation.getId());
			}
			return clientDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Recherche d'un client avec son identifiant
	 * @param id l'identifiant du client recherché
	 * @return le client recherché
	 * @throws ServiceException
	 */
	public Client findById(long id) throws ServiceException {
		if(id<=0){
			throw new ServiceException("L'id est inférieur ou égal à 0");
		}
		try {
			return clientDao.findById(id);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Recherche d'un client avec son email
	 * @param email
	 * @return le client recherché
	 * @throws ServiceException
	 */
	public Client findByEmail(String email) throws ServiceException {
		try {
			return clientDao.findByEmail(email);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Recherche de tous les clients
	 * @return une liste de tous les clients
	 * @throws ServiceException
	 */
	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * Compte le nombre de clients
	 * @return le nombre de clients
	 * @throws ServiceException
	 */
	public long count() throws ServiceException {
		try{
			return clientDao.count();
		} catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
}
