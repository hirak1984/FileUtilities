@echo off
set JAVA8_HOME=C:\jdk1.8.0_74

%JAVA8_HOME%/bin/java -cp ./FileSearch-1.0.jar;./lib/* -Dlogback.configurationFile=./logback.xml pvt.hrk.fileutilities.filesearch.main.FileSearchMain ./filesearchconfig.json