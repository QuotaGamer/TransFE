# TransFE

**TransFE** is a Minecraft compatibility mod that bridges FE (Forge Energy) power systems with the electrical simulation from [Create: Power Grid](https://github.com/patryk3211/PowerGrid) and [Create: The Factory Must Grow](https://github.com/DrMango14/Create-The_Factory_Must_Grow), allowing generators and machines that generate/consume FE, Power Grid Watts, or TFMG Watts to be powered by one, or multiple, of the others.

---

## Content

### TFMG to FE Converter
A block that reads FE energy from neighboring machines and converts it into proportional electrical voltage for the PowerGrid network.

- Compatible with any mod that exposes **IEnergyStorage** (e.g. TFMG Accumulators, [Create: New Age](https://gitlab.com/antarcticgardens/create-new-age)'s Generator multiblock)

---

## Dependencies

| Mod | Version |
|-----|---------|
| [Create](https://modrinth.com/mod/create) | 0.5.1 (1.20.1) / 6.0 (1.21.1) |
| [Create: Power Grid](https://github.com/patryk3211/PowerGrid) | 0.5.4+ |
| [Create: The Factory Must Grow](https://github.com/DrMango14/Create-The_Factory_Must_Grow) | 1.2.0+ |
---

## Supported Versions

| Minecraft | Loader    | Status      |
|-----------|-----------|-------------|
| 1.21.1    | NeoForge  | ✅ Stable    |

---

# How to use
- **Place** the converter in the world
- **Connect** it to your energy network using the terminals on the top (Power Grid) or Insulators/Cable Hubs (TFMG)
- (Optionally,) **Power** it with Redstone to turn it into a Consumer instead of a Generator
- **Enjoy!**

---

## How It Works

The FE Converters (in Generator mode) pull FE energy from adjacent blocks each tick and feeds into the realistic energy system of choice. The output voltage scales with how full the internal buffer is relative to the voltage cap.
The FE Converters (in Consumer mode) generate FE by pulling energy from the realistic energy system of choice.

---

## License

This project is licensed under the [MIT License](LICENSE), because I'm too lazy to change it to GPLv3.
