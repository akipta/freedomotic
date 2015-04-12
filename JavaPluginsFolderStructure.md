# Introduction #

Here is the plugin folder hierarchy of Freedomotic. **Sensors** and **Actuators** must be placed in the **_devices_** folder while **Objects** and **Events** in the related **_objects_** and **_events_** folder. In **_data_** folder you can provide **Triggers**, **Commands**, **Reactions** and **Resources** like images to be loaded and automatically imported into Freedomotic at startup. For example you can deliver your Actuator plugin with an example reaction to demonstrate the use of it without force the user to make the first configuration.

In the examples folder of objects you can place xml instances of the object type you have developed.

```
+-[FREEDOMOTIC_ROOT]
+---[data]
+---[config]
+---[log]
+---[plugins]
+-----[devices]
+-------[plugin_name_folder]
+----------plugin_name.jar
+----------plugin_manifest.xml
+----------[data]
+------------[trg]
+------------[cmd]
+------------[rea]
+------------[resources]
+-----[objects]
+-------[device_name_folder]
+----------object_name.jar
+----------[examples]
+----------[data]
+------------[trg]
+------------[cmd]
+------------[rea]
+------------[resources]
+-----[events]
+-------event_name.jar
```