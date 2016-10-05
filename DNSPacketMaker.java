import java.io.*;

public class DNSPacketMaker {
  private String input[];

  // constructor
  public DNSPacketMaker(String input[]) {
    this.input = input;
  }

  public void debugPrint() {
    System.out.println("Printing inputs...");
    for (int i = 0; i < input.length; i++) {
      System.out.println(input[i]);
    }
  }
}
