## Configurazione ##
Cooming soon...
<a href='Hidden comment: 
Oltre alle classi un plugin può essere composto di un file di configurazione che...
Descrizione file di configurazione con le proprietà comuni e i metodo per l"accesso e GUI.
'></a>

## Actuator ##
`public constructor()`<br>
L'unica operazione obbligatoria nel costruttore di un plugin è quella di impostare il nome del plugin stesso, richiamando il costruttore della super classe e passandogli il nome.<br>
Inoltre è possibile impostare anche la descrizione del plugin con la funzione void setDescription(String).<br>
<pre><code>public myActuator() {<br>
    super(“myActuatorNameHere”);<br>
}<br>
</code></pre>

<code>protected boolean canExecute(Command c)</code><br>
Questa funzione restituisce un valore booleano alla domanda se è possibile eseguire un particolare comando con il presente plugin. Viene utilizzata su tutti i plugin per sapere quale può eseguire un particolare comando.<br>
<br>
<code>protected void onCommand(Command c) throws IOException, UnableToExecuteException</code><br>
Esegue il comando passato come parametro. Il comando contiene tutte le informazioni necessarie per la sua esecuzione, come l'indirizzo del dispositivo. Nel caso il comando non possa essere eseguito o un qualsiasi altro errore, questa funzione deve sollevare una eccezione.