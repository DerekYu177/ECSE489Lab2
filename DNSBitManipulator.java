import java.util.Arrays;
import java.util.BitSet;
import java.util.ArrayList;
import java.nio.charset.*;

public class DNSBitManipulator {

  // for HEADERS
  // the placement of each section is determined by the head
  private int ID_head = 0;
  private int QR_head = 17;
  private int OPCODE_head = 18;
  private int AA_head = 22;
  private int TC_head = 23;
  private int RD_head = 24;
  private int RA_head = 25;
  private int Z_head = 26;
  private int RCODE_head = 29;
  private int QDCOUNT_head = 33;
  private int ANCOUNT_head = 49;
  private int NSCOUNT_head = 65;
  private int ARCOUNT_head = 81;

  // get statements require the first and last bit
  // the convention is inclusive first bit and exclusive last bit
  private int ID_tail = QR_head;
  private int QR_tail = OPCODE_head;
  private int OPCODE_tail = AA_head;
  private int AA_tail = TC_head;
  private int TC_tail = RD_head;
  private int RD_tail = RA_head;
  private int RA_tail = Z_head;
  private int Z_tail = RCODE_head;
  private int RCODE_tail = QDCOUNT_head;
  private int QDCOUNT_tail = ANCOUNT_head;
  private int ANCOUNT_tail = NSCOUNT_head;
  private int NSCOUNT_tail = ARCOUNT_head;
  private int ARCOUNT_tail = 97;

  // constructor
  public DNSBitManipulator() {}

  public byte[] createHeader() {
    BitSet header = new BitSet(96);

    // set ID to be a random 16 bit number
    setArrBit(new int[] {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, ID_head, header);

    // set QR to be a query (0)
    setBit(0, QR_head, header);

    // set OPCODE to be standard query (0000)
    setArrBit(new int[] {0,0,0,0}, OPCODE_head, header);

    // AA bit is not read in queries
    setBit(0, AA_head, header);

    // set TC to show non-truncated message
    setBit(0, TC_head, header);

    // set RD to be recursive query (1)
    setBit(1, RD_head, header);

    // RA bit is not read in queries
    setBit(0, RA_head, header);

    // set Z to normal query (000)
    setArrBit(new int[] {0, 0, 0}, Z_head, header);

    // RCODE is not read in queries
    setArrBit(new int[] {0, 0, 0, 0}, RCODE_head, header);

    // set QDCOUNT to show single query (0)
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, QDCOUNT_head, header);

    // ANCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, ANCOUNT_head, header);

    // NSCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, NSCOUNT_head, header);

    // ARCOUNT is not read in queries
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, ARCOUNT_head, header);

    return header.toByteArray();
  }

  public byte[] createQuestion(String[] validData) {
    String domainName = validData[1];
    String queryType = validData[5];

    byte[] QNAME = domainNameConverter(domainName);
    byte[] QTYPE = queryTypeSetter(queryType);
    byte[] QCLASS = queryClassSetter();

    byte[] question = new byte[QNAME.length + QTYPE.length + QCLASS.length];

    for (int i = 0; i < QNAME.length; i++) {
      question[i] = QNAME[i];
    }

    for (int j = 0; j < QTYPE.length; j++) {
      question[QNAME.length + j] = QTYPE[j];
    }

    for (int k = 0; k < QCLASS.length; k++) {
      question[QNAME.length + QTYPE.length + k] = QCLASS[k];
    }

    return question;
  }

  public byte[] domainNameConverter(String domainName) {
    ArrayList<Byte> QNAME_ArrayList = new ArrayList<Byte>();
    String[] domainNameSections = domainName.split("\\.");

    for (int i = 0; i < domainNameSections.length; i++) {
      String section = domainNameSections[i];

      // prepend each section with its length
      QNAME_ArrayList.add((byte)section.length());

      // convert each section into a byte[]
      byte[] sectionByte = section.getBytes(StandardCharsets.US_ASCII);

      // add the bytes from sectionByte into the QNAME_ArrayList
      for (int j = 0; j < sectionByte.length; j++) {
        QNAME_ArrayList.add(sectionByte[i]);
      }
    }

    // null byte to signify end of QNAME
    QNAME_ArrayList.add((byte)0);

    QNAME_ArrayList.trimToSize();

    byte[] QNAME = new byte[QNAME_ArrayList.size()];
    return QNAME;
  }

  public byte[] queryTypeSetter(String queryType) {
    switch (queryType) {
      case "MX":
        return new byte[] {0, 0, 0, 15};
      case "NS":
        return new byte[] {0, 0, 0, 2};
      default:
        return new byte[] {0, 0, 0, 1};
    }
  }

  public byte[] queryClassSetter() {
    // set QCLASS to show Internet query (0001)
    return new byte[] {0, 0, 0, 1};
  }

  // getters

  public int[] getArrBit(int firstBit, int lastBit, BitSet msg) {
    int[] result = new int[lastBit - firstBit];

    // get statements are [firstBit, lastBit)
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
