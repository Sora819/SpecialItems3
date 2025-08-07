# SamplePlugin
This is a prewritten plugin template with a lot of the basic functionalities already implemented

This version is made for Minecraft 1.21.8, Spigot

### The premade funtionalities:
- **Configuration management** with the ability to use multiple config files easily
- **Fully working localization** using the already mentioned configuration management (the localization is a separate thing in itself, but it already uses the config manager)
- **Auto-register command executors** based on plugin.yml, it takes the commands from it, capitalizes the first letter in the name and add "Command" at the end, then searches for a class in the commands package with that name, if found, it will be registered
- **Subcommand system** that enables you to easily create a main command that has subcommands with a lot of flexibility
- **Auto-register event listeners** in the listeners package
- Some **example items** to show basic usage of the prewritten components

### Configuration
- **config.yml**: [General configuration documentation](CONFIG.md)