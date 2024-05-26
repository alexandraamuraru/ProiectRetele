package proiectRetele.proiectRetele;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProiectReteleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProiectReteleApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CarRepository carRepository) {
		return (args) -> {
			// Adaugă câteva mașini în baza de date pentru testare
			carRepository.save(new Car("ABC123", "Model S", "Tesla", 2020));
			carRepository.save(new Car("DEF456", "Model 3", "Tesla", 2019));
			carRepository.save(new Car("GHI789", "Model X", "Tesla", 2021));
			carRepository.save(new Car("JKL012", "Model Y", "Tesla", 2018));
			carRepository.save(new Car("MNO345", "Civic", "Honda", 2017));
			carRepository.save(new Car("PQR678", "Accord", "Honda", 2016));
		};
	}

	@Bean
	public CommandLineRunner startSocketServer(CarService carService) {
		return args -> {
			new Thread(() -> SocketServer.startServer(carService)).start();
		};
	}
}
