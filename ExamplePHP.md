# Getting Started #

In progress

To run this example you must run first ActiveMQ with a Stomp endpoint enabled and Freedom connected to the ActiveMQ external broker.

You can download the last stable release of ActiveMQ from the
[official site](http://activemq.apache.org/activemq-550-release.html).

Then you have to change the _**ACTIVEMQDIR/conf/activemq.xml**_ file with [this](http://code.google.com/p/freedomotic/downloads/detail?name=activemq.xml&can=2&q=) having an active transport connector stomp listening **stomp://0.0.0.0:61666**.

Now you can start Freedomotic.

You can download the complete PHP project [here](http://freedomotic.googlecode.com/files/simplephpclient-v1.0.rar).

To install unrar and copy the Freedomotic folder in the www folder of your [EasyPHP](http://www.easyphp.org) installation. On Linux you can use [XAMPP](http://www.apachefriends.org/it/xampp.html).

If you experience problems write on [Freedomotic forum](http://www.freedomotic.com/forum).


# PHP receives COMMANDS from Freedomotic #

With the code below you enable a PHP application to receive Freedomotic commands as it was an Actuator. You can parse the data embedded in the command and perform the task you want.

## PHP Actuator Code sample ##

```
<?php
// A PHP client for Freedom using STOMP
// by Enrico Nicoletti and Mauro Cicolella
?>

<html>
<head>
<title>PHP receives Freedom COMMANDS</title>
</head>
<body>
<?php 
// modify the include path for stomp
set_include_path(get_include_path() . PATH_SEPARATOR . dirname(__FILE__) . '/Stomp/');
require_once('Stomp/Stomp.php');
 
$conn = new Stomp("tcp://localhost:61666");
echo "<h2>";
echo date("d/m/y : H:i:s", time()); //printing current date and time
echo "</h2>";
echo " Connecting to Freedom....";
$conn->connect();
echo " DONE!\n<br>";
//subscribe to the X10 protocol queue. If you turn on/off an X10 light (like Light 2) a message is delivered in this channel
//to turn on off the Light 2 double click on it and hit OK
$conn->subscribe("/queue/app.actuators.X10.X10Actuator.in");
$msg = $conn->readFrame();

if ($msg !== false)
{
   echo (string)$msg;
   //$conn->ack($msg);
}


$xml = new SimpleXMLElement($msg->body);

//accessing some fields of the message and printing out
echo "<h1>Receiving message: ";
echo $xml->command->name; //getting the property 'name' in the freedom command
echo "</h1>";
echo "<p>";
echo "sent by $xml->sender"; //getting the 'sender' in the freedom ExecutionResult message 
//the ExecutionResult embeds the command for the actuator plugin and is always sent by the CommandDispatcher
echo "</p>";
echo "received on channel ";
echo $xml->command->receiver; //getting the property 'receiver' in the freedom command. It's the address of the channel on which the Execution request is sent
echo "<br>Delay: " . $xml->command->delay;
echo "<br>Timeout: " .$xml->command->timeout;
echo "<br>Description: " . $xml->command->description;
echo "<hr><h3>Received raw message</h3><hr>";
print_r($xml); //the raw received message in a human readable format

echo "<hr><h3>Received message in JSON Encode</h3><hr>";
echo json_encode($msg); //the JSON encoding of the message. It's useful??
// mark the message as received in the queue
$conn->ack($msg);
// disconnect from the broker
$conn->disconnect();
?>
</body>
</html>

```


# PHP sends EVENTS to Freedomotic #

With the code below you enable a PHP application to notify events to Freedomotic as it was a Sensor. The event is serializex in XML and automatically converted to a proper POJO by Freedomotic. The example below sends the freedom event on the messaging bus using PHP as an "object clicked" sensor. It simulates a GUI single click on object Light 2. This event is the same as you click on the freedom software GUI and can be intercepted with a trigger listening on this topic channel `app.event.sensor.object.behavior.clicked`. You can associate che command you want to the trigger, for example "turn ON light 2". The final result is when this PHP page is requested the Light 2 turns ON.
On Freedomotic console you must see a notification of the received message with `PHPFreedomSensor` as the sender.

## PHP Sensor Code sample ##
```
<html>
<head>
<title>PHP sends EVENTS to Freedom</title>
</head>
<body>
<?php 
// modify the include path for stomp
set_include_path(get_include_path() . PATH_SEPARATOR . dirname(__FILE__) . '/Stomp/');
require_once('Stomp/Stomp.php');
require_once("Stomp/Message/Map.php");
$conn = new Stomp("tcp://localhost:61666");
echo "<h2>";
echo date("d/m/y : H:i:s", time()); //printing current date and time
echo "</h2>";
echo " Connecting to Freedom....";
$conn->connect();
echo " DONE!";

//connecting to the broker on stomp endpoint tcp://localhost:61666
$conn->connect();
//defining an header for the message to instruct the broker to transform XML to POJO
$JMSHeader = array();
$JMSHeader['transformation'] = 'jms-object-xml';
//define the XML representation of the POJO
$freedomEventInXML= '
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
 ';
//build the JMS Message with Stomp using the header and the object serialized in XML
$message = new StompMessage($freedomEventInXML, $JMSHeader);
//sends the freedom event on the messaging bus using PHP as an "object clicked" sensor
//it simulates a GUI single click on object Light 2
//this event is the same as you click on the freedom software GUI
//this event can be intercepted with a trigger listening on this topic channel
//you can associate che command you want to the trigger, for example "turn ON light 2"
$conn->send("/topic/VirtualTopic.app.event.sensor.object.behavior.clicked", $message);

echo "<br>";
echo "Sending this XML Event: ";
echo "<br>";
print ($freedomEventInXML);

// disconnect
$conn->disconnect();
?>
</body>
</html>
```

# Create a form to parametrize the event to send #

In the next section we add another PHP file called index.php that is a form interface to parametrize the ObjectReceiveClick event. The form outputs two parameters objectName and clickType. This parameters are used in the example above (with a little modification) to throw a parametrized event. [Here](http://freedomotic.googlecode.com/files/simplephpclient-v2.0.rar) is the complete enhanced code to download.

Here is the code of the new page index.php
```
<html>
<body>
<form action="freedom-send.php" method="post">
<img src="freedom-code-logo.png" ALIGN="bottom">
<h1>Simulate the click on an object in the environment</h1>
<p>Name of the environment object to act on (eg: Light 2): <input type="text" name="objectName" />
<br/>
<p>Click type?
<input type="radio" name="clickType" value="SINGLE_CLICK" checked="checked" /> Single Click
<input type="radio" name="clickType" value="DOUBLE_CLICK" /> Double Click
<input type="radio" name="clickType" value="RIGHT_CLICK" /> Right Click</p>
<p><input type="submit" value="Send it!"></p>
</form>
</body>
</html>
```

The PHP sensor code example is modified to catch the new parameter and put into the XML definition.
This is the updated code
```
<html>
<head>
<title>PHP sends messages to Freedom</title>
</head>
<body>
<img src="freedom-code-logo.png" ALIGN="bottom">
Object name is: <?php echo $_POST['objectName']; ?><br />
Object behavior is: <?php echo $_POST['clickType']; ?><br />
<?php 
// modify the include path for stomp
set_include_path(get_include_path() . PATH_SEPARATOR . dirname(__FILE__) . '/Stomp/');
require_once('Stomp/Stomp.php');
require_once("Stomp/Message/Map.php");
$conn = new Stomp("tcp://localhost:61666");
echo "<h2>";
echo date("d/m/y : H:i:s", time()); //printing current date and time
echo "</h2>";
echo " Connecting to Freedom....";
$conn->connect();
echo " DONE!";

//connecting to the broker on stomp endpoint tcp://localhost:61666
$conn->connect();
//defining an header for the message to instruct the broker to transform XML to POJO
$JMSHeader = array();
$JMSHeader['transformation'] = 'jms-object-xml';
//define the XML representation of the POJO
$freedomEventInXML= '
<it.freedom.api.events.ObjectReceiveClick>
  <sender>PHPFreedomSensor</sender>
  <payload>
    <payload>
      <it.freedom.reactions.Statement>
        <logical>AND</logical>
        <attribute>Object</attribute>
        <operand>EQUAL</operand>
        <value>'.$_POST['objectName'].'</value>
      </it.freedom.reactions.Statement>
      <it.freedom.reactions.Statement>
        <logical>AND</logical>
        <attribute>click</attribute>
        <operand>EQUAL</operand>
        <value>'.$_POST['clickType'].'</value>
      </it.freedom.reactions.Statement>
    </payload>
  </payload>
</it.freedom.api.events.ObjectReceiveClick>
 ';
//build the JMS Message with Stomp using the header and the object serialized in XML
$message = new StompMessage($freedomEventInXML, $JMSHeader);
//sends the freedom event on the messaging bus using PHP as an "object clicked" sensor
//it simulates a GUI single click on object Light 2
//this event is the same as you click on the freedom software GUI
//this event can be intercepted with a trigger listening on this topic channel
//you can associate che command you want to the trigger, for example "turn ON light 2"
$conn->send("/topic/VirtualTopic.app.event.sensor.object.behavior.clicked", $message);

echo "<br>";
echo "Sending this XML Event: ";
echo "<br>";
print ($freedomEventInXML);

// disconnect
$conn->disconnect();
?>
</body>
</html>
```

# Send COMMANDS from PHP to Freedomotic #

Added a form to generate commands for the actuators (freedom-command-form.php)

You can download the updated version (command form + php send commands) [here](http://freedomotic.googlecode.com/files/simplephpclient-v3.0.rar)

```
<html>
<body>

<form action="freedom-send-command.php" method="post">
<img src="freedom-code-logo.png" ALIGN="bottom">
<h1>Sends a command to an Actuator</h1>
<p>Actuator channel (try: app.actuators.usbrelay.simulator.in): <input type="text" name="channel" />
<br/>
<p>Param1 name (try: object): <input type="text" name="param1name" />
<br/>
<p>Param1 value (try: Light 1): <input type="text" name="param1value" />
<br/>
<p>Param2 name (try: behavior): <input type="text" name="param2name" />
<br/>
<p>Param2 value (try: on or off): <input type="text" name="param2value" />
<br/>
<p>Param3 name (try: let it empty): <input type="text" name="param3name" />
<br/>
<p>Param3 value (try: let it empty): <input type="text" name="param3value" />
<br/>
<p>Param4 name (try: let it empty): <input type="text" name="param4name" />
<br/>
<p>Param4 value (try: let it empty): <input type="text" name="param4value" />

<p><input type="submit" value="Send it!"></p>
</form>
</body>
</html>
```

Look at freedom-send-command.php for the stomp code to send a command from PHP to Freedom. Here it's code

```
<html>
<head>
<title>PHP sends messages to Freedom</title>
</head>
<body>
<img src="freedom-code-logo.png" ALIGN="bottom">
<?php 
echo "<br>";
echo $_POST['param1name'];
echo "<br>";
echo $_POST['param1value'];
echo "<br>";
echo $_POST['param2name'];
echo "<br>";
echo $_POST['param2value'];
echo "<br>";
// modify the include path for stomp
set_include_path(get_include_path() . PATH_SEPARATOR . dirname(__FILE__) . '/Stomp/');
require_once('Stomp/Stomp.php');
require_once("Stomp/Message/Map.php");
$conn = new Stomp("tcp://localhost:61666");
echo "<h2>";
echo date("d/m/y : H:i:s", time()); //printing current date and time
echo "</h2>";
echo " Connecting to Freedom....";
$conn->connect();
echo " DONE!";

//connecting to the broker on stomp endpoint tcp://localhost:61666
$conn->connect();
//defining an header for the message to instruct the broker to transform XML to POJO
$JMSHeader = array();
$JMSHeader['transformation'] = 'jms-object-xml';
//define the XML representation of the POJO
$freedomCommandInXML= '
<it.freedom.api.events.system.ExecutionResult>
  <payload>
    <payload>
	</payload>
  </payload>
  <command>
    <properties>
      <properties>
        <property name="'.$_POST['param1name'].'" value="'.$_POST['param1value'].'"/>
        <property name="'.$_POST['param2name'].'" value="'.$_POST['param2value'].'"/>
	<property name="'.$_POST['param3name'].'" value="'.$_POST['param3value'].'"/>
        <property name="'.$_POST['param4name'].'" value="'.$_POST['param4value'].'"/>
      </properties>
    </properties>
    <tuples>
      <tuples/>
    </tuples>
  </command>
</it.freedom.api.events.system.ExecutionResult>
 ';
//build the JMS Message with Stomp using the header and the object serialized in XML
$message = new StompMessage($freedomCommandInXML, $JMSHeader);
$conn->send("/queue/".$_POST['channel'], $message);

echo "<br>";
echo "Sending XML command on channel /queue/".$_POST['channel']." DONE";
echo "<br>";

// disconnect
$conn->disconnect();
?>
</body>
</html>

```