package com.traveljoy.reservation.repository;

import com.traveljoy.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>, ReservationRepositoryCustom{

}
