The restAPI plugin enables REST communication of environment data. Please be sure to have it installed in the framework. RestAPI plugin can be downloaded from its marketplace page at http://freedomotic.com/content/plugins/restapi

# Introduction #
```
Retrieves the Enviroment object /xml representation
http://FREEDOMIP:8111/v1/environment/ (GET) 

Retrieves a List of the Freedom Zones (ArrayList<Zone>) /xml representation of the list
http://FREEDOMIP:8111/v1/environment/zones/ (GET) -

Retrieves the zone object /xml representation with index {index} 
http://FREEDOMIP:8111/v1/environment/zones/{index} (GET)

Retrieves the objects list (ArrayList<EnvObject>) /xml representation of the list
http://FREEDOMIP:8111/v1/environment/objects/ (GET)

Retrieves the EnvObject object with name {name}  /xml representation of the object
http://FREEDOMIP:8111/v1/environment/objects/{name} (GET) 
```


### An example: retrieve installed plugins list ###
```
Retrieves the plugins list (ArrayList<EnvObject>) /xml representation of the list
http://FREEDOMIP:8111/v1/environment/plugins/ (GET)
```

You will get an XML reply like this

```
<list>
 <it.freedom.restapi.server.resources.PluginPojo>
   <plugin>PioneerKuroActuator</plugin> 
   <running>true</running> 
 </it.freedom.restapi.server.resources.PluginPojo>
 <it.freedom.restapi.server.resources.PluginPojo>
   <plugin>RestApi</plugin> 
   <running>true</running> 
 </it.freedom.restapi.server.resources.PluginPojo>
 <it.freedom.restapi.server.resources.PluginPojo>
   <plugin>OpenWebNet Actuator</plugin> 
   <running>true</running> 
 </it.freedom.restapi.server.resources.PluginPojo>
 <it.freedom.restapi.server.resources.PluginPojo>
   <plugin>OpenWebNet Sensor</plugin> 
   <running>true</running> 
 </it.freedom.restapi.server.resources.PluginPojo>
</list>
```