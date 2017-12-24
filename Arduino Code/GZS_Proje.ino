#include <SoftwareSerial.h>
#include <LiquidCrystal.h>

LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

char data = 0;
int ledpin = 22;
int sensorPin = A15;
int sensorValue;
boolean bagli = false;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600); //sets serial port for communication
  pinMode(ledpin, OUTPUT);  //Sets digital pin 13 as output pin
  //Serial.print("baglandi");

   // set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  // Print a message to the LCD.
  lcdyaz();
  lcd.setCursor(0,1);
  lcd.print("Baglaniliyor...");
}

void loop() {
  
    // Işık sensöründen gelen veriyi oku.
    sensorValue = analogRead(sensorPin); // read the value from the sensor
    delay(100); 
    if(sensorValue>50){
      Serial.print('r');
      delay(100);
    }else{
      Serial.print('s');
      delay(100);
    }

    // Seri porttan gelen sinyali al
    if(Serial.available() > 0)      
    {
      data = Serial.read();       
      Serial.print(data);          
      Serial.print("\n");
      if(data == '1'){
        digitalWrite(ledpin, HIGH);
        bagli = true;// Checks whether value of data is equal to 1
      }                         //If value is 1 then LED turns ON
      else if(data == '0'){
        digitalWrite(ledpin, LOW);
        bagli = false;//  Checks whether value of data is equal to 0
      }          
    }

    // LCD YAZ
    if(bagli){
      lcd.clear();
      lcdyaz();
      lcd.setCursor(0,1);
      lcd.print("BAGLANDI");
    }else{
      lcd.clear();
      lcdyaz();
      lcd.setCursor(0,1);
      lcd.print("BAGLI DEGIL");
    }
  
}

// LCD'yi başlangıç poziyonuna çek
void lcdyaz(){
  lcd.setCursor(0,0);
  lcd.print("Hosgeldiniz");
  
}

