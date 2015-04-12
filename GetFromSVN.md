# Introduction #

This tutorial for Freedomotic developers shows how to download source code from SVN repository. For testing purpose you can find some simple instructions at this link  http://www.freedomotic.com/content/download

## Freedomotic and Subversion ##

Freedomotic, hosted on Google Code, uses Subversion as control version system. In the repository you can find the NetBeans project configured with all libraries to be immediately active. So we suggest to use NetBeans as IDE because all development tutorials will be referred to this tool.

## SVN Repository Checkout ##

You can download NetBeans from this link http://www.oracle.com/technetwork/java/javase/downloads/index.html Only requirement is the latest version of Java JDK.

Open NetBeans, select **Team** from Menu and then **Subversion->Checkout**

Parameters:
  * **Repository URL**: https://freedomotic.googlecode.com/svn/lib
  * **User**: you can view source code as anonymous user or ask to be added as project committer sending an email to enrico.nicoletti84@gmail.com
  * **Password**: no password required for anonymous users; if you are registered click on "Source" tab in that page and then on link "googlecode.com password"

After checkout repeat the same operation using as repository url https://freedomotic.googlecode.com/svn/trunk to download code source.

At the end of these tasks you can find two new folders called lib and trunk.
In NetBeans open **New Project** and select freedom from trunk folder.
Freedomotic is ready to be compiled.


## Create project zip file ##
To create a project zip file you must use Apache Ant. Please download it from [this link](http://ant.apache.org/bindownload.cgi) and follow the installation instructions. Then move to NetBeans Freedom project folder and digit from console
```
ant -buildfile build.xml create-release
```
At the request for version number digit 5.1 and press return.



## Repository Structure ##

  * **lib**: shared libraries for freedom and plugins
  * **trunk**: Freedomotic development version
