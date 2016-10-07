import java.io.*;

public class DNSPacketValidator {
  private String[] input;

  // constructor
  public DNSPacketValidator(String[] input) {
    this.input = input;
  }

  public byte[] createPacket() {
    // create empty packet full
    byte[] packet = new byte[40];

    // we are going to create the header and the message separately
    byte[] packetHeader = new byte[24];
    DNSPacketHeader dnsHeader = new DNSPacketHeader();
    packetHeader = dnsHeader.createPacket();

    byte[] packetBody = new byte[16];

    // TODO: identify and extract relevant material for headers
    String[] validData = extract(this.input);

    // TODO: stitch packetHeader and packetBody together

    packet = new byte[20];

    // TODO: create header

    // TODO: create query with proper inputs

    // TODO: combine

    return packet;
  }

  public String[] extract(String[] data) {
    String timeOut = null, maxRetries = null, port = null, queryType = null;

    for (int i = 0; i < data.length; i++) {
      String option = data[i];
      switch (option) {
        case "-t":
          timeOut = data[i + 1];
          break;
        case "-r":
          maxRetries = data[i + 1];
          break;
        case "-p":
          port = data[i + 1];
          break;
        case "-mx":
          queryType = "MX";
          break;
        case "-ns":
          queryType = "NS";
          break;
        default: break;
      }
    }

    // dnsName has a "@" appended to the front
    String dnsNamePartial = data[data.length - 2];
    String dnsName = dnsNamePartial.substring(1);

    String domainName = data[data.length - 1];

    String[] result = new String[] {dnsName, domainName, timeOut, maxRetries, port, queryType};
    return result;
  }
}
