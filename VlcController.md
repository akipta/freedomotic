# Controllare VLC Media Player da remoto #

Con questo plugin è possibile avviare/stoppare la riproduzione di video e audio ad opera del media player open source VLC.

## Messaging System ##

A command for this plugin must be published on channel
```
app.events.actuators.video.player.in
```

## Accepted Input Parameters ##

This plugin needs this properties in input from a [Command](Command.md)

| **Parametro** | **Obbligatorio** | **Valore** | **Effetto** | **Note** |
|:--------------|:-----------------|:-----------|:------------|:---------|
| url   | Obbligatorio | un url locale o remoto| Avvia la riproduzione del ccontenuto multimediale puntato da url |  |
| close   | Opzionale | yes; no | se il valore è 'yes' viene fermata la riproduzione del file multimediale puntato da url. Se il valore è 'no' il parametro può essere omesso |  |

## A Command Example ##

Avvia la riproduzione del video in streaming da una telecamera di sorveglianza all'indirizzo http://213.192.14.189/axis-cgi/mjpg/video.cgi?resolution=320x240

```
<it.freedom.reactions.Command>
  <name>Show Surveillance Camera</name>
  <receiver>app.events.actuators.video.player.in</receiver>
  <description>shows the video streamed by the surveillance camera in the entrance</description>
  <delay>0</delay>
  <properties>
    <properties>
      <property name="url" value="http://213.192.14.189/axis-cgi/mjpg/video.cgi?resolution=320x240"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```

Ferma la riproduzione del video e chiude l'istanza di VLC

```
<it.freedom.reactions.Command>
  <name>Stop Surveillance Video</name>
  <receiver>app.events.actuators.video.player.in</receiver>
  <description>stops the video streamed by the surveillance camera in the entrance</description>
  <delay>0</delay>
  <properties>
    <properties>
      <property name="url" value="http://213.192.14.189/axis-cgi/mjpg/video.cgi?resolution=320x240"/>
      <property name="close" value="yes"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```