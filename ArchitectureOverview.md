

## Architecture Components ##



Freedomotic is therefore primarily a programming framework for the automation and control of environments whose purpose is to drastically reduce time-to-market and effort necessary for the development of automation applications. This is possible by noting that every automation system will require common functionality regardless of the scope (home, business, industrial, hotel, telemedicine, etc. ...).
Freedomotic is an automation software composed by a core (the framework) plus some plugins.

## The Framework ##

  1. Mantains an internal data structure representing the environment (topology, room connectrions, ...), the objects in it and their state (on, off, open, closed, 50%dimmed,...)
  1. Makes this data available to external clients in a language independent way (XML, JSON, RESTlet, ...), so they can use a kind of logic like "turn on kitchen light" instead of "send to COM1 port the string #**A01AON##". Is just like they can see the same environment map the user sees; as a developer you can forget hardware level stuff and related issues. From this follows you can develop in your favourite language and just connect to the framework and exchange text messages.
  1. Provides a rules engine coupled with a natural language processing system to let the user writing automations in plain english like "if outside is dark turn on livingroom light". You can add, update and delete this automations at runtime using GUIs, without the need of coding.**


<img src='http://www.freedomotic.com/images/site/software-layers.png'>

<h2>The Plugins</h2>

Freedomotic plugins can add more features to the framework and can be developed and distributed as completely independent packages on our marketplace.<br>
They usually are developed to communicate with automation hardware like X10, KNX and so on, but also graphical frontends and "web service readers" are Freedomotic plugins just as any other source of info like webcams, or output systems like text to speech and SMS senders.<br>
<br>
You can also develop "object plugins" which are pieces of software which models the behavior of objects like lamps, doors, etc... instructing the framework on how they behave. For example a lamp object plugin tells the framework that a lamp has a boolean behavior called powered and a dimmed behavior which is represented by an integer from 0 to 100. A lamp can turn on, turn off and dimm. If dimm becomes 0% the lamp is powered=false and if dimm > 0% the lamp is powered=true.<br>
<br>
Despite the majority of building automation software in Freedomotic a lamp is still a lamp regardles who turns it on and off. So you can map the generic "turn on" command (described with the object plugin above) to the related hardware command, for example KNX (so a KNX plugin will take care of it).