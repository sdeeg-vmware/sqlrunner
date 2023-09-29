# SQLRunner
A simple application that can connect to a DB and run basic SQL commands.  
Currently only supports H2.

The main use of this app is as a demo for application platforms.  It's a simple app, has a GUI,
calls a backend which connects to a DB service of some type.

## Release
The initial coding of this app has been tagged as the alpha, and I am now beginning to flesh out the 
beta.

### Beta upgrades
Changed from JDK 11 to 17
Upgraded Boot vesion
Added GraalVM to enable native compiling

## Supported Commands
- select|show (queryForList)
- create (execute)
- insert|update|delete (update)
