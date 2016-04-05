@echo off

REM =====================================
REM Batch for starting Tuxland on Windows
REM =====================================

cls

start java -classpath "%CLASSPATH%";lib\Tuxland.jar;lib\Joystick.jar darken.games.tuxland.Tuxland
