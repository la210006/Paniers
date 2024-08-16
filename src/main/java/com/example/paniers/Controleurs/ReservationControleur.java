package com.example.paniers.Controleurs;

import com.example.paniers.Models.Client;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationControleur {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Map<String, Object>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Reservation reservation : reservations) {
            Map<String, Object> reservationMap = new LinkedHashMap<>();
            reservationMap.put("id_reservation", reservation.getId());
            reservationMap.put("date_commande", reservation.getDateCommande());
            reservationMap.put("quantite", reservation.getQuantite());
            reservationMap.put("prix", reservation.getPrix());

            // Ajouter les détails du client
            Client client = reservation.getClient();
            if (client != null) {
                Map<String, Object> clientMap = new LinkedHashMap<>();
                clientMap.put("client_id", client.getId());
                clientMap.put("prenom", client.getPrenom());
                clientMap.put("nom", client.getNom());
                clientMap.put("commune", client.getCommune());
                reservationMap.put("client", clientMap);
            }

            response.add(reservationMap);
        }

        return response;
    }

    @PostMapping
    public void addReservation(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);

        if (reservation == null) {
            return null; // Ou tu peux lancer une exception
        }

        Map<String, Object> reservationMap = new LinkedHashMap<>();
        reservationMap.put("id_reservation", reservation.getId());
        reservationMap.put("date_commande", reservation.getDateCommande());
        reservationMap.put("quantite", reservation.getQuantite());
        reservationMap.put("prix", reservation.getPrix());

        Client client = reservation.getClient();
        if (client != null) {
            Map<String, Object> clientMap = new LinkedHashMap<>();
            clientMap.put("client_id", client.getId());
            clientMap.put("prenom", client.getPrenom());
            clientMap.put("nom", client.getNom());
            clientMap.put("commune", client.getCommune());
            reservationMap.put("client", clientMap);
        }

        return reservationMap;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
