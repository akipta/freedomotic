# Introduction #

I made a wiki page as support of the discussion in the forum at http://www.freedomotic.com/forum/6/230
We mantain the discussion in the forum but we use the wiki to exchange examples and pieces of code in a more readable way.

## Current Freedomotic release ##


TODO


## Next Freedomotic release ##

In the next Freedomotic release we make a change to make the relation between objects and commands more understandable. This change also simplify the development of actuators.

We have two levels of command: the first is the "**user level command**" in which an administrator can define commands like
  * Turn on light 1 (params: object.name=light 1; object.behavior=on)
  * Turn on all lights (params: object.category= lights; behavior=on)
  * Turn on all lights in the kitchen (params: object.category=lights; behavior=on; zone=kitchen)

All this commands are published on the channel `app.events.sensors.behavior.request.objects`
On this channel a Java middleware class is listening, parsing received paramenters and performs the related action. How this can be done? We need to introduce "**developer level commands**" and map an object behavior to this.

**Object ElectricDevice1**
  * on -> Turn on relay 1 in progettihwsw board
  * off -> Turn off relay 1 in progettihwsw  board

### To sum up ###

  1. An event is notified. Eg: "luminosity in kitchen is 30%"
  1. A trigger receives the event. Eg: trigger: "if luminosity in kitchen is less the 50%"
  1. If trigger fires a command is throwed. Eg: command: "turn on light 1". User level command "Turn on light 1" is published on channel `app.events.sensors.behavior.request.objects`
  1. `BehaviorManeger.java` receives on this channel
  1. `BehaviorManager.java` parse received data (object-name, behavior-name, ...)
  1. using the behavior-name parameter in received command it retrives the associated "developer level command"
  1. `BehaviorManager.java` publish the "developer level command"
  1. the developer level command is received by the proper actuator, say X10Actuator and performed as usual.
  1. the `BehaviorManager.java` updates the object behavior according to the result of the actuator execution (executed succesfully or not)

### Side effects of this solution ###

In the actuator the setTargetObjectBehavior() method is no longer neccessary, the behavior change is completely managed by the `BehaviorManager.java` class.
A PHP application that sends a command (as the PeerDispatcher do) don't need to know the hardware level properties of the command, it can send a "turn on light 1" instead of "send to X10actuator A01 AON".

### Build a developer level command ###

This commands are defined in XML by the actuator plugin developer. This commands are deployed in the FREEDOMOTIC\_ROOT/data/cmd/basic folder and they are not editable with the GUI by the end user. The association between object name and developer level command can be done in the GUI by the end user or by editing the object XML (inside every behavior tag in object XML you can edit the `<command></command>` tag eg: `<command>Turn on relay 1 in arduino board</command>`).

**The simpler case: one developer command for every actuator possible action**

**Object ElectricDevice1**
  * on -> Turn on relay 1 in progettihwsw board (params: progettihwswCommand=8; progettihwswExpectedResult=A)
  * off -> Turn off relay 1 in progettihwsw  board (params: progettihwswCommand=9; progettihwswExpectedResult=B)

This is easy. **But if we deal with a more complex protocol like X10? We want to avoid to have to build an XML per device**: turn on device A01, turn off device A01 and so on... For example in X10Actuator we need an x10 address and an x10 command.
The X10 actuator developer can define a list of hardware level commands in XML like this:

```
<it.freedom.reactions.Command>
  <name>Switch Off an X10 device</name>
  <receiver>app.events.actuators.X10.X10Actuator.in</receiver>
  <delay>0</delay>
  <description>an x10 device is turned to off</description>
  <properties>
    <properties>      
      <property name="x10address" value="@object.address"/>
      <property name="x10command" value="OFF"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```

This seems unuseful because you can get the same information from from the "turn on light 1" user level command. This is not true because if the command was "turn on all lights" the actuator should implement a cycle to get all the light objects and apply the on behavior to all of this, but this is impossible becuase an actuator can't control all lights, some of them can be connected do a do it yourself board or modbus actuator and so on. The developer level command is useful to link an object with its correct actuator (with the receiver tag) and prepare "half processed parameters" for it. This mechanism can be enhanced in the future with the evaluation of regular expression as the value of a parameter.

**Another case. We have a board with a lot of relays, we don't want to build an exagerated number of commands in the form "turn ON/OFF relay RELAY\_NUMBER"**, at the same time we want the end user to use abstracted addresses (eg: the string "RELAY1" instead of "8" which means relay 1 on in the progettihwsw protocol).

```
<it.freedom.reactions.Command>
  <name>Control progettihwsw relay board</name>
  <receiver>app.events.actuators.relayboard.progettihwsw.in</receiver>
  <delay>0</delay>
  <description>insert here a description</description>
  <properties>
    <properties>   
      a mapping section...
      <property name="RELAY1-ON" value="8"/>  
      <property name="RELAY1-ON-EXPECTEDRESULT" value="A"/>  
      <property name="RELAY1-OFF" value="9"/>  
      <property name="RELAY1-OFF-EXPECTEDRESULT" value="B"/>
      ... others mapping ...
      <property name="ProgettiHWSWCommand" value="@object.address-@event.behavior"/>
      //object.address=RELAY1 and event.behavior=ON
      <property name="ProgettiHWSWResult" value="@object.address-@event.behavior-EXPECTEDRESULT"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```


in the plugin Java class if we call
```
String result = getParameter("ProgettiHWSWCommand"); 
```
we receive something like "RELAY1-ON". If we call
```
String result = getParameter(getParameter("ProgettiHWSWCommand"));
```
we receive "8" as result, which is the string to send to the hardware to turn on the first relay.

Summing up an hardware level command can always refer to:
  * @object.address
  * @object.behavior
  * @event.A\_PROPERY\_IN\_THE\_FREEDOM\_EVENT (event: "luminosity in kitchen is 30%" -> trigger: "if luminosity in kitchen is less the 50%" -> command: "turn on kitchen light") in this case we can use "@event.luminosity"
  * @object.behavior.A\_BEHAVIOR\_PROPERTY (eg: the dimm-value if a light change from OFF to DIMMED)




### The X10 protocol reference ###

**x10address:** DEVICEHOUSECODE+DEVICEADDRESS: A01; P08; ...

DEVICEHOUSECODE: a letter from A to P

DEVICEADDRESS: a int from 01 to 16

**x10command:** DEVICEHOUSECODE+X10COMMAND: AON, AOFF; BON, ...

DEVICEHOUSECODE: a letter from A to P

X10COMMAND: ON, OFF, ...other x10 commands

## Downloadable Objects, Plugins and Commands ##

TODO
summary: objects, plugins anch commands are defined in a modular way to be downloadable from the internet. The user can manually download and past in the correct folder or use a GUI feature (to implement) to automatize the "installation".