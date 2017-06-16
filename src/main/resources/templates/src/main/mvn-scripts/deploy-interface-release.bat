@echo off
rem provided by pengyu
rem params 
rem make sure that repositoryId exists in your maven-settings file.
set groupId=py.dubbosupport
set artifactId=dubbosupport-sample-provider
set version=1.0
set packaging=jar
set url=http://192.168.8.3:8080/nexus/content/repositories/Release
set repositoryId=nexus-repos
set classifier=interface-only
set file=.\target\%artifactId%-%version%-%classifier%.%packaging%
rem 
echo 
echo deploy the interface-jar to nexus repo.
echo groupId: %groupId%
echo artifactId: %artifactId%
echo version: %version%
echo packaging: %packaging%
echo file: %file%
echo url: %url%
echo repositoryId: %repositoryId%
echo classifier: %classifier%
rem echo %~dp0
echo.

cd %~dp0
cd..

rem echo %cd%
echo call mvn deploy:deploy-file -DgroupId=%groupId% -DartifactId=%artifactId% -Dversion=%version% -Dpackaging=%packaging% -Dfile=%file% -Durl=%url% -DrepositoryId=%repositoryId% -Dclassifier=%classifier% -Dmaven.test.skip=true
call mvn deploy:deploy-file -DgroupId=%groupId% -DartifactId=%artifactId% -Dversion=%version% -Dpackaging=%packaging% -Dfile=%file% -Durl=%url% -DrepositoryId=%repositoryId% -Dclassifier=%classifier% -Dmaven.test.skip=true
echo done.
pause