# 🍽️ FoodOrder-AndroidApp

A comprehensive Android application for managing food ordering, delivery, and restaurant operations. This multi-role platform connects customers, chefs, and restaurant managers in a unified food ordering ecosystem.

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [User Roles](#user-roles)
- [API Integration](#api-integration)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### Customer Features
- **Browse Menu**: View restaurant menu items with prices
- **Place Orders**: Add items to cart and checkout
- **Order Tracking**: View order history and current order status
- **User Dashboard**: Manage customer profile and preferences

### Chef Features
- **Order Management**: View incoming orders in real-time
- **Order Fulfillment**: Mark orders as completed
- **Chef Authentication**: Secure login system for kitchen staff

### Restaurant Manager Features
- **Menu Management**: Add, edit, and manage food items and prices
- **Chef Management**: Hire and manage kitchen staff with credentials
- **Order Overview**: Monitor all restaurant orders
- **Restaurant Dashboard**: Access comprehensive management portal
- **Authentication**: Secure login for restaurant operators

## 🏗️ Project Structure

```
FoodOrder-AndroidApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/foodorderapp/
│   │   │   │   ├── Activities/
│   │   │   │   │   ├── LandingActivity.java           # Entry point - Role selection
│   │   │   │   │   ├── MainActivity.java              # Customer dashboard
│   │   │   │   │   ├── ChefMainActivity.java          # Chef dashboard
│   │   │   │   │   ├── ChefLoginActivity.java         # Chef authentication
│   │   │   │   │   ├── ManagerMainActivity.java       # Restaurant manager dashboard
│   │   │   │   │   ├── ManagerLoginActivity.java      # Manager authentication
│   │   │   │   │   ├── CheckoutActivity.java          # Order checkout
│   │   │   │   │   ├── RestaurantRegisterActivity.java # Restaurant registration
│   │   │   │   │   └── RestaurantPortalActivity.java  # Manager portal
│   │   │   │   ├── Fragments/
│   │   │   │   │   ├── CustomerFragment.java          # Customer menu browsing
│   │   │   │   │   ├── MyOrdersFragment.java          # Order history
│   │   │   │   │   └── ChefFragment.java              # Chef order queue
│   │   │   │   ├── API/
│   │   │   │   │   ├── RetrofitClient.java            # Retrofit HTTP client
│   │   │   │   │   ├── MenuApiService.java            # Menu endpoints
│   │   │   │   │   ├── ChefApiService.java            # Chef endpoints
│   │   │   │   │   └── ManagerApiService.java         # Manager endpoints
│   │   │   │   ├── Models/
│   │   │   │   │   ├── MenuItem.java                  # Menu item data model
│   │   │   │   │   ├── Chef.java                      # Chef data model
│   │   │   │   │   ├── Manager.java                   # Manager/Restaurant data model
│   │   │   │   │   ├── Order.java                     # Order data model
│   │   │   │   │   └── LoginRequest.java              # Login request model
│   │   │   │   └── Utils/                             # Utility classes
│   │   │   ├── res/
│   │   │   │   ├── layout/                            # XML layout files
│   │   │   │   ├── values/                            # Colors, strings, styles
│   │   │   │   └── mipmap/                            # App icons
│   │   │   └── AndroidManifest.xml                    # App configuration
│   │   └── test/                                      # Unit tests
│   └── build.gradle.kts                               # App-level build config
├── gradle/                                            # Gradle wrapper
├── build.gradle.kts                                   # Project-level build config
├── settings.gradle.kts                                # Project settings
└── gradle.properties                                  # Gradle properties
```


## 🏛️ Architecture

The app follows a **multi-activity, fragment-based architecture** with separation of concerns:

- **Activities**: Handle navigation and fragment management
- **Fragments**: Manage UI for specific user roles
- **API Layer**: Retrofit-based REST client for backend communication
- **Models**: Data classes for serialization/deserialization
- **Services**: Interfaces defining API endpoints

## 🛠️ Tech Stack

### Core Framework
- **Android SDK**: Minimum SDK 24, Target SDK 35
- **Language**: Java 11
- **Build Tool**: Gradle (Kotlin DSL)

### Libraries & Dependencies
- **AndroidX**: AppCompat, Activity, ConstraintLayout
- **Material Design**: Material Components
- **Networking**: 
  - Retrofit 2.9.0 - REST API client
  - Gson - JSON serialization/deserialization
- **Testing**: 
  - JUnit - Unit testing
  - Espresso - Instrumented testing

## 📦 Prerequisites

- **Android Studio**: Latest stable version
- **JDK**: Java 11 or higher
- **Gradle**: Configured via wrapper
- **Android SDK**: API level 24+
- **Backend Server**: Running FoodOrder backend API

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/Sam-Immortal/FoodOrder-AndroidApp.git
cd FoodOrder-AndroidApp

```

### 2. Open in Android Studio
```bash

# Using command line
android-studio .

# Or open Android Studio and use File > Open > Select the project directory

```

### 3. Build the Project
```bash

# Sync Gradle files
./gradlew sync

# Build the APK
./gradlew build

# Build and install on connected device/emulator
./gradlew installDebug

```

### 4. Run the App
- Select a virtual device or connect a physical Android device
- Click **Run** (or press Shift + F10)

## 📱 Usage

### First-Time Launch
1. **Landing Screen**: Choose your role
   - 🛍️ **Customer** - Browse and order food
   - 👨‍🍳 **Chef** - Manage food preparation
   - 🏪 **Restaurant** - Manage menu and operations

### Customer Flow
1. Enter **Customer Dashboard** → Browse available menu items
2. Select items and proceed to **Checkout**
3. Complete order and track in **My Orders**

### Chef Flow
1. Enter **Chef Login** with username and password
2. View incoming orders in the **Kitchen Queue**
3. Mark orders as completed

### Manager/Restaurant Flow
1. Enter **Manager Login** with restaurant name and password
2. Access **Restaurant Portal** to:
   - Add/remove menu items
   - Hire and manage chefs
   - Monitor orders in real-time

## 👥 User Roles

| Role | Permissions | Key Activities |
|------|-------------|-----------------|
| **Customer** | Browse, Order, Checkout | Browse menu, place orders, view history |
| **Chef** | View Orders, Mark Complete | Receive orders, prepare food, update status |
| **Manager** | Full Control | Manage menu, staff, pricing, operations |

## 🌐 API Integration

The app communicates with a backend API using **Retrofit 2** with the following services:

### MenuApiService
```java

- GET /menu/items - Retrieve all menu items
- POST /menu/items - Add new menu item
- PUT /menu/items/{id} - Update menu item
- DELETE /menu/items/{id} - Remove menu item

```

### ChefApiService
```java

- POST /auth/chef/login - Chef authentication
- GET /chefs - Get all chefs
- POST /chefs - Add new chef
- GET /orders - Get chef's orders
- PUT /orders/{id}/status - Update order status

```

### ManagerApiService
```java

- POST /auth/manager/login - Manager authentication
- POST /restaurants/register - Register new restaurant
- GET /restaurants/{id}/orders - Get restaurant orders

```

### Configuration
Update the API base URL in `RetrofitClient.java`:
```java

private static final String BASE_URL = "http://your-api-server.com/api/";

```

**Note**: The app allows cleartext traffic (`android:usesCleartextTraffic="true"`) for development. For production, use HTTPS and update the network security configuration.

## ⚙️ Configuration

### Network Security
- **Cleartext Traffic**: Currently enabled for development
- **INTERNET Permission**: Required for API calls
- **RTL Support**: Enabled for right-to-left language support

### Minimum Requirements
- **Min API Level**: 24 (Android 7.0 Nougat)
- **Target API Level**: 35 (Android 15)
- **Compile SDK**: 35

---

**Happy Coding! 🚀**
