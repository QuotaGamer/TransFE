# TransFE

**TransFE** is a Minecraft compatibility mod that bridges FE (Forge Energy) power systems with the electrical simulation from [Create: Power Grid](https://github.com/patryk3211/PowerGrid) and [Create: The Factory Must Grow (TFMG)](https://github.com/DrMango14/Create-The_Factory_Must_Grow), allowing machines that generate/consume FE or Power Grid/TFMG power to use the others.

---

## Content

### TFMG to FE Converter
When unpowered, it is a block that pulls FE from neighboring generators and converts it into proportional electrical power for the TFMG network.
When powered, it is a block that pulls electrical power from connected TFMG generators, and converts it into proportional FE.

### Power Grid to FE Converter
When unpowered, it is a block that pulls FE from neighboring generators and converts it into proportional electrical power for the Power Grid network.
When powered, it is a block that pulls electrical power from connected Power Grid generators, and converts it into proportional FE.

- Both are compatible with any mod that has blocks exposing **IEnergyStorage** for FE input/output. (e.g. TFMG's Accumulators and [Create: New Age](https://gitlab.com/antarcticgardens/create-new-age)'s Generator multiblock)

---

## Dependencies

| Mod | Version |
|-----|---------|
| [Create](https://modrinth.com/mod/create) | 6.0.9-215 or newer (older might work too) |
| [Create: Power Grid](https://github.com/patryk3211/PowerGrid) | 0.5.4 or newer (older might work too) |
| [Create: The Factory Must Grow](https://github.com/DrMango14/Create-The_Factory_Must_Grow) | 1.2.0 or newer (older might work too but honestly tfmg is so buggy you shouldn't go newer or older) |

---

## Supported Versions

| Minecraft | Loader    | Status      |
|-----------|-----------|-------------|
| 1.21.1    | NeoForge  | ✅ Stable    |

---

## How to use
- **Place** the converter in the world
- **Connect** it to your energy network using the terminals on the top (Power Grid) or Insulators/Cable Hubs (TFMG)
- (Optionally,) **Power** it with Redstone to turn it into a Consumer instead of a Generator
- **Enjoy!**

---

## How It Works

The FE Converters (in Generator mode) pull FE energy from adjacent blocks each tick and feed it into the realistic energy system of choice.
The FE Converters (in Consumer mode) generate FE by pulling energy from the realistic energy system of choice.

---

## License

This project is licensed under the [GNU General Public License (Version 3)](LICENSE), because it's cute like me :3

---

## Thanks
Thank you to [KLIPZzz](https://github.com/KILPZzz) for making [UniFE](https://github.com/KILPZzz/UniFE) which inspired me to build this off of his shortcomings.
