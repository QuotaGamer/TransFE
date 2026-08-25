package com.frostybadhalo.unife;

import com.frostybadhalo.unife.registry.UnifeBlockEntities;
import com.frostybadhalo.unife.registry.UnifeBlocks;
import com.frostybadhalo.unife.registry.UnifeCreativeTabs;
import com.frostybadhalo.unife.registry.UnifeItems;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;


@Mod(UniFE.MODID)
public class UniFE {
    public static final String MODID = "unife";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static float calculateVoltage(float FEBuffer, float maxVoltage, long maxBuffer, long minBuffer) {
        return FEBuffer >= minBuffer ? maxVoltage : (float) Math.sqrt(FEBuffer / minBuffer) * maxVoltage;
        // Change this how you want, this is just a lil equation I threw out to hopefully get this running
        // I left maxBuffer even though I'm not using it in case you want to make an equation that uses it,
        // since I know it'll likely be hard to read the code I've made as it's nothing like your code. Sorry?

        // This "random equation" was what started me doing all of this, by the way...
        // I noticed the converter wasn't acting how a converter should work, but more like a battery instead
        // (which does make sense, since there's a battery in the recipe), so I made it act more like a converter!
        // So now when charge is *almost empty*, the voltage goes down, rather than always. (excluding internal resistance, which *also* drops voltage)
    }

    public UniFE(IEventBus modEventBus, ModContainer modContainer) {
        UnifeBlocks.BLOCKS.register(modEventBus);
        UnifeItems.ITEMS.register(modEventBus);
        UnifeBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        UnifeCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::onRegisteringCapability);
    }

    public void onRegisteringCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, UnifeBlockEntities.IRON_FE_CONVERTER.get(), (be, c) -> be);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, UnifeBlockEntities.GOLD_FE_CONVERTER.get(), (be, c) -> be);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, UnifeBlockEntities.COPPER_FE_CONVERTER.get(), (be, c) -> be);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, UnifeBlockEntities.CREATIVE_FE_CONVERTER.get(), (be, c) -> be);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, UnifeBlockEntities.TFMG_FE_CONVERTER.get(), (be, c) -> be);
    }
}
