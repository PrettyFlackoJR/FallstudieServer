# FallstudieServer

@author
Jonas Dietsche, Elias Müller und Laura Frieß

### Allgemeine Informationen
Web Anwendung für einen Vorlesungsplaner

### Zugangsdaten für den Administrator
Email: admin@gmx.de

Passwort: testaccess


## Installation
***
Zur Nutzung der Web Anwendung:
```
$ git clone https://github.com/PrettyFlackoJR/FallstudieServer.git
$ npm install
```

## Anmerkungen zur Webanwendung
***
- Wir haben die Datenbank extern per Azure gehostet; die Daten sind in der application.properties-Datei gespeichert.
- Für jeden Dozenten muss mindestens eine Vorlesung hinterlegt werden.
- Studenten können vom Administrator registriert werden.

- Workflow für die Vorlesungsplanung *eines* Semesters aus Sicht des Admins:
    - Als Administrator anmelden und alle nötigen Dozenten registrieren
    - Der Administrator muss einen Vorlesungszeitraum festlegen
    - Planung starten (aufsteigend / absteigend nach Anzahl der Vorlesungsstunden)

- Workflow für die Vorlesungsplanung *eines* Semesters aus Sicht eines Dozenten:
    - Als Dozent bekommt man Zugangsdaten vom Admin zugewiesen, mit denen man sich in der Anwendung anmelden kann.
    - Als Dozent wird man per Mail über den Start der Planungsphase informiert und kann währenddessen
      Vorlesungstermine anlegen, modifizieren und löschen.
    - Außerhalb des Planungszeitraumes von 2 Tagen: Dozent kann seine Vorlesungstermine einsehen.

- Studenten können sich in der Webanwendung anmelden und ihren Vorlesungsplan einsehen.