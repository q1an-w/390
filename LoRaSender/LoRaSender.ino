// base code from Sandeep Mistry with adjustments

#include <SPI.h>
#include <LoRa.h>

int counter = 0;

void setup() {
  Serial.begin(9600);
  while (!Serial);

  Serial.println("Initiating LoRa Sender");

  if (!LoRa.begin(433E6)) {
    Serial.println("Module failed to initialize");
    while (1);
  }
}

void loop() {
  Serial.print("Packet Nuber: ");
  Serial.println(counter);

  // send packet
  LoRa.beginPacket();
  LoRa.print("flow sensor activated");
  LoRa.print(counter);
  LoRa.endPacket();

  // Received Signal Strength Indicator - negative value indicating signal strength
  counter++;

  delay(5000);
}