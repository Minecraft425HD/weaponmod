# WeaponMod for Minecraft 1.20.1

A comprehensive weapon mod for Minecraft Forge that adds realistic firearms, melee weapons, grenades, a weapon attachment system, and a skill progression system.

---

## Table of Contents

- [Overview](#overview)
- [Requirements](#requirements)
- [Installation](#installation)
- [Features](#features)
  - [Firearms](#firearms)
  - [Melee Weapons](#melee-weapons)
  - [Grenades](#grenades)
  - [Weapon Attachments](#weapon-attachments)
  - [Ammunition System](#ammunition-system)
  - [Skill System](#skill-system)
  - [Fire Modes](#fire-modes)
  - [Accuracy & Recoil](#accuracy--recoil)
- [Controls](#controls)
- [Configuration](#configuration)
- [Building from Source](#building-from-source)
- [License](#license)

---

## Overview

WeaponMod adds a complete combat system to Minecraft, including 6 firearm types, 3 melee weapons, 3 grenade types, and a modular attachment system. Each weapon has unique characteristics such as damage, range, accuracy, and fire rate. A skill system rewards players with passive damage bonuses as they gain experience with different weapon categories.

The mod is fully localized in **English** and **German**.

---

## Requirements

| Component        | Version          |
|------------------|------------------|
| Minecraft        | 1.20.1           |
| Minecraft Forge  | 47.4.0 or higher |
| Java             | 17 or higher     |

---

## Installation

1. Install [Minecraft Forge 1.20.1](https://files.minecraftforge.net/) (version 47.4.0 or higher).
2. Download the latest `weaponmod-1.0.0.jar` from the [Releases](#) page.
3. Place the JAR file in your Minecraft `mods/` folder.
4. Start Minecraft with the Forge profile.
5. All items are available in the **WeaponMod** creative tab.

---

## Features

### Firearms

Six firearms are available, each with unique stats:

| Weapon         | Ammo Capacity | Damage | Accuracy | Range | Fire Rate (ticks) |
|----------------|--------------|--------|----------|-------|-------------------|
| AK47           | 30           | 8      | 85%      | 120 m | 4                 |
| Pistol         | 15           | 6      | 95%      | 60 m  | 5                 |
| Revolver       | 6            | 10     | 90%      | 80 m  | 8                 |
| Shotgun        | 8 shells     | 4 × 5 pellets | 60% | 30 m | 10             |
| Sniper Rifle   | 5            | 20     | 98%      | 400 m | 20                |
| MP5            | 30           | 5      | 80%      | 80 m  | 2                 |

**Notes:**
- The Shotgun fires 5 pellets per shot, each dealing 4 damage (up to 20 total per shot).
- The Sniper Rifle has the longest range and highest single-shot damage.
- The MP5 has the fastest fire rate.

---

### Melee Weapons

| Weapon          | Special Effect         | Tier  |
|-----------------|------------------------|-------|
| Baseball Bat    | Bonus knockback        | Iron  |
| Machete         | Sword-type damage      | Iron  |
| Combat Knife    | Sword-type damage      | Iron  |

Melee weapons support all standard Minecraft enchantments and benefit from the Melee skill bonus.

---

### Grenades

Three grenade types can be thrown at enemies or into areas:

| Grenade         | Effect                                  |
|-----------------|-----------------------------------------|
| Frag Grenade    | Explosion damage in area of effect      |
| Smoke Grenade   | Creates a smoke cloud for area denial   |
| Flash Grenade   | Applies blindness effect to nearby players |

Grenades are thrown like standard Minecraft projectiles and have physics simulation.

---

### Weapon Attachments

Up to **2 attachments** can be equipped on any firearm:

| Attachment | Effect                                         |
|------------|------------------------------------------------|
| Scope      | Increases accuracy (accuracy multiplier)       |
| Silencer   | Reduces shooting sound; increases cooldown by 20% |
| Laser      | Improves accuracy and handling                 |

Attachments are applied directly to the gun item via the inventory system.

---

### Ammunition System

Firearms use a magazine-based ammunition system. Available ammo types:

| Magazine / Shell Type | Compatible Weapons           |
|-----------------------|------------------------------|
| AK47 Magazine         | AK47                         |
| Pistol Magazine       | Pistol                       |
| MP5 Magazine          | MP5                          |
| Sniper Magazine       | Sniper Rifle                 |
| Shotgun Shells        | Shotgun                      |
| Revolver Cylinder     | Revolver                     |

#### Ammo Variants

Each magazine type is available in four variants:

| Variant        | Damage Modifier | Additional Effect             |
|----------------|-----------------|-------------------------------|
| Standard       | ×1.0            | None                          |
| Armor-Piercing | ×1.5            | Reduced physics interaction   |
| Tracer         | ×1.0            | Visible tracer particle trail |
| Rubber         | ×0.3            | Applies Slowness to target    |

---

### Fire Modes

Each firearm supports three fire modes:

| Mode       | Behavior                                      |
|------------|-----------------------------------------------|
| Single     | One shot per click; highest accuracy          |
| Burst      | 3 rounds per click                            |
| Full-Auto  | Continuous fire while the fire button is held |

Toggle fire modes with **Shift + Right-Click**.

---

### Accuracy & Recoil

- Each consecutive shot increases the spread by **+10%**.
- Accuracy resets to the base value after **2 seconds** without firing.
- Scopes and Laser attachments multiply the base accuracy.
- The Shotgun has a wide base spread that fires multiple pellets per shot.

---

## Controls

| Action                  | Default Key / Button                                    |
|-------------------------|---------------------------------------------------------|
| Fire                    | Right-Click                                             |
| Reload                  | R (short press, < 300 ms)                               |
| Select Ammo Type        | Hold R (> 300 ms) → scroll wheel to cycle → release R  |
| Switch Fire Mode        | Shift + Right-Click                                     |
| Throw Grenade           | Right-Click (grenade item)                              |
| Apply Attachment        | Right-Click attachment item onto gun in inventory       |

---

## Configuration

The mod can be configured via the in-game **Mod Configuration** menu (available through Forge's mod list) or by editing the configuration file at:

```
config/weaponmod-common.toml
```

Available options include maximum range adjustments per weapon type.

---

## Building from Source

**Prerequisites:** JDK 17, Git

```bash
# Clone the repository
git clone https://github.com/Minecraft425HD/weaponmod.git
cd weaponmod

# Build with Gradle
./gradlew build
```

The compiled JAR will be located at:

```
build/libs/weaponmod-1.0.0.jar
```

**Run in development environment:**

```bash
./gradlew runClient   # Start Minecraft client
./gradlew runServer   # Start dedicated server
```

---

## Project Structure

```
src/main/java/com/weaponmod/weaponmod/
├── WeaponMod.java          # Mod entry point
├── ModCreativeTabs.java    # Creative tab definition
├── ClientSetup.java        # Client-side initialization
├── attachment/             # Attachment system
├── config/                 # Configuration
├── entity/                 # Bullet & grenade entities
├── grenade/                # Grenade items
├── gun/                    # Firearm items
├── handler/                # Event handlers
├── item/                   # Item registry
├── melee/                  # Melee weapon items
├── network/                # Client-server packets
├── particle/               # Particle effects
├── render/                 # Custom renderers
├── skill/                  # Skill & XP system
├── sound/                  # Sound events
└── util/                   # Utility classes
```

---

## License

This project is licensed under the **GNU General Public License v3.0**.
See the [LICENSE](LICENSE) file for the full license text.

Key points:
- You may use, modify, and distribute this mod freely.
- Any modified versions must also be released under the GPL v3.0.
- The source code must always be made available.
- No warranty is provided.
