# Texas-Holdem
## T03 G08, CPSC 233, WINTER 2019
### Current Version: 1.demoInteractive
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
To compile all java files in a package run the following command from the base directory:
```
javac [].*.java
```
where the each package name replaces the brackets.
To run the game execute the following command from the base directory:
```
java [].[]
```
where the brackets are the package and class respectively. The GUI version of the game is run through the GUI class in the gui package while the text-based is run through the TBGame class in the game package.

To compile and run the JUnit test cases, GameTest and HandTest, download the latest junit and hamcrest jar files form the JUnit 4 repository (https://github.com/junit-team/junit4) and place a copy of each in the respective folders of the test files. Each case must first be compiled in the command line with:
```
javac -cp .:junit-[].jar:hamcrest-core-[].jar *.java
```
where the version of each file replaces the brackets. Once compiled, the tests are run with:
```
java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore []
```
where the name of the class replaces the brackets.

The PlayerTest can be compiled and run normally without JUnit.

TESTING NOTICE: Due to the nature of poker as a game of chance it is not possible to thoroughly test every scenario. Compounding the millions of hand properities with up to 10 players innumerable ways a game can go through give a small idea of the scale of possibles. As such, the test files handle a good number of logic cases in specific areas, but it should be expected that a good number of bugs will exist in scenarios that could not be accounted for.

COPYRIGHT NOTICE: The music used in this game is valid under fair dealing exceptions according to the Copyright Act of Canada secion 29.4 due to its use for educational purposes. Marty Robbins *Gunfighter Ballads and Trail Songs* 1999 reissue is owned by Sony Music Entertainment Inc..
