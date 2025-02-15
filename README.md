<p align="center"><img src="./.idea/icon.png" alt="Logo" width="250"></p>
<h1 align="center">Counter  <br>
	<a href="https://www.curseforge.com/minecraft/mc-mods/counter-neoforge/files"><img src="https://cf.way2muchnoise.eu/versions/1100508(0280ff).svg?cachebuster=1" alt="Supported Versions"></a>
	<a href="https://github.com/BigBull-H3RO/Counter/blob/main/LICENSE"><img src="https://img.shields.io/github/license/BigBull-H3RO/Counter?style=flat&color=0280ff" alt="License"></a>
	<a href="https://www.curseforge.com/minecraft/mc-mods/counter-neoforge"><img src="https://cf.way2muchnoise.eu/1100508.svg?" alt="CurseForge"></a>
	<a href="https://modrinth.com/mod/counter"><img src="https://img.shields.io/modrinth/dt/K3I2cKId?logo=modrinth&label=&suffix=%20&style=flat&color=242629&labelColor=5ca424&logoColor=1c1c1c" alt="Modrinth"></a>
	<a href="https://www.curseforge.com/minecraft/mc-mods/counter-neoforge/files/all?page=1&pageSize=20"><img src="https://img.shields.io/curseforge/v/1100508?logo=adguard&label=&suffix=%20&style=flat&color=1c1c1c&labelColor=121212&logoColor=5ca424" alt=Version"></a>
    <br><br>
</h1>

**Counter Mod** adds **Day and Death Counters** to your Minecraft experience, allowing players to track time progression and monitor their own or other players' deaths in a structured way. With extensive configuration options, the mod is perfect for both singleplayer and multiplayer servers.

## **Features**
✅ **Day Counter**
- Displays the current Minecraft day.
- Can be shown as an **overlay** or in **chat when joining** a world.
- Server administrators can enable or disable chat messages.

✅ **Death Counter**
- Tracks the number of deaths per player.
- Supports an **overlay**, a **chat leaderboard**, and **admin control over death counts**.
- **Configurable list size**: Choose how many players are shown in the leaderboard.
- **Singleplayer vs. Multiplayer:** In **singleplayer**, players only see their own deaths, while in **multiplayer**, a leaderboard can be displayed.

---

## **⚙️ Configuration Options**
The Counter Mod provides extensive configuration options via the `server-config.toml` and `client-config.toml` files.

### **🔹 Day Counter Settings (`server-config.toml`)**
| Option                  | Default | Description |
|-------------------------|---------|-------------|
| `enableDayCounter`      | `true`  | Enables the Day Counter. |
| `enableDayMessage`      | `true`  | Displays a **chat message** when a new day begins. |
| `showOverlay`           | `true`  | Enables the **Day Counter overlay**. |
| `showDayInChat`         | `true`  | Shows the **current day in chat** when a player joins. |

### **🔹 Death Counter Settings (`server-config.toml`)**
| Option                     | Default | Description |
|----------------------------|---------|-------------|
| `enableDeathCounter`       | `true`  | Enables the Death Counter. |
| `showDeathOverlay`         | `true`  | Enables the **Death Counter overlay**. |
| `maxPlayersShown`          | `5`     | Number of players in the **leaderboard**. |
| `showDeathOverlayAlways`   | `false` | If `true`, the overlay is **always visible**; otherwise, it appears **when pressing Tab**. |
| `deathOverlayMode`         | `LIST`  | Display mode: **`ONLY_SELF`**, **`LIST`**, or **`BOTH`**. |
| `showDeathListInChat`      | `true`  | Shows the **Death Counter leaderboard in chat** when a player joins or dies. |

---

## **📝 Commands**
| Command                            | Permission | Description |
|------------------------------------|------------|-------------|
| `/counter day set <days>`          | `admin`    | Sets the current day to a specific value. |
| `/counter day reset`               | `admin`    | Resets the Day Counter to `0`. |
| `/counter day show/hide`           | `all`      | Toggles the Day Counter overlay per player. |
| `/counter death deaths`            | `all`      | Displays your own death count. |
| `/counter death set <player> <amount>` | `admin`  | Sets a player's death count. |
| `/counter death reset`             | `admin`    | Resets all death counts to `0`. |
| `/counter death show/hide`         | `all`      | Toggles the Death Counter overlay per player. |

---

## **⚠️ Notes**
- **To configure settings**, edit the `.toml` files inside the `config` folder.
- The mod **automatically syncs server settings**, ensuring **multiplayer consistency**.

---

<h4 align="center">📢 **Found a bug? Have a suggestion?**<br>

Report issues to the <a href="https://github.com/BigBull-H3RO/Counter/issues">Issue Tracker</a></h4>
<h4 align="center">💡 Find out more about Counter on our <a href="https://www.curseforge.com/minecraft/mc-mods/counter-neoforge">Curseforge</a> or <a href="https://modrinth.com/mod/counter">Modrinth</a> Page</h4>
