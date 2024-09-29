/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.workTrucksMonitoring.service;

import utcluj.isp.curs67.workTrucksMonitoring.model.Truck;

import java.util.List;

import static java.util.stream.Collectors.toList;

import utcluj.isp.curs67.workTrucksMonitoring.repository.ITruckRepository;

/**
 * @author mihai.hulea
 */
public class TrackingVehicleService implements ITracking {
    ITruckRepository trucksRepository;

    public TrackingVehicleService(ITruckRepository trucksRepository) {
        this.trucksRepository = trucksRepository;
    }

    @Override
    public void addNewTruckRecord(double latitude, double longitude, String plateNumber) {
        Truck t = new Truck(latitude, longitude, plateNumber);
        trucksRepository.save(t);
    }

    @Override
    public List<Truck> getAllTruckRecords() {
        return trucksRepository.readAll();
    }

    @Override
    public List<Truck> getAllUnique() {
        return trucksRepository.readAll().stream().distinct().collect(toList());
    }

    @Override
    public List<Truck> getALlRecordsByTruck(String plateNumber) throws TruckNotFoundException {
        List<Truck> list = trucksRepository.readAll().stream().filter(t -> t.getPlateNumber().equals(plateNumber)).collect(toList());
        if(list.size()>0)
            return list;
        else
            throw new TruckNotFoundException("No records found for plate number: " + plateNumber);
    }

}
