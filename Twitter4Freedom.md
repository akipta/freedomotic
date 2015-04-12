# Twitter4Freedom Actuator #
## User Information ##

---

### What is ###
This actuator allows Freedom to publish messages as a status updates on a twitter account.

A Twitter account is required to be configured to allow an external application status post messages.


---

## Information for the developers ##

---

### twitteractuator.xml configuration ###
The actuator usual parameters should be configured. See the Actuator page.
Also, the following parameters have to be defined in the xml.

Generic properties
| **Parameter** | **Required** | **Values** | **Effect** | **Note** |
|:--------------|:-------------|:-----------|:-----------|:---------|
|OAuthConsumerKey |yes |see next section|  | |
|OAuthConsumerSecret |yes |see next section |  | |
|OAuthAccessToken |yes |see next section |  | |
|OAuthAccessTokenSecret |yes |see next section|  | |


## How to obtain the OAuth parameters ##

  * Create an Account on Twitter that it is going to publish Freedom messages. It's recommended NOT TO USE the personal one, but another as "house account". Maintain it private, and only accept users by invitation (if not, all persons could see your Freedom messages).

  * Now we have to obtain the _ConsumerKey_ and _ConsumerSecret_ for this application. In twitter, login with the new user, and go to [https://dev.twitter.com/apps](https://dev.twitter.com/apps). Register the app that is going to be used. (configure it as read&write)

  * We have obtained the _ConsumerKey_ and the _ConsumerSecret_ values.

  * Now we have to obtain the _AccessToken_ and the _AccessTokenSecret_. In the plugin jar is included a java executable: _OAuthSetup_

  * Execute it in a terminal, paste the _ConsumerKey_ and the _ConsumerSecret_. In output you obtain an url.

  * Paste the url in a browser to obtain a Code.

  * Copy and paste that code in the commandline where _OAuthSetup_ is being executing. After _OAuthSetup_ gives you the _AccessToken_ and _AccessTokenSecret_.

  * Fill the _twitteractuator.xml_ with this 4 values (substitute them in the included example of xml) and your twitter plugin is configured.

```
<config>
    <properties>
        <property name="description"            value="Actuator for Twitter"/>
        <property name="version"                value="40"/>
        <property name="required"               value="40"/>
        <property name="category"               value="Twitter4Freedom"/>
        <property name="short-name"             value="TwitterActuator"/>
        <property name="OAuthConsumerKey"       value="**PASTE OAuthConsumerKey HERE**"/>
        <property name="OAuthConsumerSecret"    value="**PASTE OAuthConsumerSecret HERE**"/>
        <property name="OAuthAccessToken"       value="**PASTE OAuthAccessToken HERE**"/>
        <property name="OAuthAccessTokenSecret" value="**PASTE OAuthAccessTokenSecret HERE**"/>
    </properties>
</config>

```

### Messaging ###
| **Operation** | **Channel Type** | **Channel Address** |
|:--------------|:-----------------|:--------------------|
| read | Queue | `app.actuators.Twitter4Freedom.TwitterActuator.in` |
| write | Queue | no definida |


### Command Definition ###

| **Parameter** | **Required** | **Values** | **Effect** | **Note** |
|:--------------|:-------------|:-----------|:-----------|:---------|
|status|yes| text with < 140 chars (twitter limit) |the new status mess to send to Twitter|  |


Now it can be used in the Freedom system using the command format that is included as an example in the code.

```
<it.freedom.reactions.Command>
  <name>Update Status in Twitter</name>
  <receiver>app.actuators.Twitter4Freedom.TwitterActuator.in</receiver>
  <delay>0</delay>
  <timeout>2000</timeout>
  <description>The status is updated</description>
  <properties>
    <properties>      
      <property name="status" value="I have changed the status"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```

FOR DEVELOPERS: [forum topic](http://freedomotic.com/forum/6/392?page=0#415)