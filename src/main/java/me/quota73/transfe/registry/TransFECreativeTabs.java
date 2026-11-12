package me.quota73.transfe.registry;

import me.quota73.transfe.TransFE;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFECreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TransFE.MODID);
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> CONVERTER_TAB = null;


    public static void makeEntries() {
        if (CONVERTER_TAB != null) return;
        CONVERTER_TAB = CREATIVE_MODE_TABS.register("converters", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.feConverters"))
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon(() -> {
                    if (TransFEItems.POWERGRID_FE_CONVERTER != null) return TransFEItems.POWERGRID_FE_CONVERTER.get().getDefaultInstance();
                    if (TransFEItems.TFMG_FE_CONVERTER != null) return TransFEItems.TFMG_FE_CONVERTER.get().getDefaultInstance();
                    return null;
                })
                .displayItems((parameters, output) -> {
                    if (TransFEItems.POWERGRID_FE_CONVERTER != null) output.accept(TransFEItems.POWERGRID_FE_CONVERTER);
                    if (TransFEItems.TFMG_FE_CONVERTER != null) output.accept(TransFEItems.TFMG_FE_CONVERTER);
                }).build());
    }
}
