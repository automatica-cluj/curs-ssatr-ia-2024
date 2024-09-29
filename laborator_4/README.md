# Laborator 4 - RabbitMQ

## Scop 

Scopul acestui laborator este de a intelege modul de lucru cu protocolul si componentele RabbitMQ in vederea implementarii unor aplicatii distribuite. In acest laborator se va implementa o solutie ce simuleaza o aplicatie de comanda taxi. Aplicatia contine doua componente: componenta dedicata clientului ce permite comanda unui taxi si componenta dedicata taximetristului ce permite vizualizarea si acceptarea comenzilor.  Pentru implementarea Java se va utiliza libraria amqp-client ce contine clientul RabbitMQ necesar pentru a comunica din Java cu un broker AMQP (RabbitMQ). 

## Instalare unelte laborator

Pentru rularea aplicatiei de laborator este necesara instalarea unui broker de mesaje RabbitMQ(https://www.rabbitmq.com/download.html) 

Pentri verificarea instalarii si vizualizarea cozilor de mesaje se poate utiliza interfata de administrare web http://localhost:15672/ (guest/guest)

## Exercitiul 1

Sa se testeze mecanismul de trimitere si primire mesaje RabbitMQ utilizand aplicatia de laborator din pachetul 'sample'. Ordinea de executie a aplicatiilor este urmatoarea: RabbitMQSender, RabbitMQReceiver. Aceste aplicatii vor face schim de mesaje prin intermediul brokerului de mesaje si coada cu numele 'bookingrequests'. Pentru a verifica starea cozii de mesaje 'bookingrequests' se poate utiliza interfata de administrare web a brokerului RabbitMQ.

## Exercitiul 2

Sa se realize si sa se verifice modul de functionare a aplicatiei de comanda taxi. Se va lansa in executie aplicatia 'Test'.

## Exercitiul 3

Se se implementeze in cadrul aplicatiei TaxiDriverApp un mecansim prin care sa se calculeze automat pretul unei curse in functie de durata cursei. Pentru a calcula durata se va utiliza timpul de la preluare comenzii pana la finalizarea comenzii si se va inmulti cu o rata orara. Pretul se va afisa in consola in momentul in care soferul finalizeaza cursa. Alternative se poate afisa in interfata grafica in cadrul unei componente de tip Label sau TextField.

## Exercitiul 4 

Sa se implementeze in cadrul aplicatiei TaxiClientApp un mecanism prin care sa se permita clientului sa anuleze o comanda. In momentul in care clientul anuleaza o comanda aceasta va disparea din lista de comenzi disponibile.
