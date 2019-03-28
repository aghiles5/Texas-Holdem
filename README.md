# Texas-Holdem
## T03 G08, CPSC 233, WINTER 2019
### Current Version: 0.demo3
### Repository: https://github.com/aghiles5/Texas-Holdem

This program requires a system running at least JDK 1.8.

The following folders, along with all containted files, must be downloaded in the same directory and all contained java files must be compiled:
```
cards
game
gui
images
players
```
To run the GUI version of the game run GUI in the gui folder. For the text-based version, run TBGame in the game folder.

To run the JUnit test cases, GameTest and HandTest, download the latest junit and hamcrest jar files form the JUnit 4 repository (https://github.com/junit-team/junit4) and place a copy of each in the respective folders of the test files. Each case must first be compiled in the command line with:
```
javac -cp .:junit-<version>.jar:hamcrest-core-<hamcrest>.jar *.java
```
where the version of each file replaces the <> brackets. Once compiled, the tests are run with:
```
java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore <class name>
```
where the name of the class replaces the <> brackets.

The PlayerTest can be compiled and run normally without JUnit.
