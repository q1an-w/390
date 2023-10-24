#include <SPI.h>
#include <LoRa.h>

const int flowPin = 3;  // Define the pin to which the sensor is connected
volatile int pulseCount; // Variable to keep track of the pulse count

int counter = 0;

void setup() {
  Serial.begin(9600);
  while (!Serial);

  Serial.println("LoRa Sender");

  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }

  LoRa.setTxPower(20);
  
  pinMode(flowPin, INPUT); // Set the pin as an input
  attachInterrupt(digitalPinToInterrupt(flowPin), pulseCounter, FALLING); // Attach an interrupt to the pin
  pulseCount = 0; // Initialize pulse count
}

String getState() {
  if(pulseCount == 0) {
    return "No Flow";
  }
  else if (pulseCount > 20) {
    return "High";
  }
  else if (pulseCount > 10) {
    return "Medium";
  }
  else return "Low";
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

  delay(10000);
}

void pulseCounter() {
  pulseCount++; // Increment the pulse count
}
