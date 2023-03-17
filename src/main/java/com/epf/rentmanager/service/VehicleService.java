package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	@Autowired
	VehicleDao vehicleDao;

	private VehicleService() {
		this.vehicleDao = vehicleDao;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		if(id<=0){
			throw new ServiceException("L'id est inférieur ou égal à 0");
		}

		try {
			return vehicleDao.findById(id);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long count() throws ServiceException {
		try{
			return this.vehicleDao.count();
		} catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
}
