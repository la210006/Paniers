package com.example.paniers.Controleurs;

import com.example.paniers.Models.Client;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientControleur {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Map<String, Object>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Client client : clients) {
            Map<String, Object> clientMap = new LinkedHashMap<>();
            clientMap.put("client_id", client.getId());
            clientMap.put("prenom", client.getPrenom());
            clientMap.put("nom", client.getNom());
            clientMap.put("commune", client.getCommune());

            List<Map<String, Object>> reservationsList = new ArrayList<>();
            for (Reservation reservation : client.getReservations()) {
                Map<String, Object> reservationMap = new LinkedHashMap<>();
                reservationMap.put("id_reservation", reservation.getId());
                reservationMap.put("date_commande", reservation.getDateCommande());
                reservationMap.put("quantite", reservation.getQuantite());
                reservationMap.put("prix", reservation.getPrix());
                reservationsList.add(reservationMap);
            }
            clientMap.put("reservations", reservationsList);

            response.add(clientMap);
        }

        return response;
    }

    @PostMapping
    public void addClient(@RequestBody Client client) {
        clientService.addClient(client);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);

        if (client == null) {
            return null; // Ou tu peux lancer une exception
        }

        Map<String, Object> clientMap = new LinkedHashMap<>();
        clientMap.put("client_id", client.getId());
        clientMap.put("prenom", client.getPrenom());
        clientMap.put("nom", client.getNom());
        clientMap.put("commune", client.getCommune());

        List<Map<String, Object>> reservationsList = new ArrayList<>();
        for (Reservation reservation : client.getReservations()) {
            Map<String, Object> reservationMap = new LinkedHashMap<>();
            reservationMap.put("id_reservation", reservation.getId());
            reservationMap.put("date_commande", reservation.getDateCommande());
            reservationMap.put("quantite", reservation.getQuantite());
            reservationMap.put("prix", reservation.getPrix());
            reservationsList.add(reservationMap);
        }
        clientMap.put("reservations", reservationsList);

        return clientMap;
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
