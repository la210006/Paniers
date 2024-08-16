package com.example.paniers.Dao;

import com.example.paniers.Models.Reservation;

import java.util.List;

public interface ReservationDao {
    Reservation findById(Long id);
    List<Reservation> findAll();
    void save(Reservation reservation);
    void update(Reservation reservation);
    void delete(Long id);
}
