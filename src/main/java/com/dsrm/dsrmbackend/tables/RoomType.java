package com.dsrm.dsrmbackend.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "room_types")
public class RoomType {
    @Id
    private Long id;
    private String name;
}
