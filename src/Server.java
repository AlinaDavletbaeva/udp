import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(10101);
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(
                buffer,
                0,
                buffer.length
        );
        System.out.println("Listen on 10101...");
        while (true) {

            socket.receive(packet);
            System.out.println(
                    packet.getAddress() + ": " + packet.getPort());
            String message = new String(buffer, 0, packet.getLength());
            System.out.println(message);

            String responseMessage = "Received: " + message;
            byte[] responseData = responseMessage.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseData,
                    0, responseData.length,
                    packet.getAddress(),
                    packet.getPort()
            );
            socket.send(responsePacket);
        }

       Path path = Paths.get("")
                .toAbsolutePath()
                .getParent()
                .getParent();
        System.out.println(path);
        String basePath = "src/ru/udp";
        String mainFile = basePath + "/Server.java";
        String copyFile = basePath + "/txt.file";
        File file = new File(mainFile);
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (File currentFile : list) {
                System.out.println("is file: " + currentFile.isFile());
                System.out.println("is directory: " + currentFile.isDirectory());
                System.out.println(currentFile.getAbsolutePath());
            }
        }
        String mainFilePath = file.getAbsolutePath();
        try {

            try (BufferedReader reader = new BufferedReader(new FileReader(mainFilePath));
                 PrintWriter writer = new PrintWriter(copyFile)) {
                String line = reader.readLine();

                while (line != null) {
                    System.out.println(line);
                    writer.println(line);
                    line = reader.readLine();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("incorrect path");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("fail with close");
            e.printStackTrace();
        }
    }
}

