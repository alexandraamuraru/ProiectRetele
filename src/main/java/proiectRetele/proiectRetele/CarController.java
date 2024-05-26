package proiectRetele.proiectRetele;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/query")
    public List<Car> queryCars(@RequestParam List<String> licensePlates) {
        return carService.getCarsByLicensePlates(licensePlates);
    }

    @PostMapping("/update")
    public Car updateCar(@RequestBody CarDto carDto) {
        return carService.updateCar(carDto.getLicensePlate(), carDto.getModel(), carDto.getManufacturer(), carDto.getYear());
    }

    @DeleteMapping("/delete")
    public void deleteCar(@RequestBody CarDto carDto) {
        carService.deleteCar(carDto.getLicensePlate());
    }
}

// DTO Class
class CarDto {
    private String licensePlate;
    private String model;
    private String manufacturer;
    private int year;

    // Getteri și setteri
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
