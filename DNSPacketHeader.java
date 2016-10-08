import java.util.BitSet;

public class DNSPacketHeader {
  private BitSet header = new BitSet(96);
  // constructor

  // need to accept proper inputs (?)
  public DNSPacketHeader() {}

  public byte[] createPacket() {

    // set ID to be a random 16 bit number
    setID(new int[] {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0});

    // set QR to be a query (0)
    setQR(0);

    // set OPCODE to be standard query (0000)
    setOPCODE(new int[] {0,0,0,0});

    // AA bit is not read in queries
    setAA(0);

    // set TC to show non-truncated message
    setTC(0);

    // set RD to be recursive query (1)
    setRD(1);

    // RA bit is not read in queries
    setRA(0);

    // set Z to normal query (000)
    setZ(new int[] {0, 0, 0});

    // RCODE is not read in queries
    setRCODE(new int[] {0, 0, 0, 0});

    // set QDCOUNT to show single query (0)
    setQDCOUNT(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});

    // ANCOUNT is not read in queries
    setANCOUNT(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

    // NSCOUNT is not read in queries
    setNSCOUNT(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

    // ARCOUNT is not read in queries
    setARCOUNT(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

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

  public void setID(int[] value) {
    for (int i = 0; i < 17; i++ ){
      header.set(i, value[i]);
    }
  }

  public void setQR(int value) {
    header.set(17, value);
  }

  public void setOPCODE(int[] value) {
    header.set(18, value[0]);
    header.set(19, value[1]);
    header.set(20, value[2]);
    header.set(21, value[3]);
  }

  public void setAA(int value) {
    header.set(22, value);
  }

  public void setTC(int value) {
    header.set(23, value);
  }

  public void setRD(int value) {
    header.set(24, value);
  }

  public void setRA(int value) {
    header.set(25, value);
  }

  public void setZ(int[] value) {
    header.set(26, value[0]);
    header.set(27, value[1]);
    header.set(28, value[2]);
  }

  public void setRCODE(int[] value) {
    header.set(29, value[0]);
    header.set(30, value[1]);
    header.set(31, value[2]);
    header.set(32, value[3]);
  }

  public void setQDCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 33, value[i]);
    }
  }

  public void setANCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 49, value[i]);
    }
  }

  public void setNSCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 65, value[i]);
    }
  }

  public void setARCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 81, value[i]);
    }
  }
}
