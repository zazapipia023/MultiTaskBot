package ru.zaza.multitaskbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.repositories.PeripheryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeripheryService {
    private final PeripheryRepository peripheryRepository;

    @Autowired
    public PeripheryService(PeripheryRepository peripheryRepository) {
        this.peripheryRepository = peripheryRepository;
    }

    public Periphery findOne(int id) {
        Optional<Periphery> foundPeriphery = peripheryRepository.findById(id);
        return foundPeriphery.orElse(null);
    }

    public Periphery findBySerialNumber(String serialNumber) {
        Optional<Periphery> foundPeriphery = Optional.ofNullable(peripheryRepository.findBySerialNumber(serialNumber));
        return foundPeriphery.orElse(null);
    }

    public List<Periphery> findAll() {
        return peripheryRepository.findAll();
    }

    @Transactional
    public void save(Periphery periphery) {
        peripheryRepository.save(periphery);
    }

    @Transactional
    public void delete(Periphery periphery) {
        peripheryRepository.delete(periphery);
    }
}
