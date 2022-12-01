package com.dsrm.dsrmbackend.tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room_types")
public class RoomType {
    @Id
    private Long id;
    private String name;
}
