# Introduction #

The SheevaPlug, by Marvell, is the first example of "plug computer".
The hardware is contained within the "plug" and supports a Gigabit ethernet socket, SDHC socket and USB socket.
The most important advantages of this device are:
  * good performance
  * low power consumption (about 10 watts)
  * low cost
  * small dimensions
So it can be and ideally solution for "always on" applications.
In order to run Freedomotic it needs to install a Java Runtime Environment. A possible solution is using OpenJDK at this [link](http://openjdk.java.net/).

A more complete description is available on productor official site  http://www.globalscaletechnologies.com/p-22-sheevaplug-dev-kit-us.aspx


Sheevaplug hasn't a video output so you must connect by mini-usb serial cable using a serial terminal program like **putty** or by **ssh** protocol.
At this point to display locally the GUI on the remote machine we need to set X-forwarding with ssh.
Any Linux terminal should do X-forwarding.

### For Linux users ###
From client terminal digit **ssh sheeva\_user@sheeva\_ip\_address -X** where sheeva\_user is an enabled user on the Sheevaplug and sheeva\_ip\_address is the remote address.
-X option enables X-forwarding.

### For Windows users ###
> Windows users need to install an X server and configure correctly their ssh program.
Here an interesting guide for Windows program Putty: http://www.math.umn.edu/systems_guide/putty_xwin32.html

