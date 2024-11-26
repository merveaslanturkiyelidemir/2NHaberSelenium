Feature: 2NHR

Background:
  * "https://www.2ntech.com.tr/hr" sayfasina gidilir

Scenario: Form doldurulur ve submit edilir
  * "2NTechLogo" objesinin goruldugu kontrol edilir
  * "AdSoyad" objesine "Merve Aslantürkiyeli Demir" degeri yazilir
  * "DogumTarihi" tarih alanina "01021997" degeri girilir
  * 3 saniye beklenir
  * "TCKimlikNo" objesine tiklanir
  * "TCKimlikNo" objesine "19352103420" degeri yazilir
  * "TelNo" objesine "05387197747" degeri yazilir
  * "Mail" objesine "merveaslanturkiyeli@gmail.com" degeri yazilir
  * "MerveAslantürkiyeliDemirCV.pdf" dosyasi "CV" alanina yuklenir
  * "LisansButon" objesine tiklanir
  * "KVKK" checkbox objesinin tikli oldugu kontrol edilir ve tikli degilse tiklenir
  * 3 saniye beklenir
  * "NextStepButon" objesine tiklanir
  * "TestEngineer" objesinin goruldugu kontrol edilir
  * "TestEngineer" objesine tiklanir
  * "Gonder" objesine tiklanir
  * "Tesekkurler" objesinin iceriginde "Form Başarı ile gönderildi. Katıldığınız için teşekkür ederiz." degeri yazdigi kontrol edilir

