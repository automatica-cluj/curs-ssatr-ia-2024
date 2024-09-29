# Laborator 3 - MQTT

## Scop 

Scopul acestui laborator este de a intelege modul de lucru cu protocolul si componentele MQTT in vederea implementarii unor aplicatii distribuite. In cazul de fata se va implementa structura simplificata a unui sistem IoT in care senzorii, actuatorii si controlerul vor comunica prin intermediul unui broker de mesaje MQTT. Pentru implementarea Java se va utiliza libraria org.eclipse.paho.client.mqttv3 ce contine clientul MQTT necesar pentru a comunica din Java cu un broker MQTT. 

## Instalare unelte laborator

Pentru rularea aplicatiei de laborator este necesara instalarea unui broker de mesaje MQTT (https://mosquitto.org/download/) 

Pentru testarea si verificarea mesajelor trimise pe topicuri este neoive de un client MQTT (https://mqttx.app/)

## Exercitiul 1

Sa se ruleze aplicatia ce simuleaza un sistem IoT prin lansarea in executie a clasei RunAll.

## Exercitiul 2

Sa se realize diagrama UML de clase a aplicatiei. 

## Exercitiul 3

Sa se adauge un sensor de umiditate si sa se modifice logica algoritmului de control in asa fel incat comanda ON\OFF a actuatorului sa initieze prin indeplinirea simultana a doua conditii: T>x sau H>y => commanda ON si T<x sau H<y => comanda OFF. 

## Exercitiul 4 

Sa se adauge un mecanism de logare a valorilor raportate de catre senori (T si H) intr-o baza de date. Componenta de logare in baza de date va fi implementata ca si program separat si va utiliza mqtt pentru colectarea valorilr T si H.  
