package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.CarNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.CarDto;
import org.example.model.entity.Car;
import org.example.model.entity.User;
import org.example.repository.CarRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    final private CarRepository carRepository;
    final private Mapper mapper;
    final private UserService userService;

    @Override
    public CarDto createCar(CarDto carDto, UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);

        System.out.println("USEEEERNAAAAME: " + username);
        System.out.println("USEEEER: " + user);

        Car car = Car.builder()
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .location(carDto.getLocation())
                .pricePerDay(carDto.getPricePerDay())
                .user(user)
                .build();
        carRepository.save(car);

        return mapper.carFromEntityToDto(car);
    }

    @Override
    public List<CarDto> getAllCar() {

        return carRepository.findAll()
                .stream()
                .map(car -> CarDto.builder()
                        .id(car.getId())
                        .model(car.getModel())
                        .brand(car.getBrand())
                        .location(car.getLocation())
                        .pricePerDay(car.getPricePerDay())
                        .user(mapper.userFromEntityToDto(car.getUser()))
                        .build())
                .toList();
    }

    @Override
    public CarDto getCarDtoById(Integer id) {
        Car car = carRepository.findById(id).
                orElseThrow(() -> new CarNotFoundException("Car does not exists!!"));

        return mapper.carFromEntityToDto(car);
    }

    @Override
    public CarDto editCar(Integer id, CarDto carDto) {

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car does not exists!!!"));
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setLocation(carDto.getLocation());
        car.setPricePerDay(carDto.getPricePerDay());
        carRepository.save(car);

        return mapper.carFromEntityToDto(car);
    }

    @Override
    public boolean deleteCarById(Integer id) {

        if(!carRepository.existsById(id)){
            throw new CarNotFoundException("Car does not exists!!!");
        }

        if(id == null){
            throw new RuntimeException("Id must not be null!!!");
        }

        carRepository.deleteById(id);

        return true;
    }

    @Override
    public Car getCarById(Integer id) {

        return carRepository.findById(id)
                .orElseThrow(()-> new CarNotFoundException("Car does not exists!!!"));
    }
}
