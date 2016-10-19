import java.util.Arrays;
import java.util.BitSet;

public class DNSBitManipulator {

  // constructor
  public DNSBitManipulator(String[] validData) {
    String dnsName, domainName, timeOut, maxRetries, port, queryType;
    dnsName = validData[0];
    domainName = validData[1];
    timeOut = validData[2];
    maxRetries = validData[3];
    port = validData[4];
    queryType = validData[5];
  }

  public byte[] createHeader() {
    BitSet header = new BitSet(96);

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
    setArrBit(new int[] {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, ID, header);

    // set QR to be a query (0)
    setBit(0, QR, header);

    // set OPCODE to be standard query (0000)
    setArrBit(new int[] {0,0,0,0}, OPCODE, header);

    // AA bit is not read in queries
    setBit(0, AA, header);

    // set TC to show non-truncated message
    setBit(0, TC, header);

    // set RD to be recursive query (1)
    setBit(1, RD, header);

    // RA bit is not read in queries
    setBit(0, RA, header);

    // set Z to normal query (000)
    setArrBit(new int[] {0, 0, 0}, Z, header);

    // RCODE is not read in queries
    setArrBit(new int[] {0, 0, 0, 0}, RCODE, header);

    // set QDCOUNT to show single query (0)
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, QDCOUNT, header);

    // ANCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, ANCOUNT, header);

    // NSCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, NSCOUNT, header);

    // ARCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, ARCOUNT, header);

    System.out.println(header.toString());
    return header.toByteArray();
  }

  public byte[] createQuestion() {
    // the question field QNAME is at most 63 bytes long
    BitSet question = new BitSet(536);

  }

  public void getHeader(byte[] header) {
    
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

  // getters

  public int[] getArrBit(int firstBit, int lastBit, BitSet msg) {
    int[] result = new int[lastBit - firstBit];
    for (int i = 0; i < lastBit - firstBit; i++) {
      result[i - firstBit] = getBit(firstBit + i, msg);
    }
    return result;
  }

  public int getBit(int bit, BitSet msg) {
    int result  = msg.get(bit) ? 1 : 0;
    return result;
  }

  // setters

  public void setBit(int value, int location, BitSet msg) {
    boolean v = (value == 1) ? true : false;
    msg.set(location, v);
  }

  public void setArrBit(int[] value, int location, BitSet msg) {
    for (int i = 0; i < value.length; i++) {
      setBit(value[i], i + location, msg);
    }
  }
}
