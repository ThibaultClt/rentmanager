package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	@Autowired
	VehicleDao vehicleDao;

	@Autowired
	ClientDao clientDao;

	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS nb_reservation FROM Reservation;";

	/**
	 * Création d'une réservation
	 * @param reservation la réservation créée
	 * @return l'identifiant de la réservation
	 * @throws DaoException
	 */
	public long create(Reservation reservation) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
			 	PreparedStatement ps =
					connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)
			) {
			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
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

	/**
	 * Modification d'une réservation
	 * @param reservation l'identifiant de la réservation modifiée
	 * @return le nombre de lignes mises à jour dans la table
	 * @throws DaoException
	 */
	public long edit(Reservation reservation) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
			 	PreparedStatement ps =
					 connection.prepareStatement(UPDATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)
			) {
			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.setInt(5, reservation.getId());
			ps.execute();
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * Suppression d'une réservation
	 * @param id l'identifiant de la réservation supprimée
	 * @return le nombre de lignes mises à jour dans la table
	 * @throws DaoException
	 */
	public long delete(int id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)
			) {
			ps.setInt(1, id);
			ps.execute();
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * Recherche d'une réservation avec son identifiant
	 * @param id l'identifiant de la réservation recherchée
	 * @return la réservation recherchée
	 * @throws DaoException
	 */
	public Reservation findById(long id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_RESERVATION_QUERY)
			) {
			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			int client_id = rs.getInt("client_id");
			int vehicle_id = rs.getInt("vehicle_id");
			LocalDate debut = rs.getDate("debut").toLocalDate();
			LocalDate fin = rs.getDate("fin").toLocalDate();
			return new Reservation((int) id,client_id,vehicle_id,debut,fin);
		} catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
	}

	/**
	 * Recherche des réservations d'un client avec son identifiant
	 * @param clientId l'identifiant du client de la réservation recherchée
	 * @return la liste des réservations du client
	 * @throws DaoException
	 */
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)
			) {
			pstatement.setLong(1,clientId);
			ResultSet rs = pstatement.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				Vehicle vehicle = vehicleDao.findById(vehicle_id);
				reservations.add(new Reservation(id,(int) clientId,vehicle_id,debut,fin,vehicle));
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	/**
	 * Recherche des réservations d'une voiture avec son identifiant
	 * @param vehicleId l'identifiant de la voiture de la réservation recherchée
	 * @return la liste des réservations de la voiture
	 * @throws DaoException
	 */
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)
			) {
			pstatement.setLong(1,vehicleId);
			ResultSet rs = pstatement.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id,(int) client_id, (int) vehicleId,debut,fin));
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	/**
	 * Recherche de toutes les réservations
	 * @return une liste de toutes les réservations
	 * @throws DaoException
	 */
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try(
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY)
			) {
			while (rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id,client_id,vehicle_id,debut,fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	/**
	 * Compte le nombre de réservations
	 * @return le nombre de réservations
	 * @throws DaoException
	 */
	public long count() throws DaoException{
		int nb_reservation = 1;
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(COUNT_RESERVATIONS_QUERY)
			) {
			while (rs.next()){
				nb_reservation = rs.getInt(nb_reservation);
			}
			return nb_reservation;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
