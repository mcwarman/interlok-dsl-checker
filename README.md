# interlok-dsl-checker

## Help

```
$ ./build/staged/bin/interlok-dsl-checker
Parsing failed.  Reason: Missing required option: a
usage: interlok-dsl-checker
 -a,--adapter <arg>         (required) The adapter xml
 -h,--help                  Displays this..
 -p,--preprocessors <arg>   The preprocessors to execute
 -v,--variables <arg>       The variables (can be added multiple times)
```

## Sample Script

```
#!/bin/bash
ADP_HOME=/opt/afv3/

export JAVA_HOME=/opt/java/jdk1.8

ADP_VER='3.6.6'
ADP_OPT_VER=$ADP_VER
MYSQL_VER='5.1.25'


LIBS="$LIBS:$ADP_HOME/lib-dsl-checker/*"
LIBS="$LIBS:$ADP_HOME/lib-adp-core-$ADP_VER/*"
LIBS="$LIBS:$ADP_HOME/lib-json-$ADP_OPT_VER/*"
LIBS="$LIBS:$ADP_HOME/lib-mysql-conn-$MYSQL_VERv"
LIBS="$LIBS:$ADP_HOME/lib-sap-$ADP_OPT_VER/*"
LIBS="$LIBS:$ADP_HOME/lib-sonicmq-$ADP_OPT_VER/*"
LIBS="$LIBS:$ADP_HOME/lib-varsub-$ADP_OPT_VER/*"
LIBS="$LIBS:$ADP_HOME/lib-xinclude-$ADP_OPT_VER/*"
LIBS="$LIBS:$ADP_HOME/lib-ehcache-$ADP_OPT_VER/*"

cd $ADP_HOME
$JAVA_HOME/bin/java -cp $LIBS com.adaptris.utils.DSLChecker $*
```

## Lazy  Execute

```
find  /opt/afv3/config/config-includes/dsl/ -type f -exec dsl-checker  -a {} -p "xinclude:variableSubstitution" -v /opt/afv3/config/env-vars.properties \;
```
