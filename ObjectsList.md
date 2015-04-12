# Introduction #

In this table we can specify classes of objects with properties and behavior. Classes collect objects with similar features. For example, in mochad plugin there is a reference to cameras and there are different behavior related to their movements: up, down, left,right etc.

Note: some behaviors couldn't be available for some phisycal devices, but Freedom object is an abstraction of real ones, so it must consider all.


| **CATEGORY** | **OBJECT** | **PROPERTIES** | **BEHAVIOR** |
|:-------------|:-----------|:---------------|:-------------|
| Electric   | Light	  |  | on; off; dimm; brighter; dimmer; switch|
|Audio/Video | Network Camera | name; address; resolution|on; off; move\_up; move\_down; move\_left; move\_right; zoom\_in; zoom\_out|



**Electric Device**
| **Allowed Behaviors** | **Its Opposite** | **Its next** | **Its previous** |
|:----------------------|:-----------------|:-------------|:-----------------|
|ON        | off | off | off |
|OFF       | on | on | on |

**Light**
| **Allowed Behaviors** | **Its Opposite** | **Its next** | **Its previous** |
|:----------------------|:-----------------|:-------------|:-----------------|
|ON        | off | off | dim {dim-value=90} |
|OFF       | on | dim {dim-value=10} | on |
|DIM       | off | dim {dim-value=+10} | dim {dim-value=-10} |

**Automation**
| **Allowed Behaviors** | **Its Opposite** | **Its next** | **Its previous** |
|:----------------------|:-----------------|:-------------|:-----------------|
|STOP        | up | up | down |
|UP       | down | down | down |
|DOWN       | up | up | up |