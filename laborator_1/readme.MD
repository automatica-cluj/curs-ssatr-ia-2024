# Implement the following exercises

## Objectives 

Main objectives of this lab are:
- Understand how to create aplications composed of multiple clasesse using composition, aggregation and inheritance
- Understand how to read and interpret an UML class diagram
- Create a complex application which integrate external library for generating qr codes

In this lab you will create small Java applications by using as reference an UML class diagram. Please consider following general notes before start implementing:
- toString() method when required will return a string like *ClassName{attributeName1=attributeValue1,...,attributeNamen=attributeValuen}*.
- add any constructors, getter, setters or other methods you consider are required even if are not explicitly presented in the diagrams.
- Do not reuse (import) the same class in multiple exercises. **Each exercise is standalone and should be resolved in its package.**

## Lab instructions

For all bellow exercises:
1. Create Java application based on the provided UML class diagram
2. Create main method and create some objects to demonstrate the functionality 
3. Create a minimal unit test for testing the behavior of the created class.

## Exercise 1
![Exercise 1 image](docs/Ex1.jpg)

Given the UML class diagram above, implement the corresponding Java program.


## Exercise 2
![Exercise 2 image](docs/Ex2.jpg)

Given the UML class diagram above, implement the corresponding Java program.
The maximum battery level is 100.
Implement the logic of charging the devices. The 3 devices have different charging speeds.
E.g. A smartwatch is charged to 20% in 10 min.

## Exercise 3 
![Exercise 3 image](docs/Ex3.jpg)

Given the UML class diagram above, implement the corresponding Java program.

## Exercise 4

Load project exemple-curs-3-app and studty structue the eventticketingsystem application. 
1. Create a folder on your disk and update TicketsManager line to point to your directory: `ImageIO.write(bi, "png", new File("C:\\Tickets\\ticket_"+ticketOwner.getName()+".png"));`
2. Execute main method from Main class and generate 3 tickets
3. Update main method and test checking method

![Exercise 4 image](docs/tickets_app2.jpg)

Above picture presentes a high level concept of an event ticketing system. 
1. Create a class diagram to model minimal functionalities of event ticketing system. 
2. Create the minimal application based on the class diagram you have created.
3. Use eventticketsystem example from example-curs-3-apps in order to implement the mechanism for generating qr codes for tickets and validate and chekin tickets.

**NOTE 1** Bofore start this exercise run and test eventticketsystem application which exemplify the process of encoding and decoding a qr code. 

**NOTE 2** Please note that pom.xml file of this project must be updated to add required dependencies to encode and decode qrcodes. Check pom.xml file from example-curs-3-apps project (you will need to add com.google.zxing related dependencies). 
