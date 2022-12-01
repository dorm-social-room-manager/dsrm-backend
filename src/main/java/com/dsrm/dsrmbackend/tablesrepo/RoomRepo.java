package com.dsrm.dsrmbackend.tablesrepo;

import com.dsrm.dsrmbackend.tables.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Long> {
}
