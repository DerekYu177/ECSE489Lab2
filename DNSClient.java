import java.io.*;
import java.net.*;

public class DNSClient {

	public static void main(String args[]) throws Exception
	{
		// Open a reader to input from the command line
		System.out.println("Hi there, please enter characters");
		// String input;
		// BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		// input = inFromUser.readLine();

		System.out.println("Sending to DNSPacketValidator...");
		DNSPacketValidator packetValidator = new DNSPacketValidator(args);
		byte[] sendData = packetValidator.createPacket();

		// Create a UDP socket
		// (Note, when no port number is specified, the OS will assign an arbitrary one)
		// DatagramSocket clientSocket = new DatagramSocket();

		// This also has to be input by the user
		// In this case, "localhost" maps to the so-called loop-back address, 127.0.0.1
		// byte[] ipAddress = byte
		// InetAddress ipAddress = InetAddress.getByName("localhost");

		// Allocate buffers for the data to be sent and received
		// byte[] sendData = new byte[1024];
		// byte[] receiveData = new byte[1024];

		// Create a UDP packet to be sent to the server
		// This involves specifying the sender's address and port number
		// DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);

		// Send the packet
		// clientSocket.send(sendPacket);

		// Create a packet structure to store data sent back by the server
		// DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// Receive data from the server
		// clientSocket.receive(receivePacket);

		// Extract the sentence (as a String object) from the received byte stream
		// String modifiedSentence = new String(receivePacket.getData());
		// System.out.println("From self: " + sentence);

		// Close the socket
		// clientSocket.close();
	}
}
