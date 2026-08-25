package com.frostybadhalo.unife.registry;

import com.frostybadhalo.unife.UniFE;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UnifeCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UniFE.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONVERTER_TAB = CREATIVE_MODE_TABS.register("converters", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.feConverters"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> UnifeItems.IRON_FE_CONVERTER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(UnifeItems.IRON_FE_CONVERTER);
                output.accept(UnifeItems.GOLD_FE_CONVERTER);
                output.accept(UnifeItems.COPPER_FE_CONVERTER);
                output.accept(UnifeItems.CREATIVE_FE_CONVERTER);
                output.accept(UnifeItems.TFMG_FE_CONVERTER);
            }).build());
}
