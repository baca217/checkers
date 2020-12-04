Object-Oriented Project 6 Final Project (Checkers)  
By: Elmer Baca Holguin and Timothy Euken
=======================
This project is meant our knowledge and understanding of object oriented topics by applying some patterns to our own project.
This project used a game state pattern to keep track of tile pieces, aggregation for tracking pieces on each tile, and a composition
relationship for 64 tiles that the CheckersApp class needs in order to work.

How to use?
------------------
Main function class can be found under  
zookeeper-homework1\src\main\java\com\ZookeeperHomework1Application
By running the main function the program will ask for a number of days for which the ZooEmployees  
will do their tasks for. That's the only input the program needs. 
All object files can be found under zookeeper-homework1\src\main\java\com\company\classes

Assumptions
------------------
We only created two animal objects, since the output screen becomes really jumbled with more animals that we add. The two animals are a kangaroo and elephant who eat there food differently than one another. This is done to show how the strategy pattern is done. Kangaroo munches on their food while elephants eat their food whole. We also assumed that the ZooAnnouncer is told about tasks as they occur, so he announces them as they are done. We also assumed that the ZooClock kept track of the days, since it made sense that the employees get their time information from the clock.

Finding Homework Requirements
------------------
*The animal strategy pattern can be found in the "Animal" superclass. It's how the animals eat and it's handled by the constructor. Actual usage can be found in the Marsupial, Pachyderm, Canine, and Feline classes.

*The new ZooAnnouncer class can be found under the folder zoo-extension\src\main\java\com\company\classes. Their observe patterns can be seen with the property change method. They announce all tasks related to animals, done by the ZooKeeper, and also when dinner or lunch is served by the ZooFoodEmployee

*ZooClock can also be found under folder zoo-extension\src\main\java\com\company\classes which is observed by all ZooEmployees. ZooClock is used to keep track of the hours and the days.

*ZooFoodEmployee can be found under folder zoo-extension\src\main\java\com\company\classes which is observed by the ZooAnnouncer. They have there own schedule they follow.

*All UML diagrams can be found under zoo-extension\UML Diagrams\
