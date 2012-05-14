====================================
== Checkstyle Samples application ==
====================================

@author ShriKant

In this application a lot of custom Checkstyle rules have been created. For some, test conditions have been prepared. 
This document provides the details about them and also how to run them.

This is an Eclipse based Java project. However to build the application, a simple ANT script has been provided.

Build
-----
To build the application simply run build.bat on Windows platform or run build.sh on UNIX/LINUX platform.
You need to have ANT available to build the application. As part of build, it will create classes in bin directory 
and will create a Jar file inside target directory.

Run the samples
---------------

Please note to run the below mentioned checks, please comment other Checks in the custom_check.xml and uncomment 
the one you want to execute.

MethodCallWithoutObjectCreation Check
-------------------------------------
This Check provides the instances where a method has been called on an uninitialized variable.
--To run the test condition use:
  run.bat samples\InterfaceServiceImpl.java

MethodLimitCheck Check
----------------------
This Check checks the number of methods inside a class. If it exceeds from a pre-determined number,
it gives the error.
--To run the test condition use:
  run.bat samples\Test.java

TraceMessagesCheck Check
------------------------
This Check checks if for a specific pattern of classes, trace messages for method enter and exit
are there or not.
--To run the test condition use:
  run.bat samples\TraceEnterSample.java
  
NoStateVariables Check
----------------------
This Check validates if a pattern of classes contains state variables when those classes are supposed 
to be stateless.
--To run the test condition use:
  run.bat samples\SingletonSample.java
  
IllegalMethodCallInLoopCheck Check
----------------------------------
This Check checks if a method call has been used in a loop condition.
--To run the test condition use:
  run.bat samples\SingletonSample.java

IllegalExceptionCatchCheck and IllegalExceptionThrowsCheck Check
----------------------------------------------------------------
These two checks focuses on catching instances where something illegal exception type is caught or thrown
in methods.
--To run the test conditions use:
  run.bat samples\Test.java
 
 