package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.DaoException;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, nb_places=? WHERE id=?;";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS nb_vehicle FROM Vehicle;";
	private static final String FIND_VEHICLES_CLIENT_QUERY = "SELECT * FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id=Vehicle.id WHERE Reservation.client_id=?;";
	private static final String COUNT_VEHICLES_CLIENT_QUERY = "SELECT COUNT(DISTINCT Vehicle.id) FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id=Vehicle.id WHERE Reservation.client_id=?";

	public long create(Vehicle vehicle) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
					connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			) {
			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNb_places());
			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();
			int id = 0;
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long edit(Vehicle vehicle) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement(UPDATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)
		) {
			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNb_places());
			ps.setInt(3, vehicle.getId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public long delete(int id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			) {
			ps.setInt(1, id);
			ps.execute();
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_VEHICLE_QUERY)
			) {
			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			String constructeur = rs.getString("constructeur");
			int nbPlaces = rs.getInt("nb_places");
			return new Vehicle((int) id,constructeur,nbPlaces);

		} catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public List<Vehicle> findAll() throws DaoException {

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY)
			) {
			while (rs.next()){
				int id = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				int nbPlaces = rs.getInt("nb_places");

				vehicles.add(new Vehicle(id,constructeur,nbPlaces));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicles;
	}

	public List<Vehicle> findByClientId(long id) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_VEHICLES_CLIENT_QUERY)
			) {
			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			while (rs.next()){
				int id_vehicle = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				int nbPlaces = rs.getInt("nb_places");
				vehicles.add(new Vehicle(id_vehicle,constructeur,nbPlaces));
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicles;
	}

	public long count() throws DaoException{
		int nb_vehicle = 1;
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(COUNT_VEHICLES_QUERY)
			) {
			while (rs.next()){
				nb_vehicle = rs.getInt(nb_vehicle);
			}
			return nb_vehicle;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public long countVehiclesClient(int id) throws DaoException{
		int nb_vehicle = 1;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(COUNT_VEHICLES_CLIENT_QUERY)
		) {
			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			while (rs.next()){
				nb_vehicle = rs.getInt(nb_vehicle);
			}
			return nb_vehicle;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
