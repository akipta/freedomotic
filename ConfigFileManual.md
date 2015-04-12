The configuration file is located in _**FREEDOMOTIC\_ROOT/config/config.xml**_ file and defines the properties that manage the behavior and appearance of the program. Below is a list of properties, values ​​and the effect of changing


## Keys and values ​​in the main configuration file ##


| **Key**                           | **Value**               | **Description** |
|:----------------------------------|:------------------------|:----------------|
| **KEY\_ROOM\_XML\_PATH**                | /config/room.xml      | The path to the environment descriptor file |
| **KEY\_XML\_BACKUP\_PATH**              |  /config/lastWorking/ | A backup directory for config files |
| **KEY\_RESOURCES\_PATH**               |  /resources/          | The path to the resources (eg: images) |
| **KEY\_PLUGINS\_URL\_LIST**        |  true/false    |  |
| **KEY\_REPOSITORY\_URL**        |  true/false    |  |
| **KEY\_JAVACOM\_LIB**        |      |  |
| **KEY\_BROKER\_URL** |      |  |
| **KEY\_SHOW\_GUI**  |  true/false    |Start Freedomotic with GUI support |
| **KEY\_LOGGER\_POPUP** |  true/false    |  |
| **KEY\_DISCARD\_INVALID\_DESTINATIONS** |  true/false           | Discards positions outside the environment (usually used with tracking random simulator) |
| **KEY\_LOAD\_PERSISTED\_PEOPLE**        |  false/false          | Experimental, do not change |
| **KEY\_SIMULATE\_TRACKING**        |  true/false    |  |
| **KEY\_SHOW\_WEBSITE\_ON\_STARTUP** |  true/false  |  |
| **KEY\_ANIMATION\_SPEED**              |  20                   | Interpolation speed (higher value means slower animation) |
| **KEY\_MAX\_TRACE\_POINTS** |200 | Do not change |
| **KEY\_PRINT\_GRAPHS** |true/false |  |
| **KEY\_CHECK\_COMMAND\_EXECUTION\_RESULT** |true/false | Check the command execution result  |
| **KEY\_FULL\_SCREEN** |true/false        |  |
| **KEY\_COLORED\_PEOPLE** |true/false | Person marked with different colors |
| **KEY\_RENDER\_TRACES**|true/false| Render the person path |
| **KEY\_GRAPHIC\_LEVEL**|high/medium/low| Quality of the rendering process |


## Configuration file example ##

```
<config>
    <properties>
        <property name="KEY_ROOM_XML_PATH"                      value="/room_blue/room_blue.xml"/>
        <property name="KEY_XML_BACKUP_PATH"                    value="/config/backup/"/>
        <property name="KEY_RESOURCES_PATH"                     value="/data/resources/"/>
        <property name="KEY_ENABLE_PLUGINS_DOWNLOAD"            value="false"/>
        <property name="KEY_PLUGINS_URL_LIST"                   value="http://freedomotic.googlecode.com/files/server-updater.xml"/>
        <property name="KEY_REPOSITORY_URL"                     value="http://freedomotic.googlecode.com/files/"/>
        <property name="KEY_JAVACOM_LIB"                        value="../ext"/>
        <property name="KEY_BROKER_URL"                         value="tcp://localhost:61616"/>
        <property name="KEY_SHOW_GUI"                           value="true"/>
        <property name="KEY_LOGGER_POPUP"                       value="true"/>

        <property name="KEY_DISCARD_INVALID_DESTINATIONS"       value="true"/>
        <property name="KEY_LOAD_PERSISTED_PEOPLE"              value="false"/>
        <property name="KEY_SIMULATE_TRACKING"                  value="true"/>
        <property name="KEY_ANIMATE_LOCALIZATION"               value="true"/>
        <property name="KEY_SHOW_WEBSITE_ON_STARTUP"            value="false"/>
        <property name="KEY_ANIMATION_SPEED"                    value="20"/>
        <property name="KEY_MAX_TRACE_POINTS"                   value="200"/>
        <property name="KEY_PRINT_GRAPHS"                       value="false"/>
        <property name="KEY_CHECK_COMMAND_EXECUTION_RESULT"     value="false"/>

        <property name="KEY_FULL_SCREEN_MODE"                   value="false"/>
        <property name="KEY_COLORED_PEOPLE"                     value="true"/>
        <property name="KEY_RENDER_TRACES"                      value="false"/>
        <property name="KEY_GRAPHIC_LEVEL"                      value="high"/>
    </properties>
</config>
```