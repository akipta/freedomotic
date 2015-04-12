# Inviare SMS gratis #

Con questo plugin è possibile inviare SMS (Short Message Service) gratuitamente verso qualsiasi operatore.

## Messaging ##

| Operazione | Tipo Canale | Indirizzo Canale |
|:-----------|:------------|:-----------------|
| legge da | Queue | app.events.actuators.smssender.smssender.in |
| scrive su | Queue | non definita |

## Comando ##

| Parametro | Obbligatorio | Valore | Effetto | Note |
|:----------|:-------------|:-------|:--------|:-----|
| phone-number   | Obbligatorio | un numero telefonico |  |
| message   | Obbligatorio| stringa | testo del messaggio |  |

## Esempio XML ##


```
<it.freedom.reactions.Command>
  <name>Send SMS to Enrico</name>
  <receiver>app.events.actuators.video.player.in</receiver>
  <description>sends a hello sms to Enrico</description>
  <delay>0</delay>
  <properties>
    <properties>
      <property name="phone-number" value="123456789"/>
      <property name="message" value="Hello World!"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```