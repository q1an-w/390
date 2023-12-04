#include <SPI.h>
#include <LoRa.h>

const int flowPin = 3;
const int level1Pin = 5;
const int level2Pin = 6;
const int level3Pin = 7;

volatile int pulseCount;
double flow;
unsigned long lastTime;
int counter = 0;

void setup() {
  Serial.begin(115200);
  delay(1000);
  clearSerialMonitor();
  while (!Serial);

  Serial.println("LoRa Receiver");

  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  } else {
    Serial.println("Starting LoRa success!");
  }

  LoRa.setTxPower(20);
  
  pinMode(level1Pin, INPUT);
  pinMode(level2Pin, INPUT);
  pinMode(level3Pin, INPUT);
  pinMode(flowPin, INPUT);

  attachInterrupt(digitalPinToInterrupt(flowPin), pulseCounter, FALLING);
  pulseCount = 0;
  lastTime = millis(); // Initialize the last time for flow rate calculation
}

void clearSerialMonitor() {
  for (int i = 0; i < 50; i++) {
    Serial.println();
  }
}

void loop() {
  LoRa.beginPacket();

  unsigned long currentTime = millis();
  unsigned long elapsedTime = currentTime - lastTime;
  lastTime = currentTime;

  detachInterrupt(digitalPinToInterrupt(flowPin)); // Disable interrupt

  // Calculate flow rate based on elapsed time
  // Adjust the constant depending on your sensor specifications
  if (elapsedTime > 0) {
    flow = (pulseCount / 7.5) / (elapsedTime / 1000.0); // Flow rate in units/sec
  } else {
    flow = 0; // Set flow to 0 if elapsedTime is 0
  }

  pulseCount = 0;

  Serial.print("Sending packet: ");
  Serial.println(counter);

  LoRa.print(flow, 2);
  Serial.print(flow, 2);
  LoRa.print(",");
  Serial.print(",");
  LoRa.print(getRateState());
  Serial.print(getRateState());
  LoRa.print(",");
  Serial.print(",");
  LoRa.print(getHeight());
  Serial.print(getHeight());
  LoRa.print(",");
  Serial.print(",");
  LoRa.print(getLevelState());
  Serial.println(getLevelState());
  LoRa.endPacket();

  counter++;

  attachInterrupt(digitalPinToInterrupt(flowPin), pulseCounter, FALLING); // Re-enable interrupt
  delay(10000);
}

void pulseCounter() {
  pulseCount++;
}

const char* getRateState() {
  if (flow == 0) {
    return "NO FLOW";
  } else if (flow > 2) {
    return "HIGH";
  } else if (flow > 1) {
    return "MEDIUM";
  } else {
    return "LOW";
  }
}

const char* getLevelState() {
  int lv1 = digitalRead(level1Pin);
  int lv2 = digitalRead(level2Pin);
  int lv3 = digitalRead(level3Pin);

  if (lv1 == HIGH && lv2 == LOW && lv3 == LOW) {
    return "LOW";
  } else if (lv1 == HIGH && lv2 == HIGH && lv3 == LOW) {
    return "MEDIUM";
  } else if (lv1 == HIGH && lv2 == HIGH && lv3 == HIGH) {
    return "HIGH";
  } else {
    return "DRY";
  }
}

const char* getHeight() {
  int lv1 = digitalRead(level1Pin);
  int lv2 = digitalRead(level2Pin);
  int lv3 = digitalRead(level3Pin);

  if (lv1 == HIGH && lv2 == LOW && lv3 == LOW) {
    return "1";
  } else if (lv1 == HIGH && lv2 == HIGH && lv3 == LOW) {
    return "2";
  } else if (lv1 == HIGH && lv2 == HIGH && lv3 == HIGH) {
    return "3";
  } else {
    return "0";
  }
}
