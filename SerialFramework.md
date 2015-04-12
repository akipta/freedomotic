# Introduction #

**PLEASE POST YOUR QUESTIONS ON FORUM http://freedomotic.com/forum/5/784**

One of the recurring problems of Java is **communication** with devices by serial port. The most common problems concern communication with devices connected via USB port reading and writing ASCII characters.

In Java, the Java Communications API are also known as **javax.comm**. These APIs, in order to maintain compatibility between different architectures, involve serious complications for the developers intend to perform a simple reading/writing ASCII characters system.

The **RXTX project** is an excellent (and most used) implementation of the Java Communications API. RXTX is extremely comprehensive, allowing communication of parallel and serial ports, and is actively maintained open source (GPL3 license).

Unfortunately RXTX has dependencies from files **javax.comm.properties**, **librxtxSerial.so** and **rxtxSerial.dll** (in Windows) that must be installed separately on each operating system on which you will use to communicate with serial/parallel port.

Our wrapper solves these problems:

  * **requires no external dependencies**: the external libraries are loaded dynamically
  * **simplifies the use of these APIs** for common operations
  * **is open source (GLP2 license)**: the source code is freely available and modifiable under the terms of this license
  * **is being actively used in a university project** including professionals, educators and students. This will be improved and enhanced over time based on their feedbacks
  * **events style coding**: any data read from the device is automatically forwarded to the method OnDataAvailable (String date).

NOTE: This wrapper is used for reading/writing ASCII characters to/from serial port (most common case of USB ports). For other situations, refer to the implementation RXTX.


<a href='Hidden comment: 
== How to install RXTX libraries ==

====Linux distribution and Openjdk 1.6====

Download the RXTX package. As of this writing, the file to download is called *rxtx-2.1-7-bins-r2.zip*.
Unzip the rxtx archive and install it. The following commands assume your JVM is located in *_/usr/lib/jvm/java-6-openjdk/_* and that you are using a 32-bit x86 computer. You will need to slightly modify these commands if you have a different JVM or system architecture (i.e., 64-bit).

```
$ sudo apt-get install zip
$ unzip rxtx-2.1-7-bins-r2.zip
$ cd rxtx-2.1-7-bins-r2
$ sudo cp RXTXcomm.jar /usr/lib/jvm/java-6-openjdk/jre/lib/ext/
$ sudo cp Linux/i686-unknown-linux-gnu/librxtx* /usr/lib/jvm/java-6-openjdk/jre/lib/i386/
```
'></a>

## How to use the serial framework ##

The following examples relate to the communication with the controller for X10 home automation product called PMix35 XanuraHome?. The framework (for now) has been tested only with this device. If you have information to be trained on how to use the serial connection framework, contact us here.


## USB serial port device known (most common) ##

```
/**
 *
 * @author Enrico Nicoletti (enrico.nicoletti84@gmail.com)
 */
public class myTestClass implements SerialDataConsumer {
 public myTestClass() {
   SerialConnectionProvider usb = new SerialConnectionProvider();
   usb.setPortName("COM1"); //eg: /dev/ttyUSB0 on linux
   usb.addListener(this); //use this class as listener for usb received data
   String message = "A01A01AONAON";
   String reply = null;
   try {
         reply = usb.send(message); //synchronous sends the message to usb port and reads the corresponding reply from the device. Is not necessary to explicit call connect(), the connection is lazy initialized at first use.
        } catch (IOException ex) {
          System.err.println("Unable to write data on serial port " + usb.getPortName() + ". Maybe the device is not connected to this port.");
        }
        System.out.println("Device on port " + usb.getPortName() + " replies '" + reply + "' to message '" + message + "'");
    }

@Override
 public void onDataAvailable(String data) {
     System.out.println("Readed from USB: " + data);
    }

 public static void main(String[] args) {
        new myTestClass();
    }
}
```

## Device USB serial port not known (autodiscovering) ##

If the device responds to a message HELLO (or if you know the answer to a particular input device) you can use the message-response pair to uniquely identify the device and therefore the serial port it is connected.
In this case you must use the method
**`setAutodiscover (MESSAGE, EXPECTED_REPLY )`**
MESSAGE which indicates a character string of data to be sent to the device, and RISPOSTA\_ATTESA indicates the response expected from the device when it receives MESSAGE.

```
/**
 *
 * @author Enrico Nicoletti (enrico.nicoletti84@gmail.com)
 */
public class myTestClass implements SerialDataConsumer {
  public myTestClass() {
     SerialConnectionProvider usb = new SerialConnectionProvider();
     usb.setAutodiscover("$>9000PXD3#", "$<9000VP"); //message, expected_reply_to_message
     usb.addListener(this); //use this class as listener for usb received data
     String message = "A01A01AONAON";
     String reply = null;
     try {
            reply = usb.send(message);
        } catch (IOException ex) {
         System.err.println("Unable to write data on serial port " + usb.getPortName() + ". Maybe the device is not connected to this port.");
        }
        System.out.println("Device in port " + usb.getPortName() + " replies '" + reply + "' to message '" + message + "'");
    }

@Override
 public void onDataAvailable(String data) {
    System.out.println("Readed from USB: " + data);
  }

 public static void main(String[] args) {
     new myTestClass();
  }
}
```

## Devices that require polling to receive data ##

Some devices require a search continues to find out if there are no data available for reading, as in the case of the X10 controller PMix15. This device alerts the information only after receiving the request string '$> 9000RQCE #'. With the method
**`setPollingMode (REQUEST, WAIT_TIME )`**
Question will be made ​​of the device with periodic WAIT\_TIME period specified in milliseconds (500 means half a second). Any read data are automatically forwarded to the method OnDataAvailable (String data) as usual.

```
/**
 *
 * @author Enrico Nicoletti (enrico.nicoletti84@gmail.com)
 */
public class myTestClass implements SerialDataConsumer {

    public myTestClass() {
        SerialConnectionProvider usb = new SerialConnectionProvider();
        usb.setPortName("COM1"); //eg: /dev/ttyUSB0 on linux
        usb.setPollingMode("$>9000RQCE#", 500); //string to trigger the query, time between queries in millisecs 
        usb.addListener(this); //use this class as listener for usb received data
        String message = "A01A01AONAON";
        String reply = null;
        try {
            reply = usb.send(message); //synchronous sends the message to usb port and reads the corresponding reply from the device. Is not necessary to explicit call connect(), the connection is lazy initialized at first use.
        } catch (IOException ex) {
            System.err.println("Unable to write data on serial port " + usb.getPortName() + ". Maybe the device is not connected to this port.");
        }
        System.out.println("Device on port " + usb.getPortName() + " replies '" + reply + "' to message '" + message + "'");
    }

    @Override
    public void onDataAvailable(String data) {
        System.out.println("Readed from USB: " + data);
    }

    public static void main(String[] args) {
        new myTestClass();
    }
}
```

## Default Settings ##


  * Baudrate = 19200
  * Data bits = 8
  * Stop Bits = 1
  * Parity = NONE

they can be changed using this methods
  * **setBaudrate(value)**
  * **setDatabits(value)**
  * **setStopbits(value)**
  * **setParity(value)**

## Support Status ##

| **OPERATIVE SYSTEM** | **PLATFORM** | **STATUS** |
|:---------------------|:-------------|:-----------|
| Ubuntu Linux 10.10 Netbook Edition | i386 | Tested |
| Ubuntu Linux 10.10 Desktop Edition | amd64 | Tested |
| Windows 7 | 32bit | Tested |
| Windows 7 | 64bit | Tested |
| Mac | all | Should work (not tested) |
| Solaris | all | not yet supported |



## Example with a USB Relay Board ##

You can see the Serial Connection Framework in action in [this example](http://code.google.com/p/freedomotic/wiki/SerialPlugin)