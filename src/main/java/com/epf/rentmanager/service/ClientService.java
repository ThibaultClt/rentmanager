package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;

	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}
	
	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

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

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long count() throws ServiceException {
		try{
			return clientDao.count();
		} catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
}
