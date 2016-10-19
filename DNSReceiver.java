import java.io.*;
import java.net.*;
import java.util.Arrays;

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
