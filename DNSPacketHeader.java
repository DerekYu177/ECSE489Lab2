import java.util.BitSet;

public class DNSPacketHeader {
  private BitSet header = new BitSet(96);
  // constructor

  // need to accept proper inputs
  public DNSPacketHeader() {

  }

  public createPacket() {
    
  }

  // getters

  // should be a random 16 bit number
  public BitSet getID() {
    return header.get(0, 17);
  }

  // 0 is query, 1 is response
  public BitSet getQR() {
    return header.get(17);
  }

  // set this field to 0000, standard query
  public BitSet getOPCODE() {
    return header.get(18, 22);
  }

  // response queries
  public BitSet getAA() {
    return header.get(22);
  }

  // whether the message was truncated because it was too long
  public BitSet getTC() {
    return header.get(23);
  }

  // set to 1 for recursion
  public BitSet getRD() {
    return header.get(24);
  }

  // reponse whether the server can support recursion
  public BitSet getRA() {
    return header.get(25);
  }

  // bits for future use, set to 000
  public BitSet getZ() {
    return header.get(26, 29);
  }

  // 0 no error condition
  // 1 format error; unable to interpret query
  // 2 server failure;
  // 3 name error; domain name DNE (NOTFOUND)
  // 4 not supported;
  // 5 refused
  public BitSet getRCODE() {
    return header.get(29, 33);
  }

  // the number of entries, set to 1
  public BitSet getQDCOUNT() {
    return header.get(33, 49);
  }

  //response, number of RR's in the answer
  public BitSet getANCOUNT() {
    return header.get(49, 65);
  }

  // response, the number of name server resource records
  public BitSet getNSCOUNT() {
    return header.get(65, 81);
  }

  // the number of RR's in the additional records section
  public BitSet getARCOUNT() {
    return header.get(81, 97);
  }

  // setters

  // should be a random 16 bit number
  public void setID(int[] value) {
    for (int i = 0; i < 17; i++ ){
      header.set(i, value[i]);
    }
  }

  // 0 is query 1 is response
  public void setQR(int value) {
    header.set(17, value);
  }

  // set this field to 0000, standard query
  public void setOPCODE(int[] value) {
    header.set(18, value[0]);
    header.set(19, value[1]);
    header.set(20, value[2]);
    header.set(21, value[3]);
  }

  public void setAA(int value) {
    header.set(22, value);
  }

  // whether the message was truncated because it was too long
  public void setTC(int value) {
    header.set(23, value);
  }

  // set to 1 for recursion
  public void setRD(int value) {
    header.set(24, value);
  }

  public void setRA(int value) {
    header.set(25, value);
  }

  // bits for future use, set to 000
  public void setZ(int[] value) {
    header.set(26, value[0]);
    header.set(27, value[1]);
    header.set(28, value[2]);
  }

  // 0 no error condition
  // 1 format error; unable to interpret query
  // 2 server failure;
  // 3 name error; domain name DNE (NOTFOUND)
  // 4 not supported;
  // 5 refused
  public void setRCODE(int[] value) {
    header.set(29, value[0]);
    header.set(30, value[1]);
    header.set(31, value[2]);
    header.set(32, value[3]);
  }

  // the number of entries, set to 1
  public void setQDCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 33, value[i]);
    }
  }

  //response, number of RR's in the answer
  public void setANCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 49, value[i]);
    }
  }

  // response, the number of name server resource records
  public void setNSCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 65, value[i]);
    }
  }

  // the number of RR's in the additional records section
  public void setARCOUNT(int[] value) {
    for (int i = 0; i < 16; i++) {
      header.set(i + 81, value[i]);
    }
  }
}
