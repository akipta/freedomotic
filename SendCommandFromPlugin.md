# Introduction #

You can take full advantages of other installed modules from your plugin. For example you can use a third party text to speech plugin to make it say something programmatically from your plugin.

You haven't to worry about how the external plugins works, you simply send to it a generic command. The example below uses java frontend plugin to prompt a dialog with three choices.

```
public void askSomething() {
        final Command c = new Command();
        c.setName("Ask something silly to user");
        c.setReceiver("app.actuators.frontend.javadesktop.in");
        c.setProperty("question", "<html><h1>Do you like Freedomotic?</h1></html>");
        c.setProperty("options", "Yes, it's good; No, it sucks; I don't know");
        c.setReplyTimeout(10000); //10 seconds

        new Thread(new Runnable() {

            public void run() {
                Command reply = Freedomotic.sendCommand(c);
                if (reply != null) {
                    String userInput = reply.getProperty("result");
                    if (userInput != null) {
                        System.out.println("The reply to the test question is " + userInput);
                    } else {
                        System.out.println("The user has not responded to the question within the given time");
                    }
                } else {
                    System.out.println("Unreceived reply within given time (10 seconds)");
                }
            }
        }).start();
    }
```