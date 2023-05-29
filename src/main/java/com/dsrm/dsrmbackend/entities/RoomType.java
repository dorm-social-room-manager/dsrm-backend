package com.dsrm.dsrmbackend.entities;

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
    @OneToMany(mappedBy = "roomType", cascade={CascadeType.PERSIST})
    private List<Room> rooms;

    @PreRemove
    private void preRemove() {
        rooms.forEach(child -> child.setRoomType(null));
    }
}
