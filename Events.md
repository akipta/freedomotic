Events are published by sensor plugins on [channels](Channel.md). A series of useful events is predefined in Freedomotic. However one of the main strengths of this framework is that the behavior of the system is not predefined. Every action in the real environment and every interaction with the system (eg: a click on the GUI) is mapped to an event. Events can be intercepted by triggers changing at runtime the behavior of the system.

Usually events are notified by sensor plugins which listen to hardware devices like (eg: temperature sensor) and transform the readed data in hardware agnostic events. In Freedomotic this is done by two simple lines of code:

```
GenericEvent event = new GenericEvent(this);
event.addProperty("myParameterName", 42); //42 is the value of propery myParameterName
notifyEvent(event); //sends the event on the messaging bus
```

<a href='Hidden comment: 
= Predefined Events =
[EventsList Here] is a list of the predefined Freedom events.
[http://www.freedomotic.com/forum/6/24?page=1#338 Here you can discuss about new events proposal]
'></a>

# How to change the state of objects on the map #

This is the case you want an object to change behavior if something happens in the real environment. For example a light turns on in your livingroom and you want Freedomotic frontend to reflect this change visually displaying a bulb on icon.
To do this you have to:
  1. Make your plugin read a state change from hardware or other webservice
  1. Notify this change to Freedomotic using a ProtocolRead event
  1. Define a Trigger in XML in the /data/trg folder of your plugin to filter the event conditions (eg: if temperature < 20). An example [here](http://code.google.com/p/freedomotic/source/browse/trunk/freedom/plugins/devices/it.cicolella.phwsw/data/trg/int%400.xml)
  1. Link this trigger to an object. To do this you can use the Java Frontend plugin for example (right click on an object and change the values in the "Trigger mapping" tab). Doing so you make the object aware that its status is related to the hardware status defined in the trigger which is notified by the event above.

## An example ##

You can create a ProtocolRead event this way. Use this import to make the ProtocolRead event available
```
import it.freedomotic.events.ProtocolRead;
```

```
ProtocolRead event = new ProtocolRead(this, "X10", "A01");
event.addProperty("x10.function", "ON");
event.addProperty("object.class", "Light");
event.addProperty("object.name", "My X10 Light");
Freedomotic.sendEvent(event);
```

**Remember that protocol and address must be EXACTLY (read match the case) the same as the onces defined in the target object**. The protocol is a string you can freely define to identify your plugin. This will be used in objects to bound it to your plugins, so you are saying "this object is driven by this plugin".

[Here](http://code.google.com/p/freedomotic/source/browse/trunk/ETHProgettiHwSw/src/it/freedom/cicolella/ETHProgettiHwSwSensor.java) is an example of a sensor plugin

### Hardware Trigger side ###

Hardware triggers are persisted in data/trg folder of a plugin.
The object you want to change must have its behavior linked to a proper hardware trigger (eg: light behavior powered linked to "An OpenWebNet light turns on".

Hardware Triggers listen to ProtocolReads events that are notified by sensor plugins of a protocol.

The process is composed of this steps
  1. The event is notified
  1. Hardware trigger receives and check its payload for consistence with the event (protocol and address are automatically checked no need to write it in the trigger)
  1. The Hardware trigger must have a property called "behaviorValue" which is the value to set in the object if the trigger is consistent with the received ProtocolRead event.
  1. The Hardware trigger must have a property called "protocol" equals to the protocol name used in the object..

This is an example on how to set this property. **Remember to use `<logical>SET</logical>` otherwise this property is used for comparison with the received event.**

```
[...]
<payload>
  <payload>
    <it.freedomotic.reactions.Statement>
      <logical>AND</logical>
      <attribute>protocol</attribute>
      <operand>EQUALS</operand>
      <value>myArduinoRelayBoard</value>
    </it.freedom.reactions.Statement>
    <it.freedom.reactions.Statement>
      <logical>SET</logical>
      <attribute>behaviorValue</attribute>
      <operand>EQUALS</operand>
      <value>@relay.powered</value>
    </it.freedomotic.reactions.Statement>
  </payload>
</payload>
[...]
```

Every trigger, due to the object mapping knows the object address and the behavior it is linked to, so there is no need to specify this parameters in the trigger because they are checked automatically.

# How to create objects programmatically #

This feature is called **Join Device**. You can send a state change notification for an object, if this object doesen't exists (check is made on protocol+address values in the event) the object is created and the changes in the ProtocolRead event are applyed to it.

From your plugin you can send a ProtocolRead event (both as java pojo or xml) adding this two properties:

| **Property** | **Example Value** | **Description** |
|:-------------|:------------------|:----------------|
| object.class | Light | The type of the object that will be created. It must be a string containing an object type as you see in the objects list menu of java frontend (when you press F6) |
| object.name | My Light | The name of the new object. If already exist a numeric ID will be added at the end like My Light 1 |

**omitting _object.class_ and _object.name_ propeties makes the ProtocolRead event to be discarded if no object with the notifies protocol+address exists.**

## Examples ##

**All the following examples are related to an X10 plugin and the notification of a change from OFF to ON of the x10 device with address A01. Remember to change the values according to your neeeds.**

#### Java Example ####
```
ProtocolRead event = new ProtocolRead(this, "X10", "A01");
event.addProperty("x10.function", "ON");
event.addProperty("object.class", "Light");
event.addProperty("object.name", "My X10 Light");
Freedomotic.sendEvent(event);
```

#### XML Example ####
[Send this XML text](ExternalClientsMessaging.md) to the [Channel](Channel.md) `app.event.sensor.protocol.read.x10`

```
<it.freedomotic.events.ProtocolRead>
  <eventName>ProtocolRead</eventName>
  <payload>
    <payload>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>protocol</attribute>
        <operand>EQUALS</operand>
        <value>X10</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>address</attribute>
        <operand>EQUALS</operand>
        <value>A01</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>object.class</attribute>
        <operand>EQUALS</operand>
        <value>Light</value>
      </it.freedomotic.reactions.Statement>
      <it.freedomotic.reactions.Statement>
        <logical>AND</logical>
        <attribute>object.name</attribute>
        <operand>EQUALS</operand>
        <value>My X10 Light</value>
      </it.freedomotic.reactions.Statement>
    </payload>
  </payload>
</it.freedomotic.events.ProtocolRead>
```