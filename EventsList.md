## Predefined Events ##

Here is a list of the predefined Freedomotic events. The property name can be used in [Triggers](Trigger.md) to filter the event. A trigger is associated with one or more [Commands](Command.md) using a [Reaction](Reaction.md). Freedomotic permits to extends the event list as plugins, if the event is not one of the predefined please refer to the development manual provided with the event plugin.

[You can find the list of predefined events in the JavaDoc](http://www.freedomotic.com/javadoc/it/freedom/api/events/package-summary.html)

### Generic Event Parameters ###

The following parameters are common to all events and can be intercepted and filtered by any trigger:

| **PARAMETER** | **POSSIBLE VALUES** | **DESCRIPTION** |
|:--------------|:--------------------|:----------------|
| **date.dayname** | eg: Sunday | The english name of the day in which the event is throwed |
| **date.day** |  | The day number in which the event is throwed|
| **date.month** |  | The month name in which the event is throwed |
| **date.year** |  | The year number in which the event is throwed|
| **time.hour** |  | The hour number in 24h format in which the event is throwed|
| **time.minute** |  | The minute of the current hour in which the event is throwed|
| **time.second** |  | The second of the current minute in which the event is throwed|
| **sender** |  | The name of the modules tha have generated the event |


### ObjectChangeBehavior ###

**Description:** An object has changed its behavior (eg: a light change behavior from off to on)

### ObjectReceiveClick ###

**Description:** This event notifies a click on an object performed by an user in a GUI of Freedomotic (eg: desktop interface or mobile phone).


| **PARAMETER** | **POSSIBLE VALUES** | **DESCRIPTION** |
|:--------------|:--------------------|:----------------|
| **object.name** | String | The name of the clicked object |
| **object.address** | String | The address of the clicked object |
| **object.protocol** | String | The protocol of the clicked object |
| **click** | SINGLE\_CLICK, DOUBLE\_CLICK, RIGHT\_CLICK | Type of click received |


### PersonAuthenticated ###

**Description:** A person has just been authenticated to enter a zone of the environment.


### PersonDetected ###

**Description:** A person is detected in a position with coordinates x,y of the environment.


### PersonEnterZone ###

**Description:** A person enters in a zone of the environment


### PersonExitZone ###

**Description:** A person exits from a zone of the environment


### PersonMoving ###

**Description:** A person is moving from its actual coordinates to new position in the environment.


### PluginHasChanged ###

**Description:** A plugin is loaded or unloaded; starts or stops; shows or hide it's GUI.


### ScheduledEvent ###

**Description:**
