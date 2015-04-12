# Introduction #

This example shows how to control an arduino board connected by usb interface in order to switch on/off a led. The code is very simple but it's a base from which to create more complex plugin using serial protocols.


## Create a new plugin ##

To create a new plugin start from [this tutorial](http://code.google.com/p/freedomotic/wiki/HelloWorldSensor).


## Source code ##
The complete Netbeans project is located  [here](http://code.google.com/p/freedomotic/source/browse/#git%2Fplugins%2Fdevices%2Farduinousb). It uses our Serial framework (based on RXTX project) so read this [tutorial](http://code.google.com/p/freedomotic/wiki/SerialFramework) for more info.


## Arduino Code ##

The following sketch must be uploaded to your arduino board. It's very simple: when it receives a 'a' char from serial connection it switch on the led connected to pint 13. Viceversa with 'b' char the led is switched off.

```
void setup(){
  Serial.begin(9600);

  //Set all the pins we need to output pins

  pinMode(13, OUTPUT);
}

void loop (){
  if (Serial.available()) {

    //read serial as a character
    char ser = Serial.read();

    //NOTE because the serial is read as "char" and not "int", the read value must be compared to character numbers
    //hence the quotes around the numbers in the case statement
    switch (ser) {
        case 'a':
        pinON(13);
        Serial.print("on");
        break;
        case 'b':
        pinOFF(13);
        Serial.print("off");
        break;
    }
  }
}

void pinON(int pin){
  digitalWrite(pin, HIGH);
}

void pinOFF(int pin){
  digitalWrite(pin, LOW);
}
```


## Sample Video ##

Click [here](http://www.youtube.com/watch?v=Nns1KDLV3Z0&feature=plcp) to watch a simple video.