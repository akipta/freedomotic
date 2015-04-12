## Coding ##

**HIGH PRIORITY** (Strongly want to have in the release 5.0)

| **(DONE IN PROTOTYPE 1)** | Implement a BehaviorManager class to divide commands in **user level** and **developer level** |
|:--------------------------|:-----------------------------------------------------------------------------------------------|
| **(DONE IN PROTOTYPE 1)** |Implement relative behaviors like (opposite, next, previous)|
| **(DONE IN PROTOTYPE 1)** |Implement the BehaviorManager for an object name (eg command: "turn on light 1").|
| **(DONE IN PROTOTYPE 1)** |Implement a GUI to map an object behavior with a related **developer level** command (only a textbox containing the target command name)|
| **(DONE IN PROTOTYPE 1)**| Add _object.address_; _object.behavior-name_; _object.behavior.PROPERTY_ to the command parameters before dispatching|
| **(DONE IN PROTOTYPE 1)** |Refactor classes requiring persistence. Make persistence functions live in a separate class.|

**NORMAL PRIORITY**

| **(DONE IN PROTOTYPE 2)**| Make events and object to be loaded dynamically at startup as for actuators and sensors plugins. Sensors and actuator might live in their own subfolder under plugins directory. This subfolder must contain commands definition, trigger and other data related to the plugin and must be loaded at startup as if they were in the _freedom\_root/data_ folder.|
|:-------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **(DONE IN PROTOTYPE 2)** |Add a `<channel></channel>` xml tag to triggers to address a channel with a plain string|
| **(DONE IN PROTOTYPE 2)** |the plugin folder must be reorganized with a subfolder for every plugin.|
| **(DONE IN PROTOTYPE 2)** |Prepare a catalogue of objects, actuator, sensors, commands that can be downloaded from the website. This require a website page for every plugin that explains where put the downloaded files to make the system work.   |
| **(DONE IN PROTOTYPE 2)** | Moved the resources folder under /data/|
| **(DONE IN PROTOTYPE 2)** |Perimetral walls are auto generated and drawed.|

**LOW PRIORITY** (May slip to later milestone)

| **(DONE IN PROTOTYPE 3)** |Make object easier to develop. Created a new java event like interface to expose object properties that extends predefined behaviors like "BooleanBehavior" that can be used for on/off of an electric device or "RangedIntBehavior" that can be used to set lights brightness (from 0% to 100%)|
|:--------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  **(DONE IN PROTOTYPE 3)** |Create Rooms as subclass of Zones. Room shape can be used to draw walls.|
|  **(DONE IN PROTOTYPE 3)** |Add a way to control object behaviors from the GUI. The new object system exposes the object behavior like "RangedIntBehavior" or "BooleanBehavior". Now the GUI (left click on an object to show) renders a layer of controller besed on the type of behavior. Eg: for BooleanBehavior we have a button to tutn on/off the property; for RangedIntBehavior we have a slidebar to set the integer value.|
| **(DONE IN PROTOTYPE 3)** |Port useful info that are only in the console (like loaded plugin, trigger, commands, reaction) to a GUI frontend or something "easy-to-reach". An HTML logger is created|
| **(DONE IN PROTOTYPE 3)** |Add the regular expression (regex) check as an avalaible operation in triggers along with EQUAL, GREATER\_THEN, LOWER\_THEN. Regex check uses "REGEX" as operand keyword. It's is extremely useful for example to check if an object is part of a hierarchy requesting the match for a part of the dot notation used to define this hierarchy. Eg: we can request that an object has a type that matches `EnvObject.ElectricDevice.*` to know the target object is a subclass of Electric Device like a light.|
| **(DONE IN PROTOTYPE 3)** |Add the type tag at an object definition. it is the dot notation of it's hierarchy eg: `EnvObject.ElectricDevice.Light` In triggers is possile to filter the event performing regex evaluations on the type of the object that have generated it.|
| **(DONE IN PROTOTYPE 3)** |Added to trigger: maxExecutions; numberOfExecutions; suspensionTime; this values can be used to control and limit the number and the timing of triggers executions |
| **(DONE IN PROTOTYPE 3)** | Added math operations in the values of commands. For example value = 100 - @event.luminosity if the luminosity listened in the LuminosityEvent is 40 the value becomes value = 60 |
| **(DONE IN PROTOTYPE 3)** | Added a first implementation of LuminosityEvent and TemperatureEvent. Future improvemnts are needed |
| **(DONE IN PROTOTYPE 3)** | Define which plugin can be activated at Freedom startup. Added a parameters in the configuration file of plugins to make the plugins start at load time. parameter is `<property name="startup-time" value="on load"/>`  |
| **(DONE IN PROTOTYPE 3)** | Added gate objects (like doors) to connect rooms. At startup a check is performed to see which rooms are connected. When a gate opens/close the reachable rooms are updated. Rooms are considered as edges of a graph and gates are the links between nodes. The path of reachable rooms is calculated with Dijkstra's Algorithm |
| **(DONE IN PROTOTYPE 3)** | Add a flag to commands to distinguish between hardware level command (not usable in reactions) and user level command (usable in a reaction). To use set this parameter     `<hardwareLevel>true</hardwareLevel>` in the command xml file. No parameters means the command can be used in reactions |

## Tasks ##

| TODO | Make a freedom brochure with a commercial perspective (vision, mission, target audience, ...). It can be translated in different languages.|
|:-----|:-------------------------------------------------------------------------------------------------------------------------------------------|
| TODO |Develop a guide for environment editing |
| TODO |Develop a guide for Activemq installation and configuration on different os for using an external broker |
| TODO |Develop a guide for different using modes of Freedom (one pc, more pc, pc and embedded system) |
| TODO |Develop a user guide with basic info like how to make a light turn on, how to create a trigger/command. Discuss an index on the forum starting from the current **Getting started** tutorial.|
| TODO |Develop a guide for new objects creation [refer to this page](http://code.google.com/p/freedomotic/wiki/DevelopObjects)|
| TODO | Put a beta icon on the wesite frontpage to say clear that the work is in progress. Explain it on the website|
| TODO |Open an offtopic section on the forum|
| **(50% DONE)**|Add more meaningful screenshots (with descriptions)to the website gallery|
| TODO |Make a list of predefined events and their parameters|
| **DONE** | Add a clear vision and mission statement in the about us page|
| TODO | Explain the contribution system, with roles and rules for applicants|
| TODO | Create a brief tutorial on how to report bug using the issue tracking|
| TODO | Update Javadoc |
| TODO | internazionalization of software and documentation |
| TODO | add a forum topic to discuss about different solutions for sincronization of different freedom instances |