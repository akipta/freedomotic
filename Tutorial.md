

# Developers Tutorial #

THIS TUTORIAL IS IN PROGRESS. PLEASE REFER TO THE OFFICIAL WIKI FOR STABLE DOCUMENTATION



Here you will learn how to setup the development environment and create your first Freedomotic Hello world plugin. This plugin will just print some "Hello World" messages in the log. These messages could be seen with a right click on Log Viewer plugin. Hello world plugin is pretty simple but it has the same structure that is present on more advanced plugins.

Throught this tutorial, it is assumed the use of the NetBeans IDE. For others IDE like Eclipse or IntelliJ the steps are very similar.
Download and install the Freedomotic SDK

  1. Download Netbeans IDE from http://netbeans.org/. You can use your favourite IDE but this is the reference for this project, so all instructions are related to this IDE.
  1. Download the latest Freedomotic SDK release from the Freedomotic downloads site  http://freedomotic.googlecode.com/files/freedomotic-sdk-v5.3.0.zip
  1. Unzip the downloaded archive.
  1. Open Netbeans. Use menu File->Open Project to open the hello\_world project you'll find in UNZIPPED\_FOLDER/hello-world.
  1. Build the hello-world project. To build a project in Netbeans right click on project name -> Build. Now the hello world plugin is compiled and installed automatically into freedomotic. You can find it in UNZIPPED\_FOLDER/freedomotic/plugins/devices.
  1. After you have built the hello-world project, enter in UNZIPPED\_FOLDER/freedomotic. Now you can run freedomotic.exe (on windows) or freedomotic.sh (on linux) with your changes to hello-world plugin.

Next step is experimenting with hello-world code and rebuild the project after every change you made to have them installed automatically into Freedomotic.

Notes:

> The hello-world plugin just prints some log messages you can see with a right click on Log Viewer plugin.
> To build a project in Netbeans right click on Project name -> Build. The plugin will be copied automatically at every build into UNZIPPED\_FOLDER/freedomotic/plugins/devices

# Before start coding #

Do you know how to create an automation? If you want to execute something like "if this than that" you don't need to write code. Please be sure to understand how to configure objects and how to create an automation before start coding. Take a look at users getting started.

# Play with Hello World source code #

## Write a message to the GUI using a Freedomotic event ##

in onRun() method of your plugin write:
```
    protected void onRun() {
            //get and format the current date and time
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            //create a freedomotic message event
            MessageEvent message = new MessageEvent(null, "Hello world plugins says current time is " + dateFormat.format(date));
            Freedomotic.sendEvent(message);
    }
```

then build your plugin, start Freedomotic, start your plugin, you will see a blue bubble with the current date and time in the upper left side of the environment map.

## Make your plugin send emails ##

in onRun() method of your plugin write:

```
    protected void onRun() {
        MessageEvent message = new MessageEvent(this, "The mail text");
        message.setType("mail"); //send this message as an email
        message.setTo("destination@gmail.com"); //the destination mail address
        Freedomotic.sendEvent(message);
    }
```

this requires the Mailer plugin to be installed and properly configured.

## Print the list of objects in the environment ##

in onRun() method of your plugin write:

```
    protected void onRun() {
            StringBuilder buffer = new StringBuilder();
            for (EnvObjectLogic object : EnvObjectPersistence.getObjectList()) {
                buffer.append(object.getPojo().getName() + "\n");
                for (BehaviorLogic behavior : object.getBehaviors()) {
                    buffer.append("  " + behavior.getName() + ": " + behavior.getValueAsString()  + "\n");
                }
            }

            //print the string in the freedomotic log using INFO level
            Freedomotic.logger.info(buffer.toString());
    }
```

then build your plugin, start Freedomotic, start your plugin, with a right click on "Log Viewer" plugin you will see the list of objects printed. NOTE: change logging level to "INFO" using the combobox of log viewer plugin to filter the less important log records.

## Print the list of zones of the environment ##
TODO

## Change an object location on the map ##
This piece of code iterates over all loaded objects and moves objects of type Person to a random location. The randomLocation() function should be implemented and must return an `it.freedomotic.model.geometry.FreedomPoint` type (remember to add freedomotic-model.jar to your classpath)
```
    protected void onRun() {
        for (EnvObjectLogic anObject : EnvObjectPersistence.getObjectList()) {
            if (anObject instanceof it.freedomotic.objects.impl.Person){
                Person person = (Person)anObject;
                FreedomPoint location = randomLocation();
                person.getPojo().getCurrentRepresentation().setOffset(
                        (int)location.getX(), 
                        (int)location.getY()
                        );
                person.setChanged(true);
            }
        }
    }
```

## Change objects state programmatically ##
TODO

## Make your plugin execute Freedomotic commands ##
TODO


# Freedomotic data structures #

If you plan to develop a plugin for Freedomotic you need to access the framework data structures.

These types of data are concerned with building automation and Freedomotic architecture:

> Environment topology
> Environment objects
> People
> Loaded plugins and extensions

## Environment topology ##

Environment data are accessible by the static reference. It returns the Environment object instance which gives you access to all the environment properties
```
    Freedomotic.environment.getPojo();
```

Returns all the zones in the environment
```
    Freedomotic.environment.getZones()
```

Zones are logical (virtual) portions of the environment. To retrieve the list of physical environment rooms use (rooms are zones too)
```
    Freedomotic.environment.getRooms()
```

## Environment objects ##

The [objects](Object.md) (lights, doors, couches, ...) in the environment can be retrived using:
Get an object by name (returns null if is not in the list)
```
    EnvObjectPersistence.getObject(String name)
```

Get the objects filtering by protocol and address property
```
    EnvObjectPersistence.getObject(String protocol, String address)
```
Get the list of objects that are linked to a protocol
```
    EnvObjectPersistence.getObjectByProtocol(String protocol)
```
Get the list of all objects in the current environment
```
    EnvObjectPersistence.getObjects()
```
This is the correct import to access this method
`import it.freedom.environment.EnvObject;`

## Plugins ##

Get the list of loaded plugins
```
    AddonManager.getLoadedPlugins()
```
it returns an ArrayList of Plugin type.

Get a plugin by name
```
    AddonManager.getPluginByName(String name)
```
Remember to import it.freedomotic.plugins.AddonManager;
Get plugin configuration from manifest

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
Learn more about Freedomotic anatomy

# Learn more about freedomotic anatomy #

## Architecture Components ##

Freedomotic is therefore primarily a programming framework for the automation and control of environments whose purpose is to drastically reduce time-to-market and effort necessary for the development of automation applications. This is possible by noting that every automation system will require common functionalities regardless of the scope (home, business, industrial, hotel, telemedicine, etc. ...).
Freedomotic is an automation software composed by a core (the framework) plus some plugins.

## The Framework ##
  1. Mantains an internal data structure representing the environment (topology, room connectrions, ...), the objects in it and their state (on, off, open, closed, 50%dimmed,...)
  1. Makes this data available to external clients in a language independent way (XML, JSON, RESTlet, ...), so they can use a kind of logic like "turn on kitchen light" instead of "send to COM1 port the string `#*A01AON##"`. Is just like they can see the same environment map the user sees; as a developer you can forget hardware level stuff and related issues. From this follows you can develop in your favourite language and just connect to the framework and exchange text messages.
  1. Provides a rules engine coupled with a natural language processing system to let the user writing automations in plain english like "if outside is dark turn on livingroom light". You can add, update and delete this automations at runtime using GUIs, without the need of coding.

<img src='http://www.freedomotic.com/images/wiki/architecture-layers.png'>

<h2>The Plugins</h2>

<h3>Devices plugins</h3>
Freedomotic plugins can add more features to the framework and can be developed and distributed as completely independent packages on our marketplace.<br>
They usually are developed to communicate with automation hardware like X10, KNX and so on, but also graphical frontends and "web service readers" are Freedomotic plugins just as any other source of info, like webcams, text to speech engines and SMS senders.<br>
<br>
<h3>Objects plugins</h3>
You can also develop "object plugins" which are pieces of software which models the behavior of objects like lamps, doors, etc... instructing the framework on how they behave. For example a lamp object plugin tells the framework that a lamp has a boolean behavior called powered and a dimmed behavior which is represented by an integer from 0 to 100. A lamp can turn on, turn off and dimm. If dimmed becomes 0% the lamp is powered=false and if dimmed > 0% the lamp is powered=true.<br>
<br>
<img src='http://www.freedomotic.com/images/wiki/objects.png'>

<h3>Interaction between components</h3>

Here is a diagram which explains the interaction between plugins, events+triggers+commands, and freedomotic APIs<br>
<br>
<img src='http://www.freedomotic.com/images/wiki/object-plugin interaction.png'>

<h1>Communicate with web services</h1>


<h1>Communicate with hardware devices</h1>


<h1>How to debug using log messages</h1>


<h1>Create custom objects as plugins</h1>

<h1>Publish your plugin</h1>

<h1>Freedomotic API for external softwares</h1>

<h1>Resources</h1>

<h2>Event channels</h2>