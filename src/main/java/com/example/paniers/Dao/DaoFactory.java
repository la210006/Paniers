package com.example.paniers.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ReservationDao reservationDao;

    public ClientDao getClientDao() {
        return clientDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }
}
