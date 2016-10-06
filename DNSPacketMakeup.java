public class DNSPacketMakeup {
  public static void main(String args[]) {

    // should be a random 16 bit number
    public int ID = 0b0000000000000000;

    // 0 is query 1 is response
    public int QR = 0b0;

    // set this field to 0000, standard query
    public int OPCODE = 0b0000;

    // response queries
    public int AA = 0b0000;

    // whether the message was truncated because it was too long
    public int TC = 0b0;

    // set to 1 for recursion
    public int RD = 0b0;

    // reponse whether the server can support recursion
    public int RA = 0b0;

    // bits for future use, set to 000
    public int Z = 0b000;

    // 0 no error condition
    // 1 format error; unable to interpret query
    // 2 server failure;
    // 3 name error; domain name DNE (NOTFOUND)
    // 4 not supported;
    // 5 refused
    public int RCODE = 0b0000;

    // the number of entries, set to 1
    public int QDCOUNT = 0b0000000000000000;

    //response, number of RR's in the answer
    public int ANCOUNT = 0b0000000000000000;

    // response, the number of name server resource records
    public int NSCOUNT = 0b0000000000000000;

    // the number of RR's in the additional records section
    public int ARCOUNT = 0b0000000000000000;
  }
}
