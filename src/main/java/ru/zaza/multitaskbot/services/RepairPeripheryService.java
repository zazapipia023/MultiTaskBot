package ru.zaza.multitaskbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zaza.multitaskbot.repositories.RepairPeripheryRepository;

@Service
public class RepairPeripheryService {

    private final RepairPeripheryRepository peripheryRepository;

    @Autowired
    public RepairPeripheryService(RepairPeripheryRepository peripheryRepository) {
        this.peripheryRepository = peripheryRepository;
    }
}
