Environment is described to the system with an xml file. To create a new environment go to **FREEDOMOTIC\_ROOT/data/furn/** and create a new folder named for example "myhome".

Inside this folder create a new xml file called "myhome.xml"

An environment file is composed of a descriptive section and a list of zones inside it. A zone can be a logical division of the space like "the zone near the exit door" or a room like "the kitchen".

Here is an example of a full environment file http://code.google.com/p/freedomotic/source/browse/trunk/freedom/data/furn/room_blue/room_blue.xml

To make this enviroment the one loaded at startup you have to change the property **KEY\_ROOM\_XML\_PATH** in the file **FREEDOMOTIC\_ROOT/config/config.xml** to your environment file, for example **"/myhome/myhome.xml"**

# Environment Description #

Here is the frame of an environment with some rooms.
```
<it.freedom.model.environment.Environment>
    <name>My Home</name>
    <width>1110</width>
    <height>710</height>
    <objectsFolder>room_blue</objectsFolder>
    <renderer>image</renderer>
    <backgroundColor>
        <red>60</red>
        <green>59</green>
        <blue>55</blue>
        <alpha>255</alpha>
    </backgroundColor>
    <zones>
        <it.freedom.model.environment.Zone>
            <name>Indoor</name>
            <description></description>
            <shape  class="polygon">
                <point x="0" y="0"/>
                <point x="1100" y="0"/>
                <point x="1100" y="675"/>
                <point x="0" y="675"/>
         </shape>
        </it.freedom.model.environment.Zone>
        <it.freedom.model.environment.Zone>
            <name>Office</name>
            <description></description>
            <room>true</room>
            <shape  class="polygon">
                <point x="0" y="0"/>
                <point x="325" y="0"/>
                <point x="325" y="310"/>
                <point x="0" y="310"/>
            </shape>
            <texture>piastrelle.jpg</texture>
        </it.freedom.model.environment.Zone>
        <it.freedom.model.environment.Zone>
            <name>Bedroom</name>
            <room>true</room>
            <description></description>
            <shape  class="polygon">
                <point x="0" y="310"/>
                <point x="480" y="310"/>
                <point x="480" y="675"/>
                <point x="0" y="675"/>
            </shape>
            <texture>piastrelle.jpg</texture>
        </it.freedom.model.environment.Zone>
        <it.freedom.model.environment.Zone>
            <name>Toilet</name>
            <room>true</room>
            <description></description>
            <shape  class="polygon">
                <point x="325" y="0"/>
                <point x="480" y="0"/>
                <point x="480" y="180"/>
                <point x="325" y="180"/>
            </shape>
         <texture>piastrelle.jpg</texture>
        </it.freedom.model.environment.Zone>
        <it.freedom.model.environment.Zone>
            <name>Entrance</name>
            <room>true</room>
            <description></description>
            <shape  class="polygon">
                <point x="325" y="180"/>
                <point x="480" y="180"/>
                <point x="480" y="310"/>
                <point x="325" y="310"/>
            </shape>
           <texture>piastrelle.jpg</texture>
        </it.freedom.model.environment.Zone>
        <it.freedom.model.environment.Zone>
            <name>Balcony</name>
            <room>true</room>
            <description></description>
            <shape  class="polygon">
                <point x="480" y="0"/>
                <point x="1100" y="0"/>
                <point x="1100" y="145"/>
                <point x="480" y="145"/>
           </shape>
          <texture>piastrelle.jpg</texture>
        </it.freedom.model.environment.Zone>
        <it.freedom.model.environment.Zone>
            <name>Livingroom</name>
            <room>true</room>
            <description></description>
            <shape  class="polygon">
                <point x="480" y="145"/>
                <point x="1100" y="145"/>
                <point x="1100" y="675"/>
                <point x="480" y="675"/>
            </shape>
            <texture>piastrelle.jpg</texture>
        </it.freedom.model.environment.Zone>
        <it.freedom.model.environment.Zone>
            <name>Wall</name>
            <room>true</room>
            <description></description>
            <shape  class="polygon">
                <point x="1095" y="519"/>
                <point x="929" y="519"/>
             </shape>
            <texture>piastrelle.jpg</texture>
        </it.freedom.model.environment.Zone>
    </zones>
</it.freedom.model.environment.Environment>
```

In the description section you can define the name of the environment, its dimension (width and height), the folder in which it is placed, the background color.

```
    <name>my Home</name>
    <width>1110</width>
    <height>710</height>
    <objectsFolder>room_blue</objectsFolder>
    <renderer>image</renderer>
    <backgroundColor>
        <red>60</red>
        <green>59</green>
        <blue>55</blue>
        <alpha>255</alpha>
    </backgroundColor>
```


# Zones Description #

TODO

## Differences between Rooms and Zones ##

TODO

## Add Doors and Windows on Rooms Walls ##

TODO