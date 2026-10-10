package me.quota73.transfe.registry;

import me.quota73.transfe.TransFE;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFECreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TransFE.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONVERTER_TAB = CREATIVE_MODE_TABS.register("converters", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.feConverters"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> TransFEItems.IRON_FE_CONVERTER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(TransFEItems.IRON_FE_CONVERTER);
                output.accept(TransFEItems.GOLD_FE_CONVERTER);
                output.accept(TransFEItems.COPPER_FE_CONVERTER);
                output.accept(TransFEItems.CREATIVE_FE_CONVERTER);
                output.accept(TransFEItems.TFMG_FE_CONVERTER);
            }).build());
}
