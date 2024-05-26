package proiectRetele.proiectRetele;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

@Component
public class SocketServer {

    private static Map<String, List<Socket>> subscriptions = new HashMap<>();
    private static CarService carService;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SocketServer(CarService carService) {
        SocketServer.carService = carService;
    }

    public static void startServer(CarService carService) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Socket Server is listening on port 12345");
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (InputStream input = socket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                 OutputStream output = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(output, true)) {

                String request;
                while ((request = reader.readLine()) != null) {
                    System.out.println("Received request: " + request);
                    String[] requestData = request.split(" ");
                    String action = requestData[0];
                    String licensePlate = requestData[1];
                    String model = requestData.length > 2 ? requestData[2] : null;
                    String manufacturer = requestData.length > 3 ? requestData[3] : null;
                    int year = requestData.length > 4 ? Integer.parseInt(requestData[4]) : 0;

                    switch (action) {
                        case "query":
                            List<Car> cars = carService.getCarsByLicensePlates(Collections.singletonList(licensePlate));
                            if (cars.isEmpty()) {
                                writer.println("[]");
                            } else {
                                writer.println(objectMapper.writeValueAsString(cars));
                            }
                            subscriptions.computeIfAbsent(licensePlate, k -> new ArrayList<>()).add(socket);
                            break;
                        case "update":
                            Car updatedCar = carService.updateCar(licensePlate, model, manufacturer, year);
                            if (updatedCar != null) {
                                notifyClients(licensePlate, updatedCar);
                                writer.println("updated");
                            } else {
                                writer.println("Car not found");
                            }
                            break;
                        case "delete":
                            carService.deleteCar(licensePlate);
                            notifyClients(licensePlate, null);
                            writer.println("deleted");
                            break;
                        default:
                            writer.println("Invalid action");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void notifyClients(String licensePlate, Car car) {
            if (subscriptions.containsKey(licensePlate)) {
                String carJson = "";
                try {
                    carJson = car == null ? "null" : objectMapper.writeValueAsString(car);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Socket> clientSockets = subscriptions.get(licensePlate);
                List<Socket> socketsToRemove = new ArrayList<>();
                for (Socket clientSocket : clientSockets) {
                    if (clientSocket.isClosed()) {
                        socketsToRemove.add(clientSocket);
                    } else {
                        try {
                            PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                            clientWriter.println("update " + licensePlate + " " + carJson);
                        } catch (SocketException e) {
                            e.printStackTrace();
                            socketsToRemove.add(clientSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                clientSockets.removeAll(socketsToRemove);
            }
        }
    }
}
