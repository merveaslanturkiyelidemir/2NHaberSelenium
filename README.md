# 2N News Test Otomasyon Projesi

## Genel Bakış
Bu proje, 2N News websitesi için Selenium WebDriver ve Cucumber kullanılarak oluşturulmuş bir test otomasyon çerçevesidir. Page Object Model tasarım desenini takip eder ve website navigasyonu, arama fonksiyonu ve form etkileşimleri için kapsamlı test senaryoları içerir.

## Teknoloji Altyapısı
- Java 17
- Maven
- Selenium WebDriver
- Cucumber
- JUnit 
- Locator yönetimi için JSON

## Temel Bağımlılıklar
```xml
<dependencies>
    <!-- Selenium WebDriver -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.26.0</version>
    </dependency>

    <!-- Cucumber -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.20.1</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-junit</artifactId>
        <version>7.20.1</version>
    </dependency>

    <!-- JUnit -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.11.3</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
    </dependency>

    <!-- Reporting -->
    <dependency>
        <groupId>net.masterthought</groupId>
        <artifactId>cucumber-reporting</artifactId>
        <version>5.7.7</version>
    </dependency>

    <!-- HTML Parsing -->
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.16.1</version>
    </dependency>

    <!-- JSON Processing -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20231013</version>
    </dependency>
```

## Proje Yapısı
```
twonnewstestautomation/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/twonnews/testautomation/
│   │           ├── helper/
│   │           │   ├── ElementHelper.java
│   │           │   └── JsonReader.java
│   │           ├── manager/
│   │           │   └── DriverManager.java
│   │           └── model/
│   │               └── MenuItem.java
│   └── test/
│       ├── java/
│       │   └── com/twonnews/testautomation/
│       │       ├── runner/
│       │       │   └── TestRunner.java
│       │       └── steps/
│       │       │   └── SampleSteps.java
│       │       └── config/
│       │           └── TestConfig.java
│       └── resources/
│           ├── features/
│           │   └── 2NHome.feature
│           └── locator/
│               └── locators.json
│           └── config/
│               └── config.properties
```

## Ana Özellikler
- **Page Object Model**: Sürdürülebilir test otomasyonu için sağlam bir page object model uygular
- **Merkezi Locator Yönetimi**: Kolay bakım için JSON tabanlı locator depolama kullanır
- **Kapsamlı Raporlama**: Detaylı Cucumber raporları oluşturur
- **Dinamik Eleman İşleme**: Doğru bekleme mekanizmaları ile sağlam eleman etkileşimi
- **Esnek Navigasyon Testi**: Tüm navigasyon menü öğelerinin otomatik testi
- **Hata Yönetimi**: Detaylı hata raporlama ve kurtarma mekanizmaları

## Testleri Çalıştırma
Testleri Maven kullanarak çalıştırın:
```bash
mvn clean test
```

Raporları oluşturun:
```bash
mvn verify
```

## Rapor Konumları
- Cucumber JSON Raporu: `target/testreport/cucumber-json/cucumber.json`
- HTML Raporu: `target/testreport/cucumber-reports/Cucumber-Report.html`
- JUnit Raporu: `target/testreport/cucumber-reports/Cucumber-junit.xml`

## Konfigürasyon
- ChromeDriver varsayılan WebDriver olarak kullanılır
- Test zaman aşımı varsayılan olarak 10 saniye olarak ayarlanmıştır
- Test hatalarında ekran görüntüleri alınır
- Raporlar hem HTML hem de JSON formatında oluşturulur

## Uygulanan En İyi Uygulamalar
- Thread-safe WebDriver yönetimi
- Güvenilir eleman etkileşimi için açık beklemeler
- Kapsamlı hata yönetimi ve loglama
- Temiz kod prensipleri ve uygun dokümantasyon
- Ortak işlemler için yeniden kullanılabilir yardımcı yöntemler
