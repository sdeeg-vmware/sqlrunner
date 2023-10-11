# SQLRunner

A simple application that can connect to a DB and run basic SQL commands.  
Currently only supports H2, but active development is happening for postgresql.

The main use of this app is as a demo for application platforms.  It's a simple app, has a GUI,
calls a backend which connects to a DB service of some type.

## Release

The current coding of this app is tagged as beta.  This is an active development goal, and beta stats has
not been reached.  When it has, it'll be on the releases page.  ;-)

## As a Sample Application

One of the uses of this app is for demos of the Tanzu Application Platform.  Key in TAP is the developer
portal which stresses efficiency for developers.  Story tracking is part of the development process, and 
in that spirit I have a project in Pivotal Tracker for this app.

[SQLRunner Tracker](https://www.pivotaltracker.com/n/projects/2679961)

### Beta upgrades

Changed from JDK 11 to 17
Upgraded Boot vesion
Added GraalVM to enable native compiling

## Supported Commands

- select|show (queryForList)
- create (execute)
- insert|update|delete (update)
