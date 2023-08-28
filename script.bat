@echo off

javac -d . GameOfLife.java src/Writer.java src/Rule.java src/Coordinates.java
java GameOfLife
del *.class

echo Script completado.