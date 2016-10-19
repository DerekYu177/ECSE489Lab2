import java.io.*;
import java.net.*;
import java.util.Arrays;


// Create a packet structure to store data sent back by the server
// DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

// Receive data from the server
// clientSocket.receive(receivePacket);

public class DNSReceiver{
int tries;
int timeTaken;
byte[] receivedPacket = new byte[2048];

  public void DNSReceived(int timeTaken, int tries)
    {

    System.out.println("Response received after " + timeTaken + " seconds (" +
  						tries +" retries)");
            }

}
