@echo off
rem provided by Generator
rem params 
rem make sure that repositoryId exists in your maven-settings file.
rem echo %~dp0
echo.

cd %~dp0
cd..

rem echo %cd%
echo call mvn clean install -Dmaven.test.skip=true
call mvn clean install -Dmaven.test.skip=true
echo done.
pause