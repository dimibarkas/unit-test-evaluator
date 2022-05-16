# Unit-Test Evaluator

Bei dem Unit-Test-Evaluator handelt es sich um eine interaktive web-basierte Programmierumgebung die dem Benutzer das Verfassen von Java-Unit-Tests ermöglicht.

Der Benutzer erhält eine Übersicht über den Anteil der abgedeckten Code-Zeilen in einer Fortschrittsanzeige.

Nach jeder Ausführung der Test-Klasse bekommt der Benutzer Feedback in Form eines Videos.

### Benutzersicht

Der Benutzer bekommt die Möglichkeit sich aus einer Liste von Aufgaben eine auszusuchen, die er bearbeiten möchte.

Er kann die Aufgabe bearbeiten und diese dann einreichen, diese wird verarbeitet und darauf hin wird Feedback basierend 
auf die Bearbeitung zurückgeliefert.

Das Tool liefert formatives Feedback, um den Benutzer mit Informationen zu versorgen, um die zugrundeliegende Aufgabe abzuschließen.

### Erhebung der Coverage-Daten

Das Tool sammelt die Daten über die zwei folgenden Metriken:

* Code-Coverage mit [J-Unit 5](https://junit.org/junit5/)
* Mutation-Coverage mit [Pitest](https://pitest.org)


### Lokale Ausführung

Um die Anwendung lokal auszuführen wird eine Postgres-Instanz benötigt diese kann mithilfe von
Docker gestartet werden.

```shell
docker-compose up -d
```


### Zweck der Anwendung

Das Tool wurde im Rahmen einer Bachelorarbeit entwickelt, um die Wirksamkeit von Videofeedback in der Lehre hinsichtlich
der Lernmotivation der Lernenden zu untersuchen.

