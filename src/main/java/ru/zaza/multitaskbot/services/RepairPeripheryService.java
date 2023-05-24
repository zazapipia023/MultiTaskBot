package ru.zaza.multitaskbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zaza.multitaskbot.entities.RepairPeriphery;
import ru.zaza.multitaskbot.repositories.RepairPeripheryRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RepairPeripheryService {

    private final RepairPeripheryRepository peripheryRepository;

    @Autowired
    public RepairPeripheryService(RepairPeripheryRepository peripheryRepository) {
        this.peripheryRepository = peripheryRepository;
    }

    public RepairPeriphery findOne(int id) {
        Optional<RepairPeriphery> foundPeriphery = peripheryRepository.findById(id);
        return foundPeriphery.orElse(new RepairPeriphery());
    }

    @Transactional
    public void save(RepairPeriphery periphery) {
        peripheryRepository.save(periphery);
    }
}
