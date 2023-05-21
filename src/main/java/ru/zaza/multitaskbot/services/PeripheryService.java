package ru.zaza.multitaskbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zaza.multitaskbot.repositories.PeripheryRepository;

@Service
@Transactional(readOnly = true)
public class PeripheryService {

    private final PeripheryRepository peripheryRepository;

    @Autowired
    public PeripheryService(PeripheryRepository peripheryRepository) {
        this.peripheryRepository = peripheryRepository;
    }


}
