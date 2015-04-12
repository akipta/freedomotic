# Modbus Actuator #

---

## User Information ##

---

### What is ###
This sensor allows Freedom to be configured as a Master device in the Modbus network to write values to the slaves of the system.

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

### modbusactuator.xml configuration ###

The actuator usual parameters should be configured. See the Actuator page.
Also, the following parameters have to be defined in the xml.

Generic properties
|**Parameter**|**Required**|**Values**|**Effect**|**Note**|
|:------------|:-----------|:---------|:---------|:-------|
|port | yes | "COM1" or "/dev/ttyUSB0" | Name of the Serial port used |  |
|baudrate | yes |  |Serial Port Parameter |  |
|data-bits |yes |  |Serial Port Parameter |  |
|parity | yes |0|1|2  |Serial Port Parameter |  |
|stop-bits |yes |  |Serial Port Parameter |  |
|timeout |yes |Time in milliseconds |  |Modbus Parameter |
|retries |yes |  |Number of retries to obtain a value |Modbus Parameter |


### Messaging ###
| **Operation** | **Channel Type** | **Channel Address** |
|:--------------|:-----------------|:--------------------|
| read | Queue | `app.events.actuators.Modbus.ModbusActuator.in` |
| write | Queue | no definida |


### Command Definition ###

|**Parameter**|**Required**|**Values**|**Effect**|**Note**|
|:------------|:-----------|:---------|:---------|:-------|
|object|yes| Name of the object|Name of the object defined in the ambient file (see the object definition guide)|Keyword reserved and required|

Tuple properties
The command must define a single tuple with the following parameters:
|**Parameter**|**Required**|**Values**|**Effect**|**Note**|
|:------------|:-----------|:---------|:---------|:-------|
|Name | yes | String with the name of this value in the system |  |  |
|SlaveId | yes | 0|..|n |Number of the slave in hich the value is write|  |
|RegisterRange | yes | See table of Register Ranges allowed |  |  |
|DataType |yes | See table of Data Type allowed |  |  |
|Bit | no |0|..|n | Sets the bit to be write in the Binary register | It is only used when the DataType is set to Binary |
|Offset | yes | 0|..|n |Address of the register to be write in the slave |  |
|NumberOfRegisters |no | 0|..|n | Number of consecutive registers to be write|Not used at this moment |
|Multiplier | no |double value |Used with the Additive parameter to transform the value from the Freedom scale system to the Modbus scale system | Mx+A where M= Multiplier x= value readed A= Additive |
|Additive |no |double value |Used with the Multiplier parameter to transform the value from the Freedom scale system to the Modbus scale system |Mx+A where M= Multiplier x= value readed A= Additive |
|value |yes |String| value that is going to be write in the Modbus system with the value| The real value that is write in the slave is the result of applying the transformation Mv+A where v is the value used in the command|


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
The following command writes the Room desired temperature in a Innobus Modbus A/A machine. AS the temperature in freedom is showed in a different scale than the Modbus storage scale, we apply the multiplier.

```
<it.freedom.reactions.Command>
  <name>Set zone1 temperature</name>
  <receiver>app.actuators.Modbus.ModbusActuator.in</receiver>
  <delay>0</delay>
  <timeout>2000</timeout>
  <description>The desired zone1 temperature is set</description>
  <properties>
    <properties>      
      <property name="object" value="Zone1DesiredTemp"/>     
      <tuples>
        <tuple>
          <property name="Name" value="SetTemperatureZone1"/>
          <property name="SlaveId" value="1"/>
          <property name="RegisterRange" value="HOLDING_REGISTER"/>
          <property name="DataType" value="TWO_BYTE_INT_UNSIGNED"/>
          <property name="Offset" value="259"/>
          <property name="NumberOfRegisters" value="1"/>          
          <property name="Multiplier" value="10"/>
          <property name="Additive" value="0"/>
          <property name="value" value="26.5"/>
        </tuple>
       </tuples>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```