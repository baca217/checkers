Object-Oriented Project 6 Final Project (Checkers)  
By: Elmer Baca Holguin and Timothy Euken
=======================
This project is meant our knowledge and understanding of object oriented topics by applying some patterns to our own project.  
This project used a game state pattern to keep track of tile pieces, aggregation for tracking pieces on each tile, and a composition
relationship for 64 tiles that the CheckersApp class needs in order to work.

Video Demonstratin and Document
------------------
Document: https://docs.google.com/document/d/1JiNVNZUEqjJc7C2XHqHgQ5pG9Xcgt_PfUs5oGSfMMIg/edit?usp=sharing  
Video Demo: https://drive.google.com/file/d/1Ia7HV0hPXNEgEREihZXKlDfwBeCizSDQ/view?usp=sharing

How to use?
------------------
Main function class can be found under checkers/src/checkersGame.java  
By running the main function the program will open a dialog for inputting the names of the players.  
Once that has been done a checkers board will appear where the two players can play a game of checkers.  
Object files for the checkers game can be found under checkers/src/checkers.  
Object files for the end game screen can be found under checkers/src/endGameScreen.  
Object files for the login screen can be found under checkers/src/login.

Getting and using JavaFX
-------------------
1. Download JavaFX 11 from here : https://gluonhq.com/products/javafx/  
2. place it in a directory that you know  
3. In the file project in intellij, click the "File" tab at the top left corner  
4. Click on the "Project Structure" tab  
5. On the "Libraries" tab, click the "+" sign  
6. Click "Java"
7. enter the directory where the JavaFX library is located at. EX: C:\Users\User\Documents\JDKs\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib  
8. press okay
9. JavaFX should be ready to use

API
------------------
link: https://github.com/tidaeu/checkers-api-OOP
1. Download repo
2. Open in intellij by selecting the pom.xml and "Import as Project"
3. Install docker and pull Postgres (https://hub.docker.com/_/postgres)
4. Confirm Postgres is pointing to port 5432
5. Create Database named "checkers_platform"
6. Start API in IntellJ. API will be pointing to serverport 9999
7. All configurable settings can be found in resources/application.properties

Resources
------------------
Youtube tutorial playlist for javaFX: https://youtu.be/FLkOX4Eez6o  
Youtube creator github (notice no java projects there): https://github.com/thenewboston-developers  
Wikipedia page for checkers (used “rules” section for behavior): https://simple.wikipedia.org/wiki/Checkers  
