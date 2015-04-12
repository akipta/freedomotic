# Introduction #

Every Java plugin needs a XML manifest file to describe the plugin to Freedomotic.

# Plugins build script #

The framework provides bulding scripts that install automatically your plugin in the framework after compile, so you can test it withoud the need to do manual copy&paste of plugins files.

  1. Open the **build.xml** file in your plugin project folder
  1. Edit the **post-jar** target as this
```
    <target name="-post-jar">
        <!-- calls an external build file located in SVN_ROOT/buildscript-files folder -->
        <ant antfile = "${basedir}../../../../scripts/plugin-build.xml" inheritAll="true"/> 
    </target>
```

  1. BACKUP your plugin project folder and the **freedom/plugins** folder
  1. On Netbeans right click -> build on your plugin project name. You should now see a **build.options** file, two new folders **framework-dist** and **market-dist** and the copy of framework-dist content in **freedom/plugins/PLUGIN\_TYPE**. In **framework-dist** you will find also an empty manifest example.
  1. Edit **build.options** with your personal information and rebuild.

NOTE: the data folder is sync only from plugin folder to framework folder. If you change command/trigger in framework folder they must be merged back manually in plugin folder. So we suggest to edit xml files in data in plugin project folder and rebuild to have in sync on the framework.

# Basic Info #

Remember to name this file as the string passed in the plugin contructor (in this case "first-sensor-manifest.xml"). The path is case sensitive so `"/net.freedom.firstsensor/first-sensor-manifest.xml"` is not the same as `"/net.freedom.FirstSensor/First-Sensor-Manifest.xml"`.

```
super("Hello World Sensor" ,"/net.freedom.firstsensor/first-sensor-manifest.xml");
```
This is the body of the manifest file
```
<config>
    <properties>
        <property name="description"   value="A basic plugin that prints 'Hello World' on standard output"/>
        <property name="version"       value="4.0"/>
        <property name="required"      value="4.0"/>
        <property name="vendor"        value="yourNameOrCompanyNameHere"/>
        <property name="category"      value="system"/>
        <property name="short-name"    value="logger"/>
    </properties>
</config>
```

Every plugin has an unique [Channel](Channel.md) used for messages exchange.
For sensors the channel have this sintax:
```
app.sensor.CATEGORY.SHORT-NAME.in
```
where CATEGORY and SHORT-NAME are parameter values of category and short-name properties in configuration xml file. In this case it will be
```
app.sensor.system.logger.in
```

For CATEGORY you can use any string you want, but we suggest to choose one of the items included in this list. ADD LIST LINK

For actuators the Channel name is:
```
app.actuators.CATEGORY.SHORT-NAME.in
```

So the prefix (app.actuators / app.sensor) and suffix ".in" are created automatically by system at plugin loading.

Place the file along with the plugin JAR.

# Optional properties #

The following are optional properties used to manage special use cases. They are not needed in standard plugins development.

| Property | Values | Description |
|:---------|:-------|:------------|
| threaded-commands-execution | true / false | An Actuator plugin can choose to execute every received command in a separate thread or to execute them in sequence reading from a queue. Default value is true.|
| automatic-reply-to-commands | true / false | An Actuator plugin can choose if the system has to send automatically a command execution reply or is the developer who call it using `command.reply()` method. This is useful for example if you have to wait results from threaded executors.|

# Adding tuples #

This are useful to have a properties set.

You can add as many `<tuple></tuple>` blocks as you need. The `<tuples></tuples>` block may be added after `<properties></properties>` on the same hierarchical level. Tuples are useful to have configuration data specific for you plugin to be loaded in Freedom at startup. Related configuration data can be stored in blocks called tuple.

```
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
          <property name="Name" value="TemperatureZone2"/>
          <property name="SlaveId" value="1"/>
          <property name="RegisterRange" value="HOLDING_REGISTER"/>
          <property name="DataType" value="TWO_BYTE_INT_UNSIGNED"/>
          <property name="Offset" value="522"/>
          <property name="NumberOfRegisters" value="1"/>          
          <property name="Multiplier" value="0.1d"/>
          <property name="Additive" value="0.0d"/>
          <property name="EventName" value="TemperatureZone2"/>
       </tuple>
  </tuples>
```

This data are automatically available to you plugin. You can use free custom strings for the attribute name and the value. To read tuples variables within a plugin read the [Access Freedomotic Data page](Data.md)