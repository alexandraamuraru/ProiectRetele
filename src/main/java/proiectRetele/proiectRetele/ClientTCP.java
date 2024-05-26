package proiectRetele.proiectRetele;

import java.io.*;
import java.net.Socket;

public class ClientTCP {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientTCP(String address, int port) throws IOException {
        socket = new Socket(address, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendRequest(String request) {
        writer.println(request);
    }

    public String readResponse() throws IOException {
        return reader.readLine();
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            // Creăm mai mulți clienți pentru testare
            ClientTCP client1 = new ClientTCP("localhost", 12345);
            ClientTCP client2 = new ClientTCP("localhost", 12345);
            ClientTCP client3 = new ClientTCP("localhost", 12345);
            ClientTCP client4 = new ClientTCP("localhost", 12345);
            ClientTCP client5 = new ClientTCP("localhost", 12345);

            // Lista mașinilor pentru interogare
            String[] licensePlates = {"ABC123", "DEF456", "GHI789", "JKL012", "MNO345", "PQR678"};

            // Clienți care fac interogări pentru toate mașinile
            for (String plate : licensePlates) {
                client1.sendRequest("query " + plate);
                System.out.println("Client1 Response: " + client1.readResponse());

                client2.sendRequest("query " + plate);
                System.out.println("Client2 Response: " + client2.readResponse());

                client3.sendRequest("query " + plate);
                System.out.println("Client3 Response: " + client3.readResponse());

                client4.sendRequest("query " + plate);
                System.out.println("Client4 Response: " + client4.readResponse());

                client5.sendRequest("query " + plate);
                System.out.println("Client5 Response: " + client5.readResponse());
            }

            // Actualizare mașini
            client1.sendRequest("update DEF456 ModelX Tesla 2022");
            System.out.println("Client1 Response: " + client1.readResponse());

            // Notificări pentru actualizare
            System.out.println("Client1 Notification: " + client1.readResponse());
            System.out.println("Client2 Notification: " + client2.readResponse());
            System.out.println("Client3 Notification: " + client3.readResponse());
            System.out.println("Client4 Notification: " + client4.readResponse());
            System.out.println("Client5 Notification: " + client5.readResponse());

            client3.sendRequest("update ABC123 ModelY Tesla 2022");
            System.out.println("Client3 Response: " + client3.readResponse());

            // Notificări pentru actualizare
            System.out.println("Client3 Notification: " + client3.readResponse());
            System.out.println("Client1 Notification: " + client1.readResponse());
            System.out.println("Client2 Notification: " + client2.readResponse());
            System.out.println("Client4 Notification: " + client4.readResponse());
            System.out.println("Client5 Notification: " + client5.readResponse());

            client4.sendRequest("update GHI789 ModelZ Tesla 2023");
            System.out.println("Client4 Response: " + client4.readResponse());

            // Notificări pentru actualizare
            System.out.println("Client4 Notification: " + client4.readResponse());
            System.out.println("Client1 Notification: " + client1.readResponse());
            System.out.println("Client2 Notification: " + client2.readResponse());
            System.out.println("Client3 Notification: " + client3.readResponse());
            System.out.println("Client5 Notification: " + client5.readResponse());

            // Ștergere mașini
            client2.sendRequest("delete DEF456");
            System.out.println("Client2 Response: " + client2.readResponse());

            // Notificări pentru ștergere
            System.out.println("Client1 Notification: " + client1.readResponse());
            System.out.println("Client2 Notification: " + client2.readResponse());
            System.out.println("Client3 Notification: " + client3.readResponse());
            System.out.println("Client4 Notification: " + client4.readResponse());
            System.out.println("Client5 Notification: " + client5.readResponse());

            client5.sendRequest("delete JKL012");
            System.out.println("Client5 Response: " + client5.readResponse());

            // Notificări pentru ștergere
            System.out.println("Client1 Notification: " + client1.readResponse());
            System.out.println("Client2 Notification: " + client2.readResponse());
            System.out.println("Client3 Notification: " + client3.readResponse());
            System.out.println("Client4 Notification: " + client4.readResponse());
            System.out.println("Client5 Notification: " + client5.readResponse());

            // Închidere conexiuni
            client1.close();
            client2.close();
            client3.close();
            client4.close();
            client5.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
