**Have you got a Debian-based distro and want to give Freedomotic a try?**

This procedure shows how to install Freedomotic from a .deb package on any debian-based distribution.

It's already been tested on:
  * Ubuntu 12.4.1
  * RaspberryPI Raspbian


# Install scenarios #

---


## Get the .deb and install ##

### _Download the .deb package_ ###
The package will be available [here](http://www.freedomotic.com/content/download)

### _Installation_ ###

`$ sudo dpkg -i freedomoticXXXXX.deb`

I'll get an error about probabily missing dependencies, so the package doesn't get configured. It's ok, the next command will provide all you need and complete the installation.

`$ sudo apt-get install -f`

### _First run_ ###

You can now call freedomotic with SUDO (no problem, it automatically runs as "freedomotic" user) but the user pi won't be able to access config files unles it belongs to group "freedomotic", so the best choice is to issue a:

`$ sudo adduser pi freedomotic`

exit and re-login. You no longer need to SUDO when calling freedomotic.

### _Upgrades_ ###
Whenever a new edition of freedomotic is released, you just have to download the corresponding .deb file and repeat the current procedure. All your config data will be availlable in the new version.


---



## Add the Freedomotic repo and install ##
Repo ulr to come

### _Add Repository_ ###

`$ sudo echo "deb http://repo_address/ main" >> /etc/apt/sources.list`

`$ sudo apt-get update`

### _Installation_ ###
`$ sudo apt-get install freedomotic`

### _Upgrades_ ###
`$ sudo apt-get update && sudo apt-get upgrade`