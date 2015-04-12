## What is a plugin ##

Freedomotic is an application extensible through plugins.
Plugins are simple classes within a .jar Java package. Each plugin is deployed in the _**/FREEDOMOTIC\_ROOT/plugins/**_ folder and loaded and initialized automatically at Freedomotic startup.

The communication between the plugin and Freedomotic is automatically managed via a Message Oriented Middleware. Plugins in addition to the 'Manager of the messages' has direct access to [Freedomotic data structures](Data.md). In a plugin, you can create, read, update, or delete data and use them to accomplish your goals.


## Plugin folder structure ##
TODO

## Freedom API ##

This API defines four types of plugins:

  * [Sensor](Sensors.md): extends the class it.freedoom.api.Sensor. It notify a change in the environment sending [Events](Events.md) to the middleware.
  * [Actuator](Actuators.md): extends the class it.freedoom.api.Actuator. An actuator plugin receives [Commands](Command.md) from the middleware. It carries out actions on the environment by executing the command received from Freedom.
  * [Environment Custom Objects](DevelopObjects.md): **available from beta version 5**.
  * [Custom Events](DevelopEvents.md): **available from beta version 5**.

## How to make a non-Java application communicate with Freedomotic ##

Till now we have talked about how to extend Freedomotic with Java plugins. However is possible to make non-Java application communicate with Freedomotic. To know which languages are supported and how to do it [read this](Integrate.md). If you are interested in Java plugins continue to read this page, if you plan to make Freedomotic communicates with other languages (for intance PHP, C, C++, AJAX, Python, ...) see [this page](Integrate.md).

## Plugins features ##

  * own configuration file with storage persistance and automatic loading
  * user interface accessible by right click in plugins list
  * simplyfied access to all program data structures
  * automatic managing of plugin status (loaded, running, stopped,...)
  * access to messaging system for event notifications by a simple code row
  * automatic download and upgrade from web for plugins and new program versions
  * plugins event programming (onCommand(), onInformationRequest(), onRun(), onStart, onStop()).