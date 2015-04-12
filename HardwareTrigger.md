# Introduction #

This is the case you want an object to change behavior if something happens in the real environment. For example a light turns on in your livingroom and you want Freedom virtual map to reflect this change visually.
To do this you have to:
  1. Make your SensorPlugin reads the status change from its connected hardware
  1. Notify this change to Freedomotic using a ProtocolRead event which is defined in Freedomotic APIs.
  1. A trigger defined in XML in the /data/trg folder of your plugin. An example here http://code.google.com/p/freedomotic/source/browse/trunk/freedom/plugins/devices/it.cicolella.phwsw/data/trg/int%400.xml
  1. Link this trigger to an object behavior. To do this you can use the Java Frontend plugin for example (right click on an object and change the values in the "Trigger mapping" tab). Doing so you make the object aware that its status is related to the hardware status defined in the trigger which is notified by the event above.

# Sensor plugin side #

You can create a ProtocolRead event this way. Use this import to make the ProtocolRead event available
`import it.freedom.events.ProtocolRead;`

```
private void sendChanges(int relayLine, Board board, String status) {
  //first parameter in the constructor is the reference for the source of the event (typically the sensor plugin class)
  //second parameter SNT084Eth8R8I" is the protocol of the object we want to change
  //third parameter must be the exact address of the object we want to change
  ProtocolRead event = new ProtocolRead(this, "SNT084Eth8R8I", address); 
  //add a property that defines the status readed from hardware
  if (status.equals("0")) {
      event.addProperty("isOn", "false");
  } else {
      event.addProperty("isOn", "true");
  }
  //others additional optional info
  event.addProperty("boardIP", board.getIpAddress());
  event.addProperty("boardPort", new Integer(board.getPort()).toString());
  event.addProperty("relayLine", new Integer(relayLine).toString());
  //publish the event on the messaging bus
  this.notifyEvent(event);
}
```

**Remember that protocol and address must be EXACTLY the same as the onces defined in the target object**. The protocol is a fixed string but the address can be reconstructed for example parsing the data received from the hardware.

Here is an example of a sensor plugin http://code.google.com/p/freedomotic/source/browse/trunk/ETHProgettiHwSw/src/it/freedom/cicolella/ETHProgettiHwSwSensor.java

# Hardware Trigger side #

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
    <it.freedom.reactions.Statement>
      <logical>SET</logical>
      <attribute>behaviorValue</attribute>
      <operand>EQUALS</operand>
      <value>@event.isOn</value>
    </it.freedom.reactions.Statement>
  </payload>
</payload>
[...]
```

Every trigger, due to the object mapping knows the object address and the behavior it is linked to, so there is no need to specify this parameters in the trigger because they are checked automatically.