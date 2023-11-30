package com.traveljoy.room.repository;

import com.traveljoy.room.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
