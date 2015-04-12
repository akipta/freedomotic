# Introduction #

You can easely advertise your plugin on the Freedomotic marketplace at **www.freedomotic.com/plugins**. We have a tool to auto-publish your latest build using Apache ANT.

# First time configuration #

First of all create a new plugin page here http://www.freedomotic.com/node/add/plugin. After your plugin page is created enter in edit mode and look at its address in your browser bar. Take note of the number in it for example if you have `www.freedomotic.com/node/850/edit` your **nodeid** is **850**, remember it.

To do so check your build.option file and be sure it has all this properies

```
package.type=device
package.name=it.nicoletti.test
package.nodeid=850
package.license=GNU GPL2
package.vendor.name=Enrico
package.vendor.surname=Nicoletti
package.vendor.contact=info@freedomotic.com
package.vendor.company=freedomotic
package.vendor.nation=it
framework.required.major=5
framework.required.minor=2
framework.required.build=x
build.major=1
build.number=221
build.create.dist.market=true
build.create.dist.framework=true
build.synch.dist=true
build.synch.dist.path=../../../framework/freedomotic/plugins/
```

the package.nodeid property must have the value you have previously taken from the address bar of your browser. Now save the changes to build.option file.

The last change needed is to add this few lines
```
<target name="upload">
    <!-- calls an external build file located in scripts folder -->
    <ant antfile="${basedir}../../../../scripts/plugin-build.xml" target="upload-to-marketplace" inheritAll="true"/>
</target>
```

at the end of your build.xml file, just before the ending `</project>` tag.

# Upload after changes #

Now you have done. Build your plugin as usual and when you want to upload your code you can do it by calling this ANT task from command line or more simple from **NetBeans**:

  1. switch to `File` tab
  1. search for your plugin folder and right click on its build.xml
  1. in the popup menu click on `Run ANT target -> Other targets -> upload`
  1. input the username and password of your freedomotic.com account
  1. check on the website your new downloadable package in your plugin page