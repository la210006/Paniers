package com.example.paniers;

import com.example.paniers.Controleurs.ReservationControleur;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservationControleurTests {

    @InjectMocks
    private ReservationControleur reservationControleur;

    @Mock
    private ReservationService reservationService;

    private MockMvc mockMvc;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationControleur).build();
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setDateCommande(LocalDate.now());
        reservation.setQuantite(10);
        reservation.setPrix(100.0);
    }

    @Test
    void testGetAllReservations() throws Exception {
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservation));
        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantite").value(10))
                .andExpect(jsonPath("$[0].prix").value(100.0));
    }

    @Test
    void testGetReservationById() throws Exception {
        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantite").value(10))
                .andExpect(jsonPath("$.prix").value(100.0));
    }

    @Test
    void testAddReservation() throws Exception {
        doNothing().when(reservationService).addReservation(any(Reservation.class));
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateCommande\":\"2024-08-09\", \"quantite\":10, \"prix\":100.0}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteReservation() throws Exception {
        doNothing().when(reservationService).deleteReservation(1L);
        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isOk());
    }
}
