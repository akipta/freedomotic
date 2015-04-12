# Introduction #

This tutorial for Freedomotic developers shows how to download source code from GIT repository. Otherwise if you are serching only for ready-to-run binaries you can get them here http://code.google.com/p/freedomotic/downloads/list

## Freedomotic and GIT Version Control ##

Freedomotic, hosted on Google Code, uses GIT as control version system. In the repository you can find the NetBeans project configured with all libraries to be immediately active. So we suggest to use NetBeans as IDE because all development tutorials will be referred to this tool.

## Clone GIT Repository ##

You can download NetBeans from this link http://www.oracle.com/technetwork/java/javase/downloads/index.html Only requirement is the latest version of Java JDK.

Open NetBeans, select **Team** from Menu and then **Git->Clone**

Parameters:
  * **Repository URL**: https://code.google.com/p/freedomotic
  * **User**: you can view source code as anonymous user or ask to be added as project committer sending an email to enrico.nicoletti84@gmail.com
  * **Password**: no password required for anonymous users; if you are registered click on "Source" tab in that page and then on link "googlecode.com password"
  * **RemoteBranches**: master


At the end of these tasks you can find a new folder called **freedomotic**.
In NetBeans open **New Project** and select freedomotic from framework folder.
Freedomotic is ready to be compiled.


## Repository Structure ##

  * **clients**:
  * **framework**:
  * **lib**: shared libraries for Freedomotic and plugins
  * **plugins**:
  * **scripts**:
  * **tools**:
