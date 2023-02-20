package com.dsrm.dsrmbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "room_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {
    @Id
    private String id;
    private String name;
}
