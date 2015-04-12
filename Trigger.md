# What is a Trigger #

A trigger is a filter that permits to intercept an [Event](Events.md) on a [Channel](Channel.md). It performs check on the event carried values and tag this particular situation assigning a meaningful and reusable name to this restriction.

For example an event can be the notification that are the 10 o'Clock; a trigger can listen to time events and if the hour is between 7 o'Clock till 13 o'Clock you can name this trigger "it's morning" and reuse it to perform [Reactions](Reactions.md) like if "it's morning" then "turn off outdoor lights".

Therefore a trigger can be used to decide whether a notified event has to be processed or not. Whenever an event is processed by a trigger, if the trigger is consistent with it's definition the associated commands are executed. A reaction represents a link between a trigger and one or more commands list executed by an actuator.

In this brief tutorial is explained the manual creation of an XML trigger, however the end user can define triggers using the included graphical editor, so there is no need to edit the XML manually. The graphical editor as final result produces an XML like this.

## How to intercept a notified event ##

Events can be intercepted by [triggers](Trigger.md). Every event has a default [channel](Channel.md) on which it is notified. To know which is the default channel of a particular event, see [listenable events page](EventsList.md). To capture the event you just create a trigger that is listening on the same channel.
For example the PersonMoving event is published on channel `app.event.sensor.person.movement.moving`

To intercept a person movement you can define a [Trigger](Trigger.md) listening on channel "app.event.sensor.person.movement.moving".

## XML Example ##

This trigger can filter PersonExitZone events. In that case the trigger fires only if the event is related to the kitchen zone and the person ID can be ANY (valid for ANY person). If the trigger is consistent with the event one or more [Commands](Command.md) will be executed. The association between trigger and commands to execute is called [Reaction](Reaction.md)

```
<it.freedomotic.reactions.Trigger>
    <name>Someone Exits from Kitchen</name>
    <description>When someone exits from kitchen area</description>
    <channel>app.event.person.zone</channel>
    <payload>
        <payload>
            <it.freedomotic.reactions.Statement>
                <logical>AND</logical>
                <attribute>zone</attribute>
                <operand>EQUAL</operand>
                <value>Kitchen</value>
            </it.freedomotic.reactions.Statement>
            <it.freedomotic.reactions.Statement>
                <logical>AND</logical>
                <attribute>person</attribute>
                <operand>EQUAL</operand>
                <value>ANY</value>
            </it.freedomotic.reactions.Statement>
            <it.freedomotic.reactions.Statement>
                <logical>AND</logical>
                <attribute>action</attribute>
                <operand>EQUAL</operand>
                <value>exit</value>
            </it.freedomotic.reactions.Statement>
        </payload>
    </payload>
    <delay>0</delay>
</it.freedomotic.reactions.Trigger>
```


## How to filter received event parameters ##

As said before a trigger is a event filter. It can read event parameters and filter they according to the rules defined in the trigger. Every rule is called Statement. A statement is composed by a logical value, an attribute name, an operand and a value.

The **action** tag can be used to listen on the default channel of a specif event. You have to insert the complete path of the Java class that implements the event. Otherwise you can specify the channel with a custom string inside the `<channel> </channel>` tag.

**logical**: is used to concatenate a statement with the previous. The default value is AND meaning the following rule is in logical AND with the previous. At this time only the AND logical value is implemented.

**attribute**: its the name of the event property to filter. The know which properties are carried by an event you have to refer to the [event page](Events.md).

**operand**: can be EQUAL, REGEX, GREATER\_THAN or LESS\_THAN. It's used to relate the attribute with the value.

**value**: can be a string or an integer value. You can use the ANY key to match any value, sometimes can be useful.


## Max execution limit and flood control ##

Every trigger has a **max-executions** parameter which defines how many times this trigger can fire. This counter is reset on Freedomotic start up. If the value is -1 this trigger has no max executions limit

Another property if **suspension-time** which defines for how many milliseconds this trigger is disabled after firing. The trigger cannot fire again until it's suspension time is finished. Every trigger has a standard suspension time of 100ms which can be overwrited if needed setting a lower value.

## Listening to Channels with wildcard paths ##

This feature is applicable only if you insert the custom channel path as a string in the `<channel> </channel>` tag.

For example if a sensor generates events on channel
```
app.events.sensors.moving.person.P003
```
a trigger can listen to this particular event to receive details about person P003’s movements. Otherwise if the trigger listens to
```
app.events.sensors.moving.person.∗
```
it will receive information about the movement of all people detected in the environment.

The wildcard semantic is as follows:
  * period (.) is used to separate names in a path
  * asterisk (`*`) is used to match any name in a path
  * greater than sign (>) is used to recursively match any destination starting from this name

## Trigger scripting ##
TODO
example at http://code.google.com/p/freedomotic/source/browse/framework/freedomotic/test/it/freedomotic/core/ResolverTest.java

## Deploy of a trigger ##

Triggers are deployed in the FREEDOMOTIC\_ROOT/data/trg folder. They are files with .xtrg extension. Triggers in this folder are loaded at Freedomotic startup. In the console you can have a view of the loaded triggers and the channel on which they are listening.

## Examples ##

An object of type Electric Device is clicked

```
<trigger>
  <name>When an electric device is clicked</name>
  <description>When an electric device is clicked</description>
  <channel>app.event.sensor.object.behavior.clicked</channel>
  <payload>
    <payload>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>object.type</attribute>
        <operand>REGEX</operand>
        <value>^EnvObject.ElectricDevice\.(.*)</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>click</attribute>
        <operand>EQUALS</operand>
        <value>SINGLE_CLICK</value>
      </it.freedomotic.reactions.Statement>
    </payload>
  </payload>
  <persistence>true</persistence>
</trigger>
```