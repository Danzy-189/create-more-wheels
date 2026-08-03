package dev.danzy.morewheels.compat;

import com.mojang.serialization.JsonOps;
import dev.danzy.morewheels.MoreWheels;
import dev.danzy.morewheels.registry.ModItems;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

/**
 * Attaches Offroad's {@code offroad:tire} data component to our wheels without a
 * compile-time dependency on Offroad.
 *
 * <p>The component type is looked up in the frozen registry, and the value is
 * produced by feeding JSON through the type's own codec. If Offroad is missing
 * or its codec changes shape, we log and skip instead of crashing the game.</p>
 */
public final class TireComponentAttacher {
    public static final ResourceLocation TIRE_COMPONENT =
            ResourceLocation.fromNamespaceAndPath("offroad", "tire");

    private TireComponentAttacher() {
    }

    @SuppressWarnings("unchecked")
    public static void onModifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        DataComponentType<?> raw = BuiltInRegistries.DATA_COMPONENT_TYPE.get(TIRE_COMPONENT);
        if (raw == null) {
            MoreWheels.LOGGER.error("[More Wheels] {} is not registered - is Offroad installed? Wheels will be inert.",
                    TIRE_COMPONENT);
            return;
        }

        DataComponentType<Object> type = (DataComponentType<Object>) raw;

        for (ModItems.WheelEntry entry : ModItems.wheels()) {
            final Object value;
            try {
                value = type.codecOrThrow()
                        .parse(JsonOps.INSTANCE, entry.definition().toJson())
                        .getOrThrow();
            } catch (Exception exception) {
                MoreWheels.LOGGER.error("[More Wheels] failed to build tire data for {}",
                        entry.item().getId(), exception);
                continue;
            }

            event.modify(entry.item().get(), builder -> builder.set(type, value));
            MoreWheels.LOGGER.debug("[More Wheels] attached tire data to {}", entry.item().getId());
        }
    }
}
