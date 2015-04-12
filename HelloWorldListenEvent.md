Your plugins can listen for any type of events on the messaging bus. What you have to do is to subscribe a topic [Channel](Channel.md) and implement what you want to do after receiving the event.

# Listen for state changes #

The objects state changes are notified by an ObjectHasChangedBehavior? event. To be notified of this kind of event just start to listen on channel app.event.sensor.object.behavior.change
Java Example

Add a listener in onStart() method of your plugin then in onEvent() you will be notified of such changes.
```
@Override
public void onStart() {
//add a listener for object change notifications
addEventListener("app.event.sensor.object.behavior.change");
}

@Override
protected void onEvent(EventTemplate event) {
  if (event instanceof ObjectHasChangedBehavior) {
    ObjectHasChanged change = (ObjectHasChanged) event;
    //now you can inspet the event using getters method
    //change.getXXX() 
    //or reading properties directly
    //String changedObject = change.getProperty("object.name");
    drawer.setNeedRepaint(true);
  }
}
```

Take a look at the available [events list](EventsList.md)

The same is valid for non java plugins. Just create a listener on the cannel then parse the received XML.