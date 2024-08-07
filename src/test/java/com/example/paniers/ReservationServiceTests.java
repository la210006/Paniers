package com.example.paniers;

import com.example.paniers.Dao.ReservationDao;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTests {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setDateCommande(LocalDate.now());
        reservation.setQuantite(10);
        reservation.setPrix(100.0);
    }

    @Test
    void testGetAllReservations() {
        when(reservationDao.findAll()).thenReturn(Arrays.asList(reservation));
        assertNotNull(reservationService.getAllReservations());
        verify(reservationDao, times(1)).findAll();
    }

    @Test
    void testGetReservationById() {
        when(reservationDao.findById(1L)).thenReturn(Optional.of(reservation));
        assertEquals(reservation, reservationService.getReservationById(1L));
    }

    @Test
    void testAddReservation() {
        when(reservationDao.save(reservation)).thenReturn(reservation);
        reservationService.addReservation(reservation);
        verify(reservationDao, times(1)).save(reservation);
    }

    @Test
    void testDeleteReservation() {
        reservationService.deleteReservation(1L);
        verify(reservationDao, times(1)).deleteById(1L);
    }
}
