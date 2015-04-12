## Import a new object type in your environment ##

  1. Download the object compressed package from the [marketplace](http://www.freedomotic.com/plugins)
  1. Unzip or unrar the package
  1. Copy the uncompressed folder in **FREEDOMOTIC\_ROOT/plugins/objects**
  1. Restart Freedomotic for loading the new object
  1. Customize the object

## Create a copy of an already existent object ##

  1. First of all go to the the active environment folder under **FREEDOMOTIC\_ROOT/data/furn**. Inside you will find a list of **int@NUMBER.xml** files, that represent all the loaded objects at startup along with the environment definition file which is in the same folder (the only one with a different naming pattern).
  1. Create a copy of the xml object you want to copy.
  1. Rename it maintaining the progressive order of the file names. For example if the last object file is **int@11.xml** you have to call you new file **int@12.xml**
  1. Open this file and change at least the object name (inside `<name></name>` tags) to make the object unique.
  1. Restart Freedomotic to apply the changes

## Customize imported objects ##

TODO