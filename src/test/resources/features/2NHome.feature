Feature: 2NHome

  Background:
    * "https://2nhaber.com" sayfasina gidilir

  Scenario: Ziyaretçi 2NHome anasayfasında yer alan bütün navbar elementlerine tıklar
    * Tum navbar elementleri kontrol edilir
    * 3 saniye beklenir

  Scenario: Arama Fonksiyonu Kontrolü
    * "AramaButonu" objesine tiklanir
    * "AramaCubugu" objesine "İstanbul" degeri yazilir
    * Enter butonuna basilir
    # 8.Yerine 3.haber basligi kontrol edilmesi için locator içinden tek bir parametre değiştirilmesi yeterlidir.
    * "8NoHaberBasligi" objesinin iceriginde "İletişim" degeri yazdigi kontrol edilir
    * "8NoHaberBasligi" objesi ekranda gorulene kadar sayfa kaydirilir
    * "8NoHaberBasligi" objesine tiklanir
    * 3 saniye beklenir