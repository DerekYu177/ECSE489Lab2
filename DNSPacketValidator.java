import java.io.*;

public class DNSPacketValidator {
  private String[] input;
  private String[] validData = new String[5];

  // constructor
  public DNSPacketValidator(String[] input) {
    this.input = input;
    this.validData = extract(input);
  }

  public byte[] createPacket() {
    String[] validData = extract(this.input);

    DNSBitManipulator dnsbit = new DNSBitManipulator();

    byte[] packetHeader = dnsbit.createHeader();
    byte[] packetQuestion = dnsbit.createQuestion(validData);

    DNSClient.printbyte(packetHeader);

    // we declare a new byte[] to be the final packet sent to the DNS server
    // we then stitch packetHeader and packetQuestion into it
    byte[] packet = new byte[packetHeader.length + packetQuestion.length];

    for (int i = 0; i < packetHeader.length; i++) {
      packet[i] = packetHeader[i];
    }

    for (int j = 0; j < packetQuestion.length; j++) {
      packet[packetHeader.length + j] = packetQuestion[j];
    }

    return packet;
  }

  public String[] extract(String[] data) {
    String timeOut = null, maxRetries = null, port = null, queryType = null;

    // detects the required identifier
    // sets the appropriate variable, which must be the value after the identifier
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

    // dnsName must be the second to last variable in the interface
    // dnsName has a "@" appended to the front
    String dnsNamePartial = data[data.length - 2];
    String dnsName = dnsNamePartial.substring(1);

    // domainName is the last variable in the interface
    // as specified in the README
    String domainName = data[data.length - 1];

    return new String[] {dnsName, domainName, timeOut, maxRetries, port, queryType};
  }

  // getters

  public String[] getValidData() {
    return this.validData;
  }
}
