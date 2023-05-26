package ru.zaza.multitaskbot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Repair_periphery")
@Getter
@Setter
public class RepairPeriphery {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "unit_name")
    private String name;

    @Column(name = "serial_number")
    private String serialNumber;
}
