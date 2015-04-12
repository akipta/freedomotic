# Introduction #

To run Freedomotic on BeagleBoard you need a running Linux distribution like Angstrom, with OpenJDK 1.6 java virtual machine.

You can create an Angstrom image ready to be installed on a miniSD from this website http://narcissus.angstrom-distribution.org/

  1. Select BeagleBoard as the target machine in the first combobox
  1. Choose advanced settings
  1. Choose 2001.03
  1. Choose extended (task-base-extended) to have USB and network support
  1. Choose uDev
  1. Choose sysvinit
  1. Choose OMAP SD Image
  1. Choose no software manifest
  1. Choose no SDK creation
  1. Choose X11 to have a graphic environment
  1. Choose Gnome as desktop environment
  1. Under Java Packages choose openjdk
  1. Add any other package you need

Wait the image creation, download it and copy to a miniSD image using this command
```
sudo dd if="YOUR_PATH_TO_BEAGLEBOARD.IMG_FILE" of=/dev/THE_DEVICE_OF_YOUR_SD
```
pay attention to specify the right device.
for example
```
sudo dd=/home/enrico/random-ss33524-beagleboard.img of=/dev/sdc/
```

now start the beagleboard, download the latest release of freedomotic unpack and start it with
```
java -jar freedomotic.jar
```

You can run the default frontend in fullscreen mode pressing F11 (ESC to come back to window mode).