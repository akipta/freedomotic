# Types of Data #

If you plan to [develop a plugin](Plugins.md) for Freedomotic you need to access the framework data structures.

This types of data are concerned with building automation and Freedomotic architecture:
  * Environment topology
  * Environment objects
  * People
  * Loaded plugins and extensions

## Accessing Data Structures from Java Plugins ##


### Environment Topology ###

Environment data are accessible by the static reference:
```
Freedomotic.environment.getPojo();
```
it returns the Environment object instance.
it gives access to all Environment properties.
```
Freedom.environment.getRooms()
```
Returns all the zones in the environment (rooms are zones too)
```
Freedom.environment.getZones()
```
This is the correct import to access this method
`import it.freedom.application.Freedom;`

### Environment Objects ###

The [objects](Object.md) (lights, doors, couches, ...) in the environment can be retrived using
Get an object by name. null if is not in the list
```
EnvObjectPersistence.getObject(String name)
```
get the object with name the protocol and address passed.
```
EnvObjectPersistence.getObject(String protocol, String address)
```
iterate over the list of object that are linked to a protocol
```
EnvObjectPersistence.getObjectByProtocol(String protocol)
```
iterate over the list of objects
```
EnvObjectPersistence.iterator()
```

This is the correct import to access this method
`import it.freedom.environment.EnvObject;`



### People ###

returns the person with index = i. Null if not exists
```
Freedom.people.get(int i)
```
iterate over the people
```
Freedom.people.iterator()
```

This is the correct import to access this method
`import it.freedom.application.Freedom;`

### Plugins ###

To get the list of loaded plugins:
```
AddonManager.getLoadedPlugins()
```
it returns an ArrayList of Plugin type.
To get a plugin by name
```
AddonManager.getPluginByName(String name)
```
This is the correct import to access this method
`import it.freedom.plugins.AddonManager;`

## Get plugin configuration from manifest ##

You can access configuration file of a plugin in this way:
```
int myVar = configuration.getIntProperty("PROPERTY-NAME", 1);
```

The second parameter in getIntProperty is the default value to use if the _PROPERTY-NAME_ cannot be found or cannot be converted to the proper type (int, double, string, ...)

other methods are:
```
boolean myVar = configuration.getBooleanProperty("PROPERTY-NAME", true);
double myVar = configuration.getDoubleProperty("PROPERTY-NAME", 1.5f);
String myVar = configuration.getStringProperty("PROPERTY-NAME", "some text");
```

read tuples properties from config file:
```
boolean myVar = tuple.getBooleanProperty(tupleIndex, "PROPERTY-NAME", true);
double myVar = tuple.getDoubleProperty(tupleIndex, "PROPERTY-NAME", 1.5f);
String myVar = tuple.getStringProperty(tupleIndex, "PROPERTY-NAME", "some text");
```


## Get received Command parameters ##

The onMessage method has a _Command c_ parameter. Is possible to access the received parameters this way:

String saveItInAVariable = c.getProperty("COMMAND-PARAM-NAME");

## Accessing Data Structures from Crosslanguage Plugins ##

This is done through a REST connection which serves this data. More info [here](RestAPI.md)