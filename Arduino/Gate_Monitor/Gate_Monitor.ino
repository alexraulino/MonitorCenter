#include <SoftwareSerial.h>  
int ledPin = 13;
int inPinClose = 3;
int inPinOpen = 2;
int valClose = 0;
int valOpen = 0;
int stateGate = 0; 
int stateGateOld = 0; 
SoftwareSerial mySerial(10, 11); // RX, TX 
const int CLOSEGATE = 0;
const int OPENGATE = 1;

void setup() {
  mySerial.begin(9600);
  pinMode(ledPin, OUTPUT);
  pinMode(inPinClose, INPUT);
  pinMode(inPinOpen, INPUT);
  digitalWrite(ledPin, LOW);
}

void loop(){
  valClose = digitalRead(inPinClose);
  valOpen = digitalRead(inPinOpen);  
 
  
 if ((valClose == LOW) && (stateGate == OPENGATE)){
   stateGate = CLOSEGATE;
   digitalWrite(ledPin, LOW);
 }

   if ((valOpen == LOW) && (stateGate == CLOSEGATE)){
     stateGate = OPENGATE;
     digitalWrite(ledPin, HIGH);
   }    

  if (stateGateOld != stateGate){
    mySerial.write(stateGate);
  }
 stateGateOld = stateGate;
}
