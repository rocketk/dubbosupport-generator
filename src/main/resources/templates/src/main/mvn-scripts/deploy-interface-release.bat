@echo off
rem provided by Generator
rem params 
rem make sure that repositoryId exists in your maven-settings file.
set groupId=${groupId}
set artifactId=${artifactId}
set version=${version}
set packaging=jar
set url=${nexusReleaseUrl!}
set repositoryId=${nexusRepoId!}
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