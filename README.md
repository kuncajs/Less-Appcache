Less-Appcache
=============

Framework which enables to create appcaches in style like LESS.js enhance CSS. Allows **dynamic** creation
of application manifests.

Application manifest
--------------------
Every offline web application concentrates on a cache manifest file. The manifest
file is a list of all the files the application has to download and store and also a set of rules how the application
should behave when it reaches an unavailable resource.

How it works?
-------------
It is an extension of the language of manifest files and also a processing tool capable of parsing these files
and generate proper appcache manifests.


Key features
------------
* add resources dynamically by regular expressions or globs
* use @import to create modules to make offline just parts of your complex applications offline
* checks whether resources really exist and warns user if they do not, that a typo might occur
* create version comment based on last modified file of listed resources
* checks syntax and errors in appcache (multiply defined namespaces in fallback and more...)

Use
---
Use the tool to generate appcache files.


<code>
   java -jar lesscache.jar file.lesscache > file.appcache
</code>


You can even add second parameter which determines the path of server root so the processing tool
can process absolute paths


<code>
    java -jar lesscache.jar file.lesscache C:\wamp\www > file.appcache
</code>


Tool processes the resources against actual directory! To parse all lesscache files in subfolders use this code on Windows:

    @ECHO OFF
    set lesspath=C:\path\to\lesscache.jar
    set original=%CD%
    FOR /R . %%G IN (*.lesscache) DO 
      cd %%~dpG
      java -jar "%lesspath%" %%~nxG > %%~nG.appcach
    )
    cd %original%


Extensibility
-------------
You can write your own Filter or Module by implementing the interface and including your generated jar.
Filters are searched on classpath in package `cz.muni.fi.lesscache.filters` and modules are loade via ServiceLoader
so do not forget to include a file in META-INF (http://pietrowski.info/2009/04/serviceloader-modularization-for-free/).
Also you need to specify a phase when your module should parse the file. Check classes in modules package to see how it works
(namely ModulePhases, ParsingContext, Module, ModuleOutput and an example of working module)
