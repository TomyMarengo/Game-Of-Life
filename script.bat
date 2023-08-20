@echo off

javac -d . GameOfLife.java src/WriteFiles.java src/Rule.java src/Coordinates.java
java WriteFiles
java GameOfLife
del *.class *.txt
cd ../GameOfLifeAnimation/src
python animation2d.py
python animation3d.py

echo Script completado.
pause