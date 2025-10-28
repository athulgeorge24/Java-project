# 🪑 UniSeat – Universal Smart Seating Arrangement System

A **Java Swing-based Smart Seating Management Application** built using the **Model-View-Controller (MVC)** pattern.  
The project helps to **generate, visualize, and manage seating arrangements** in venues such as examination halls, auditoriums, and conferences.

---

## 🧭 Overview

The **UniSeat System** simplifies the complex process of seating management by allowing users to:

- 📂 **Upload participant lists** (simulated `.xlsx` processing)
- 🧩 **Generate venue layouts** (rows × columns)
- 🟩 **Toggle seat availability dynamically**
- 📊 **View real-time statistics** — total, available, and occupied seats
- 🤖 **Simulate automatic seating assignment**

Built entirely in **Java**, it demonstrates strong use of:

- **Object-Oriented Programming (OOP)**
- **Swing GUI Components**
- **MVC (Model-View-Controller) Design Pattern**

---

## ⚙️ How to Run

### 🧩 Option 1 — Run via IDE
1. Open the project folder in **IntelliJ IDEA**, **Eclipse**, or **NetBeans**.  
2. Ensure your **Java SDK (JDK 17 or later)** is configured.  
3. Run the `UniSeatGUI.java` file.

### 💻 Option 2 — Run via Terminal
```bash
javac UniSeatGUI.java
java UniSeatGUI
```

---

## 🧩 Features in Detail
### Feature	Description
- 🎟️ Dynamic Seat Layout	Create custom layouts by specifying rows and columns
- 🟢 Seat Status Toggle	Click on seats to mark them as occupied or available
- 📊 Live Statistics	Real-time updates of total, occupied, and free seats
- 🧮 Auto Assignment	Automatically fills seats for uploaded participant lists
- 💾 File Handling	Supports simulated data loading for test scenarios
- 🎨 Intuitive UI	Built with Swing, optimized for clarity and simplicity
- 🧱 Concepts Demonstrated

## MVC Architecture

- Model → Seat, SeatManager

- View → UniSeatGUI

- Controller → Event Listeners inside GUI

- Encapsulation & Abstraction

- Swing Layout Management

- Event Handling (ActionListener, MouseListener)

- Dynamic UI Rendering

## 🧠 Learning Outcomes

- Java GUI programming using Swing

- Event-driven programming

- Implementing modular design with MVC

- Managing states and user interaction dynamically

## 🪪 License

This project is open-source and available under the MIT License.

## ⭐ Acknowledgment

Special thanks to **St. Joseph’s College of Engineering and Technology** for academic guidance and support in completing this mini project.
