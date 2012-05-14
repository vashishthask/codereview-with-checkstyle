set ANT_HOME=C:\tools\apache-ant-1.6.2
set ANT_OPTS=-Xmx256m
set JAVA_HOME=C:\j2sdk1.4.2_05
set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin
set CLASSPATH=
%JAVA_HOME%/bin/java -cp %ANT_HOME%/lib/ant.jar;%ANT_HOME%/lib/ant-launcher.jar;%JAVA_HOME%/lib/tools.jar org.apache.tools.ant.Main %1