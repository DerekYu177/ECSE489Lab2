# ECSE489Lab2
Let's try this one again

The purpose of this lab is to be able create a simple CLI for DNS packets

Interface:

`DnsClient	[-t	timeout]	[-r	max-retries] [-port] [-mx|-ns] @server name`

where the arguments are defined as follows.
  - `timeout` (optional) gives how long to wait, in seconds, before retransmitting an unanswered query. Default value: 5.
  - `max-retries` (optional)  is  the  maximum  number  of  times  to  retransmit  an  unanswered query before giving up. Default value: 3.
  - `port` (optional) is the UDP port number of the DNS server. Default value: 53.
  - `mx | -ns` flags (optional) indicate whether to send a MX (mail server) or NS (name server) query. At most one of these can be given, and if neither is given then the client should send a type A (IP address) query.
  - `server` (required) is the IPv4 address of the DNS server, in a.b.c.d. format
  - `name` (required) is the domain name to query for.

Technical details:
1. `DNSPacketMaker`, which is called form `UDPClient`
2. `DNSPacketInterpreter`, which decodes and displays replies for the `UDPClient`
