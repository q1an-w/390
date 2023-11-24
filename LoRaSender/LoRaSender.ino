#include <SPI.h>
#include <LoRa.h>

const int flowPin = 3;
const int inputPin = 4;
const int level1Pin = 5;
const int level2Pin = 6;
const int level3Pin = 7;

volatile int pulseCount;
double flow;
unsigned long currentTime;
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
  pinMode(inputPin, INPUT);

  attachInterrupt(digitalPinToInterrupt(flowPin), pulseCounter, FALLING);
  currentTime = millis();
  lastTime = currentTime;
  pulseCount = 0;
}

void clearSerialMonitor() {
  for (int i = 0; i < 50; i++) {
    Serial.println();
  }
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
    return "1 cm";
  } else if (lv1 == HIGH && lv2 == HIGH && lv3 == LOW) {
    return "2 cm";
  } else if (lv1 == HIGH && lv2 == HIGH && lv3 == HIGH) {
    return "3 cm";
  } else {
    return "0 cm";
  }
}

void loop() {
  LoRa.beginPacket();

  currentTime = millis();

  if (currentTime >= (lastTime + 500)) {
    lastTime = currentTime;
    flow = (pulseCount / 7.5); 
    pulseCount = 0;

    Serial.print("Sending packet: ");
    Serial.println(counter);

    LoRa.print(flow, 2);
    LoRa.print(" L/min");
    LoRa.print(",");
    LoRa.print(getRateState());
    LoRa.print(",");
    LoRa.print(getHeight());
    LoRa.print(",");
    LoRa.print(getLevelState());
    LoRa.endPacket();
  }
  
  counter++;
  pulseCount = 0;
  delay(30000);
}

void pulseCounter() {
  pulseCount++;
}
