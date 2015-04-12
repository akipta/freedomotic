# Attuatore X10 #

---

## Info per gli utenti ##

---

### A cosa serve ###

Questo plugin abilita la comunicazione e l'esecuzione di comandi da parte dei dispositivi X10. Questi servono per accendere/spegnere elettrodomestici e dimmerizzare luci. I dispositivi X10 comunicano attraverso powerline (onde convogliate) uilizzando un controllore connesso al computer attraverso porta seriale USB. I moduli X10 sono utilizzati per controllare luci ed apparecchiature elettroniche ricevendo i comandi modulati sulla rete elettrica. Ogni modulo dispone di due rotelle per settare un indirizzo univoco sulla rete. Una rotella definisce l'house code e l'altra lo unit code. Per ulteriori informazioni sul protocollo http://it.wikipedia.org/wiki/X10_%28standard%29

### Controllori X10 supportati ###

|Nome| Descrizione | Produttore | Link |
|:---|:------------|:-----------|:-----|
|PMIX15 | controllore con capacità di testing del rumore sulla rete elettrica |  |  |


### Moduli X10 supportati ###

Ogni dispositivo X10 compatibile con i controllori supportati specificati sopra. Il sistema è stato testato con i seguenti moduli:

| Nome | Funzionalità | Produttore | Prezzo Indicativo | Link |
|:-----|:--------------|:-----------|:------------------|:-----|
|   |              |            |                  |      |






---

## Info per gli sviluppatori ##

---

### Messaging ###

| Operazione | Tipo Canale | Indirizzo Canale |
|:-----------|:------------|:-----------------|
| legge da | Queue | `app.events.actuators.X10.X10Actuator.in` |
| scrive su | Queue | non definita |


### Definizione di un command ###

| Parametro | Obbligatorio | Valore | Effetto | Note |
|:----------|:-------------|:-------|:--------|:-----|
| address   | Opzionale | HOUSECODE+UNITCODE | Indica l'indirizzo del dispositivo target secondo lo standard X10 | Opzionale poichè l'indirizzo fisico è definito nell'oggetto stesso |
| object | Obbligatorio |Nome oggetto | Nome dell'oggetto definito nel file dell'ambiente (vedi guida su definizione oggetti) | Keyword riservata ed obbligatoria |
| behavior | Obbligatorio | on;offf;dimm | Indica il comportamento che deve assumere l'object (acceso; spento; dimmerizzato) | dimm è applicabile solo ad oggetti di tipo luce e va accoppiato con il parametro dimValue |
| dimValue | Obbligatorio se il behavior è dimm | intero da 0 a 100 | Indica il valore di luminosità da raggiungere |applicabile solo ad oggetti luce. Indica la luminosità di una lampadina. I valori di dimm possono essere anche relativi (+20, -30, ...) |
| command | Opzionale | HOUSECODE+ON;OFF;DIMM | Verrà tolto nella nuova versione, il commmand dipenderà solo dal behavor specificato|

### XML di esempio ###

è possibile editare il comando anche utilizzando l'apposito editor grafico. I parametri e i relativi valori ammessi sono gli stessi specificati sopra. Ricordo che i comandi per funzionare devono essere collegati ad un qualche trigger, es: TRIGGER: qualcuno entra in cucina" COMMAND "accendi luce cucina"

```
<it.freedom.reactions.Command>
  <name>Switch Off Kitchen Light</name>
  <receiver>app.events.actuators.X10.X10Actuator.in</receiver>
  <delay>0</delay>
  <description>the light in the kitchen is turned to off</description>
  <properties>
    <properties>
      <property name="address" value="A01"/>
      <property name="object" value="Light 1"/>
      <property name="behavior" value="off"/>
      <property name="command" value="AOFF"/>
    </properties>
  </properties>
</it.freedom.reactions.Command>
```