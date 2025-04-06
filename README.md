# MyRPG

A Minecraft plugin that allows you to create your own RPG game experience, including quests and custom mobs.

## Features

- Custom RPG class system
- Quest system
- Custom mobs
- Admin commands for RPG management

## Requirements

- Paper/Spigot server 1.21.4
- Java 21

## Installation

1. Download the latest release of MyRPG.jar
2. Place the jar file in your server's `plugins` folder
3. Restart your server
4. The plugin will generate default configuration files

## Commands

- `/rpgadmin` - Main administration command for the RPG system
- `/class` - Opens the class selection menu
- `/testcmd` - Test command for development purposes

## Permissions

- `rpg.admin` - Grants access to RPG administration commands (Default: op)

## Building from Source

This project uses Maven for dependency management and building.

1. Clone the repository
2. Run `mvn clean package`
3. The compiled jar will be in the `target` directory

## Dependencies

- Paper API 1.21.4-R0.1-SNAPSHOT

## Development

The project is structured as a Maven project with the following details:
- GroupId: com.github.ebjerke04
- ArtifactId: MyRPG
- Version: 1.0

## Resources

Special thanks to this website: https://nms.screamingsandals.org/comparison/mojang-to-spigot.html
 - Provided naming conversions between Mojang and Spigot mappings for NMS.

## Author

- Ethan Bjerke
