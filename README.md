Purfect Train - Meow
=============

Tested heirachy of classes to encapsulate different types of train carriages; used in an implementation of a train building gui.

##Part One
###Task One
**Create the _object classes_ and the heirachy. _Create the `asgn2Exceptions` first._**

####Tester
* Tests for `RollingStockTests` fitting the specification

####Coder
* Train carriage heirachy of classes in `asgn2RollingStock`
    - RollingStock
         - FreightCar
         - Locomotive
         - PassengerCar

###Task Two
**Implement the `DepartingTrain` class and the tests for it, `TrainTests`.**

####Tester
* Tests for `TrainTests` fitting the specification

####Coder
* Class `DepartingTrain`  
Tracks the configuration of the carriages and passengers, _following a number of requirements & specifications in the assignment doc & javadoc_.
    - One locomotive. Must be the **first carriage**. Has haulage power.
    - Zero or more passenger cars. Must be **after locomotive** and **before freight cars**. Has a number of passengers.
    - Zero or more freight cars. Must be **after passenger cars**.
    - _More requirements are found in the javadoc..._

##Part Two - _TODO_
