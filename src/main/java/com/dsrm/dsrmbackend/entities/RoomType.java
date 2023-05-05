package com.dsrm.dsrmbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "room_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {
    @Id
    private String id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "roomType", cascade = CascadeType.REMOVE)
    private List<Room> rooms;
}
