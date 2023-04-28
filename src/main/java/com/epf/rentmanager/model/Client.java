package com.epf.rentmanager.model;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PersistenceDelegate;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Service
public class Client {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public Client(int id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public Client() {
    }

    /**
     * Vérification de l'âge du client
     * @param client le client que l'on veut créer ou modifier
     * @return un boolean s'il est majeur ou non
     */
    public static boolean isLegal(Client client) {
        Period period = Period.between(client.getNaissance(),LocalDate.now());
        System.out.println(period.getYears());
        return period.getYears() >= 18;
    }

    /**
     * Vérification de la validité du nom et du prénom du client
     * @param client le client que l'on veut créer ou modifier
     * @return un boolean si le nom et le prénom font 3 caractères ou plus
     */
    public static boolean isNameLongEnough(Client client) {
        return client.getNom().length() >= 3 && client.getPrenom().length() >= 3;
    }

    /**
     * Vérification de la validité du mail du client
     * @param client le client que l'on veut créer ou modifier
     * @param clientService le Service que l'on utilise pour comparer à la base de données
     * @return un boolean si le mail est déjà pris ou non
     */
    public static boolean isMailValid(Client client, ClientService clientService){
        boolean mailisvalid = true;
        try {
            Client mail = clientService.findByEmail(client.getEmail());
            if (mail != null && client.getEmail() == mail.getEmail() && client.getId() != mail.getId()) {
                mailisvalid = false;
            }
        }catch (ServiceException e){
            e.printStackTrace();
        }
        return mailisvalid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}
