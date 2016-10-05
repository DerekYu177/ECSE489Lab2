import java.io.*;
import org.apache.commons.validator.routines;


public class DNSPacketMaker {
  private String input[];

  // constructor
  public DNSPacketMaker(String input[]) {
    this.input = input;
    inputChecker(input);
  }

  public void debugPrint() {
    System.out.println("Printing inputs...");
    for (int i = 0; i < input.length; i++) {
      System.out.println(input[i]);
    }
  }

  // checks for the "required" fields - read README on GitHub for more information
  public void inputChecker(String input[]) {
    System.out.println("Checking for errors");


    // the second to last element must be the server name
    String serverName = input[input.length - 2];

    // the last element must be the domain name
    String domainName = input[input.length - 1];

    // so far we have ignored all of the other variables -t -r -p -mx|ns
    // TODO: create methods that check for these other variables
    
    if (validServerName(serverName) || validName(domainName)) {
      createPacket();
    } else {
      throw new IllegalArgumentException();
    }
  }

  public byte[] createPacket() {
    // TODO: create packets
  }

  public boolean validServerName(String serverName) {
    if (!serverName.startsWith("@")) {
      System.out.println("Failed to start with @");
      return false;
    }

    // remove the first char to get the ipAddress
    String ipAddress = serverName.substring(1);
    String[] octets = ipAddress.split("\\.");
    int ipLength = 4;

    if (octets.length != ipLength) {
      System.out.println("Failed to be correct length, length is:" + octets.length);
      return false;
    }

    for (int i = 0; i < octets.length; i++) {
      if (Integer.parseInt(octets[i]) > 255 || Integer.parseInt(octets[i]) < 0) {
        System.out.println("Failed to be within IP range");
        return false;
      }
    }

    return true;
  }

  public boolean validName(String domainName) {
    // TODO: do this.
    return true
  }
}
