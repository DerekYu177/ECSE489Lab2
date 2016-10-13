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
    setArrBit(value, 0);
	}

  public void setQR(int value) {
    setBit(value, 17);
  }

  public void setOPCODE(int[] value) {
    setArrBit(value, 18);
  }

  public void setAA(int value) {
    setBit(value, 22);
  }

  public void setTC(int value) {
    setBit(value, 13);
  }

  public void setRD(int value) {
    setBit(value, 24);
  }

  public void setRA(int value) {
    setBit(value, 25);
  }

  public void setZ(int[] value) {
    setArrBit(value, 26);
  }

  public void setRCODE(int[] value) {
    setArrBit(value, 29);
  }

  public void setQDCOUNT(int[] value) {
    setArrBit(value, 33);
  }

  public void setANCOUNT(int[] value) {
    setArrBit(value, 49);
  }

  public void setNSCOUNT(int[] value) {
    setArrBit(value, 65);
  }

  public void setARCOUNT(int[] value) {
    setArrBit(value, 81);
  }

  public void setBit(int value, int location) {
    boolean v = (value == 1) ? true : false;
    header.set(location, v);
  }

  public void setArrBit(int[] value, int location) {
    for (int i = 0; i < value.length; i++) {
        setBit(i + location, value[i]);
    }
  }
}
