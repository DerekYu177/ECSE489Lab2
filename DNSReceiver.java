import java.io.*;
import java.net.*;
import java.util.Arrays;


public class DNSReceiver{
ByteBuffer packetdata;
int tries;
int timeTaken;
byte[] receivedPacket = new byte[2048];

public DNSreader (byte[] receivedData)
	{

		//Initialize byte buffer
    //put array of data in it
		packetData = ByteBuffer.allocate(receivedData.length);
		packetData.put(receivedData);
		packetData.position(0);

	}





//printout if sent ID is not equal to receive ID
System.out.println("ERROR\tDifferent Query ID and Response ID");
//Do not execute rest of code if IDs are different



  public void DNSReceived(int timeTaken, int tries)
    {

    System.out.println("Response received after " + timeTaken + " seconds (" +
  						tries +" retries)");
            }

}
