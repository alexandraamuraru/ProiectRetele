package proiectRetele.proiectRetele;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getCarsByLicensePlates(List<String> licensePlates) {
        return carRepository.findAll().stream()
                .filter(car -> licensePlates.contains(car.getLicensePlate()))
                .collect(Collectors.toList());
    }

    public Car updateCar(String licensePlate, String model, String manufacturer, int year) {
        Car car = carRepository.findById(licensePlate).orElse(null); // Now licensePlate is used as the key
        if (car != null) {
            car.setModel(model);
            car.setManufacturer(manufacturer);
            car.setYear(year);
            carRepository.save(car);
        }
        return car;
    }

    public void deleteCar(String licensePlate) {
        Car car = carRepository.findById(licensePlate).orElse(null); // Now licensePlate is used as the key
        if (car != null) {
            carRepository.delete(car);
        }
    }
}
