# What a Sensor plugin can do #

The sensors are responsible to notify any changes detected in the environment or from any other source of information, like a webservice. This is done by submitting an [event](Events.md) to Freedomotic.

# Sensor Plugins Explaned #

In the constructor of a plugin you have to provide the plugin name and it's path starting from FREEDOMOTIC\_ROOT/plugins/devices/. In the example below the jar file of the sensor plugin can be found in folder `FREEDOMOTIC_ROOT/plugins/devices/YOUR_SURNAME/MANIFEST_FILE_NAME.xml`
```
// constructor
public mySensor() {
     super ("mySensorNameHere", "/YOUR_SURNAME/MANIFEST_FILE_NAME.xml");
}
```

```
protected void onRun()
```
Function performed continuously while the plugin is running. The plugin main thread calls this function inside a while loop, until it is stopped.

If your plugin doesn't need a loop to perform its task you can disable it calling _setAsNotPollingSensor()_ in the plugin constructor (however before the start() method). The effect is that the _onRun()_ method is executed only once, not as a loop.

```
protected void onStart()
```
Function called before the while loop of the thread of the plugin. See onRun().

```
protected void onStop()
```
Function called after running the while loop of the thread of the plugin. See onRun().

```
protected void onInformationRequest(_?_) throws IOException,UnableToExecuteException
```
Not yet implemented

```
start()
```
You can use this method to start the execution of your plugin (eg: at the end of the contructor). This cause the call of _onStart()_ and after that the _onRun()_

```
stop()
```
It will stop the plugin . This causes the call of the _onStop()_ method.

## How to create the manifest file ##

To make Freedomotic load the plugin you need an XML manifest file. Here are instructions on how to make it [XmlManifest](XmlManifest.md)

## Related Tasks ##

  * Learn how to notify an event to Freedomotic
  * Learn how to develop a custom event