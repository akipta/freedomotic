### Person enter or exit a zone ###

**Class name**: PersonInteractZone.java

**Description**: Fired when a user enters or exit a [Zone](Zone.md) in the environment.

**Channel**: 'app.event.person.zone'

**Properties**:
| **Property Name** | **Required** | **Type** | **Accepted Values** |
|:------------------|:-------------|:---------|:--------------------|
| zone | yes | String | the zone name.eg: 'kitchen' |
| person | yes | Integer | ID of the user |
| action | yes | String | 'enter' OR 'exit" |