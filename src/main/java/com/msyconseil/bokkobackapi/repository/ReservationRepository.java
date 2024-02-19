package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationModel, Integer> {

    public ReservationModel save(ReservationModel reservationModel);

}
