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
		printbyte(sendData);

		// get the options from the user
		String[] validData = new String[5];
		validData = packetValidator.getValidData();

		print(validData);

		int port = portNumber(validData[4]);

		// current methodology does work
		byte[] ipAddr = translateIPAddress(validData[0]);
		InetAddress ipAddress = InetAddress.getByAddress(ipAddr);
		// System.out.println(ipAddress);

		// THIS IS NOT THE CORRECT WAY
		// InetAddress ipAddress = InetAddress.getByName(validData[0]);

		// create UDP socket
		DatagramSocket clientSocket = new DatagramSocket();

		// Allocate buffers for the data to be received
		byte[] receiveData = new byte[1024];

		// Create a UDP packet to be sent to the server
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);

		// Send the packet
		clientSocket.send(sendPacket);

		// Create a packet structure to store data sent back by the server
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// Receive data from the server
		clientSocket.receive(receivePacket);

		System.out.println("ReceivePacket: " + receivePacket);
		printbyte(receivePacket.getData());

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

	// debug methods

	public static void printbyte(byte[] b) {
		System.out.println("length = " + b.length);
		for (int i = 0; i < b.length; i++) {
			String spacer = "";
			if (b[i] > 10 || b[i] < 0) {
				spacer = " ";
			}
			System.out.print(spacer + b[i] + spacer);
		}
		System.out.println();
	}

	public static void print(String[] s) {
		System.out.println("length = " + s.length);
		for (int i = 0; i < s.length; i++) {
			System.out.print(s[i] + " ");
		}
	}
}
