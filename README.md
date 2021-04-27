# FallstudieServer

@author
Jonas Dietsche, Elias Müller und Laura Frieß

### Allgemeine Informationen
Web Anwendung für einen Vorlesungsplaner

### Zugangsdaten für den Administrator
Email: admin@gmx.de
Passwort: testaccess

### Zugangsdaten für zwei Gmail-Accounts (Dozenten)
Email: dozent1fallstudie@gmail.com
Passwort: dozent1Acc_2021

Email: dozent2fallstudie@gmail.com
Passwort: dozent2Acc_2021

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
- Studenten und Dozenten können vom Administrator registriert werden.

- Workflow für die Vorlesungsplanung *eines* Semesters aus Sicht des Admins:
    - Als Administrator anmelden (Daten siehe oben) und alle nötigen Dozenten registrieren
    - Als Dozenten-Emails bieten sich "dozent1fallstudie@gmail.com" und "dozent2fallstudie@gmail.com" an, da hierfür Email-Accounts bereit stehen (Daten siehe oben)
    - Der Administrator muss einen Vorlesungszeitraum festlegen
    - Planung starten (aufsteigend / absteigend nach Anzahl der Vorlesungsstunden)

- Workflow für die Vorlesungsplanung *eines* Semesters aus Sicht eines Dozenten:
    - Als Dozent bekommt man Zugangsdaten vom Admin zugewiesen, mit denen man sich in der Anwendung anmelden kann.
    - Als Dozent wird man per Mail über den Start der Planungsphase informiert und kann währenddessen
      Vorlesungstermine anlegen, modifizieren und löschen.
    - Fürs Modifizieren muss einfach der Termin im Kalender angeklickt werden. Hierbei können nur eigene (rote) Termine modifiziert werden.
    - Wenn die Planung fertig ist, kann sie über den Button "Planung beenden" abgeschlossen werden. Alternativ endet der Zeitraum nach 2 Tagen.
    - Außerhalb des Planungszeitraumes von 2 Tagen: Dozent kann seine Vorlesungstermine einsehen.

- Studenten können sich in der Webanwendung anmelden und ihren Vorlesungsplan einsehen.

## Änderungen nach Präsentation
***
Die Planung kann nun erneut gestartet werden, ohne dass der Server dafür einen Neustart benötigt.
Die Dozenten müssen dafür alle ihre Planung beendet haben.