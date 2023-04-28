package com.epf.rentmanager.model;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public Reservation() {
    }

    /**
     * Vérification que la réservation ne dure pas plus de 7 jours
     * @param reservation la réservation que l'on veut créer ou modifier
     * @return un boolean si la durée fait plus de 7 jours ou non
     */
    public static boolean isBookedMore7Days(Reservation reservation) {
        Period period = Period.between(reservation.getDebut(), reservation.getFin());
        return period.getDays() > 7;
    }

    /**
     * Vérification que la voiture ne peut pas être réservée 2 fois le même jours
     * @param reservation la reservation que l'on veut créer ou modifier
     * @param reservationService le Service que l'on utilise pour comparer à la base de données
     * @return un boolean si le mail est déjà pris ou non
     */
    public static boolean isDateOk(Reservation reservation, ReservationService reservationService) throws ServiceException {
        List<Reservation> reservations = reservationService.findResaByVehicleId(reservation.getVehicle_id());
        LocalDate dateDebut = reservation.getDebut();
        LocalDate dateFin = reservation.getFin();
        boolean dateOK = true;
        for (Reservation listeReservation : reservations) {
            if (listeReservation.getId() != reservation.getId()) {
                if (dateDebut.isAfter(listeReservation.getDebut()) && dateDebut.isBefore(listeReservation.getFin())) {
                    dateOK = false;
                }
                if (dateFin.isAfter(listeReservation.getDebut()) && dateFin.isBefore(listeReservation.getFin())) {
                    dateOK = false;
                }
                if (listeReservation.getDebut().isAfter(dateDebut) && listeReservation.getDebut().isBefore(dateFin)) {
                    dateOK = false;
                }
                if (dateDebut.compareTo(listeReservation.getDebut()) == 0 || dateDebut.compareTo(listeReservation.getFin()) == 0) {
                    dateOK = false;
                }
                if (dateFin.compareTo(listeReservation.getDebut()) == 0 || dateFin.compareTo(listeReservation.getFin()) == 0) {
                    dateOK = false;
                }
            }

        }
        return dateOK;
    }

    /**
     * Vérification que la voiture concernée par la réservation n'est pas réservée plus de 30 jours d'affilée
     * @param reservation la reservation que l'on veut créer ou modifier
     * @param reservationService le Service que l'on utilise pour comparer à la base de données
     * @return un boolean si la réservation est conforme ou non
     */
    public static boolean isNotBooked30Days(Reservation reservation, ReservationService reservationService) throws ServiceException {
        List<Reservation> reservations = reservationService.findResaByVehicleId(reservation.vehicle_id);
        reservations.add(reservation);
        Collections.sort(reservations, Comparator.comparing(Reservation::getDebut));
        boolean isAvailableFor30Days = true;
        int cpt = 0;
        if (reservations.size() > 1) {
            LocalDate lastDate = reservations.get(reservations.size() - 1).fin;
            for (int i = 0; i < 31; i++) {
                LocalDate dateToCheck = lastDate.minusDays(i);
                boolean isReserved = false;
                for (Reservation listeReservation : reservations) {
                    if ((listeReservation.getDebut().isBefore(dateToCheck) || listeReservation.getDebut().isEqual(dateToCheck))
                            && (listeReservation.getFin().isAfter(dateToCheck) || listeReservation.getFin().isEqual(dateToCheck))) {
                        isReserved = true;
                        break;
                    }
                }
                if (!isReserved) {
                    cpt = 0;
                } else {
                    cpt++;
                    if (cpt > 30) {
                        isAvailableFor30Days = false;
                        break;
                    }
                }
            }
        }

        return isAvailableFor30Days;
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
