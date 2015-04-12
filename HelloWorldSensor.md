## Develop the first Freedomotic Plugin ##

Here you will learn how to create your first Freedomotic "Hello world" plugin. This plugin will simply print a "Hello World" message when is started and stopped. It is pretty simple but the structure is the same also for more complicated plugins.

Every freedomotic plugin can notify events and listen to commands so you can for example notify to freedomotic the data you have listen from a web service like google weather (with an event) or turn on a led on your Arduino board (listening for a command).

In this tutorial we consider you are using NetBeans IDE but is the same for others IDE.

### Step by step tutorial ###

  1. Download Netbeans IDE from http://netbeans.org/. You can use your favourite IDE but this is the reference for this project, so all instructions are related to this IDE.
  1. Download the latest freedomotic SDK release from http://freedomotic.googlecode.com/files/freedomotic-sdk-v5.3.0.zip
  1. Unzip the downloaded archieve
  1. Open Netbeans. Use menu **File->Open Project** to open the _hello\_world_ project you'll find in UNZIPPED\_FOLDER/hello-world.
  1. **Build the hello-world project**. To build a project in Netbeans right click on project name -> Build. The plugin will be copied automatically at every build into UNZIPPED\_FOLDER/freedomotic/plugins/devices
  1. **Next step is experimenting with hello-world code and rebuild the project after every change to have them installed automatically into freeedomotic.** Note: the hello-world plugin just prints some log messages you can see with a right click on "Log Viewer" plugin.
  1. After you have built the hello-world project, enter in UNZIPPED\_FOLDER/freedomotic. Now you can run _freedomotic.exe_ (on windows) or _start\_freedom.sh_ (on linux) with your changes to hello-world plugin.

Notes:
  * to build a project in Netbeans right click on project name -> Build. The plugin will be copied automatically at every build into UNZIPPED\_FOLDER/freedomotic/plugins/devices


<a href='Hidden comment: 
=== STEP 0: Download source code ===

You can follow the [GetFromGIT Get from GIT] tutorial to download the entire source tree in a minute. To be brief you will end up doing:
```
git clone https://code.google.com/p/freedomotic/ 
```

=== STEP 1: Create new IDE Project ===

* Open NetBeans IDE, and then open the projects "*freedomotic*" and "*freedomotic-model*" you have just downloaded in the previous step.

* Create a new NetBeans project from menu File-> New Project, select Java Class Library and name it "hello-sensor". *Be sure to change the project location to be inside the freedomotic folder you have just cloned from GIT repository*. In this case you plugin should be in _PATH_TO_FREEDOMOTIC_GIT_FOLDER/plugins/devices/hello-world_.
* In the default package of your new NetBeans project, right click and select *New* -> *Java Class*.

http://freedomotic.com/images/wiki/create-sensor-class.png

Name it "HelloWorld" and click *Finish*.

=== STEP 2: Write the code ===

Copy and paste the body of the HelloWorld class you find here

```
import it.freedomotic.api.EventTemplate;
import it.freedomotic.api.Protocol;
import it.freedomotic.app.Freedomotic;
import it.freedomotic.exceptions.UnableToExecuteException;
import it.freedomotic.reactions.Command;
import java.io.IOException;

public class HelloWorld extends Protocol {
    final int POLLING_WAIT;

    public HelloWorld() {
        //every plugin needs a name and a manifest XML file
        super("HelloWorld", "/it.freedomotic.hello/hello-world.xml");
        //read a property from the manifest file below which is in
        //FREEDOMOTIC_FOLDER/plugins/devices/it.freedomotic.hello/hello-world.xml
        POLLING_WAIT = configuration.getIntProperty("time-between-reads", 2000);
        //POLLING_WAIT is the value of the property "time-between-reads" or 2000 millisecs,
        //default value if the property does not exist in the manifest
        setPollingWait(POLLING_WAIT); //millisecs interval between hardware device status reads
    }

    @Override
    protected void onShowGui() {
        /**
         * uncomment the line below to add a GUI to this plugin
         * the GUI can be started with a right-click on plugin list
         * on the desktop frontend (it.freedomotic.jfrontend plugin)
         */
        
        //bindGuiToPlugin(new HelloWorldGui(this));
    }

    @Override
    protected void onHideGui() {
        //implement here what to do when the this plugin GUI is closed
        //for example you can change the plugin description
        setDescription("My GUI is now hidden");
    }

    @Override
    protected void onRun() {
        Freedomotic.logger.info("HelloWorld onRun() logs this message every "
                + "POLLINGWAIT=" + POLLING_WAIT + "milliseconds");
        //at the end of this method the system waits POLLINGTIME 
        //before calling it again. The result is this log message is printed
        //every 2 seconds (2000 millisecs)
    }

    @Override
    protected void onCommand(Command c) throws IOException, UnableToExecuteException {
        Freedomotic.logger.info("HelloWorld plugin receives a command called " + c.getName()
                + " with parameters " + c.getProperties().toString());
    }

    @Override
    protected boolean canExecute(Command c) {
        //don"t mind this method for now
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void onEvent(EventTemplate event) {
        //don"t mind this method for now
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

```

You will get import errors. To fix this you have to import *freedomotic.jar* and *freedomotic-model.jar*. To do this right click on freedomotic project and hit compile, the same on freedomotic-model to create the two jar files.

Now on your plugin project name, right click, choose "properties" and "Add Project" selecting freedomotic project. Do the same with freedomotic-model and you have done.

NOTE: for more info on plugin GUI creation take a look [http://code.google.com/p/freedomotic/source/browse/plugins/devices/test/src/it/freedomotic/plugins/VariousSensors.java here]

=== STEP 3: Install and run your new plugin ===

Now you need an XML manifest file to describe the plugin to the middleware, but don"t warry this file will be created automatically by our build scripts along with a package ready to be uploaded on the [http://freedomotic.com/plugins].

The manifest file is the one we have written in the plugin contructor (in this case "hello-world.xml"). Remember the path is case-sensitive, a Freedomotic convention is to use all lowcase letters to name the manifest.

The framework provides bulding scripts that install automatically your plugin in the framework after compile, so you can test it withoud the need to do manual copy&paste of plugins files every time you change something in the code.

# Open the *build.xml* file in your plugin project folder
# Edit the *post-jar* target as this
```
    <target name="-post-jar">
    <!-- calls an external build file located in GIT_ROOT/scripts folder -->
        <ant antfile = "${basedir}../../../../scripts/plugin-build.xml" inheritAll="true"/>
    </target>
   
    <target name="upload">
        <!-- calls an external build file located in scripts folder -->
        <ant antfile="${basedir}../../../../scripts/plugin-build.xml" target="upload-to-marketplace" inheritAll="true"/>
    </target>
```
# On Netbeans right click -> build on your plugin project name. You should now see a *build.options* file, two new folders *framework-dist* and *market-dist* and the copy of framework-dist content in *freedomotic/plugins/PLUGIN_TYPE*. In *framework-dist* you will find also an empty manifest example.
# Edit *build.options* with your personal information and rebuild. Note that a *major version number* means a brake in compatibility with plugins (changes in xml structure, in framework public method signature,...), the *minor version number* indicates a change in features which not affects plugins (the fix of a typo or the performance improvement of an existent method). Here is an example of *build.options*. *NOTE: for the last build major=5 and minor=3*
```
package.type=device
package.name=it.freedomotic.hello
package.nodeid=-1
package.license=GNU GPL2
package.vendor.name=John
package.vendor.surname=Doe
package.vendor.contact=johndoe@freedomotic.com
package.vendor.company=freedomotic
package.vendor.nation=it
framework.required.major=5
framework.required.minor=3
framework.required.build=x
build.major=1
build.number=0
build.create.dist.market=true
build.create.dist.framework=true
build.synch.dist=true
build.synch.dist.path=../../../framework/freedomotic/plugins/
```
# Now create the manifest file for your plugin called hello-world.xml and put in the it.freedomotic.hello directory under your plugin project. Add this content:
```
<config>
    <properties>
        <property name="description" value="A simple plugin to start coding"/>
        <property name="category"    value="devel"/>
        <property name="short-name"  value="hello"/>
    </properties>
</config>
```

The following property

```
<property name="startup-time"        value="on load"/>
```

makes the plugin is started automatically after all resources are loaded. If you omit this property you have to start the plugin by hand (double click on frontend).

NOTE: the data folder is sync only from plugin folder to framework folder. If you change command/trigger in framework folder they must be merged back manually in plugin folder. So we suggest to edit xml files in data in plugin project folder and rebuild to have in sync on the framework.


=== STEP 4: Use your plugin ===

Run Freedomotic from NetBeans and you will see your new plugin "Hello World" in the list on the left. Double click on the plugin name to start/stop it.

Right click on plugin "Log Viewer" to see log messages, you will see the message written in onRun()


=== Optional Customization: add a plugin icon ===

If you want to add an icon to your plugin so it can be showed in the plugins list create a folder into your plugin *data/resources* with two 64x64 pixel images in png format named in lowercase
*CLASSNAME-running.png* (coloured)
*CLASSNAME-stopped.png* (grey scale)

For example if your class is HelloWorld.java then you need to create
*helloworld-running.png*
*helloworld-stopped.png*

'></a>