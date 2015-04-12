# Introduction #

This is the ordered list of tasks performed by Freedomotic at startup

### At startup ###

  1. First of all the configuration file is loaded into a data structure. This file is located in FREEDOMOTIC\_ROOT/config/config.xml
  1. Starting the logger and popup it in the browser if this option is marked TRUE in the config file
  1. The shared messaging bus is created
  1. Shows the freedom website if stated in the config file (can be disabled)
  1. Dynamically load events definition (jar files) in /plugin/events folder
  1. Dynamically load objects definition (jar files) in /plugin/objects folder
  1. Deserialize the default environment (its shape + zones). The environment path to load is saved in config file with this property
```
<property name="KEY_ROOM_XML_PATH" value="/room_blue/room_blue.xml"/>
```
  1. Creates the Behavior managers for objects. It intercepts behavior notification changes or request of behavior change.
  1. Autoloading of the right RXTX system file to communicate with serial devices.
  1. Dynamically load sensors and actuators plugins from folder FREEDOM\_ROOT/plugins/devices
  1. Chache data infered from the XML definitions of objects to allow fast access to data that needs preprocessing (like object topology and behaviors).
  1. Loads the entire Reactions system (Triggers + Commands + Reactions)
  1. Starts loaded plugins which have a property "startup-time = on load" in their mainifest file.
  1. Prints on the logfile a summary of loaded resources
  1. Freedomotic is now ready to run




### At closing ###

When Freedom stops all objects are serialized in xml files into the folder _**FREEDOMOTIC/data/furn/ENV\_FOLDER/**_

TODO