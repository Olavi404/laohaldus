# Mini laohaldusrakendus

Java konsoolirakendus väikese poe laoseisu haldamiseks (TAK25, Olavi Keerd).

Kasutaja saab:
- näha kõiki tooteid;
- lisada uue toote (toit või elektroonika);
- suurendada või vähendada toote kogust;
- otsida toodet nime järgi;
- näha laos olevate toodete koguväärtust.

Käivitamisel lisatakse lattu 4 näidistoodet (Piim, Leib, Hiir, Laadija).

## Ülesehitus

| Klass | Ülesanne |
|---|---|
| `Product` | Abstraktne ülemklass: nimi, hind, kogus (kapseldatud), koguse muutmine, väärtus |
| `FoodProduct` | Alamklass: lisaks parim-enne kuupäev |
| `ElectronicProduct` | Alamklass: lisaks garantiiaeg kuudes |
| `Inventory` | Hoiab tooteid `LinkedHashMap`-is, otsing, koguse muutmine, koguväärtus |
| `Main` | Konsoolimenüü ja kasutaja sisendi lugemine |

Selgitused OOP põhimõtete ja andmestruktuuri kohta on failis [ESITLUS.md](ESITLUS.md).

## Käivitamine

Vaja on Java 17 või uuemat (JDK).

**IntelliJ IDEA:** ava kaust `laohaldus`, märgi `src` kaust *Sources Root*-iks ja käivita `Main`.

**Käsurealt** (projekti kaustas):

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

Kui täpitähed kuvatakse Windowsi konsoolis valesti, käivita enne `chcp 65001`.
