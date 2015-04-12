# Introduction #

# Freedomotic source code #



# Freedomotic Reaction example #

[Here](Reaction.md) instructions on how to create a freedom reaction.

```
<it.freedom.reactions.Reaction>
    <trigger>Someone Enters in Kitchen</trigger>
    <reaction>
        <it.freedom.reactions.CommandSequence>
            <command>
                <string>ETH Prova Accensione LED Cicolella</string>
            </command>
        </it.freedom.reactions.CommandSequence>
    </reaction>
</it.freedom.reactions.Reaction>

```