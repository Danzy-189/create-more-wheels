package dev.danzy.morewheels;

import com.mojang.logging.LogUtils;
import dev.danzy.morewheels.compat.TireComponentAttacher;
import dev.danzy.morewheels.registry.ModCreativeTabs;
import dev.danzy.morewheels.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(MoreWheels.MOD_ID)
public class MoreWheels {
    public static final String MOD_ID = "morewheels";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreWheels(IEventBus modBus) {
        ModItems.register(modBus);
        ModCreativeTabs.register(modBus);

        // Attaches the offroad:tire component to every registered wheel once
        // all registries are frozen.
        modBus.addListener(TireComponentAttacher::onModifyDefaultComponents);

        LOGGER.info("[More Wheels] initialised with {} wheel(s)", ModItems.wheels().size());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
