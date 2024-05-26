package proiectRetele.proiectRetele;



import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
    Car findByLicensePlate(String licensePlate);
}

