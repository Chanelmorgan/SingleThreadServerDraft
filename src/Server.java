import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader  inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(1234);

            while (true) {
                /* this accepts method waits for a client connection the program won't advance until a client has a
                 connection. Once connected, a Socket object is returned that can be used to communicate with the client
                */
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                // Even though we are having mainly short messages, we use this for efficiency
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while (true) {
                    String messageFromClient = bufferedReader.readLine();
                    System.out.println("Client " + messageFromClient);

                    bufferedWriter.write("MSG Received! ");
                    bufferedWriter.newLine();
                    bufferedWriter.flush(); // automatically flush because it won't be full

                    if (messageFromClient.equalsIgnoreCase("BYE")) {
                        break;
                    }
                }

                // when the client has said they want to disconnect ( by sending "bye")
                // we want to close all the resources that the client was using

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
