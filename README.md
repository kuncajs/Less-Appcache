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
and generate proper appcache manifests completely written in Java SE 7


Key features
------------
* add resources dynamically by regular expressions or globs
* use @import to create modules to make offline just parts of your complex applications offline
* checks whether resources really exist and warns user if they do not, that a typo might occur
* create version comment based on last modified file of listed resources
* checks syntax and errors in appcache (multiply defined namespaces in fallback and more...)
 
Language of less-appcache files
-------------------------------
You can import other files by `@import` statement (see example). The application automatically import relative
paths of imported file in correct context.

There are currently four filters in the application which allows developers to add resources to the appcache
by wildcards and patterns they are `@glob, @regex, @r-glob and @r-regex`. See example for more info.

Glob syntax: http://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob

Processing tool automatically searches for stated resources, create version comments (which change only
if the files have really changed so redownloads occur only when really needed) and watches your syntax.

Example of main.lesscache:

    CACHE MANIFEST
    CACHE: 
     
    #imports the system lesscache in correct context of generated main.appcache
    @import ../system.lesscache   
      
    images/banner1.png
    images/bg.png
    images/logo.png
    /css/styles.css
     
    http://code.jquery.com/jquery-1.9.1.min.js
     
    #add all jpg files from dir/path directory
    # (you can use @regex if you need more complicated pattern)
    @glob dir/path *.jpg
     
    #recursive add of all js file from scripts/libs and its subfolders
    # (you can use @regex if you need more complicated pattern)
    @r-glob scripts/libs *.js
     
    FALLBACK:
    / /offline
    NETWORK:
    *
    SETTINGS:
    prefer-online

Use of processor
----------------
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
    
Difference between module and filter
------------------------------------
Filters are classes responsible for parsing lines in format

`@filter-name argument1 argument2 ... argmunetN`

they often generate new line or lines which should be added to the output.

Modules are more general: They are classes which are responsible for parsing a line in certain format.
In the system is currently eight modules parsing the contents of the file
* HeaderModule - parses headers CACHE MANIFEST, FALLBACK:, CACHE:, ...
* CommentModule - parses lines starting with #
* ImportModule - imports files by @import statement
* FilterModule - calls filter by @filter-name syntax
* Explicit, Network, Fallback and SettingsModule - responsible for parsing resources in their sections

The modules process the line in this order. You can create your own module and add them to the application. In ModulePhases
class are constants to specify in which phase shoul the module parse the line. In ModuleControl enum the control structures
to tell the parser how to behave when module parsed the line. More in next section.

Extensibility
-------------
You can write your own Filter or Module by implementing the interface and including your generated jar.
Filters are searched on classpath in package `cz.muni.fi.lesscache.filters` and modules are loaded via ServiceLoader
so do not forget to include a file in META-INF (http://pietrowski.info/2009/04/serviceloader-modularization-for-free/).
Also you need to specify a phase when your module should parse the file. Check javadocs in classes in modules package to see how it works
(namely ModulePhases, ModuleControl, ParsingContext, Module, ModuleOutput and an example of working module)

For example if would like to add support for variables you would create a module parsing lines in format
`{var=value}` (setter) and any line includeing `{var}` (getter). This module should parse the line AFTER CommentModule
but BEFORE ImportModule.
Setter would not return anything and stop processing, getter would return modified line and send the line to further
modules.
