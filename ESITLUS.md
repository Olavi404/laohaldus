# Esitluse materjalid – Mini laohaldusrakendus

## 1. Tööpäevik

> **NB! Kirjuta see lõik ümber oma sõnadega** – see peab kirjeldama sinu enda tööprotsessi.

Alustasin `Product` klassist, sest kõik muu sõltub sellest, milline on üks toode. Seejärel tegin
alamklassid `FoodProduct` ja `ElectronicProduct`, et näidata pärilust ja polümorfismi. Siis lõin
`Inventory` klassi, mis hoiab tooteid ja teeb otsingu, koguse muutmise ja koguväärtuse arvutuse.
Viimasena tegin konsoolimenüü ja lisasin vigase sisendi käsitluse. Kõige keerulisem oli
otsustada, kus andmeid kontrollida – lõpuks panin kontrolli `Product` klassi, et vigane toode ei
saaks üldse tekkida.

## 2. Koodi selgitus – neli OOP põhimõtet

### Kapseldamine
`Product.java` – väljad `name`, `price` ja `quantity` on `private`. Kogust saab muuta ainult
meetoditega `increaseQuantity()` ja `decreaseQuantity()`, mis ei luba negatiivset kogust ega
rohkem eemaldada, kui laos on. Nii ei saa keegi kirjutada `product.quantity = -5`.
Ka `Inventory.getAllProducts()` tagastab nimekirja **koopia**, et väljastpoolt ei saaks lao
sisu otse muuta.

### Pärilus
`FoodProduct extends Product` ja `ElectronicProduct extends Product`. Alamklassid saavad
nime, hinna, koguse ja kõik meetodid ülemklassilt kaasa ning lisavad ainult oma välja
(`bestBefore` või `warrantyMonths`). Ühist koodi ei pea kaks korda kirjutama.

### Polümorfism
`Main.printProducts()` käib läbi `List<Product>` ja kutsub `System.out.println(product)`.
`toString()` kutsub omakorda `getExtraInfo()`, mis on alamklassides erinevalt üle kirjutatud:
toit näitab „Parim enne: …“, elektroonika „Garantii: … kuud“. Muutuja tüüp on `Product`,
aga käivitub objekti **tegeliku** tüübi meetod.

### Abstraktsioon
`Product` on `abstract` klass, millel on abstraktsed meetodid `getCategory()` ja
`getExtraInfo()`. `new Product(...)` ei saa teha – iga toode peab olema konkreetset tüüpi.
Ülemklass määrab, *mida* iga toode oskama peab, alamklass otsustab, *kuidas*.

## 3. Andmestruktuuri põhjendus

Tooted on `LinkedHashMap<String, Product>`-is, võtmeks toote nimi väiketähtedega
(`Inventory.java`).

- **Otsing täpse nime järgi** (`findByName`) – `products.get(nimi)`, ei pea kogu nimekirja
  läbi käima (HashMap leiab võtme järgi kohe).
- **Unikaalsus** – sama võtit ei saa kaks korda olla, seega `addProduct` saab kergesti
  kontrollida, kas toode on juba olemas (`containsKey`).
- **Järjekord** – tavaline `HashMap` ei hoia järjekorda; `LinkedHashMap` jätab meelde
  lisamise järjekorra, nii et nimekiri kuvatakse alati samamoodi.
- **Osaline otsing** (`searchByName`) – käiakse läbi `products.values()` ja kontrollitakse,
  kas nimi sisaldab otsingusõna. Tulemused kogutakse `ArrayList`-i.
- Võrdlus: `ArrayList`-iga peaks nime järgi otsimiseks alati kõik tooted läbi käima.

## 4. Käivitustõend

> Lisa siia ekraanipilt või salvestus, kus on näha **toote lisamine**, **otsing** ja
> **koguväärtuse arvutamine** (menüü valikud 2, 4 ja 5).

![Käivitustõend](kaivitus.png)

## 5. Commit'ide loetelu

```
e9fb210 Lisa abstraktne Product klass ning FoodProduct ja ElectronicProduct
f9cb2ef Lisa Inventory klass toodete haldamiseks
daa18b9 Lisa konsoolimenüü: toodete kuvamine, lisamine ja otsing
d9bd645 Lisa koguse muutmine, koguväärtus ja vigase sisendi käsitlus
```
(+ viimane commit, mis lisas README ja selle faili; täielik nimekiri: `git log --oneline`)
