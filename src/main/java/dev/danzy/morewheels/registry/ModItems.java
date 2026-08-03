package dev.danzy.morewheels.registry;

import dev.danzy.morewheels.MoreWheels;
import dev.danzy.morewheels.content.WheelDefinition;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreWheels.MOD_ID);

    private static final List<WheelEntry> WHEELS = new ArrayList<>();

    /**
     * Soviet-style small off-road wheel. Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Verified in game with:
     * {@code /give @s offroad:small_tire[offroad:tire={radius:0.8f,rotation:[270.0d,0.0d,0.0d]}]}</p>
     *
     * <p>Measured OBJ bounds: 1.3875 x 0.635 x 1.3875 blocks (22.2 x 10.2 x 22.2 px).</p>
     */
    public static final DeferredItem<Item> SOVIET_SMALL_WHEEL = wheel(
            "soviet_small_wheel",
            WheelDefinition.simple(0.8F, WheelDefinition.AXLE_Y_FLIPPED, 0.6F)
    );

    /**
     * Soviet-style standard wheel. Offroad counterpart: {@code tire} (0.96875).
     *
     * <p>Measured OBJ bounds: 2.3588 x 0.7045 x 2.3588 blocks (37.7 x 11.3 x 37.7 px),
     * so the model's own radius is ~1.18 - noticeably bigger than vanilla {@code tire}.
     * The radius below follows the geometry so the tread meets the ground; drop it to
     * {@code 0.96875F} only if the model is scaled down to match.</p>
     */
    public static final DeferredItem<Item> SOVIET_WHEEL = wheel(
            "soviet_wheel",
            WheelDefinition.simple(1.18F, WheelDefinition.AXLE_Y_FLIPPED, 0.5F)
    );

    private ModItems() {
    }

    private static DeferredItem<Item> wheel(String name, WheelDefinition definition) {
        DeferredItem<Item> item = ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(16));
        WHEELS.add(new WheelEntry(item, definition));
        return item;
    }

    public static List<WheelEntry> wheels() {
        return List.copyOf(WHEELS);
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    public record WheelEntry(DeferredItem<Item> item, WheelDefinition definition) {
    }
}
