### Person Moving ###

**Class name**: PersonMoving.java

**Description**: Fired by an environmental tracking position sensor when a person change location.

**Channel**: `app.event.sensor.person.movement.moving`

**Properties**:
| **Property Name** | **Required** | **Type** | **Accepted Values** |
|:------------------|:-------------|:---------|:--------------------|
| id | yes | int | MIN\_INT - MAX\_INT |
| xCoord | yes | int | An x coordinate point within the environment|
| yCoord | yes | int | An y coordinate point within the environment |

Note:
The point (x,y) must be within the envronment dimension otherwise the event is discarded. The point (0,0) , the origin, is the upper-left corner of the environment. The coordinates are expressed in centimeters: the point (100, 300) is located one meter on the right and three meter on the bottom from the origin point.
this event is intercepted by the ZoneIntelligence basic plugin. This plugins fires a PersonEnterZone or PersonExitZone event.