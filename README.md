# MoreStorageStack

A Fabric mod for Minecraft 26.1.1 that lets you adjust maximum item stack sizes up to 4096 in-game.

## Features
- Increase stack limits for stackable items (Ender Pearls, blocks, resources) up to 4096.
- 8 instant presets: `64`, `127`, `128`, `256`, `512`, `1024`, `2048`, and `4096`.
- Tools, weapons, and armor stay unstackable (stack size 1) to prevent gameplay issues.
- In-game GUI config screen.

## How to Use
Access the config screen in-game via **Mod Menu** or assign a hotkey under **Options -> Controls -> Key Binds -> MoreStorageStack**.

- **SAFE (64 – 127)**: Recommended for standard survival gameplay and server compatibility.
- **BULK (128 – 4096)**: High-capacity stack sizes for large storage setups.

## Configuration
Settings can be changed in-game or in `config/mss.properties`:

```properties
max_stack_size=64
```

> **Note:** Both client and server must have the mod installed with the same `max_stack_size` for proper inventory synchronization.

## Requirements
- Minecraft `26.1.1`
- Fabric Loader `>=0.16.0`
- Fabric API
- Java 25+

## Screenshots
Screenshots are available on the [Modrinth project page](https://modrinth.com/mod/morestoragestack).

## License
All Rights Reserved (ARR) — Copyright (c) 2026 Ruinerpie.