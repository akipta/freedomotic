# Introduction #

Plugins usually let the user interact with Freedomotic, e.g. by showing dialog boxes, logging messages and so on.
I'd be advisable to let plugin use the user's language in order to make them much usable and understendable. If you're a developer and want to add your plugin the capability to 'speak' different languages, just follow this simple guide to adapt your code.

# Adding localization support #

Freedomotic ships a mechanism for easily support localized strings and allows the developer to use a prebuild bag of general purpose strings. Moreover the developer could add custom messages on his/her own

The static function to use, in place of your 'unlocalized' string is:
```

  i18n.msg(_STRING_KEY_)

```

#### A quick example ####
```
 // old code (non localized)
 Freedomotic.logger.info("Hello");

 // new code
 Freedomotic.logger.info(i18n.msg("greeting"));
```

### Behind the scenes - What happens when calling i18n.msg() ? ###
  * Freedomotic reads some system config to automatically guess user locale
  * it searches proper localization string inside /i18n/Freedomotic _locale_ .properties
  * if the current locale is not defined (translation doesn't exist) en\_UK is used and **Freedmotic.properties** is loaded

# Advanced usages #

## Making custom plugin translations ##

If global translation strings aren't enough, plugin's developer could write custom strings and save them using the following path, starting from plugin's base folder:
> / _plugin\_package\_name_ / i18n / _package\_last\_part_ .properties for en\_UK

or

> / _plugin\_package\_name_ / i18n / _package\_last\_part_ - _locale_ .properties

e.g.

/it.freedomotic.jfrontend/i18n/jfrontend.properties

/it.freedomotic.jfrontend/i18n/jfrontend-es\_ES.properties

## Accessing custom plugin translations ##
Just pass the current object as a parameter to **i18n.msg()**
```
 Freedomotic.logger.info("Plugin " + i18n.msg(this,"plug_name"));
```
## Composing strings ##
Consider the following example:
we want to translate "save environment as", "save object as", "save room as" and so on.
he translation files looks like this
| Key string | Default translation | it\_IT localization |
|:-----------|:--------------------|:--------------------|
| save\_as| Save as | Salva come |
| environment| Environment | Ambiente |

using a concatenation of strings doesn't work,
```
   i18n.msg("save_as") + i18n.msg("environment";
```
it'll result in "save as environment" ...

We can then use basic java string format, like that

| Key string | Default translation | it\_IT localization |
|:-----------|:--------------------|:--------------------|
| save\_as| Save {0} as... | Salva {0} come... |
| environment| Environment | Ambiente |

```
 i18n.msg("save_as",new Object[]{i18n.msg(environment)});
```
So the second variable is given as replacement for placeholder {0}. This applies to many placeholders, not only one.