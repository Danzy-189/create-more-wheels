package dev.danzy.morewheels.registry;

import dev.danzy.morewheels.MoreWheels;
import dev.danzy.morewheels.content.WheelDefinition;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * The wheel lineup. Radii mirror Offroad's own tiers so the wheels are drop-in
 * alternatives to the vanilla tires.
 *
 * <table>
 *   <tr><th>wheel</th><th>radius</th><th>Offroad tier</th><th>mesh radius</th></tr>
 *   <tr><td>soviet_small_wheel</td><td>0.8</td><td>small_tire (0.75)</td><td>0.694</td></tr>
 *   <tr><td>soviet_wheel</td><td>0.96875</td><td>tire (0.96875)</td><td>0.896</td></tr>
 *   <tr><td>soviet_large_wheel</td><td>1.25</td><td>large_tire (1.25)</td><td>1.179</td></tr>
 * </table>
 */
public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreWheels.MOD_ID);

    private static final List<WheelEntry> WHEELS = new ArrayList<>();

    /**
     * Soviet-style small off-road wheel. Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Verified in game with:
     * {@code /give @s offroad:small_tire[offroad:tire={radius:0.8f,rotation:[270.0d,0.0d,0.0d]}]}</p>
     *
     * <p>Mesh bounds: 1.3875 x 0.635 x 1.3875 blocks (22.2 x 10.2 x 22.2 px).</p>
     */
    public static final DeferredItem<Item> SOVIET_SMALL_WHEEL = wheel(
            "soviet_small_wheel",
            WheelDefinition.simple(0.8F, WheelDefinition.AXLE_Y_FLIPPED, 0.6F)
    );

    /**
     * Soviet-style standard wheel. Offroad counterpart: {@code tire} (0.96875).
     *
     * <p>Mesh bounds: 1.7927 x 0.5354 x 1.7927 blocks (28.7 x 8.6 x 28.7 px),
     * mesh radius 0.896 - an 8% margin under the registered radius, the same
     * relationship the small wheel was tuned with.</p>
     */
    public static final DeferredItem<Item> SOVIET_WHEEL = wheel(
            "soviet_wheel",
            WheelDefinition.simple(0.96875F, WheelDefinition.AXLE_Y_FLIPPED, 0.5F)
    );

    /**
     * Soviet-style large wheel. Offroad counterpart: {@code large_tire} (1.25).
     *
     * <p>Mesh bounds: 2.3588 x 0.7045 x 2.3588 blocks (37.7 x 11.3 x 37.7 px),
     * mesh radius 1.179.</p>
     */
    public static final DeferredItem<Item> SOVIET_LARGE_WHEEL = wheel(
            "soviet_large_wheel",
            WheelDefinition.simple(1.25F, WheelDefinition.AXLE_Y_FLIPPED, 0.55F)
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
