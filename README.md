# AirHockey

Winning project of Gannon University Programming Contest

This game is a multiplayer air hockey game build using Java and Swing.  Games take place either against an AI (with difficulty level of your choosing) or against another player via LAN.  The multiplayer aspect is implemented via the `java.net.Socket` and `java.net.ServerSocket` classes, and includes automatic opponent finding features.

This project is licensed under the MIT license.

# Building and Running

1. Open a terminal or command prompt and navigate to the root directory of this repository (the directory containing this file, `README.md`)

2. Compile all files in the source package: `javac gannon-air-hockey/src/ackermanCoplanMuscianoAirHockey/*.java`

3. Run the project's main class (Driver): `java -cp ./gannon-air-hockey/src ackermanCoplanMuscianoAirHockey.Driver`
