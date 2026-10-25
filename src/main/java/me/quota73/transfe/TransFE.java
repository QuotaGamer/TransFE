package me.quota73.transfe;

import me.quota73.transfe.registry.TransFEBlockEntities;
import me.quota73.transfe.registry.TransFEBlocks;
import me.quota73.transfe.registry.TransFECreativeTabs;
import me.quota73.transfe.registry.TransFEItems;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;


@Mod(TransFE.MODID)
public class TransFE {
    public static final String MODID = "transfe";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static float calculateVoltage(float FEBuffer, float maxVoltage, long maxBuffer, long minBuffer) {
        return FEBuffer >= minBuffer ? maxVoltage : (float) Math.sqrt(FEBuffer / minBuffer) * maxVoltage;
    }

    public TransFE(IEventBus modEventBus, ModContainer modContainer) {
        TransFEBlocks.BLOCKS.register(modEventBus);
        TransFEItems.ITEMS.register(modEventBus);
        TransFEBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        TransFECreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::onRegisteringCapability);
    }

    public void onRegisteringCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TransFEBlockEntities.FE_TO_POWER_GRID_CONVERTER.get(), (be, c) -> be);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TransFEBlockEntities.TFMG_FE_CONVERTER.get(), (be, c) -> be);
    }
}
