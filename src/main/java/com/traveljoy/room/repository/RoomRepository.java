package com.traveljoy.room.repository;

import com.traveljoy.member.entity.Member;
import com.traveljoy.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

}
