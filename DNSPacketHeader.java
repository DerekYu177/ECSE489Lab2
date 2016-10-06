public class DNSPacketHeader {
  private byte[] header = new byte[24];
  // constructor

  // need to accept proper inputs
  public DNSPacketHeader() {
  }

  // getters

  // should be a random 16 bit number
  public byte[] getID() {
      return header[];
  }

  // 0 is query 1 is response
  public byte[] getQR() {
    return header[];
  }

  // set this field to 0000, standard query
  public byte[] getOPCODE() {
    return header[];
  }

  // response queries
  public byte[] getAA() {
    return header[];
  }

  // whether the message was truncated because it was too long
  public byte[] getTC() {
    return header[];
  }

  // set to 1 for recursion
  public byte[] getRD() {
    return header[];
  }

  // reponse whether the server can support recursion
  public byte[] getRA() {
    return header[];
  }

  // bits for future use, set to 000
  public byte[] getZ() {
    return header[];
  }

  // 0 no error condition
  // 1 format error; unable to interpret query
  // 2 server failure;
  // 3 name error; domain name DNE (NOTFOUND)
  // 4 not supported;
  // 5 refused
  public byte[] getRCODE() {
    return header[];
  }

  // the number of entries, set to 1
  public byte[] getQDCOUNT() {
    return header[];
  }

  //response, number of RR's in the answer
  public byte[] getANCOUNT() {
    return header[];
  }

  // response, the number of name server resource records
  public byte[] getNSCOUNT() {
    return header[];
  }

  // the number of RR's in the additional records section
  public byte[] getARCOUNT() {
    return header[];
  }

  // setters
}
