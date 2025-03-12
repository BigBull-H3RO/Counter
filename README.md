<p align="center"><img src="./.idea/icon.png" alt="Logo" width="250"></p>

<h1 align="center">Counter  <br>
	<a href="https://www.curseforge.com/minecraft/mc-mods/counter-day-death/files"><img src="https://cf.way2muchnoise.eu/versions/1214103.svg?cachebuster=1" alt="Supported Versions"></a>
	<a href="https://github.com/BigBull-H3RO/Counter/blob/main/LICENSE"><img src="https://img.shields.io/github/license/BigBull-H3RO/Counter?style=flat&color=ffffff" alt="License"></a>
	<a href="https://www.curseforge.com/minecraft/mc-mods/counter-day-death"><img src="https://cf.way2muchnoise.eu/1214103.svg?" alt="CurseForge"></a>
	<a href="https://modrinth.com/mod/counter-neoforge"><img src="https://img.shields.io/modrinth/dt/u43pMIKj?logo=modrinth&label=&suffix=%20&style=flat&color=242629&labelColor=5ca424&logoColor=1c1c1c" alt="Modrinth"></a>
	<a href="https://www.curseforge.com/minecraft/mc-mods/counter-day-death/files/all?page=1&pageSize=20"><img src="https://img.shields.io/curseforge/v/1214103?logo=adguard&label=&suffix=%20&style=flat&color=1c1c1c&labelColor=121212&logoColor=5ca424" alt="Version"></a>
    <br><br>
</h1>

The **Counter Mod** introduces **Day** and **Death Counters** to your Minecraft experience, allowing players to track world progression and maintain an organized record of their own (or others') deaths.

Additionally, the mod offers an option to replace the **vanilla ping bars** in the player list (**Tab menu**) with a **color-coded numeric ping**, providing a precise latency display (e.g., `123ms`) at a glance.

All features are **highly configurable**, making this mod an excellent choice for both **singleplayer and multiplayer servers**.

## **✨ Features**
✅ **Day Counter**
- Shows the **current Minecraft day**.
- Can be displayed as an **overlay** or in **chat when joining** a world.
- Server administrators can enable or disable **day messages**.

✅ **Death Counter**
- Tracks the **total deaths per player**.
- Supports a **screen overlay**, a **chat leaderboard**, and **admin control over death counts**.
- **Configurable leaderboard size**: Choose how many players appear in the ranking.
- **Singleplayer vs. Multiplayer**:
  - In **singleplayer**, players see only their **own deaths**.
  - In **multiplayer**, a **server-wide leaderboard** can be displayed.

✅ **Ping as Text**  
- Replaces the **default ping bars** in the **Tab** list with a numeric readout (e.g., `123ms`).
- Can be **color-coded** based on latency (**Green = Good**, **Orange = Moderate**, **Red = High**).
- Easily toggled in the **client-config** (`showPingAsText`).  
- If disabled, the **default vanilla ping bars** will be shown again.

---

## **⚙️ Configuration Options**
The Counter Mod provides extensive configuration options via **`server-config.toml`** and **`client-config.toml`**.

### **Client Configuration (`client-config.toml`)**

#### 🌞Day Counter Overlay Settings
| Option                | Default    | Description                                                       |
|-----------------------|------------|-------------------------------------------------------------------|
| `showDayOverlay`      | `true`     | Enable/disable the **day counter overlay** on the client side.    |
| `showOverlayAlways`   | `true`     | Should the **day counter overlay** always be visible?             |
| `dayOverlayX`         | `0.05`     | Relative X position (0.0 = left, 1.0 = right) of the day overlay. |
| `dayOverlayY`         | `0.05`     | Relative Y position (0.0 = top, 1.0 = bottom) of the day overlay. |
| `dayOverlaySize`      | `1.0`      | Scale factor for the day counter text size.                       |
| `dayOverlayTextColor` | `0xFFFFFF` | Color for the **day counter overlay** text.                       |

#### 💀Death Counter List Settings
| Option                  | Default    | Description                                                                         |
|-------------------------|------------|-------------------------------------------------------------------------------------|
| `showDeathListOverlay`  | `true`     | Enable/disable the **death counter list overlay**.                                  |
| `showListOverlayAlways` | `false`    | Should the **death counter list overlay** always be visible?                        |
| `deathListX`            | `0.05`     | Relative X position for the death list overlay (0.0 = left, 1.0 = right).           |
| `deathListY`            | `0.05`     | Relative Y position for the death list overlay (0.0 = top, 1.0 = bottom).           |
| `deathListSize`         | `1`        | Scale factor for the death list text size.                                          |
| `deathOverlayWidth`     | `120`      | Maximum width (in pixels) for the **death counter list overlay**.                   |
| `deathOverlayStyle`     | `TABLE`    | Style used for the **death list overlay**: **`CLASSIC`**, **`BOXED`**, **`TABLE`**. |
| `deathListTextColor`    | `0xFF0000` | Default text color for the death list overlay.                                      |
| `firstPlaceColor`       | `0xFFD700` | Color for first place in the death leaderboard.                                     |
| `secondPlaceColor`      | `0xC0C0C0` | Color for second place in the death leaderboard.                                    |
| `thirdPlaceColor`       | `0xCD7F32` | Color for third place in the death leaderboard.                                     |

#### 💀Death Counter Self Settings
| Option                  | Default    | Description                                                           |
|-------------------------|------------|-----------------------------------------------------------------------|
| `showDeathSelfOverlay`  | `true`     | Enable/disable the **personal death counter overlay**.                |
| `showSelfOverlayAlways` | `false`    | Should the **personal death counter overlay** always be visible?      |
| `deathSelfX`            | `0.10`     | Relative X position (0.0 = left, 1.0 = right) for your death overlay. |
| `deathSelfY`            | `0.10`     | Relative Y position (0.0 = top, 1.0 = bottom) for your death overlay. |
| `deathSelfSize`         | `1`        | Scale factor for the **personal death counter** text size.            |
| `deathSelfTextColor`    | `0xFF0000` | Color for your **personal death counter** text.                       |

#### 📶Tab Overlay Settings
| Option            | Default    | Description                                                             |
|-------------------|------------|-------------------------------------------------------------------------|
| `showPingAsText`  | `true`     | Show the **ping as text** (e.g. 123ms) instead of bars in the Tab list. |
| `pingColorGood`   | `0x00FF00` | Color for low ping (<100ms).                                            |
| `pingColorMedium` | `0xFF9900` | Color for medium ping (100-249ms).                                      |
| `pingColorBad`    | `0xFF0000` | Color for high ping (>=250ms).                                          |

#### 😀Emote Settings
| Option       | Default | Description                               |
|--------------|---------|-------------------------------------------|
| `showEmojis` | `true`  | Enable or disable emojis in **overlays**. |

---

### **Server Configuration (`server-config.toml`)**

#### 🌞Day Counter Settings
| Option             | Default    | Description                                                         |
|--------------------|------------|---------------------------------------------------------------------|
| `enableDayCounter` | `true`     | Enables or disables the **Day Counter** feature on the server.      |
| `enableDayMessage` | `true`     | Displays a **chat message** whenever a new Minecraft day starts.    |
| `showOverlay`      | `true`     | Allows the **Day Counter overlay** to be shown (client can toggle). |
| `showDayInChat`    | `true`     | Shows the **current day in chat** when a player joins the server.   |
| `dayChatTextColor` | `0xFFFFFF` | Text color for the **day counter messages** in chat.                |

#### 💀Death Counter Settings
| Option               | Default | Description                                                                                                          |
|----------------------|---------|----------------------------------------------------------------------------------------------------------------------|
| `enableDeathCounter` | `true`  | Enables or disables the **Death Counter** feature on the server.                                                     |
| `showDeathOverlay`   | `true`  | Allows the **Death Counter overlay** to be shown (client can toggle).                                                |
| `maxPlayersShown`    | `5`     | Number of players displayed in the **death leaderboard**.                                                            |
| `deathOverlayMode`   | `LIST`  | Defines which **death overlay types** are allowed: **`ONLY_SELF`** (personal), **`LIST`** (leaderboard), **`BOTH`**. |

#### 💀Death Counter Chat Settings
| Option                       | Default    | Description                                                                                              |
|------------------------------|------------|----------------------------------------------------------------------------------------------------------|
| `showDeathInChat`            | `true`     | Enables **death counter** messages in chat (on join or on death).                                        |
| `showDeathInChatMode`        | `BOTH`     | When to display **death messages** in chat: **`ON_JOIN`**, **`ON_DEATH`**, or **`BOTH`**.                |
| `deathChatMode`              | `LIST`     | Chat mode: **`ONLY_SELF`** (each player sees only their own total) or **`LIST`** (show a leaderboard).   |
| `showDeathListOnDeathGlobal` | `false`    | If `true`, shows the **death list** to all players when someone dies; otherwise only the victim sees it. |
| `deathListChatTextColor`     | `0xFFFFFF` | Text color for **death counter messages** in chat.                                                       |
| `deathSelfChatTextColor`     | `0xFFFFFF` | Text color for **personal death messages** in chat.                                                      |

---

## **📝 Commands**
Below is an overview of the main commands the mod provides:

| Command                                    | Permission | Description                                          |
|--------------------------------------------|------------|------------------------------------------------------|
| **`/counter day get`**                     | `all`      | Shows the current day counter value.                 |
| **`/counter day set <days>`**              | `admin`    | Sets the Day Counter to the specified value.         |
| **`/counter day reset`**                   | `admin`    | Resets the Day Counter to the current Minecraft day. |
| **`/counter death get`**                   | `all`      | Shows your own total deaths.                         |
| **`/counter death get <player>`**          | `all`      | Shows another player’s total deaths.                 |
| **`/counter death set <player> <amount>`** | `admin`    | Sets the death count for the specified player(s).    |
| **`/counter death reset`**                 | `admin`    | Resets the death count of all players to `0`.        |

---

## **🛠 Overlay Edit Mode**
You can open an **Edit Screen** to customize the position and visibility of overlays:
1. Use the **keybind** (defined in the controls) or another available method to open the **Overlay Edit Screen**.
2. **Drag & drop** the overlays to reposition them anywhere on your screen.
3. Click the **"Toggle Overlay"** button to enable or disable an overlay.
4. Press **"Done"** to save your changes, or **"Cancel"** to discard them.

With this intuitive editor, you can easily configure the **Day Counter** and **Death Counter** overlays without modifying config files.

---

## **📌 Planned Features**
🔹 **Ingame Time Display**
- Displays the current **Minecraft time** as an overlay (e.g., `14:35` in-game).
- Configurable in **12-hour or 24-hour format**.

🔹 **Coordinate Display**
- An overlay to show the player's current coordinates (`X, Y, Z`).
- Position and formatting will be customizable.

🔹 **Compass for Navigation**
- A **compass overlay** displaying the player's **facing direction** (`North, South, East, West`).
- Optional **marker function** for tracking important locations.

---

<h4 align="center">📢 **Found a bug? Have a suggestion?**<br>

Report issues to the <a href="https://github.com/BigBull-H3RO/Counter/issues">Issue Tracker</a></h4>

<h4 align="center">💡 Find out more about Counter on our <a href="https://www.curseforge.com/minecraft/mc-mods/counter-neoforge">Curseforge</a> or <a href="https://modrinth.com/mod/counter">Modrinth</a> Page</h4>
