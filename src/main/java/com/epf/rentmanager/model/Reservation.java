package com.epf.rentmanager.model;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
@Service
public class Reservation {

    private int id;
    private int client_id;
    private int vehicle_id;
    private LocalDate debut;
    private LocalDate fin;
    private Vehicle vehicle;
    private Client client;

    public Reservation(int client_id, int vehicle_id, LocalDate debut, LocalDate fin) {
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(int id, int client_id, int vehicle_id, LocalDate debut, LocalDate fin, Vehicle vehicle) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
        this.vehicle = vehicle;
    }

    public Reservation(int id, int client_id, int vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(){
    }

    public static boolean isBookedMore7Days(Reservation reservation) {
        Period period = Period.between(reservation.getDebut(), reservation.getFin());
        return period.getDays() > 7;
    }

    public static boolean isDateOk(Reservation reservation, ReservationService reservationService1) throws ServiceException {
        List<Reservation> reservations = reservationService1.findResaByVehicleId(reservation.getVehicle_id());
        LocalDate dateDebut = reservation.getDebut();
        LocalDate dateFin = reservation.getFin();
        boolean dateOK = true;
        for(int i =0; i<reservations.size(); i++){
            if (dateDebut.isAfter(reservations.get(i).getDebut()) && dateDebut.isBefore(reservations.get(i).getFin())){
                dateOK = false;
            }
            if (dateFin.isAfter(reservations.get(i).getDebut()) && dateFin.isBefore(reservations.get(i).getFin())){
                dateOK = false;
            }
            if (reservations.get(i).getDebut().isAfter(dateDebut) && reservations.get(i).getDebut().isBefore(dateFin)){
                dateOK = false;
            }
            if (dateDebut.compareTo(reservations.get(i).getDebut()) == 0 || dateDebut.compareTo(reservations.get(i).getFin()) == 0){
                dateOK = false;
            }
            if (dateFin.compareTo(reservations.get(i).getDebut()) == 0 || dateFin.compareTo(reservations.get(i).getFin()) == 0){
                dateOK = false;
            }
        }
        return dateOK;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +
                ", vehicle=" + vehicle +
                ", client=" + client +
                '}';
    }
}
