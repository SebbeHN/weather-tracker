# 🌤️ Weather Tracker

Micronaut-baserad applikation som hämtar aktuellt väder från OpenWeatherMap och sparar det i en PostgreSQL-databas.

---

## 🚀 Funktioner

- 🔗 Hämtar väderdata från OpenWeatherMap
- ☁️ Stöd för valfri stad via query-param
- 🗃️ Sparar väderhistorik i PostgreSQL
- ⚡ REST-API via Micronaut
- ✨ Byggt med Java 21 och Gradle

---

## 🔧 Teknisk stack

| Teknik         | Beskrivning                          |
|----------------|---------------------------------------|
| Micronaut      | Backend-ramverk (JVM, IoC, REST)     |
| PostgreSQL     | Databas                              |
| Gradle         | Byggverktyg                          |
| OpenWeatherMap | Extern vädertjänst                   |
| Java 21        | Programmeringsspråk                  |

---

## 📦 Installation

### 1. Klona projektet

```bash
git clone https://github.com/SebbeHN/weather-tracker.git
cd weather-tracker
```

### 2. Lägg till en lokal `application.yml`

Skapa filen `src/main/resources/application.yml` med detta innehåll:

```yaml
weather:
  api:
    base-url: https://api.openweathermap.org/data/2.5
    key: DIN_API_NYCKEL
    default-city: Stockholm
    units: metric

datasources:
  default:
    url: jdbc:postgresql://localhost:5432/weatherdb
    username: ditt_användarnamn
    password: ditt_lösenord
    driverClassName: org.postgresql.Driver

jpa:
  default:
    properties:
      hibernate:
        hbm2ddl:
          auto: update
        show_sql: true
```

☝️ Din `application.yml` är med i `.gitignore` och ska **inte** committas.

---

### 3. Starta applikationen

```bash
./gradlew run
```

---

## 🌐 Användning

### Hämta väder för specifik stad

```bash
curl "http://localhost:8080/api/weather?city=Stockholm"
```

### Hämta väder för förvald stad

```bash
curl "http://localhost:8080/api/weather"
```

---

## 🧱 Datamodell

- `WeatherEntry`: innehåller `city`, `temperature`, `humidity`, `description`, `timestamp`
- Sparas i `weather_entries`-tabellen

---

## 🛡️ Säkerhet

- `application.yml` ignoreras via `.gitignore`
- Inga API-nycklar eller lösenord bör läggas upp publikt

---

## 🔮 Möjliga förbättringar

- 🔔 Skicka notiser via AWS SNS vid extremväder
- 🧾 Lägg till `/api/weather/history`
- 💾 Exportera historik till CSV eller lagra på S3
- 🧪 Enhetstester med JUnit

---

## 🧑‍💻 Utvecklare

Sebastian Holmberg  
📫 https://github.com/SebbeHN 

---

## 📝 Licens

MIT License – Fri att använda och modifiera
