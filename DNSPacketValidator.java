import java.io.*;

public class DNSPacketValidator {
  private String[] input;
  private String[] validData;

  // constructor
  public DNSPacketValidator(String[] input) {
    this.input = input;
  }

  public byte[] createPacket() {
    // create empty packet full
    byte[] packet = new byte[40];

    // header and the question bodies are created separately
    byte[] packetHeader = new byte[12];
    byte[] packetQuestion = new byte[8];

    String[] validData = extract(this.input);

    DNSBitManipulator dnsbit = new DNSBitManipulator();

    packetHeader = dnsbit.createHeader();
    packetQuestion = dnsbit.createQuestion(validData);

    return packetHeader;
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

  // getters

  public String[] getValidData() {
    return validData;
  }
}
