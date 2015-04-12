# Overview #

With this plugin Freedomotic can communicate with this ethernet relay board by [Progetti HW-SW](http://www.progetti-hw-sw.it/relays_ethernet_board.htm).

This board has from 8 up to 16 relays, an ethernet port and an integrated web server. Its relays can be driven simply by a web interface or simulating the presence of a web browser sending specific http requests.
In particulary two type of commands are supported:

  * a classic on/off
  * an impulse of 1 or 2 seconds (useful for simulating a click to open a door).



# Board Protocol #

The default board ip address is **192.168.1.201**. To interact with relays you must open a browser at these links:

  * http://192.168.1.201/forms.htm?led0=1 - switch relay 1 on
  * http://192.168.1.201/forms.htm?led0=0 - switch the same relay off

Changing led number (from 0 to 15 in hexadecimal format) it's possible to switch on/off each of them.

Switching on for 1 or 2 seconds needs a different http request:

  * http://192.168.1.201/toggle.cgi?toggle=A - switch on relay 1 for a second and then switch it off. In this case relays are identified by uppercase letters (from A to H corresponding to 1 to 8).

  * http://192.168.1.201/toggle.cgi?toggle=I  - switch on relay 1 for two seconds and then switch it off.

For 2 seconds toggle relays are identified by uppercase letters (from I to R corresponding to 1 to 8).


Starting from these requirements we are describing how to create a plugin for Freedomotic.

First of all, we need to specify a physical address (ip address) to communicate with then board and select one of the eight relays. For having a command independent from this address we assign it directly to the Freedom object. In this way if the address changes it's no need to edit every command.

Physical address consists of three components: _server ip_, _server port_ number (of board) and _relay number_.
We must combine them in a single string using a field delimiter like ":". This delimiter can be specified between command properties. An example is 192.168.1.201:80:1 (relay 1 on board with web server listening on 192.168.1.201:80).

Three different commands can be send to the board:
  * **REPLY** for simple on/off switching to combine with object behavior (on/off) for determining which action to execute (REPLY+on or REPLY+off)
  * **TOGGLE1S** for switching on for 1 second
  * **TOGGLE2S** for switching on for 2 seconds

Another important property is expected-reply to compare with received reply from device in order to establish is command has been executed or not. At this moment we can use the classic http response: **HTTP/1.1 200 OK**.

# Configuration file #

All configuration parameters are included into **progettihwsw.xml**. The plugin supports multiple boards each of them must be configured into a separated 

&lt;tuple&gt;



&lt;/tuple&gt;

 section.
```
<config>
    <properties>
        <property name="description"    value="Communicates with the ethernet board SNT084Eth8R8I developed by www.progetti-hw-sw.it"/>
        <property name="version"        value="40"/>
        <property name="required"       value="40"/>
        <property name="category"       value="protocol"/>
        <property name="short-name"     value="SNT084Eth8R8I"/>
        <property name="address-delimiter" value=":"/>
        <property name="startup-time"   value="on load"/>
	<property name="TOGGLE1S1"  value="A"/>
        <property name="TOGGLE1S2"  value="B"/>
        <property name="TOGGLE1S3"  value="C"/>
        <property name="TOGGLE1S4"  value="D"/>
        <property name="TOGGLE1S5"  value="E"/>
        <property name="TOGGLE1S6"  value="F"/>
        <property name="TOGGLE1S7"  value="G"/>
        <property name="TOGGLE1S8"  value="H"/>
	<property name="TOGGLE1S9"  value="W"/>
        <property name="TOGGLE1S10" value="X"/>
        <property name="TOGGLE1S11" value="Y"/>
        <property name="TOGGLE1S12" value="Z"/>
        <property name="TOGGLE1S13" value="1"/>
        <property name="TOGGLE1S14" value="2"/>
        <property name="TOGGLE1S15" value="3"/>
        <property name="TOGGLE1S16" value="4"/>
        <property name="TOGGLE2S1"  value="I"/>
        <property name="TOGGLE2S2"  value="L"/>
        <property name="TOGGLE2S3"  value="M"/>
        <property name="TOGGLE2S4"  value="N"/>
        <property name="TOGGLE2S5"  value="O"/>
        <property name="TOGGLE2S6"  value="P"/>
        <property name="TOGGLE2S7"  value="Q"/>
        <property name="TOGGLE2S8"  value="R"/>
	<property name="TOGGLE2S9"  value="S"/>
        <property name="TOGGLE2S10" value="T"/>
        <property name="TOGGLE2S11" value="U"/>
        <property name="TOGGLE2S12" value="V"/>
        <property name="TOGGLE2S13" value="5"/>
        <property name="TOGGLE2S14" value="6"/>
        <property name="TOGGLE2S15" value="7"/>
        <property name="TOGGLE2S16" value="8"/>
        <property name="polling-time"   value="200"/>
    </properties>
    <tuples>
        <tuple>
            <property name="ip-to-query" value="192.168.1.201"/>
            <property name="port-to-query" value="8085"/>
            <property name="relay-number" value="12"/>
            <property name="analog-input-number" value="4"/>
            <property name="digital-input-number" value="4"/>
            <property name="starting-relay" value="0"/>
            <property name="line-to-monitorize" value="btn"/>
        </tuple>
	<tuple>
            <property name="ip-to-query" value="192.168.1.202"/>
            <property name="port-to-query" value="8085"/>
            <property name="relay-number" value="12"/>
            <property name="analog-input-number" value="4"/>
            <property name="digital-input-number" value="4"/>
            <property name="starting-relay" value="0"/>
            <property name="line-to-monitorize" value="btn"/>
        </tuple>
    </tuples>
</config>
```


# Command Example v.1.0 #

```
<it.freedom.reactions.Command>
   <name>Turn OFF Relay on Progetti HWSR board</name> 
   <receiver>app.actuators.protocol.SNT084Eth8R8I.in</receiver>  
   <description>turns off the relay linked to this object on SNT084Eth8R8I board</description>  
   <hardwareLevel>true</hardwareLevel>    
   <delay>0</delay>  
   <timeout>0</timeout>   
   <properties>
      <property name="command" value="RELAY"/> 
      <property name="behavior" value="off"/>        
      <property name="address" value="@event.object.address"/> 
   </properties>    
</it.freedom.reactions.Command> 
```

# Reaction Example v1.0 #

```
<it.freedom.reactions.Reaction>
  <trigger>Someone Enters in Kitchen</trigger>
  <reaction>
    <it.freedom.reactions.CommandSequence>
      <command>
        <string>ETH ProgettiHw-Sw Board Switch on</string>
      </command>
    </it.freedom.reactions.CommandSequence>
  </reaction>
</it.freedom.reactions.Reaction>
```


# Board Autodiagnostic #

This feature is embedded into a sensor plugin that reads periodically the status.xml file to retrieve the status of input/output board lines.

# Source Code #

In the last version Actuator and Sensor are included into a single Protocol plugin
[Plugin source code](http://code.google.com/p/freedomotic/source/browse/trunk/ETHProgettiHwSw/src/it/freedom/cicolella/ETHProgettiHwSw.java?spec=svn2297&r=2297)


# Final Notes #

This example can be used as model for creating your own plugins based on http communication.

Suggestions, improvements are welcome.
If you want to contribute please refer to [this topic](http://www.freedomotic.com/forum/6/178) on community forum.

# Changelog #
Implemented in version 1.1

  * Added relays state autodiagnostic (based on status.xml)