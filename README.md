# ECSE489Lab2

The purpose of this lab is to be able create a simple CLI for DNS packets

##### Built using Java 5, Java version 1.8.0_101

### Interface:

`DnsClient	[-t	timeout]	[-r	max-retries] [-port] [-mx|-ns] @server name`

where the arguments are defined as follows.
  - `timeout` (optional) gives how long to wait, in seconds, before retransmitting an unanswered query. Default value: 5.
  - `max-retries` (optional)  is  the  maximum  number  of  times  to  retransmit  an  unanswered query before giving up. Default value: 3.
  - `port` (optional) is the UDP port number of the DNS server. Default value: 53.
  - `mx | -ns` flags (optional) indicate whether to send a MX (mail server) or NS (name server) query. At most one of these can be given, and if neither is given then the client should send a type A (IP address) query.
  - `server` (required) is the IPv4 address of the DNS server, in a.b.c.d. format
  - `name` (required) is the domain name to query for.

Technical details:
  1. `DNSClient` passes `args` to `DNSPacketValidator`
  2. `DNSPacketValidator` calls `extract()` on `args` to extract explicit user inputs. These are passed to `DNSBitManipulator` when creating query packets
  3. `DNSBitManipulator` has `createHeader` and `createBody` methods
  4. `createHeader()` uses `BitSet` to create header packets
  5. `createBody()` dynamically creates QNAME using `ArrayList<byte>`
  6. Individual packets are returned back to `DNSPacketValidator`, and stitched together for the final query packet.
  7. This packet is then placed inside a UDP datagram and sent to the desired address specified in the interface
  8. The return packet is captured and placed into a receiveBuffer, but there is no implementation that will interpret this packet.

Copied from my github @ https://github.com/DerekYu177/ECSE489Lab2

n.b. `ctrl`+`shift`+`m` for live display if you are viewing this from Atom
