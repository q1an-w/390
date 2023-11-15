# Hardware Overview
This document comprehensively details the hardware specifications utilized in the DrainFlow Solutions roof drain sensor. It encompasses crucial information about components, connections, setup instructions, and offers a high-level overview.

### Components
1. Arduino Uno
2. Arduino Nano
3. Rasperry Pi 3
4. SX1278 LoRa Module
5. YF-S201C Water Flow Sensor
6. Water Strip Sensor

### High Level View
DrainFlow Solutions introduces an effortless roof drain sensor—an intuitive monitoring system enabling users to access vital data on water flow and levels at the drain's intake.
Through the Android application, real-time data retrieval and proactive notifications in case of potential blockages empower homeowners to safeguard their property and avert roof damage.

The product comprises two key components: the Transmitter module, positioned on the roof, and the Receiver module, placed indoors, designed for seamless setup and optimal performance.

### Transmitter Module
The transmitter module is comprised of the following components:

1. Arduino Uno, SX1278 LoRa Module
2. YF-S201C Water Flow Sensor
3. Water Strip Sensors.

These components serve as the foundational pillars of the product's data collection mechanism, with the transmitter serving as the primary collector of real-world data.
At the heart of this module lies the Arduino Uno, functioning as the central hub that orchestrates connectivity among all components.
By leveraging standard Arduino code, we seamlessly integrate water flow and water level sensors as inputs, consistently monitoring their reported values.

The incorporation of an open-source library facilitates the connection of the SX1278 module to the Uno, enabling seamless SPI communication.
This integration empowers the translation and transmission of Serial prints across the frequency spectrum.
