# Introduction #

This guide describes some guidelines for software developers who want contribute to the project.

# General Conventions #

Here some examples of coding style conventions used in this project. You can find a complete tutorial at http://www.oracle.com/technetwork/java/codeconvtoc-136057.html

**Class names**

Use CamelCase (also known as medial capitals) names **in English**

|**MyMeaningfulClassName** | **yes** |
|:-------------------------|:--------|
|myClass | no |
|Myclass | no |
|My-Class| no |
|MY\_CLASS| no |

**Methods and variables names**
  * for method names start with lower case letter and use upper case letter to separate words. Eg: myMethodName() or myOwnVariable
  * Use getter and setter methods with private fields. Eg: setMyVariable() / getMyVariable() / for booleans: isVisible()
  * Avoid abbreviations and use meaningful names in English, so other people can understand and edit the code.
  * Constants must have all upper case letters with words separated using underscores. Eg: MAX\_WAITING\_TIME = 100
  * Avoids underscores as first/last char in the name

**Packages names**

  * All lowcases characters with no `"_"` or "-" and so on
  * Objects extensions must be developed in a package called "it.freedomotic.objects.impl" subpackages are allowed.
  * Plugins must be developed in a packages called "it.freedomotic.plugins" subpackages are allowed.

**Data file names**
  * Use lowcase names and extensions for commands `(*.xcmd)`, triggers `(*.xtrg)` and reactions `(*.xrea)` otherwise your plugin may not work in case-sensitive OSs (eg: Linux)

**Trigger and Commands properties**
  * all lowcase letters (object.name instead of object.Name)
  * separate logical hierarchy with dots (object.name instead of objectName)
  * use meaningful names based on the context perceived by the end user

**Formatting Tip & Readability**

  * In NetBeans you can use ALT+SHIFT+F to reformat your code properly.
  * Mantain the code simple in logic.
  * Don't spare comments
  * Avoid cryptic lines. For example initialization in a standalone line plus a comment is recommended for variables.
  * **Write comments and variables names in ENGLISH so other people can contribute to your code**

# Best practices for plugins #
  * Avoid unbounded waiting in onCommand() for actuator plugins (eg: on socket connections).
  * Avoid long operations in onCommand() that can slow down the system like file reads. Instead cache useful data on plugin startup and make the execution of onCommand the fastest possible.
  * Avoid external configuration files, use the plugin manifest file to store configuration data.
  * Use well known software [design patterns](http://en.wikipedia.org/wiki/Software_design_pattern) to resolve classic problems