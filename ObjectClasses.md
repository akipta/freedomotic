# Introduction #


Objects are things in the environment corresponding to real world objects like lights or abstract and intangible things like markers, GUI buttons and widgets. They can be downloaded as compressed archives from our marketplace and added to the environment.

An object is composed by one or more Behaviors which are the definition of a property. Each behavior defines a listener for the change of a variable. For example the BooleanBehavior can assume two values: true or false; so it can be used to model the "powered" behavior of an electric device which can be true of false. Doing so you have to simply implement the logic of your object in onTrue() and onFalse() methods of this behavior instructing the framework on what to do if the property "powered" of your electric device changes.

There are other behaviors, for example the RangedInBehavior can model the volume of a TV object, assuming a finite range of integer value from 0 to 100.

The same way an object can be used to store environmental properties that can be referenced in triggers or commands. Is possible to have an object called 'Indoor Thermometer' which saves inside the indoor temperature, doing so you can define triggers like 'if indoor temperature is greater than 25°C then turn on air conditioning system'. Another example can be a 'Clock object' which stores current time and part of the day like morning, eavening and so on. A possible trigger can be 'if indoor luminosity is less then 50% and is not night then turn on lights dimmed as the inverse of indoor luminosity'

An object has a **representation** field that is used to render the object on the screen. It can be a path to an image file or the description of a geometrical shape.


# Behaviors #

BooleanBehavior
RangedIntBehavior

# Representations #

todo

# Actions #

Actions are a link to a common action perfomerd by a type of object to the command which can instruct an actuator on how to perform it.
For example an object action can be "turn on" and the related command can be "turn on X10 device" performed and exposed by an X10 Actuator plugin.

Doing so an object can be general and independent from it's controller, so a light is always a light despite what protocol turns it on/off. You don't need to develop custom objects if you want to implement a new automation protocol.