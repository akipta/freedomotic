# Install freedomotic on Raspberry Pi (first untested draft) #

### STEP 1: Download Raspberry Debian Wheezy image ###

```
wget http://ftp.snt.utwente.nl/pub/software/rpi/images/debian/7/2012-08-08-wheezy-armel/2012-08-08-wheezy-armel.zip
unzip 2012-08-08-wheezy-armel.zip
```

### STEP 2: Copy the image on the SD card ###

Plug SD card (2GB or 4GB) and take note of the related device (eg: using dmesg command)
```
sudo dd if=2012-08-08-wheezy-armel.img of=SD_DEVICE
```

**WARNING**: be sure the **of** (output file) is the SD card otherwise you can mess up your hard drive.
This operation will take at least 10 minutes and will not print any output until the end.

### STEP 3: Download and install Freedomotic stable version ###

```
wget http://freedomotic.googlecode.com/files/Freedomotic_v5.3.0.zip
```

To be sure unplug and replug the SD card. Execute these commands
```
cp Freedomotic_v5.3.0.zip /media/SD_DEVICE_LABEL/home/pi/Desktop
cd /media/SD_DEVICE_LABEL/home/pi/Desktop
unzip Freedomotic_v5.3.0.zip
```

**NOTE**: say yes to all if it asks to replace files

### STEP 4: Running Raspberry Pi ###
Umount the SD card, insert it into your raspberry pi and turn it on.


### STEP 5: Download and install Oracle Java SE Embedded ###

Follow these guide
https://blogs.oracle.com/hinkmond/entry/quickie_guide_getting_java_embedded  http://www.oracle.com/technetwork/java/embedded/downloads/javase/index.html

**NOTE:** First click accept, then choose the third bundle in the list:
> ARMv7 Linux - Headful EABI, VFP, SoftFP ABI, Little Endian
> ejre-7u6-fcs-b24-linux-arm-vfp-client\_headful-10\_aug\_2012.tar.gz

If you download the package on the pc you must before **scp** the bundle to your RPi:
```
   scp <ejre-bundle> pi@<ip_addr_rpi>:/home/pi
```

From Raspberry untar the bundle and rename (move) the **ejre1.7.0\_06** directory to **/usr/local/java**
```
   cd /home/pi
   tar zxvf ejre-7u6-fcs-b24-linux-arm-vfp-client_headful-10_aug_2012.tar.gz
   sudo mv ejre1.7.0_06 /usr/local/java
   export PATH=$PATH:/usr/local/java/bin
```

To verify if all is ok digit
```
   java -version
```

You should see
```
     java version "1.7.0_06"
     Java(TM) SE Embedded Runtime Environment (build 1.7.0_06-b24, headless)
     Java HotSpot(TM) Embedded Client VM (build 23.2-b09, mixed mode)

```

### STEP 6: Start Freedomotic ###

**From Raspberry:**

run the **/home/pi/Desktop/freeodmotic/start-freedom.sh** script

You can also use
```
java -jar freedomotic.jar
```

**From another pc using ssh:**

set the display variable on the Raspberry (needed for graphical interface)
```
export DISPLAY =:0.0
```

connect to the Raspberry by **ssh**
```
ssh -X pi@ip-address-raspberry
```

run the **start-freedom.sh** script in **/home/pi/Desktop/freeodmotic/start-freedom.sh**.

**NOTE: to follow and contribute to the development [join the group](http://www.freedomotic.com/content/freedomotic-raspberry)**

# How to install qemu on Ubuntu 11.10 (if you don't have a raspi) #

This tutorial is for internal use and is written following this guides:
http://drogon.net/blog/2012/03/raspberry-pi-in-qemu-debian/
http://www.raspberrypi.org/forum/general-discussion/official-image-and-qemu

all credit to respective authors.

`sudo apt-get install qemu-system`
create a folder (call it raspberry) in which you will put:

http://downloads.raspberrypi.org/images/debian/6/debian6-19-04-2012/debian6-19-04-2012.zip

http://dl.dropbox.com/u/45842273/zImage_3.1.9

a qemu startup shell script like this (call it start-qemu.sh)
```
qemu-system-arm -M versatilepb -cpu arm1176 -m 192   -hda debian6-19-04-2012.img -kernel zImage_3.1.9 -append "root=/dev/sda2" -serial stdio -redir tcp:2222::22 -usb -usbdevice host:0403:600
```
make the script executable.
Unzip debian6


open terminal
`sh start-qemu.sh`
wait the system to start and login with

username: pi
password: raspberry

# How to setup for freedomotic #
```
cd /home/pi
wget http://freedomotic.googlecode.com/files/freedomotic-dailybuild-5.2.395.zip
unzip freedomotic-dailybuild-5.2.395.zip
cd freedom
sudo apt-get install openjdk-6-jre
sudo startx
sudo sh start-freedomotic.sh
```

you are encouraged to refine this guidelines.


## Test1: Install light window manager matchbox ##
Matchbox is a lightweight window manager which is good for kiosk like systems
```
sudo apt-get install matchbox
sudo xinit /usr/bin/matchbox-session -display :0 &
cd freedomotic
java -jar freedomotic.jar
```