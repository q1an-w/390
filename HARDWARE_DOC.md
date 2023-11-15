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
The Transmitter's primary function is to gather and transmit sensor data from the rooftop drain. Meanwhile, the Receiver module plays a pivotal role in capturing LoRa packets, parsing the information, and ensuring successful uploading of the data to the Cloud-based Firebase database.

### Transmitter Module
The Transmitter Module, crucial to data collection and communication is comprised of the following components:

1. Arduino Uno, SX1278 LoRa Module
2. YF-S201C Water Flow Sensor
3. Water Strip Sensors.

These components serve as the foundational pillars of the product's data collection mechanism, with the transmitter serving as the primary collector of real-world data.
At the heart of this module lies the Arduino Uno, functioning as the central hub that orchestrates connectivity among all components.
By leveraging standard Arduino code, we seamlessly integrate water flow and water level sensors as inputs, consistently monitoring their reported values.

The incorporation of an open-source library facilitates the connection of the SX1278 module to the Uno, enabling seamless SPI communication.
This integration empowers the translation and transmission of Serial prints across the frequency spectrum.
With its adjustable transfer rate, the Arduino offers programmable transmission intervals tailored to the clients' requirements, whether it's every second, minute, or hour.

The YF-S201C water flow sensor generates a digital signal, enabling the microcontroller to compute an estimated flow rate in liters per minute (L/min).
When combined with water strip sensors, the Transmitter module is capable of transmitting comprehensive data, encompassing the flow rate, water level, connectivity status, and critical importance state, leveraging its internal logic and C++ source code.

### Receiver Module

The Receiver Module, crucial in completing the data transmission process, consists of the following components:

1. Arduino Nano
2. Raspberry Pi 3
3. SX1278 LoRa Module

These components collectively facilitate the reception, processing, and forwarding of transmitted data from the Transmitter Module.

The Arduino Nano within the Receiver Module acts as an interface between the SX1278 LoRa Module and the Raspberry Pi 3. Its role involves preliminary data processing and handling LoRa communication protocols.
Serving as the central processing unit, the Raspberry Pi 3 takes in data received by the Arduino Nano. It executes intricate data parsing algorithms, ensuring accurate extraction of information from the incoming LoRa packets.
The LoRa module in the Receiver Module receives transmissions sent by the Transmitter Module. Its primary function involves the reception and initial handling of LoRa packets, forwarding them to the Arduino Nano for initial processing.

Upon receiving LoRa packets, the Arduino Nano interprets the incoming data, prepares it for further analysis, and transfers it to the Raspberry Pi 3. The Raspberry Pi 3, equipped with sophisticated software capabilities, parses the received data and orchestrates its integration into a Cloud-based Firebase database.
Through this coordinated effort among the Arduino Nano, Raspberry Pi 3, and SX1278 LoRa Module, the Receiver Module effectively captures, processes, and securely uploads transmitted data to the Cloud, ensuring a seamless flow of information from the rooftop drain sensor system to the centralized database.
