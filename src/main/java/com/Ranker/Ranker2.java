package com.Ranker;

import com.Data.KeyWord;
import com.Data.TweetUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.Data.KeyWord.ExtractKeyWord;

/**
 * Model 2: Text-Based Analysis
 * 1. Based on tweets text contents, find the most representative keywords.
 */
public class Ranker2 {
    String username;

    public Ranker2(String username){
        System.out.println(username);
        this.username = username;
    }

    public List<String> findKeyWords(List<TweetUnit> tlist) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> results = new ArrayList<>();
        List<KeyWord> keywords;
        for(TweetUnit tweet: tlist){
            sb.append(tweet.text + " ");
        }
        String test = "INTRODUCTION\n" +
                "\n" +
                "The Transmission Control Protocol (TCP) is intended for use as a highly\n" +
                "reliable host-to-host protocol between hosts in packet-switched computer\n" +
                "communication networks, and in interconnected systems of such networks.\n" +
                "\n" +
                "This document describes the functions to be performed by the\n" +
                "Transmission Control Protocol, the program that implements it, and its\n" +
                "interface to programs or users that require its services.\n" +
                "\n" +
                "1.1.  Motivation\n" +
                "\n" +
                "  Computer communication systems are playing an increasingly important\n" +
                "  role in military, government, and civilian environments.  This\n" +
                "  document focuses its attention primarily on military computer\n" +
                "  communication requirements, especially robustness in the presence of\n" +
                "  communication unreliability and availability in the presence of\n" +
                "  congestion, but many of these problems are found in the civilian and\n" +
                "  government sector as well.\n" +
                "\n" +
                "  As strategic and tactical computer communication networks are\n" +
                "  developed and deployed, it is essential to provide means of\n" +
                "  interconnecting them and to provide standard interprocess\n" +
                "  communication protocols which can support a broad range of\n" +
                "  applications.  In anticipation of the need for such standards, the\n" +
                "  Deputy Undersecretary of Defense for Research and Engineering has\n" +
                "  declared the Transmission Control Protocol (TCP) described herein to\n" +
                "  be a basis for DoD-wide inter-process communication protocol\n" +
                "  standardization.\n" +
                "\n" +
                "  TCP is a connection-oriented, end-to-end reliable protocol designed to\n" +
                "  fit into a layered hierarchy of protocols which support multi-network\n" +
                "  applications.  The TCP provides for reliable inter-process\n" +
                "  communication between pairs of processes in host computers attached to\n" +
                "  distinct but interconnected computer communication networks.  Very few\n" +
                "  assumptions are made as to the reliability of the communication\n" +
                "  protocols below the TCP layer.  TCP assumes it can obtain a simple,\n" +
                "  potentially unreliable datagram service from the lower level\n" +
                "  protocols.  In principle, the TCP should be able to operate above a\n" +
                "  wide spectrum of communication systems ranging from hard-wired\n" +
                "  connections to packet-switched or circuit-switched networks.\n" +
                "\n" +
                "\n" +
                "                                                                [Page 1]\n" +
                " \n" +
                "                                                          September 1981\n" +
                "Transmission Control Protocol\n" +
                "Introduction\n" +
                "\n" +
                "\n" +
                "\n" +
                "  TCP is based on concepts first described by Cerf and Kahn in [1].  The\n" +
                "  TCP fits into a layered protocol architecture just above a basic\n" +
                "  Internet Protocol [2] which provides a way for the TCP to send and\n" +
                "  receive variable-length segments of information enclosed in internet\n" +
                "  datagram \"envelopes\".  The internet datagram provides a means for\n" +
                "  addressing source and destination TCPs in different networks.  The\n" +
                "  internet protocol also deals with any fragmentation or reassembly of\n" +
                "  the TCP segments required to achieve transport and delivery through\n" +
                "  multiple networks and interconnecting gateways.  The internet protocol\n" +
                "  also carries information on the precedence, security classification\n" +
                "  and compartmentation of the TCP segments, so this information can be\n" +
                "  communicated end-to-end across multiple networks.\n" +
                "\n" +
                "                           Protocol Layering\n" +
                "\n" +
                "                        +---------------------+\n" +
                "                        |     higher-level    |\n" +
                "                        +---------------------+\n" +
                "                        |        TCP          |\n" +
                "                        +---------------------+\n" +
                "                        |  internet protocol  |\n" +
                "                        +---------------------+\n" +
                "                        |communication network|\n" +
                "                        +---------------------+\n" +
                "\n" +
                "                                Figure 1\n" +
                "\n" +
                "  Much of this document is written in the context of TCP implementations\n" +
                "  which are co-resident with higher level protocols in the host\n" +
                "  computer.  Some computer systems will be connected to networks via\n" +
                "  front-end computers which house the TCP and internet protocol layers,\n" +
                "  as well as network specific software.  The TCP specification describes\n" +
                "  an interface to the higher level protocols which appears to be\n" +
                "  implementable even for the front-end case, as long as a suitable\n" +
                "  host-to-front end protocol is implemented.\n" +
                "\n" +
                "1.2.  Scope\n" +
                "\n" +
                "  The TCP is intended to provide a reliable process-to-process\n" +
                "  communication service in a multinetwork environment.  The TCP is\n" +
                "  intended to be a host-to-host protocol in common use in multiple\n" +
                "  networks.\n" +
                "\n" +
                "1.3.  About this Document\n" +
                "\n" +
                "  This document represents a specification of the behavior required of\n" +
                "  any TCP implementation, both in its interactions with higher level\n" +
                "  protocols and in its interactions with other TCPs.  The rest of this\n" +
                "\n" +
                "\n" +
                "[Page 2]\n" +
                " \n" +
                "September 1981\n" +
                "                                           Transmission Control Protocol\n" +
                "                                                            Introduction\n" +
                "\n" +
                "\n" +
                "\n" +
                "  section offers a very brief view of the protocol interfaces and\n" +
                "  operation.  Section 2 summarizes the philosophical basis for the TCP\n" +
                "  design.  Section 3 offers both a detailed description of the actions\n" +
                "  required of TCP when various events occur (arrival of new segments,\n" +
                "  user calls, errors, etc.) and the details of the formats of TCP\n" +
                "  segments.\n" +
                "\n" +
                "1.4.  Interfaces\n" +
                "\n" +
                "  The TCP interfaces on one side to user or application processes and on\n" +
                "  the other side to a lower level protocol such as Internet Protocol.\n" +
                "\n" +
                "  The interface between an application process and the TCP is\n" +
                "  illustrated in reasonable detail.  This interface consists of a set of\n" +
                "  calls much like the calls an operating system provides to an\n" +
                "  application process for manipulating files.  For example, there are\n" +
                "  calls to open and close connections and to send and receive data on\n" +
                "  established connections.  It is also expected that the TCP can\n" +
                "  asynchronously communicate with application programs.  Although\n" +
                "  considerable freedom is permitted to TCP implementors to design\n" +
                "  interfaces which are appropriate to a particular operating system\n" +
                "  environment, a minimum functionality is required at the TCP/user\n" +
                "  interface for any valid implementation.\n" +
                "\n" +
                "  The interface between TCP and lower level protocol is essentially\n" +
                "  unspecified except that it is assumed there is a mechanism whereby the\n" +
                "  two levels can asynchronously pass information to each other.\n" +
                "  Typically, one expects the lower level protocol to specify this\n" +
                "  interface.  TCP is designed to work in a very general environment of\n" +
                "  interconnected networks.  The lower level protocol which is assumed\n" +
                "  throughout this document is the Internet Protocol [2].\n" +
                "\n" +
                "1.5.  Operation\n" +
                "\n" +
                "  As noted above, the primary purpose of the TCP is to provide reliable,\n" +
                "  securable logical circuit or connection service between pairs of\n" +
                "  processes.  To provide this service on top of a less reliable internet\n" +
                "  communication system requires facilities in the following areas:\n" +
                "\n" +
                "    Basic Data Transfer\n" +
                "    Reliability\n" +
                "    Flow Control\n" +
                "    Multiplexing\n" +
                "    Connections\n" +
                "    Precedence and Security\n" +
                "\n" +
                "  The basic operation of the TCP in each of these areas is described in\n" +
                "  the following paragraphs.\n" +
                "\n" +
                "\n" +
                "                                                                [Page 3]\n" +
                " \n" +
                "                                                          September 1981\n" +
                "Transmission Control Protocol\n" +
                "Introduction\n" +
                "\n" +
                "\n" +
                "\n" +
                "  Basic Data Transfer:\n" +
                "\n" +
                "    The TCP is able to transfer a continuous stream of octets in each\n" +
                "    direction between its users by packaging some number of octets into\n" +
                "    segments for transmission through the internet system.  In general,\n" +
                "    the TCPs decide when to block and forward data at their own\n" +
                "    convenience.\n" +
                "\n" +
                "    Sometimes users need to be sure that all the data they have\n" +
                "    submitted to the TCP has been transmitted.  For this purpose a push\n" +
                "    function is defined.  To assure that data submitted to a TCP is\n" +
                "    actually transmitted the sending user indicates that it should be\n" +
                "    pushed through to the receiving user.  A push causes the TCPs to\n" +
                "    promptly forward and deliver data up to that point to the receiver.\n" +
                "    The exact push point might not be visible to the receiving user and\n" +
                "    the push function does not supply a record boundary marker.\n" +
                "\n" +
                "  Reliability:\n" +
                "\n" +
                "    The TCP must recover from data that is damaged, lost, duplicated, or\n" +
                "    delivered out of order by the internet communication system.  This\n" +
                "    is achieved by assigning a sequence number to each octet\n" +
                "    transmitted, and requiring a positive acknowledgment (ACK) from the\n" +
                "    receiving TCP.  If the ACK is not received within a timeout\n" +
                "    interval, the data is retransmitted.  At the receiver, the sequence\n" +
                "    numbers are used to correctly order segments that may be received\n" +
                "    out of order and to eliminate duplicates.  Damage is handled by\n" +
                "    adding a checksum to each segment transmitted, checking it at the\n" +
                "    receiver, and discarding damaged segments.\n" +
                "\n" +
                "    As long as the TCPs continue to function properly and the internet\n" +
                "    system does not become completely partitioned, no transmission\n" +
                "    errors will affect the correct delivery of data.  TCP recovers from\n" +
                "    internet communication system errors.\n" +
                "\n" +
                "  Flow Control:\n" +
                "\n" +
                "    TCP provides a means for the receiver to govern the amount of data\n" +
                "    sent by the sender.  This is achieved by returning a \"window\" with\n" +
                "    every ACK indicating a range of acceptable sequence numbers beyond\n" +
                "    the last segment successfully received.  The window indicates an\n" +
                "    allowed number of octets that the sender may transmit before\n" +
                "    receiving further permission.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "[Page 4]\n" +
                " \n" +
                "September 1981\n" +
                "                                           Transmission Control Protocol\n" +
                "                                                            Introduction\n" +
                "\n" +
                "\n" +
                "\n" +
                "  Multiplexing:\n" +
                "\n" +
                "    To allow for many processes within a single Host to use TCP\n" +
                "    communication facilities simultaneously, the TCP provides a set of\n" +
                "    addresses or ports within each host.  Concatenated with the network\n" +
                "    and host addresses from the internet communication layer, this forms\n" +
                "    a socket.  A pair of sockets uniquely identifies each connection.\n" +
                "    That is, a socket may be simultaneously used in multiple\n" +
                "    connections.\n" +
                "\n" +
                "    The binding of ports to processes is handled independently by each\n" +
                "    Host.  However, it proves useful to attach frequently used processes\n" +
                "    (e.g., a \"logger\" or timesharing service) to fixed sockets which are\n" +
                "    made known to the public.  These services can then be accessed\n" +
                "    through the known addresses.  Establishing and learning the port\n" +
                "    addresses of other processes may involve more dynamic mechanisms.\n" +
                "\n" +
                "  Connections:\n" +
                "\n" +
                "    The reliability and flow control mechanisms described above require\n" +
                "    that TCPs initialize and maintain certain status information for\n" +
                "    each data stream.  The combination of this information, including\n" +
                "    sockets, sequence numbers, and window sizes, is called a connection.\n" +
                "    Each connection is uniquely specified by a pair of sockets\n" +
                "    identifying its two sides.\n" +
                "\n" +
                "    When two processes wish to communicate, their TCP's must first\n" +
                "    establish a connection (initialize the status information on each\n" +
                "    side).  When their communication is complete, the connection is\n" +
                "    terminated or closed to free the resources for other uses.\n" +
                "\n" +
                "    Since connections must be established between unreliable hosts and\n" +
                "    over the unreliable internet communication system, a handshake\n" +
                "    mechanism with clock-based sequence numbers is used to avoid\n" +
                "    erroneous initialization of connections.\n" +
                "\n" +
                "  Precedence and Security:\n" +
                "\n" +
                "    The users of TCP may indicate the security and precedence of their\n" +
                "    communication.  Provision is made for default values to be used when\n" +
                "    these features are not needed.Elements of the Internetwork System\n" +
                "\n" +
                "  The internetwork environment consists of hosts connected to networks\n" +
                "  which are in turn interconnected via gateways.  It is assumed here\n" +
                "  that the networks may be either local networks (e.g., the ETHERNET) or\n" +
                "  large networks (e.g., the ARPANET), but in any case are based on\n" +
                "  packet switching technology.  The active agents that produce and\n" +
                "  consume messages are processes.  Various levels of protocols in the\n" +
                "  networks, the gateways, and the hosts support an interprocess\n" +
                "  communication system that provides two-way data flow on logical\n" +
                "  connections between process ports.\n" +
                "\n" +
                "  The term packet is used generically here to mean the data of one\n" +
                "  transaction between a host and its network.  The format of data blocks\n" +
                "  exchanged within the a network will generally not be of concern to us.\n" +
                "\n" +
                "  Hosts are computers attached to a network, and from the communication\n" +
                "  network's point of view, are the sources and destinations of packets.\n" +
                "  Processes are viewed as the active elements in host computers (in\n" +
                "  accordance with the fairly common definition of a process as a program\n" +
                "  in execution).  Even terminals and files or other I/O devices are\n" +
                "  viewed as communicating with each other through the use of processes.\n" +
                "  Thus, all communication is viewed as inter-process communication.\n" +
                "\n" +
                "  Since a process may need to distinguish among several communication\n" +
                "  streams between itself and another process (or processes), we imagine\n" +
                "  that each process may have a number of ports through which it\n" +
                "  communicates with the ports of other processes.\n" +
                "\n" +
                "2.2.  Model of Operation\n" +
                "\n" +
                "  Processes transmit data by calling on the TCP and passing buffers of\n" +
                "  data as arguments.  The TCP packages the data from these buffers into\n" +
                "  segments and calls on the internet module to transmit each segment to\n" +
                "  the destination TCP.  The receiving TCP places the data from a segment\n" +
                "  into the receiving user's buffer and notifies the receiving user.  The\n" +
                "  TCPs include control information in the segments which they use to\n" +
                "  ensure reliable ordered data transmission.\n" +
                "\n" +
                "  The model of internet communication is that there is an internet\n" +
                "  protocol module associated with each TCP which provides an interface\n" +
                "  to the local network.  This internet module packages TCP segments\n" +
                "  inside internet datagrams and routes these datagrams to a destination\n" +
                "  internet module or intermediate gateway.  To transmit the datagram\n" +
                "  through the local network, it is embedded in a local network packet.\n" +
                "\n" +
                "  The packet switches may perform further packaging, fragmentation, or\n" +
                "\n" +
                "\n" +
                "                                                                [Page 7]\n" +
                " \n" +
                "                                                          September 1981\n" +
                "Transmission Control Protocol\n" +
                "Philosophy\n" +
                "\n" +
                "\n" +
                "\n" +
                "  other operations to achieve the delivery of the local packet to the\n" +
                "  destination internet module.\n" +
                "\n" +
                "  At a gateway between networks, the internet datagram is \"unwrapped\"\n" +
                "  from its local packet and examined to determine through which network\n" +
                "  the internet datagram should travel next.  The internet datagram is\n" +
                "  then \"wrapped\" in a local packet suitable to the next network and\n" +
                "  routed to the next gateway, or to the final destination.\n" +
                "\n" +
                "  A gateway is permitted to break up an internet datagram into smaller\n" +
                "  internet datagram fragments if this is necessary for transmission\n" +
                "  through the next network.  To do this, the gateway produces a set of\n" +
                "  internet datagrams; each carrying a fragment.  Fragments may be\n" +
                "  further broken into smaller fragments at subsequent gateways.  The\n" +
                "  internet datagram fragment format is designed so that the destination\n" +
                "  internet module can reassemble fragments into internet datagrams.\n" +
                "\n" +
                "  A destination internet module unwraps the segment from the datagram\n" +
                "  (after reassembling the datagram, if necessary) and passes it to the\n" +
                "  destination TCP.\n" +
                "\n" +
                "  This simple model of the operation glosses over many details.  One\n" +
                "  important feature is the type of service.  This provides information\n" +
                "  to the gateway (or internet module) to guide it in selecting the\n" +
                "  service parameters to be used in traversing the next network.\n" +
                "  Included in the type of service information is the precedence of the\n" +
                "  datagram.  Datagrams may also carry security information to permit\n" +
                "  host and gateways that operate in multilevel secure environments to\n" +
                "  properly segregate datagrams for security considerations.\n" +
                "\n" +
                "2.3.  The Host Environment\n" +
                "\n" +
                "  The TCP is assumed to be a module in an operating system.  The users\n" +
                "  access the TCP much like they would access the file system.  The TCP\n" +
                "  may call on other operating system functions, for example, to manage\n" +
                "  data structures.  The actual interface to the network is assumed to be\n" +
                "  controlled by a device driver module.  The TCP does not call on the\n" +
                "  network device driver directly, but rather calls on the internet\n" +
                "  datagram protocol module which may in turn call on the device driver.\n" +
                "\n" +
                "  The mechanisms of TCP do not preclude implementation of the TCP in a\n" +
                "  front-end processor.  However, in such an implementation, a\n" +
                "  host-to-front-end protocol must provide the functionality to support\n" +
                "  the type of TCP-user interface described in this document.Transmission is made reliable via the use of sequence numbers and\n" +
                "  acknowledgments.  Conceptually, each octet of data is assigned a\n" +
                "  sequence number.  The sequence number of the first octet of data in a\n" +
                "  segment is transmitted with that segment and is called the segment\n" +
                "  sequence number.  Segments also carry an acknowledgment number which\n" +
                "  is the sequence number of the next expected data octet of\n" +
                "  transmissions in the reverse direction.  When the TCP transmits a\n" +
                "  segment containing data, it puts a copy on a retransmission queue and\n" +
                "  starts a timer; when the acknowledgment for that data is received, the\n" +
                "  segment is deleted from the queue.  If the acknowledgment is not\n" +
                "  received before the timer runs out, the segment is retransmitted.\n" +
                "\n" +
                "  An acknowledgment by TCP does not guarantee that the data has been\n" +
                "  delivered to the end user, but only that the receiving TCP has taken\n" +
                "  the responsibility to do so.\n" +
                "\n" +
                "  To govern the flow of data between TCPs, a flow control mechanism is\n" +
                "  employed.  The receiving TCP reports a \"window\" to the sending TCP.\n" +
                "  This window specifies the number of octets, starting with the\n" +
                "  acknowledgment number, that the receiving TCP is currently prepared to\n" +
                "  receive.\n" +
                "\n" +
                "2.7.  Connection Establishment and Clearing\n" +
                "\n" +
                "  To identify the separate data streams that a TCP may handle, the TCP\n" +
                "  provides a port identifier.  Since port identifiers are selected\n" +
                "  independently by each TCP they might not be unique.  To provide for\n" +
                "  unique addresses within each TCP, we concatenate an internet address\n" +
                "  identifying the TCP with a port identifier to create a socket which\n" +
                "  will be unique throughout all networks connected together.\n" +
                "\n" +
                "  A connection is fully specified by the pair of sockets at the ends.  A\n" +
                "  local socket may participate in many connections to different foreign\n" +
                "  sockets.  A connection can be used to carry data in both directions,\n" +
                "  that is, it is \"full duplex\".\n" +
                "\n" +
                "  TCPs are free to associate ports with processes however they choose.\n" +
                "  However, several basic concepts are necessary in any implementation.\n" +
                "  There must be well-known sockets which the TCP associates only with\n" +
                "  the \"appropriate\" processes by some means.  We envision that processes\n" +
                "  may \"own\" ports, and that processes can initiate connections only on\n" +
                "  the ports they own.  (Means for implementing ownership is a local\n" +
                "  issue, but we envision a Request Port user command, or a method of\n" +
                "  uniquely allocating a group of ports to a given process, e.g., by\n" +
                "  associating the high order bits of a port name with a given process.)\n" +
                "\n" +
                "  A connection is specified in the OPEN call by the local port and\n" +
                "  foreign socket arguments.  In return, the TCP supplies a (short) local\n" +
                "\n" +
                "\n" +
                "[Page 10]\n" +
                " \n" +
                "September 1981\n" +
                "                                           Transmission Control Protocol\n" +
                "                                                              Philosophy\n" +
                "\n" +
                "\n" +
                "\n" +
                "  connection name by which the user refers to the connection in\n" +
                "  subsequent calls.  There are several things that must be remembered\n" +
                "  about a connection.  To store this information we imagine that there\n" +
                "  is a data structure called a Transmission Control Block (TCB).  One\n" +
                "  implementation strategy would have the local connection name be a\n" +
                "  pointer to the TCB for this connection.  The OPEN call also specifies\n" +
                "  whether the connection establishment is to be actively pursued, or to\n" +
                "  be passively waited for.\n" +
                "\n" +
                "  A passive OPEN request means that the process wants to accept incoming\n" +
                "  connection requests rather than attempting to initiate a connection.\n" +
                "  Often the process requesting a passive OPEN will accept a connection\n" +
                "  request from any caller.  In this case a foreign socket of all zeros\n" +
                "  is used to denote an unspecified socket.  Unspecified foreign sockets\n" +
                "  are allowed only on passive OPENs.\n" +
                "\n" +
                "  A service process that wished to provide services for unknown other\n" +
                "  processes would issue a passive OPEN request with an unspecified\n" +
                "  foreign socket.  Then a connection could be made with any process that\n" +
                "  requested a connection to this local socket.  It would help if this\n" +
                "  local socket were known to be associated with this service.\n" +
                "\n" +
                "  Well-known sockets are a convenient mechanism for a priori associating\n" +
                "  a socket address with a standard service.  For instance, the\n" +
                "  \"Telnet-Server\" process is permanently assigned to a particular\n" +
                "  socket, and other sockets are reserved for File Transfer, Remote Job\n" +
                "  Entry, Text Generator, Echoer, and Sink processes (the last three\n" +
                "  being for test purposes).  A socket address might be reserved for\n" +
                "  access to a \"Look-Up\" service which would return the specific socket\n" +
                "  at which a newly created service would be provided.  The concept of a\n" +
                "  well-known socket is part of the TCP specification, but the assignment\n" +
                "  of sockets to services is outside this specification.  (See [4].)\n" +
                "\n" +
                "  Processes can issue passive OPENs and wait for matching active OPENs\n" +
                "  from other processes and be informed by the TCP when connections have\n" +
                "  been established.  Two processes which issue active OPENs to each\n" +
                "  other at the same time will be correctly connected.  This flexibility\n" +
                "  is critical for the support of distributed computing in which\n" +
                "  components act asynchronously with respect to each other.\n" +
                "\n" +
                "  There are two principal cases for matching the sockets in the local\n" +
                "  passive OPENs and an foreign active OPENs.  In the first case, the\n" +
                "  local passive OPENs has fully specified the foreign socket.  In this\n" +
                "  case, the match must be exact.  In the second case, the local passive\n" +
                "  OPENs has left the foreign socket unspecified.  In this case, any\n" +
                "  foreign socket is acceptable as long as the local sockets match.\n" +
                "  Other possibilities include partially restricted matches.\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                                               [Page 11]\n" +
                " \n" +
                "                                                          September 1981\n" +
                "Transmission Control Protocol\n" +
                "Philosophy\n" +
                "\n" +
                "\n" +
                "\n" +
                "  If there are several pending passive OPENs (recorded in TCBs) with the\n" +
                "  same local socket, an foreign active OPEN will be matched to a TCB\n" +
                "  with the specific foreign socket in the foreign active OPEN, if such a\n" +
                "  TCB exists, before selecting a TCB with an unspecified foreign socket.\n" +
                "\n" +
                "  The procedures to establish connections utilize the synchronize (SYN)\n" +
                "  control flag and involves an exchange of three messages.  This\n" +
                "  exchange has been termed a three-way hand shake [3].\n" +
                "\n" +
                "  A connection is initiated by the rendezvous of an arriving segment\n" +
                "  containing a SYN and a waiting TCB entry each created by a user OPEN\n" +
                "  command.  The matching of local and foreign sockets determines when a\n" +
                "  connection has been initiated.  The connection becomes \"established\"\n" +
                "  when sequence numbers have been synchronized in both directions.\n" +
                "\n" +
                "  The clearing of a connection also involves the exchange of segments,\n" +
                "  in this case carrying the FIN control flag.\n" +
                "\n" +
                "2.8.  Data Communication\n" +
                "\n" +
                "  The data that flows on a connection may be thought of as a stream of\n" +
                "  octets.  The sending user indicates in each SEND call whether the data\n" +
                "  in that call (and any preceeding calls) should be immediately pushed\n" +
                "  through to the receiving user by the setting of the PUSH flag.\n" +
                "\n" +
                "  A sending TCP is allowed to collect data from the sending user and to\n" +
                "  send that data in segments at its own convenience, until the push\n" +
                "  function is signaled, then it must send all unsent data.  When a\n" +
                "  receiving TCP sees the PUSH flag, it must not wait for more data from\n" +
                "  the sending TCP before passing the data to the receiving process.\n" +
                "\n" +
                "  There is no necessary relationship between push functions and segment\n" +
                "  boundaries.  The data in any particular segment may be the result of a\n" +
                "  single SEND call, in whole or part, or of multiple SEND calls.\n" +
                "\n" +
                "  The purpose of push function and the PUSH flag is to push data through\n" +
                "  from the sending user to the receiving user.  It does not provide a\n" +
                "  record service.\n" +
                "\n" +
                "  There is a coupling between the push function and the use of buffers\n" +
                "  of data that cross the TCP/user interface.  Each time a PUSH flag is\n" +
                "  associated with data placed into the receiving user's buffer, the\n" +
                "  buffer is returned to the user for processing even if the buffer is\n" +
                "  not filled.  If data arrives that fills the user's buffer before a\n" +
                "  PUSH is seen, the data is passed to the user in buffer size units.\n" +
                "\n" +
                "  TCP also provides a means to communicate to the receiver of data that\n" +
                "  at some point further along in the data stream than the receiver is\n" +
                "\n" +
                "\n" +
                "[Page 12]\n" +
                " \n" +
                "September 1981\n" +
                "                                           Transmission Control Protocol\n" +
                "                                                              Philosophy\n" +
                "\n" +
                "\n" +
                "\n" +
                "  currently reading there is urgent data.  TCP does not attempt to\n" +
                "  define what the user specifically does upon being notified of pending\n" +
                "  urgent data, but the general notion is that the receiving process will\n" +
                "  take action to process the urgent data quickly.\n" +
                "\n" +
                "2.9.  Precedence and Security\n" +
                "\n" +
                "  The TCP makes use of the internet protocol type of service field and\n" +
                "  security option to provide precedence and security on a per connection\n" +
                "  basis to TCP users.  Not all TCP modules will necessarily function in\n" +
                "  a multilevel secure environment; some may be limited to unclassified\n" +
                "  use only, and others may operate at only one security level and\n" +
                "  compartment.  Consequently, some TCP implementations and services to\n" +
                "  users may be limited to a subset of the multilevel secure case.\n" +
                "\n" +
                "  TCP modules which operate in a multilevel secure environment must\n" +
                "  properly mark outgoing segments with the security, compartment, and\n" +
                "  precedence.  Such TCP modules must also provide to their users or\n" +
                "  higher level protocols such as Telnet or THP an interface to allow\n" +
                "  them to specify the desired security level, compartment, and\n" +
                "  precedence of connections.\n" +
                "\n" +
                "2.10.  Robustness Principle\n" +
                "\n" +
                "  TCP implementations will follow a general principle of robustness:  be\n" +
                "  conservative in what you do, be liberal in what you accept from\n" +
                "  others.";
        keywords = ExtractKeyWord(test);
        List<String> result = new ArrayList<>();

        for(KeyWord keyWord: keywords){
            if(keyWord.getStem().length() > 1){
                result.add(keyWord.getStem());
            }
        }
        System.out.println("Keyword Number : " + result.size() + "  " + test.length());
        for(String word: result){
            System.out.println(word);
        }
        return results;
    }
}
