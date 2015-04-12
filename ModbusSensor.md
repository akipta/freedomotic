# Sensor Modbus #

---

## Information for the users ##

---

### What is ###
This sensor allows Freedom to be configured as a Master device in the Modbus network to read values from the slaves of the system.

The read values are then published as events in the system.

At this moment only the Serial RTU configuration is implemented. The rest of systems will be implemented in the future.

### Modbus Protocols supported ###

|Name| Supported |
|:---|:----------|
|Modbus RTU| Yes|
|Modbus ASCII| NO |
|Modbus TCP/IP| NO |



---

## Information for the developers ##

---

### modbussensor.xml configuration ###
The sensor usual parameters should be configured. See the Sensor page.
Also, the following parameters have to be defined in the xml.

Generic properties
| Parameter | Required | Values | Effect | Note |
|:----------|:---------|:-------|:-------|:-----|
|port | yes | "COM1" or "/dev/ttyUSB0" | Name of the Serial port used |  |
|baudrate | yes |  |Serial Port Parameter |  |
|data-bits |yes |  |Serial Port Parameter |  |
|parity | yes |0|1|2  |Serial Port Parameter |  |
|stop-bits |yes |  |Serial Port Parameter |  |
|timeout |yes |Time in milliseconds |  |Modbus Parameter |
|retries |yes |  |Number of retries to obtain a value |Modbus Parameter |
|NumRegisters |yes |0|..|n |Number of registers that are going to be configured | This value should match the total of tuples defined |

For every register that is going to be read from the network a tuple should be configured with the correct parameters to locate and transform from the modbus system to Freedom system as one Event. For every tuple, the sensor sends an event on the Freedom system.

Tuples properties
| Parameter | Required | Values | Effect | Note |
|:----------|:---------|:-------|:-------|:-----|
|Name | yes | String with the name of this value in the system |  | Must be unique in the sensor  |
|SlaveId | yes | 0|..|n |Number of the slave from which the value is read |  |
|RegisterRange | yes | See table of Register Ranges allowed |  |  |
|DataType |yes | See table of Data Type allowed |  |  |
|Bit | no |0|..|n | Sets the bit to be read from the Binary register | It is only used when the DataType is set to Binary |
|Offset | yes | 0|..|n |Address of the register to be read in the slave |  |
|NumberOfRegisters |no | 0|..|n | Number of consecutive registers to be read |Not used at this moment |
|Multiplier | no |double value |Used with the Additive parameter to transform the value from the modbus scale system to the Freedom scale system | Mx+A where M= Multiplier x= value readed A= Additive |
|Additive |no |double value |Used with the Additive parameter to transform the value from the modbus scale system to the Freedom scale system |Mx+A where M= Multiplier x= value readed A= Additive |
|EventName |yes |String |Name of the parameter in the event that is going to be sent with the value| This value configures a GenericEvent with a parameter with name="EventName" value="Mx+A"|

Register Ranges:

|Name | Note |
|:----|:-----|
|COIL\_STATUS|  |
|INPUT\_STATUS|  |
|HOLDING\_REGISTER|  |
|INPUT\_REGISTER|  |


Data Types

|Name | Note |
|:----|:-----|
|BINARY |  |
|TWO\_BYTE\_INT\_UNSIGNED |  |
|TWO\_BYTE\_INT\_SIGNED |  |
|FOUR\_BYTE\_INT\_UNSIGNED |  |
|FOUR\_BYTE\_INT\_SIGNED |  |
|FOUR\_BYTE\_INT\_UNSIGNED\_SWAPPED |  |
|FOUR\_BYTE\_INT\_SIGNED\_SWAPPED |  |
|FOUR\_BYTE\_FLOAT |  |
|FOUR\_BYTE\_FLOAT\_SWAPPED |  |
|EIGHT\_BYTE\_INT\_UNSIGNED |  |
|EIGHT\_BYTE\_INT\_SIGNED |  |
|EIGHT\_BYTE\_INT\_UNSIGNED\_SWAPPED |  |
|EIGHT\_BYTE\_INT\_SIGNED\_SWAPPED |  |
|EIGHT\_BYTE\_FLOAT |  |
|EIGHT\_BYTE\_FLOAT\_SWAPPED |  |
|TWO\_BYTE\_BCD |  |
|FOUR\_BYTE\_BCD |  |



### Example of XML ###
This Xml configures 2 registers.

The first register reads a temperature value from the slave and, as the Modbus in this example stores the value multiplied by 10, the sensor transforms that value multiplying it by 0.1

The second register reads the bit value from position 6 from the register.

```
<<config>
    <properties>
        <property name="description"    value="Sensor for Modbus"/>
        <property name="version"        value="40"/>
        <property name="required"       value="40"/>
        <property name="category"       value="Modbus"/>
        <property name="short-name"     value="ModbusSensor"/>
        <property name="PollingTime"     value="1000"/>
        <property name="NumRegisters"     value="2"/>
        <property name="port"       value="/dev/ttyUSB10"/>
        <property name="baudrate"      value="19200"/>
        <property name="data-bits"     value="8"/>
        <property name="parity"     value="2"/>
        <property name="stop-bits"     value="1"/>
        <property name="timeout"     value="10000"/>
        <property name="retries"     value="1"/>
    </properties>
    <tuples>
        <tuple>
          <property name="Name" value="TemperatureZone1"/>
          <property name="SlaveId" value="1"/>
          <property name="RegisterRange" value="HOLDING_REGISTER"/>
          <property name="DataType" value="TWO_BYTE_INT_UNSIGNED"/>
          <property name="Offset" value="266"/>
          <property name="NumberOfRegisters" value="1"/>
          <property name="Multiplier" value="0.1d"/>
          <property name="Additive" value="0.0d"/>
          <property name="EventName" value="TemperatureZone1"/>
       </tuple>
       <tuple>
          <property name="Name" value="BitTest"/>
          <property name="SlaveId" value="1"/>
          <property name="RegisterRange" value="HOLDING_REGISTER"/>
          <property name="DataType" value="BINARY"/>
          <property name="Bit" value="6"/>
          <property name="Multiplier" value="1"/>
          <property name="Additive" value="0"/>
          <property name="Offset" value="256"/>
          <property name="NumberOfRegisters" value="1"/>
          <property name="EventName" value="BitTest"/>
       </tuple> 
    </tuples>
```