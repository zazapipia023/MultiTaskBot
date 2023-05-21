package ru.zaza.multitaskbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.multitaskbot.entities.RepairPeriphery;

@Repository
public interface RepairPeripheryRepository extends JpaRepository<RepairPeriphery, Integer> {
}
