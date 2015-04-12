# Introduction #

Data persistence in Freedom is based on xml serialization/deseralization.

### At startup ###
When Freedom starts, it reads the environment path from _**FREEDOM/config/config.xml**_ , e.g.
```
<property name="KEY_ROOM_XML_PATH" value="/room_blue/room_blue.xml"/>
```
and then it deserializes xml file, adding all objects found in the same folder.

### At closing ###
When Freedom stops all objects are serialized in xml files into the folder _**FREEDOM/data/furn/ENV\_FOLDER/**_