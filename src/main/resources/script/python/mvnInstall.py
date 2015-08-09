#!/usr/bin/python
import os
import sys
if len(sys.argv) != 3:
    print './mvnInstall.py jarFile groupdId'
    quit()

jar = sys.argv[1]
groupId = sys.argv[2]
index = jar.rfind('-')
artifactId = jar[:index]
version = jar[index+1:]
#print artifactId, version
command = 'mvn install:install-file -Dfile=%s -DgroupId=%s -DartifactId=%s -Dversion=%s -Dpackaging=jar -DgeneratePom=true'%(jar,groupId,artifactId,version)
result = os.popen(command)
print result.read()