package ru.zaza.multitaskbot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Periphery")
@Getter
@Setter
public class Periphery {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "unit_name")
    private String name;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "per_description")
    private String description;

    @Column(name = "is_repairing")
    private Boolean isRepairing;
}
