import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.nio.ByteBuffer;


public class DNSClient {

	public static void main(String args[]) throws Exception
	{
		DNSPacketValidator packetValidator = new DNSPacketValidator(args);

		// creating packet
		byte[] sendData = packetValidator.createPacket();
		System.out.println(Arrays.toString(sendData));

		// get the options from the user
		String[] validData = new String[5];
		validData = packetValidator.getValidData();

		System.out.println("validData.length = " + validData.length);
		for (int i = 0; i < validData.length; i++) {
			System.out.println(validData[i]);
		}

		int port = portNumber(validData[4]);
		byte[] ipAddr = translateIPAddress(validData[0]);
		InetAddress ipAddress = InetAddress.getByAddress(ipAddr);
		System.out.println(ipAddress);

		// create UDP socket
		DatagramSocket clientSocket = new DatagramSocket();

		// Allocate buffers for the data to be sent and received
		byte[] receiveData = new byte[1024];

		// Create a UDP packet to be sent to the server
		// This involves specifying the sender's address and port number
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);

		// Send the packet
		clientSocket.send(sendPacket);

		// Create a packet structure to store data sent back by the server
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		System.out.println(receiveData);

		// Receive data from the server
		// clientSocket.receive(receivePacket);

		// Extract the sentence (as a String object) from the received byte stream
		// String modifiedSentence = new String(receivePacket.getData());
		// System.out.println("From self: " + sentence);

		// Close the socket
		// clientSocket.close();
	}

	public static int portNumber(String port) {
		// default is port = 53, else the value assigned through args
		return (port != null) ? Integer.valueOf(port) : 53;
	}

	public static byte[] translateIPAddress(String ip) {
		byte[] result = new byte[4];

		// break into individual octets
		String[] subIp = ip.split("\\.");
		for (int i = 0; i < subIp.length; i++) {
			int octet = Integer.valueOf(subIp[i]);
			result[i] = intToByte(octet)[3];
			System.out.println("Original: " + octet + " Altered: " + result[i]);
		}

		return result;
	}

	public static byte[] intToByte(final int i) {
    ByteBuffer bb = ByteBuffer.allocate(4);
    bb.putInt(i);
		System.out.println(bb.array().length);
		for (int j = 0; j < bb.array().length; j++) {
			int value = (bb.array()[j] < 0) ? bb.array()[j] + 256 : bb.array()[j];
			System.out.println(value);
		}
    return bb.array();
	}
}
