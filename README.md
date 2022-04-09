# Unit-Test Evaluator

Bei dem Unit-Test-Evaluator handelt es sich um eine interaktive Online-Anwendung die den Benutzer beim Verfassen von Java-Unit-Tests
unterstützt.

Das Tool wurde im Rahmen einer Bachelorarbeit entwickelt, um die Wirksamkeit von Videofeedback in der Lehre hinsichtlich
der Lernmotivation der Lernenden zu analysieren.


### Benutzersicht

Der Benutzer bekommt die Möglichkeit sich aus einer Liste von Aufgaben eine auszusuchen, die er bearbeiten möchte.

Er kann die Aufgabe bearbeiten und diese dann einreichen, diese wird verarbeitet und darauf hin wird Feedback basierend 
auf die Bearbeitung zurückgeliefert.

## Lokale Ausführung

Um die Anwendung lokal auszuführen wird eine Postgres-Instanz benötigt diese kann mithilfe von
Docker gestartet werden.

```shell
docker-compose up -d
```
