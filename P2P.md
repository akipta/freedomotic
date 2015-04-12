# Introduction #

Peer to peer requests are a system to directly request the execution of a [Command](Command.md), created at runtime, from a plugin to another plugin without the need to involve the event->trigger->command mechanism.

For example a PHP client can request directly to the text to speech plugin to say something sending to it a command without the need to instantiate an event, create a trigger to catch this event and then associate the trigger with a "say something" command.

# How to make a peer2peer execution request in Java #

In the example below the plugin CamMotionDetector request to the VlcController plugin to display on the screen the video stream that it is listening for movement events. The url of the video to display is set with the "url" property. Finnaly the response to the request is catched to know if the command is correctly executed. For the parameter list (key and values in the command) accepted by the target actuator, you have to refer to its guide, in this example the VlcController guide.

```
public void displayVideo(URL url) {
  //sends a request to the video player plugin to visualize 
  //the video stream at the url passed as method parameter
  Command playMedia = new Command();
  playMedia.setName("Media Player Request");
  playMedia.setDescription("request to play a media file or web stream");
  playMedia.setReceiver("app.actuators.video.player.in");
  playMedia.setProperty("url", url.toString());
  playMedia.setProperty("fullscreen", "false");
  playMedia.setProperty("dimension", "from source");
  PeerDispatcher request = new PeerDispatcher(playMedia);

  ExecutionResult response = request.execute();
  //execute() returns null if the actuator don't reply
  if (response != null) {
    System.out.println("Media Player starts to play");
  }
}
```