import java.util.Arrays;
import java.util.BitSet;

public class DNSPacketHeader {
  private BitSet header = new BitSet(96);
  // constructor

  // need to accept proper inputs (?)
  public DNSPacketHeader() {}

  public byte[] createPacket() {
    int ID = 0;
    int QR = 17;
    int OPCODE = 18;
    int AA = 22;
    int TC = 23;
    int RD = 24;
    int RA = 25;
    int Z = 26;
    int RCODE = 29;
    int QDCOUNT = 33;
    int ANCOUNT = 49;
    int NSCOUNT = 65;
    int ARCOUNT = 81;

    // set ID to be a random 16 bit number
    setArrBit(new int[] {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, ID);

    // set QR to be a query (0)
    setBit(0, QR);

    // set OPCODE to be standard query (0000)
    setArrBit(new int[] {0,0,0,0}, OPCODE);

    // AA bit is not read in queries
    setBit(0, AA);

    // set TC to show non-truncated message
    setBit(0, TC);

    // set RD to be recursive query (1)
    setBit(1, RD);

    // RA bit is not read in queries
    setBit(0, RA);

    // set Z to normal query (000)
    setArrBit(new int[] {0, 0, 0}, Z);

    // RCODE is not read in queries
    setArrBit(new int[] {0, 0, 0, 0}, RCODE);

    // set QDCOUNT to show single query (0)
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, QDCOUNT);

    // ANCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, ANCOUNT);

    // NSCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, NSCOUNT);

    // ARCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, ARCOUNT);

    System.out.println(header.toString());
    return header.toByteArray();
  }

  // getters

  public BitSet getID() {
    return header.get(0, 17);
  }

  public int getQR() {
    int result = (header.get(17)) ? 1 : 0;
    return result;
  }

  public BitSet getOPCODE() {
    return header.get(18, 22);
  }

  public int getAA() {
    int result = (header.get(22)) ? 1 : 0;
    return result;
  }

  public int getTC() {
    int result = (header.get(23)) ? 1 : 0;
    return result;
  }

  public int getRD() {
    int result = (header.get(24)) ? 1 : 0;
    return result;
  }

  public int getRA() {
    int result = (header.get(25)) ? 1 : 0;
    return result;
  }

  public BitSet getZ() {
    return header.get(26, 29);
  }

  public BitSet getRCODE() {
    return header.get(29, 33);
  }

  public BitSet getQDCOUNT() {
    return header.get(33, 49);
  }

  public BitSet getANCOUNT() {
    return header.get(49, 65);
  }

  public BitSet getNSCOUNT() {
    return header.get(65, 81);
  }

  public BitSet getARCOUNT() {
    return header.get(81, 97);
  }

  // setters

  public void setBit(int value, int location) {
    boolean v = (value == 1) ? true : false;
    header.set(location, v);
  }

  public void setArrBit(int[] value, int location) {
    for (int i = 0; i < value.length; i++) {
        setBit(value[i], i + location);
    }
  }
}
