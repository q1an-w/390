#include <SPI.h>
#include <LoRa.h>

void setup() {
  Serial.begin(115200);
  while (!Serial);

  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }
}

void RssiPercent() {
  int rssiValue = LoRa.packetRssi(); // Get RSSI value
  int mappedValue = map(rssiValue, -65, -120, 100, 0); // Map RSSI range to percentage range
  mappedValue = constrain(mappedValue, 0, 100);
  Serial.print(mappedValue);
  Serial.println("% Connection Strength");
}

void loop() {
  int packetSize = LoRa.parsePacket();
  if (packetSize) {
    while (LoRa.available()) {
      Serial.print((char)LoRa.read());
    }
    Serial.print(",");
    RssiPercent();
  }
}
