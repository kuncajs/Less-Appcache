Less-Appcache
=============

Framework which enables to create appcaches in style like LESS enhance CSS.

Application manifest
--------------------
Every offline web application concentrates on a cache manifest file. The manifest
file is a list of all the files the application has to download and store and also a set of rules how the application
should behave when it reaches an unavailable resource.

Solved problems
---------------
Proposed language and framework helps to overcome several pitfalls of developing an offline
web application. Solved problems are: change of resources by checking modification of resources listed
in the manifest, modularity by introducing @import clause, error prone lists by checking existence
of listed resources. It also enables to list resources dynamically and to create manifest files much easier.
