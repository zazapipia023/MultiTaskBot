package ru.zaza.multitaskbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.multitaskbot.entities.Periphery;

@Repository
public interface PeripheryRepository extends JpaRepository<Periphery, Integer> {
}
