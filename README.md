# TeamSigns

**TeamSigns** ist ein Spigot-1.8-Plugin, das dein Server-Team mithilfe von Köpfen und Schildern visualisiert. Es arbeitet eng mit **PermissionsEx** und einer MySQL-Datenbank zusammen und zeigt automatisch an, welche Spieler in welchem Rang sind.

---

## Features

1. **Automatische Kopf- und Schilderzuweisung**  
   - **Köpfe**: Platziere einen Spielerkopf mit dem `Owner`-Namen des gewünschten Rangs (z.B. `Builder`), und die Position wird im Plugin gespeichert. Danach setzt das Plugin den Kopf auf „MHF_Question“ zurück.  
   - **Schilder**: Platziere ein Schild, schreibe `[TeamSign]` in die erste Zeile und den gewünschten Rang (z.B. `Moderator`) in die zweite Zeile – Position wird gespeichert und der Schildtext anschließend gelöscht.

2. **MySQL-Anbindung**  
   - Das Plugin liest die Spieler-UUIDs direkt aus deiner MySQL-Datenbank, in der PermissionsEx seine Daten speichert. So werden die Spieler automatisch den korrekten Rängen zugewiesen.

3. **Unterstützte Ränge**  
   - YouTuber, Builder, SrBuilder, Supporter, Moderator, Content, Developer, SrModerator, SrContent, SrDeveloper, Administrator, Owner

> Du kannst die Ränge im Code anpassen, falls du eigene Gruppen benutzen willst.

4. **Automatische Aktualisierung**  
- Per Scheduler werden in festgelegten Intervallen (100 Ticks) die Köpfe und Schilder aktualisiert.  
- Falls mehr Köpfe als Spieler vorhanden sind oder niemand im Rang ist, zeigt der Kopf „MHF_Question“ bzw. das Schild „???“.

5. **Berechtigungen**  
- `system.teamsigns`: Erlaubt das Platzieren von Rangköpfen und das Erstellen von `[TeamSign]`-Schildern.

---

## Installation

1. **Spigot 1.8** (oder kompatible Version) verwenden.  
2. **PermissionsEx** installieren, da die Ränge daraus ausgelesen werden.  
3. **TeamSigns.jar** in den `plugins`-Ordner legen.  
4. Server starten – das Plugin erzeugt automatisch den Ordner `TeamSchilder` mit der `config.yml`.

---

## Konfiguration

- In `plugins/TeamSchilder/config.yml` findest du u.a. die folgenden Einträge:
```yaml
MySQL:
 Host: "127.0.0.1"
 Port: 3306
 Datenbank: "mydatabase"
 Benutzername: "root"
 Passwort: "secretpassword"
```

## Nutzung
1. **Kopf platzieren**

- Besorge dir einen Spielerkopf und gib ihm mittels Command (z.B. /give) den Namen eines existierenden Rangs.
- Stelle sicher, dass du die Permission system.teamsigns hast.
- Platziere den Kopf – das Plugin übernimmt den Rest.

2. Schild platzieren

- Stelle sicher, dass du die Permission system.teamsigns hast.
- Platziere ein Schild.
- Erste Zeile: [TeamSign]
- Zweite Zeile: Gewünschter Rang (z.B. Owner)
- Das Plugin speichert Position und Rang, löscht den Text, und kümmert sich um die Aktualisierung.
  
## Automatische Updates

- In regelmäßigen Abständen (Standard: 100 Ticks) werden die Spieler, die in den jeweiligen Rängen sind, aus der MySQL-Datenbank abgefragt.
- Die entsprechenden Köpfe und Schilder werden aktualisiert, um den korrekten Skin bzw. Namen anzuzeigen.
