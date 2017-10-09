#!/bin/bash
JAVA_HOME=/opt/jdk1.8.0_121
PATH=$PATH:$JAVA_HOME/bin
CLASSPATH=$CLASSPATH:$JAVA_HOME/jre/bin

export CLASSPATH PATH JAVA_HOME

cd /opt/inhouse/bg/bg_getworkonsite_dev
$JAVA_HOME/bin/java -cp /opt/inhouse/bg/config/libs/*:./bg_getworkonsite.jar -Xms64m -Xmx128m com.cct.getworkonsite.bg.control.GetWorkOnSiteMain > /dev/null &
