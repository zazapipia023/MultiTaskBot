package ru.zaza.multitaskbot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Client")
@Getter
@Setter
public class Client {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "bot_action")
    private String action;

}
