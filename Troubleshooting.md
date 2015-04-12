# FAQ & Troubleshooting #

## Freedomotic doesn't start with JRE 7.x ##
If you use JRE 7.x please goto **config** folder, open **config.xml** and set **KEY\_LOGGER\_POPUP** to false. Restart Freedomotic

## Freedomotic doesn't start without an active Internet connection ##
Freedomotic uses an external tcp broker (Activemq) so it needs an active Internet connection to work correctly. If no connections are available it returns an exception and exits.

## I'm trying to run the repository version of Freedomotic and it fails, it is normal? ##
The exception is:
```
javax.jms.JMSException: Could not connect to broker URL: tcp://localhost:61616. Reason: java.net.ConnectException: connection refused
```


The repository version has the embedded broker disabled by default
you can edit FREEDOMOTIC\_ROOT/config/config.xml file and delete the line
```
<property name="KEY_BROKER_URL"  value="tcp://localhost:61616"/>
```
to start an embedded broker by default.
If you want to stay with the external broker download Apache ActiveMQ and follow [instructions](http://activemq.apache.org/getting-started.html) on their website.


## Create project zip file ##
To create a project zip file you must use Apache Ant. Please download it from [this link](http://ant.apache.org/bindownload.cgi) and follow the installation instructions. Then move to NetBeans Freedom project folder and digit from console
```
ant -buildfile build.xml create-release
```
At the request for version number digit 5.2 and press return.


## How to make a plugin project build in the Freedomotic plugins folder ##

If you are using NetBeans to develop your plugin can be useful to build the plugin in the Freedomotic dedicated folder so you can immediately run freedom without cut and paste your jar anytime.
  * In your plugin NetBeans project you will find a nbproject folder, open it and search for project.properties file.
  * In this file locate the lines
```
# This directory is removed when the project is cleaned:
dist.dir=dist
```
  * change it to
```
# This directory is removed when the project is cleaned:
#dist.dir=dist
dist.dir=../freedom/plugins
#the ../ supposes that your project folder is at the same level of the freedom root folder
```

  * save the file and try to build.
  * Pay attention with the "clean and build" option for it deletes all files in the building folder (/freedom/plugins/) so you will loose all config xml files as well.