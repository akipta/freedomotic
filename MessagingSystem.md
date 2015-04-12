Events
One-sentence summary of this page.

## Freedomotic Messaging System ##


Freedomotic messaging system consists in a MOM (Message Oriented Middleware) that uses an Advanced Message Queuing Protocol (AMQP) for management of incoming events from environmental sensors and the routing of commands to the actuators, forwarding them to the hardware component which can execute them.

Freedomotic is based entirely on events, and any changes in the environment and every interaction with the software by the user (eg clicks on the GUI) generate events. [Events](Events.md) are published on [Channels](Channel.md) and can be intercepted by [Triggers](Trigger.md). Each trigger may be associated with one or more [Commands](Command.md), defining a [Reaction](Reaction.md).

From this architecture it follows that the program behavior is not predetermined but is fully modificable at runtime, making it extremely flexible and adaptable to any possible use in building automation.

![http://www.freedomotic.com/images/site/software-layers.png](http://www.freedomotic.com/images/site/software-layers.png)

A **sensor** communicates a change in the environment sending out an **event**. Events are sent on Channels.

A **trigger**, which is a sort of event filter, listens to the event  subscribing the channel on which this event is sent. If the event is consistent with the trigger, one or more **commands** will be executed. The command is automatically sent to the actuator which is able to execute it.

  * A sensor can be an hardware device like a luminosity sensor.
  * An event is fired by a sensor, for example "luminosity in the kitchen is 30%"
  * A trigger can define an expression like "if luminosity is less than 50%"
  * A command can be something like "turn on the light in the kitchen"
  * An actuator can be an X10 Lamp module

The result of the interaction between event, trigger and command is a reaction. In this case the reaction is "if luminosity is less than 50% turn on the light in the kitchen"

**Notes**: Triggers and commands are defined by the user using the graphical EventEditor. Triggers, Commands and Reactions are saved as XML files.

## A Message Journey ##

The communication process of notification of an event to the execution of a command consists of several steps:
[Event](Event.md) (notified by plugins or freedomotic itself) -> [Trigger](Trigger.md) (acting as a filter of events to define simple use cases) -> [Command](Command.md) (executed by an actuator)

In this example we will analyze an automation [Reaction](Reaction.md) composed of a single command:

If 'Livingroom light turns on or off' then 'say its status using text to speech'

### What happens in the framework ###

This is an event which describes a state change of a light which turns from OFF (powered=false) to ON (powered=true). This kind of events is notified on channel `app.event.sensor.object.behavior.change` by a sensor plugin, for example an X10 sensor.

Events can be notified by hardware protocol plugins, frontends or freedomotic itself as in this case. This event informs all listeners that an object named "Livingroom Light" is changed.

```
<it.freedomotic.events.ObjectHasChangedBehavior>
  <eventName>ObjectHasChangedBehavior</eventName>
  <sender>Light</sender>
  <payload>
    <payload>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>date.dayname</attribute>
        <operand>EQUALS</operand>
        <value>Sunday</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>date.day</attribute>
        <operand>EQUALS</operand>
        <value>23</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>date.month</attribute>
        <operand>EQUALS</operand>
        <value>September</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>date.year</attribute>
        <operand>EQUALS</operand>
        <value>2012</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>time.hour</attribute>
        <operand>EQUALS</operand>
        <value>9</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>time.minute</attribute>
        <operand>EQUALS</operand>
        <value>45</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>time.second</attribute>
        <operand>EQUALS</operand>
        <value>49</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>sender</attribute>
        <operand>EQUALS</operand>
        <value>Light</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>object.name</attribute>
        <operand>EQUALS</operand>
        <value>Livingroom Light</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>powered</attribute>
        <operand>EQUALS</operand>
        <value>true</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>brightness</attribute>
        <operand>EQUALS</operand>
        <value>0</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>object.currentRepresentation</attribute>
        <operand>EQUALS</operand>
        <value>0</value>
      </it.freedomotic.reactions.Statement>
    </payload>
  </payload>
  <isValid>true</isValid>
  <uid>116</uid>
  <executed>true</executed>
  <isExecutable>true</isExecutable>
  <creation>1348386349837</creation>
  <priority>0</priority>
</it.freedomotic.events.ObjectHasChangedBehavior>
```

You can define triggers to narrow any event just by listening on the event channel and setting a list of conditions (the statements) that must be met in order to consider this trigger as fired. This trigger can be used as the "when/if" part of an automation (aka scenary).

Freedomotic starts with a set of predefined triggers which cover the most use cases. At any time you can add new use cases using an existent trigger as a template.

```
<trigger>
  <name>Livingroom Light turns ON or OFF</name>
  <channel>app.event.sensor.object.behavior.change</channel>
  <payload>
    <payload>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>object.name</attribute>
        <!-- allowed operand are EQUALS, REGEX, GREATER_THEN, GREATER_EQUAL_THEN, LESS_THEN, LESS_EQUAL_THEN -->
        <operand>EQUALS</operand>
        <value>Livingroom Light</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>powered</attribute>
        <operand>EQUALS</operand>
        <!-- here you can write true to select only 'turns on' cases -->
        <!-- here you can write false to select only 'turns off' cases -->
        <!-- ANY is used to match any case -->
        <value>ANY</value>
      </it.freedomotic.reactions.Statement>
    </payload>
  </payload>
</trigger>
```

In automation you bound a trigger to one or more commands. In this case the automation is "when **Livingroom Light turns on** then **Say electric device status**"

The command "Say electric device status" is shipped with the text to speech plugin (http://freedomotic.com/content/plugins/text-speech) and looks like this:

```
<it.freedomotic.reactions.Command>
  <name>Say electric device status</name>
  <description>say electric device status</description>
  <receiver>app.actuators.media.tts.in</receiver>
  <properties>
    <properties>
      <property name="say" value="= 
      	if (@current.object.powered) 
      		say="@current.object.name is on with brightness at @current.object.brightness"; 
      	else 
      		say="@current.object.name is off";
      		
      	"/>
    </properties>
    <tuples/>
  </properties>
</it.freedomotic.reactions.Command>
```


When a trigger is fired freedomotic loads all related commands and evaluate them using runtime properties. So the command above will look like this when received by the TTS plugin.

Every plugin have access to time and date info, the set of properties defined in the event and the current object state if the event have have something to do with environment objects (in this case a light). **Your plugin can use all this information for tokens substitution and scripting as for the 'say' property in the command above. In the command below you can see how the 'say' property is evaluated by freedomotic before sending it to the text to speech plugin**.


```
<it.freedomotic.reactions.Command>
  <name>Say electric device status [EVALUATED]</name>
  <description>say electric device status</description>
  <receiver>app.actuators.media.tts.in</receiver>
  <properties>
    <properties>  
      <!-- Static properties for the text to speech actuator. -->
      <!-- This are defined in data/cmd folder of the actuator itself -->
      <!-- The 'say' property is evaluated using runtime properties -->
      <property name="say" value="Livingroom Light is off."/>
      <!-- ALL PROPERTIES BELOW ARE EVALUATED AT RUNTIME -->    
      <!-- generic data taken from the event which started the event-trigger-command chain. -->
      <property name="event.sender" value="Light"/>
      <property name="event.date.dayname" value="Sunday"/>
      <property name="event.date.day" value="23"/>
      <property name="event.date.month" value="September"/>
      <property name="event.date.year" value="2012"/>
      <property name="event.time.hour" value="10"/>
      <property name="event.time.minute" value="30"/>
      <property name="event.time.second" value="24"/>
      <!-- the state of the object Livingroon Light when the event was fired -->
      <property name="event.object.name" value="Livingroom Light"/>
      <property name="event.brightness" value="0"/>
      <property name="event.powered" value="false"/>
      <property name="event.object.currentRepresentation" value="0"/>
      <!-- the current state of the object Livingroom Light (when this command is executed -->
      <property name="current.object.name" value="Livingroom Light"/>
      <property name="current.object.type" value="EnvObject.ElectricDevice.Light"/>
      <property name="current.object.protocol" value="unknown"/>
      <property name="current.object.address" value="unknown"/>
      <property name="current.object.brightness" value="0"/>
      <property name="current.object.powered" value="false"/>
    </properties>
  </properties>
</it.freedomotic.reactions.Command>
```