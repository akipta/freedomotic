# How to create #

## Java Imports ##
```
import it.freedom.api.events.ObjectReceiveClick;
import it.freedom.api.events.ObjectReceiveClick.Click;
```

## Java creation ##
In Java this event can be instatiated using
```
ObjectReceiveClick event = new ObjectReceiveClick(this, obj, Click.SINGLE_CLICK);
```

Parameters:
  * this: is the class that generates the event
  * obj: is a instance of `EnvObject.java` that represents the clicked object
  * Click.SINGLE\_CLICK: is an enumeration that contains the type of click received SINGLE\_CLICK, DOUBLE\_CLICK, RIGHT\_CLICK

## Published on channel ##

`app.event.sensor.object.behavior.clicked`

# XML Representation #

You can notify this event from a non Java client, like a PHP application. The event is notified in XML format and automatically converted to POJO by Freedom. This is the string that represents the event mentioned above. It's easy to understand the meaning of its parameters.

```
<it.freedom.api.events.ObjectReceiveClick>
  <sender>PHPFreedomSensor</sender>
  <payload>
    <payload>
      <it.freedom.reactions.Statement>
        <logical>AND</logical>
        <attribute>Object</attribute>
        <operand>EQUAL</operand>
        <value>Light 2</value>
      </it.freedom.reactions.Statement>
      <it.freedom.reactions.Statement>
        <logical>AND</logical>
        <attribute>click</attribute>
        <operand>EQUAL</operand>
        <value>SINGLE_CLICK</value>
      </it.freedom.reactions.Statement>
    </payload>
  </payload>
</it.freedom.api.events.ObjectReceiveClick>
```

## How to catch this event with a trigger ##

To know more about Trigger creation go [here](Trigger.md).
This is the XML definition of a trigger to catch the event described above.

```
<it.freedom.reactions.Trigger>
  <name>An Object is right clicked</name>
  <description>When a object is clicked with the right button of the mouse</description>
  <action class="it.freedom.api.events.ObjectReceiveClick"></action>
  <payload>
    <payload>
      <it.freedom.reactions.Statement>
        <logical>AND</logical>
        <attribute>click</attribute>
        <operand>EQUAL</operand>
        <value>RIGHT_CLICK</value>
      </it.freedom.reactions.Statement>
    </payload>
  </payload>
  <delay>0</delay>
  <priority>0</priority>
</it.freedom.reactions.Trigger>
```

After a trigger is catched you can associate it with one or more commands definig a [Reaction](Reaction.md) (eg: if "An Object is right clicked" then "Say the object name with text to speech")