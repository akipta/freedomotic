# Introduction #

When you create a new command you can choose two different ways, the first is the creation of an xml file deployed in the _**FREEDOMOTIC\_ROOT/data/cmd**_ folder, the second option is to use the EventEditor plugin. The first choice is the better for developers because it guarantees full control of the values because the EventEditor is still under development.

A Freedom command is a container of customizable parameters in the form of `parameter = value`.  Standard parameters are name, description and receiver to guarantee the correct routing of the message to the actuator that can execute the task.

The commands xml's are the "telegrams" used to instruct the actuators on which action must be performed. Some commands are created at runtime, the same way the sensors creates events, however there are commands that can be created at "design" time by the users.

## Common parameters ##

This fields are common to all Freedomotic commands and they are mandatory

| **Field** | **Description** |
|:----------|:----------------|
| name | A short string that identifies the effect of the command execution (eg: turn on light in the kitcken) |
| description | An extended description of the effect of the command execution. Write it in form "IF anEvent OCCURS THEN THE SYSYEM ... yourDescription|
| receiver | The Channel on which the target plugin is listening to |
| delay | Not Yet Implemented, let this parameter to 0 |


## Examples ##

Turn on object named "livingroom light"

```
<it.freedomotic.reactions.Command>
  <name>Turn on livingroom light</name>
  <receiver>app.events.sensors.behavior.request.objects</receiver>
  <description>turns on an object called livingroom light</description>
  <editable>true</editable>
  <properties>
    <properties>
      <property name="behavior" value="powered"/>
      <property name="value" value="true"/>
      <property name="object" value="Livingroom light"/>
    </properties>
    <tuples/>
  </properties>
</it.freedomotic.reactions.Command>
```

Switch power of all Light type objects in the environment

```
<it.freedomotic.reactions.Command>
  <name>switch power for all lights</name>
  <receiver>app.events.sensors.behavior.request.objects</receiver>
  <description>switch power for all lights</description>
  <editable>true</editable>
  <properties>
    <properties>
      <property name="behavior" value="powered"/>
      <property name="value" value="opposite"/>
      <property name="object.class" value="EnvObject.ElectricDevice.Light"/>
    </properties>
    <tuples/>
  </properties>
</it.freedomotic.reactions.Command>
```

Increase brightness (one step) of all Light type objects in the environment

```
<it.freedomotic.reactions.Command>
  <name>Increase lights brightness</name>
  <receiver>app.events.sensors.behavior.request.objects</receiver>
  <description>increases light brightness</description>
  <editable>true</editable>
  <properties>
    <properties>
      <property name="behavior" value="brightness"/>
      <property name="value" value="next"/>
      <property name="object.class" value="EnvObject.ElectricDevice.Light"/>
    </properties>
    <tuples/>
  </properties>
</it.freedomotic.reactions.Command>
```

Decrease brightness (one step) of all Light type objects in the environment

```
<it.freedomotic.reactions.Command>
  <name>Decrease lights brightness</name>
  <receiver>app.events.sensors.behavior.request.objects</receiver>
  <description>decreases lights brightness</description>
  <editable>true</editable>
  <properties>
    <properties>
      <property name="behavior" value="brightness"/>
      <property name="value" value="previous"/>
      <property name="object.class" value="EnvObject.ElectricDevice.Light"/>
    </properties>
    <tuples/>
  </properties>
</it.freedomotic.reactions.Command>
```

## Custom parameters ##

Custom parameters are the key/values accepted by the target plugin. You can send to an actuator any value associated to any parameter name as long as the target plugin can accept it. This makes the system fully configurable and make easy the integration with external crosslanguage clients.

The list of parameter accepted by a plugin are referenced in its wiki page, for example the VlcController plugin accepts "url" and "close".

Parameters are defined inside the `<properties>` tag with
`<property name="parameterName" value="parameterValue"/>`
you can add more than one parameter adding more lines

```
<properties>
  <properties>
    <property name="param1" value="value 1"/>
    <property name="param2" value="value 2"/>
    <property name="param3" value="value 3"/>
  </properties>
</properties>
```

In some contexts, in which parameters number is variable, it's possible to specify the list of them using the word "parameter" as prefix following by a progressive number between square brackets.

```
<properties>
  <properties>
    <property name="parameter[AN_INT_FROM_0_TO_999]" value="aStringValue"/>
  </properties>
</properties>
```


**TODO: move in developer tutorial**
You can retrieve the list of parameters using this method
```
ArrayList<String> myParams = c.getParametersAsList();
```

It creates an ordered list reading the command properties writed in the previous format. Other properties format are ignored (not added to the returned list). The indexes must be contiguous (1,2,3,...)
For example if in command:

```
parameter[0] = foo
parameter[1] = bar
parameter[3] = asd
object = Light 1
another-param = myValue
```

the returned `ArrayList<String>` is
```
[0]->foo
[1]->bar
[3]->asd //because index = 2 is missing.
```


# Create the command file #

The files are named with progressive integer values. If the last command in folder _**FREEDOMOTIC\_ROOT/data/cmd**_ is named int@11.xml you should name the next command as int@12.xml.

# An example #

```
<it.freedom.reactions.Command>
  <name>Show Surveillance Camera</name>
  <receiver>app.actuators.video.player.in</receiver>
  <delay>0</delay>
  <description>shows the video streamed by the surveillance camera in the entrance</description>
  <properties>
    <properties>
      <property name="url" value="http://213.192.14.189/axis-cgi/mjpg/video.cgi?resolution=320x240"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```


# Using reference values taken from the event #

This command sends the parameters address, object, behavior and command to the X10 Protocol Actuator.
Values can be referenced unsing the `@event.` prefix. This means the value is the same as the one embedded in the event captured by the trigger. You can learn more about this useful feature [here](CommandInheritance.md).

```
<it.freedom.reactions.Command>
  <name>Turn this X10 device to ON</name>
  <receiver>app.actuators.X10.X10Actuator.in</receiver>
  <delay>0</delay>
  <description>the system turns this device ON</description>
  <properties>
    <properties>
      <property name="address" value="@event.device"/>
      <property name="object" value="@event.device"/>
      <property name="behavior" value="On"/>
      <property name="command" value="@event.command"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```

# Reserved Parameter Names #

Some parameters names are reserved and cannot be used to store different values.

| **Parameter Name** | **Used by** |
|:-------------------|:------------|
| result | Used by the actuator to store the return value of its execution like error codes of ack |
| object | This parameter always identifies the target object of the command (eg: Light 1) |
| behavior | Identifies the new behavior of "object" if the command is succesfully executed (eg: ON) |
| stopIf | Identifies the value that cause the execution of the command list to stop. For example, you don't want to execute the next command if this is not succeded |


# Command Scripting #
Commands parameters can be scripted using javascript syntax like this:

```
<it.freedom.reactions.Command>
  <name>Say the current temperature converted in fahrenheit</name>
  <receiver>app.actuators.media.tts.in</receiver>
  <delay>0</delay>
  <timeout>2000</timeout>
  <description>say the current temperature using TTS engine</description>
  <hardwareLevel>false</hardwareLevel>
  <persistence>true</persistence>
  <executed>false</executed>
  <properties>
    <properties>
      <property name="say" value="= say="The current temperature in @event.zone is " + Math.round(((@event.temperature+40)*1.8)-40) + " fahrenheit degrees. In celsius is @event.temperature degrees"/>
    </properties>
    <tuples/>
  </properties>
</it.freedom.reactions.Command>
```

This command uses **text to speech** to say the current temperature in a zone and makes a on the fly conversion fron celsius to fahrenheit degrees. The property key of the is a variable in the scripting context that can be evaluated. To make a value scriptable it must start with an "=" just like Excel. Value that not start with "=" are the same as the previous freedom versions.

Here other example of scripting:

```
<property name="myVar" value="= myVar=0; for (i=0; i<10; i++) myVar+=i;"/> //sums the first 10 integer and store the value in myVar property
```

```
<property name="myVar" value="= if (1==1)  myVar=1; else myVar="AREYOUJOKING?";"/> //if one is one myVar property is one
```

```
<property name="myVar" value="= myVar=!@event.object.powered;"/> //negate the powered value of on envobject; if is true becomes false, if is false become true
```