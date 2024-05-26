package proiectRetele.proiectRetele;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Car {
    @Id
    private String licensePlate; // Change this to be the primary key

    private String model;
    private String manufacturer;
    private int year;

    // Constructor implicit
    public Car() {}

    // Constructor cu argumente
    public Car(String licensePlate, String model, String manufacturer, int year) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
    }

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
