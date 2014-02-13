#!/bin/bash

##check the input
case $1 in 
    (j--_gr_*.zip)
    	group_number=(`echo $1 | tr '.zip' ' ' | tr 'j--_gr_' ' '`)
       echo correct file name
       echo group number is : $group_number
    ;;
    (clean)
       echo delete the correction directory
       rm -r ./correction
       exit
    ;;
    (*)
       echo wrong file name
       exit
    ;;
esac


#unzip project
if [ ! -d correction ]; then
	mkdir correction 
fi

if [ ! -d correction/${group_number}/ ]; then
	mkdir correction/${group_number}/
	unzip $1 -d correction/${group_number}/
else
	rm -r correction/${group_number}/
	unzip $1 -d correction/${group_number}/
fi

#go to group project directory
cd correction/${group_number}/j--/


#add a dummy JUnit test
sed '
/return suite;/i\
suite.addTestSuite(DummyTest.class);
' tests/junit/JMinusMinusTestRunner.java > tests/junit/JMinusMinusTestRunner.java.tmp

mv tests/junit/JMinusMinusTestRunner.java.tmp tests/junit/JMinusMinusTestRunner.java

echo "package junit;

import junit.framework.TestCase;

public class DummyTest
    extends TestCase {

    public void test() {
        this.assertEquals(1, 1);
    }
}" > tests/junit/DummyTest.java

#dummy pass test
echo "package pass; public class EmptyClass {}" > tests/pass/EmptyClass.java

#dummy fail test
echo "package pass; public class EmptyClass {" > tests/fail/EmptyClass.java

#Ant default task
ant

#CheckStyle Ant tasks
#NOT DEFINED HERE