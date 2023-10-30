#include <SPI.h>
#include <LoRa.h>

const int flowPin = 3;  // Define the pin to which the sensor is connected
volatile int pulseCount; // Variable to keep track of the pulse count

int counter = 0;

void setup() {
  Serial.begin(9600);
  delay(1000);
  clearSerialMonitor();
  while (!Serial);

  Serial.println("LoRa Sender");

  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }
  else if(LoRa.begin(433E6)) {
    Serial.println("Starting LoRa success!");
  }

  LoRa.setTxPower(16);
  
  pinMode(flowPin, INPUT); // Set the pin as an input
  attachInterrupt(digitalPinToInterrupt(flowPin), pulseCounter, FALLING); // Attach an interrupt to the pin
  pulseCount = 0; // Initialize pulse count
}

void clearSerialMonitor() {
  for(int i = 0; i < 50; i++) { // Print 50 empty lines to clear the serial monitor
    Serial.println();
  }
}

int getState() {
  if(pulseCount == 0) {
    return 0;
  }
  else if (pulseCount > 20) {
    return 3;
  }
  else if (pulseCount > 10) {
    return 2;
  }
  else return 1;
}

void loop() {
  Serial.print("Sending packet: ");
  Serial.println(counter);

  // send packet
  LoRa.beginPacket();
  LoRa.print(pulseCount);
  LoRa.print(", ");
  LoRa.print(getState());
  LoRa.endPacket();

  counter++;
  pulseCount = 0; // Reset the pulse count

  delay(500);
}

void pulseCounter() {
  pulseCount++; // Increment the pulse count
}
