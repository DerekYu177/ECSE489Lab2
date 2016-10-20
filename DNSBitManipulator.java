import java.util.Arrays;
import java.util.BitSet;
import java.util.ArrayList;
import java.nio.charset.*;

public class DNSBitManipulator {

  private final int BYTE_SIZE = 8;

  // for createHeader
  // the placement of each section is determined by the head
  private int ID_head = 0;
  private int QR_head = 16;
  private int OPCODE_head = 17;
  private int AA_head = 21;
  private int TC_head = 22;
  private int RD_head = 23;
  private int RA_head = 24;
  private int Z_head = 25;
  private int RCODE_head = 28;
  private int QDCOUNT_head = 32;
  private int ANCOUNT_head = 48;
  private int NSCOUNT_head = 64;
  private int ARCOUNT_head = 80;

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
  private int ARCOUNT_tail = 95;

  // constructor
  public DNSBitManipulator() {}

  public byte[] createHeader() {
    int BIT_SIZE = 96;
    BitSet header = new BitSet(BIT_SIZE);
    byte[] packetHeader = new byte[12];

    // set ID to be a random 16 bit number
    setArrBit(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, ID_head, header);

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

    // set the last bit to true to prevent truncation
    header.set(BIT_SIZE);

    // account for the truncation prevention bit
    System.out.println("Cardinality = " + header.cardinality());

    // this method is not to be trusted
    // packetHeader = header.toByteArray();

    packetHeader = toByteArray(header, BIT_SIZE);

    System.out.println("packetHeader.length = " + packetHeader.length);

    return packetHeader;
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
        QNAME_ArrayList.add(sectionByte[j]);
      }
    }

    // null byte to signify end of QNAME
    QNAME_ArrayList.add((byte)0);

    QNAME_ArrayList.trimToSize();

    byte[] QNAME = new byte[QNAME_ArrayList.size()];

    for (int i = 0; i < QNAME.length; i++) {
      QNAME[i] = QNAME_ArrayList.get(i);
    }
    return QNAME;
  }

  public byte[] queryTypeSetter(String queryType) {
    if (queryType == null) {
      return new byte[] {0, 0, 0, 1};
    }
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

  public byte[] toByteArray(BitSet b, int size) {

    // account for extra bits which require an extra byte
		int extra = (size % BYTE_SIZE > 0) ? 1 : 0;
		int byte_size = size / BYTE_SIZE + extra;

    // initialize the return array
		byte[] result = new byte[byte_size];

    // loop with double variables for placing answers into result and individual bits
		for (int byte_head = 0, write_head = 0; byte_head < size; byte_head+=BYTE_SIZE, write_head++) {

      int[] octet = new int[BYTE_SIZE];
			for (int byte_point = 0; byte_point < BYTE_SIZE; byte_point++) {
				int value = b.get(byte_point + byte_head) ? 1 : 0;
				octet[byte_point] = value;
			}

			// octet now is filled with the 8 consecutive integers [0,1]
			result[write_head] = bitsToBytes(octet);
		}

		return result;
	}

  public byte bitsToBytes(int[] int_arr) {
    double double_val = 0;

    for (int pos = 0; pos < BYTE_SIZE; pos++) {
      int exp = BYTE_SIZE - (pos + 1);
      if (int_arr[pos] == 1) {
        double_val = double_val + Math.pow(2, exp);
      }
    }

    return (byte) double_val;
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
