# What is Escape?
Escape is a family of board games designed for the term project in CS4233: Object-Oriented Analysis & Design. Variations are usually played by two players. Each player starts with a set of pieces on the board, with each piece having a point value and specific movement rules. The player who exits the most points off the board by the end of the game wins. Variations modify the rules in several ways such as the number and type of pieces, valid exit squares, different movement and battle rules, and so on.

Escape has the following:
- variable board dimensions
- variable board location shapes (e.g. squares, hexes, etc.)
- rule variations such as game length, victory conditions, and so on
- different types of pieces with various attributes

The boards are initialized using XML files that are stored in /config. This project focused on maintaining an OO structure to implement iterations of new rules, pieces, and board types.

It's important to note there is no GUI for this game, so it cannot be played as one would think. The board game concept is largely to create a project where OO principles can be applied.

## Why return to a 5 year old project?
It's been a while since I've worked with a Java codebase, so I wanted to refresh what I'd learned in a graduate-level course while at WPI. Coming back to this project, I also made several key improvements that I wish I had done back in 2020:

### Resolved all warnings in the project
- **Eliminated Raw Types Everywhere**
- **Fixed Wildcard Capture Issues**
- **Made Builders Return Properly Typed Objects**
- **Created a Cohesive Generic Hierarchy**

Essentially, I replaced "hope it works at runtime" with "know it works at compile-time" by fully embracing Java's generics system.

### Created a visualization tool for the XML configs
I remember during testing this project in college having headaches trying to visualize what the provided XML boards looked like. Instead of sketching on a whiteboard, I implemented a basic HTML/JS/CSS solution to quickly generate visuals of how both SQUARE & HEX boards were initialized. For HEX boards, I used a library called [honeycomb.js](https://abbekeultjes.nl/honeycomb/) to avoid having to implement the math needed to render hexagons.

<img width="866" height="669" alt="vis" src="https://github.com/user-attachments/assets/2ea38891-6f30-4f80-88cc-66d9f0ac409a" />

### Resolved failing tests
This project was graded based on how many tests your code passed from both a set of master tests and tests that I had to implement. After enforcing type safety throughout the project, this was largely trivial, and would have improved my grade by a few points.

## Assignment PDF
[Escape-4.pdf](https://github.com/user-attachments/files/24247407/Escape-4.pdf)
