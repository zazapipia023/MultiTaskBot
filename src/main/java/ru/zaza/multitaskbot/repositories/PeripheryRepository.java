package ru.zaza.multitaskbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.multitaskbot.entities.Periphery;

import java.util.List;

@Repository
public interface PeripheryRepository extends JpaRepository<Periphery, Integer> {

    Periphery findBySerialNumber(String serialNumber);

    List<Periphery> findAllByIsRepairingIsTrue();

    void deleteAllByIsRepairingTrue();
}
