**_Reactions are based on the concept of [Channel](Channel.md), be sure to have understood this concept before continue reading._**

## Introduction ##

The reaction consists of a trigger and one or more lists of commands. The commands in a list are executed sequentially. The lists of a reaction run in parallel with a dedicated thread for each one of them. The triggers and commands are defined in files independent from reaction, which constitutes only a link. So it is possible to reuse commands and triggers in different reaction.

Example:

**Reaction Name**: Monday evening entertainment scenario

**Trigger**: It is monday evening and TV turns ON

**Command Sequence 1:**
Turn OFF Livingroom lights

**Command Sequence 2:**
Close Windows -> Close Blinds

When is monday evening and the TV turns ON the lights in the livingroom are switched off. At the same time the windows start to close, when all the windows are completely closed the system begins the lowering the blinds.

# XML Representation #

Reaction are deployed on **FREEDOMOTIC\_ROOT/data/rea** folder. This is the XML describing the previous scenary.

```
<it.freedom.reactions.Reaction>
  <trigger>It is monday evening and TV turns ON</trigger>
  <sequences>
    <sequence>
      <command>Turn OFF Livingroom lights</command>
    </sequence>
    <sequence>
      <command>Close Livingroom windows</command>
      <command>Close Livingroom blinds</command>
    </sequence>
  </reaction>
</it.freedom.reactions.Reaction>
```