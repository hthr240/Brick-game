# Arkanoid Game Engine | Java & Design Patterns

A robust, object-oriented game engine recreating the classic **Arkanoid/Breakout** arcade game. Designed with a focus on software architecture, utilizing the **Strategy** and **Factory** design patterns to create a scalable collision detection system and dynamic game element generation.

## 🚀 Key Features
* **Physics Engine:** Custom collision detection logic handling vector-based reflections and dynamic interactions.
* **Extensible Design:** Built with **Polymorphism** to easily add new brick types, power-ups, and behaviors without modifying core logic.
* **Design Patterns:**
    * **Strategy Pattern:** Decouples collision behaviors (e.g., "Break", "Reflect", "PowerUp") from game objects.
    * **Factory Pattern:** Centralizes object creation for Bricks and Game Elements.
* **Responsive UI:** Smooth rendering loop using **Java Swing/AWT**.

## 🛠️ Tech Stack
* **Language:** Java (JDK 11+)
* **Graphics:** Java Swing / AWT
* **Architecture:** OOP (Inheritance, Polymorphism)
* **Mechanics:** Game Loop, Event Listeners

## 📂 Project Structure
* `src/bricker/main`: Entry point and Game Loop management.
* `src/bricker/gameobjects`: Abstract classes for `Paddle`, `Ball`, `Brick`.
* `src/bricker/brick_strategies`: Implementation of the **Strategy Pattern** for collision behaviors.
* `src/bricker/factory`: **Factory** classes for generating game levels and objects.
