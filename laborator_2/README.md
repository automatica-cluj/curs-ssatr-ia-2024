# Laborator 2 - JDBC

## Scop 

Scopul acestui laborator este de a intelege modul de lucru cu baza de date relationale utilizand libraria Java JDBC. 

## Instalare unelte laborator

Pentru rularea aplicatiei de laborator este necesara instalarea serverului de baze de date MySQL Community Edition.  

Pentru crearea si editarea bazelor de date este recomandata instalarea MySQL Workbench. 

Ambele componente de mai sus pot fi instalare utilizand instalerul de aici: https://dev.mysql.com/downloads/mysql/

## Exercitiul 1

Sa se testeze si sa se valideze conectivitatea Java -> server MySQL prin rularea aplicatei TestConnection. 

ATENTIE: inainte de rulare sa se verifice si sa se modifice daca este cazul parola si utilizatorul MySQL de pe linia: Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flights2","root","root");

## Exercitiul 2

Sa se creeze o baza de date flights.

Sa se creeze cele doua tabele necesare petru rularea aplicatiei utilizand instructiunile cerate de mai jos:

CREATE TABLE `flights` (
  `FLIGHTNUMBER` varchar(30) NOT NULL,
  `NOOFSEATS` int DEFAULT NULL,
  `DEPARTUREDATE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`FLIGHTNUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reservations` (
  `idreservations` int NOT NULL AUTO_INCREMENT,
  `flightnumber` varchar(45) DEFAULT NULL,
  `nooftickets` int DEFAULT NULL,
  PRIMARY KEY (`idreservations`),
  KEY `flightnumber_idx` (`flightnumber`),
  CONSTRAINT `flightnumber` FOREIGN KEY (`flightnumber`) REFERENCES `flights` (`FLIGHTNUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

              
## Exercitiul 3

Sa se ruleze si sa se testeze aplicatia de rezervari de bilete prin lansarea in executie a clasei MainApp. 

ATENTIE: Inainte de rulare sa se modifice daca este cazul parola si utilizatorul MySQL din clasa DBAccess.

## Exercitiul 3 

Sa se implementeze corect tab-ul Reservations. In acest moment acest tab afiseaza continutul tabelei flights, ceea ce nu este corect. 

## Exercitiul 4

Sa se adauge un nou functionalitate in aplicatie ce permite adaugarea de utilizatori in sistem (aceasta presupune adaugarea unui nou tab in interfata grafica, adaugarea componentelor service si repository, precum si crearea unei tabele noi in baza de date).

In proiect gasiti implementarea partiala a acestei functionalitati (A se vedea clasa DBAccess si metoda insertUser). Puteti sa va folositi de aceasta implementare pentru finalizarea exercitiului sau sa realizati de la zero o implementare noua cu o structura a tabelului users diferita. ATENTIE - nu uitati sa creati tabela users.

## Exercitiul 5

Sa se adauge un nou tab in aplicatie ce permite vizualizarea utilizatorilor sub forma tabelara (adaugati in exercitiul 3).

## Exercitiul 6

In acest moment functia de rezervare realizeaza rezervarea unui numar de bilete pentru un anumit zbor. Sa se modifice functia de reservare bilete de avion in asa fel incat aceasta sa se realizeze pentru un zbors si un anumit utilizator (fara a specifica numarul de bilete - se presupune rezervarea unui singur bilet). 