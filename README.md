# JuniorCatanJava
An emulation of the junior version of the Catan game - done through Java!

Java Version: jdk11.0.12

The junit library is required for running the the test scripts.

When playing the game, the player will be presented with a list of options that looks like:

1.  Build
2.  Trade
3.  View Resources
...
...
6.  End Turn

Simply enter the number corresponding to your desired action and the Game will take care of the rest.
When you make a choice, you will be presented with a your next list of options. Make sure to look above this list
as sometimes additional information will also be displayed above this list.

(NOTE: Whenever you are presented with a list of options, only enter a number corresponding to the action you
want to take. Any numbers outside the list of options will prompt you to try again.)

The Game board looks like this:

	                 .               6               8               .
	               .   .           .   .           .   .           .   .
	             .       .       +       +       +       +       .       .
	           .           .   .           .   .           .   .           .
	         .               r               7               o               .
	         .       B       .       E       .       G       .       J       .
	         .      "1"      R      "2"      +      "1"      O      "2"      .
	         .    Cutlass    .     Wood      .     Goats     .    Molasses   .
	         2               4              28              10               b
	       .   .           .   .           .   .           .   .           .   .
	     +       +       +       +       +       +       +       +       B       +
	   .           .   .           .   .           .   .           .   .           .
	 1               3              27       S      29              11              13
	 .       A       .       D       .    Spooky     .       I       .       L       .
	 +      "3"      +      "5"      +    Island     +      "3"      +      "5"      +
	 .      Wood     .     Gold      .      33       .     Gold      .     Goats     .
	26              24              32              30              16              14
	   .           .   .           .   .           .   .           .   .           .
	     +       O       +       +       +       +       +       +       R       +
	       .   .           .   .           .   .           .   .           .   .
	         o              23              31              17               r
	         .       C       .       F       .       H       .       K       .
	         .      "4"      B      "1"      +      "2"      +      "4"      .
	         .    Cutlass    .     Wood      .     Goats     .    Molasses   .
	         .               b              20              18               .
	           .           .   .           .   .           .   .           .
	             .       .       +       +       +       +       .       .
	               .   .           .   .           .   .           .   .
	                 .               21              19              .
                   
		   
For a nicer picture of the board, see the board.png image in the Diagrams folder.


Lair sites are represented by the numbers 1 - 33.
Ship sites are represented by the '+' symbol. 

Note in the game when you view the board, you will also be given a list of
the player's ships labeled as " 1 - 2 " and so on. This means that player owns the ship site between lair 
site 1 and lair site 2.

When a player builds on a site a letter corresponding to the players colour will appear on that site:
Lairs are represented by lowercase letters and ships are represented by upper case letters.

The rules of the game are in the top level directory of the project.

We hope you are ready to Trade, Build, and Settle!
