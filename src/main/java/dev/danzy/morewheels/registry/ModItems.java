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
 *   <tr><th>wheel</th><th>radius</th><th>rotation</th><th>Offroad tier</th><th>mesh radius</th></tr>
 *   <tr><td>simple_wheel</td><td>0.75</td><td>270</td><td>small_tire (0.75)</td><td>0.717</td></tr>
 *   <tr><td>steel_small_wheel</td><td>0.75</td><td>90</td><td>small_tire (0.75)</td><td>0.750</td></tr>
 *   <tr><td>classic_wheel</td><td>0.75</td><td>90</td><td>small_tire (0.75)</td><td>0.750</td></tr>
 *   <tr><td>sport_wheel</td><td>0.75</td><td>90</td><td>small_tire (0.75)</td><td>0.750</td></tr>
 *   <tr><td>drag_wheel</td><td>0.75</td><td>90</td><td>small_tire (0.75)</td><td>0.750</td></tr>
 *   <tr><td>power_wheel</td><td>0.75</td><td>90</td><td>small_tire (0.75)</td><td>0.750</td></tr>
 *   <tr><td>spoke_wheel</td><td>0.75</td><td>90</td><td>small_tire (0.75)</td><td>0.750</td></tr>
 *   <tr><td>soviet_small_wheel</td><td>0.8</td><td>270</td><td>small_tire (0.75)</td><td>0.694</td></tr>
 *   <tr><td>soviet_wheel</td><td>0.96875</td><td>270</td><td>tire (0.96875)</td><td>0.896</td></tr>
 *   <tr><td>soviet_large_wheel</td><td>1.25</td><td>270</td><td>large_tire (1.25)</td><td>1.179</td></tr>
 *   <tr><td>semi-truck_tire</td><td>0.96875</td><td>270</td><td>tire (0.96875)</td><td>0.896</td></tr>
 *   <tr><td>ural_tire</td><td>1.09375</td><td>270</td><td>between tire and large_tire</td><td>1.012</td></tr>
 *   <tr><td>small-semi-truck_tire</td><td>0.8</td><td>270</td><td>small_tire (0.75)</td><td>0.740</td></tr>
 *   <tr><td>tractor_tire</td><td>1.25</td><td>270</td><td>large_tire (1.25)</td><td>1.156</td></tr>
 *   <tr><td>small-tractor_tire</td><td>0.96875</td><td>270</td><td>tire (0.96875)</td><td>0.896</td></tr>
 * </table>
 *
 * <p><b>The steel_small_wheel mesh family.</b> {@code steel_small_wheel}, {@code classic_wheel},
 * {@code sport_wheel}, {@code drag_wheel}, {@code power_wheel} and {@code spoke_wheel} all ship
 * the very same {@code block.obj} - the files are byte for byte identical - and differ only in
 * their 32x32 texture. They therefore share every number below: radius 0.75, rotation
 * {@link WheelDefinition#AXLE_Y} (90) because the mesh came out of Blender, and
 * minimum friction 0.5. Fixing the mesh means re-uploading it to all six folders.</p>
 *
 * <p><b>Scaled pairs.</b> {@code small-semi-truck_tire} is {@code semi-truck_tire} and
 * {@code small-tractor_tire} is {@code tractor_tire}, in each case the identical mesh written
 * out at a smaller target radius. The generator takes that radius as an argument, so the two
 * halves of a pair can never drift apart in topology or texture - only in size.</p>
 */
public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreWheels.MOD_ID);

    private static final List<WheelEntry> WHEELS = new ArrayList<>();

    /**
     * Simple rubber wheel with a bolted rim and a brake disc.
     * Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>The only wheel so far built from <b>three</b> textures - {@code med_tyre},
     * {@code rim_hub} and {@code bake_disc} - so its {@code block.mtl} keeps three
     * materials and its {@code item.json} declares three texture keys.</p>
     *
     * <p>The source mesh stood upright (axle along Z) and was centred on
     * (0.0, 0.75, 0.0555). It was rotated +90 degrees about X and translated so the axle
     * runs along +Y and the bounding box is centred on the block, matching every other
     * wheel; see the model README for the exact transform.</p>
     *
     * <p>Mesh bounds after the fix: 1.4334 x 0.5734 x 1.4334 blocks (22.9 x 9.2 x 22.9 px),
     * mesh radius 0.7167 - a 4% margin under the registered 0.75.</p>
     */
    public static final DeferredItem<Item> SIMPLE_WHEEL = wheel(
            "simple_wheel",
            WheelDefinition.simple(0.75F, WheelDefinition.AXLE_Y_FLIPPED, 0.55F)
    );

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
     * Spoked steel small wheel (belted tire, steel core, open spokes).
     * Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Mesh bounds: 1.5 x 0.6334 x 1.5 blocks (24.0 x 10.1 x 24.0 px), so the mesh
     * radius is exactly 0.75 and the registered radius matches it 1:1 - the tread meets
     * the ground with no fudge factor. Perfectly centred on 0.5 in X and Z.</p>
     *
     * <p>Authored in Blender rather than Blockbench, so its front face points the other
     * way than the soviet meshes: it uses {@link WheelDefinition#AXLE_Y} (90) instead of
     * {@code AXLE_Y_FLIPPED} (270), otherwise the spokes face the suspension.</p>
     *
     * <p>Its material placeholder is {@code #small_tire_rework}, so its {@code item.json}
     * uses that texture key instead of {@code tire_0}.</p>
     */
    public static final DeferredItem<Item> STEEL_SMALL_WHEEL = wheel(
            "steel_small_wheel",
            WheelDefinition.simple(0.75F, WheelDefinition.AXLE_Y, 0.5F)
    );

    /**
     * Classic road wheel - a retexture of the {@code steel_small_wheel} mesh.
     * Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Same geometry, same numbers, different 32x32 skin ({@code classic_tire}).</p>
     */
    public static final DeferredItem<Item> CLASSIC_WHEEL = wheel(
            "classic_wheel",
            WheelDefinition.simple(0.75F, WheelDefinition.AXLE_Y, 0.5F)
    );

    /**
     * Sport wheel - a retexture of the {@code steel_small_wheel} mesh.
     * Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Same geometry, same numbers, different 32x32 skin ({@code sport_tire}).</p>
     */
    public static final DeferredItem<Item> SPORT_WHEEL = wheel(
            "sport_wheel",
            WheelDefinition.simple(0.75F, WheelDefinition.AXLE_Y, 0.5F)
    );

    /**
     * Drag wheel - a retexture of the {@code steel_small_wheel} mesh.
     * Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Same geometry, same numbers, different 32x32 skin ({@code drag_tire}).</p>
     */
    public static final DeferredItem<Item> DRAG_WHEEL = wheel(
            "drag_wheel",
            WheelDefinition.simple(0.75F, WheelDefinition.AXLE_Y, 0.5F)
    );

    /**
     * Power wheel - a retexture of the {@code steel_small_wheel} mesh.
     * Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Same geometry, same numbers, different 32x32 skin ({@code power_tire}).</p>
     */
    public static final DeferredItem<Item> POWER_WHEEL = wheel(
            "power_wheel",
            WheelDefinition.simple(0.75F, WheelDefinition.AXLE_Y, 0.5F)
    );

    /**
     * Spoke wheel - a retexture of the {@code steel_small_wheel} mesh.
     * Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Same geometry, same numbers, different 32x32 skin ({@code spoke_tire}).</p>
     */
    public static final DeferredItem<Item> SPOKE_WHEEL = wheel(
            "spoke_wheel",
            WheelDefinition.simple(0.75F, WheelDefinition.AXLE_Y, 0.5F)
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

    /**
     * Semi-truck tire on a polished multi-slot rim - a 16-sided carcass with four rib
     * bands, a dished rim with eight webs and hand holes, an octagonal hub carrying ten
     * lug nuts and an axle cap. The reverse carries a chrome ring, a recessed steel brake
     * drum and a small centre boss. Offroad counterpart: {@code tire} (0.96875).
     *
     * <p>Generated parametrically and converted to OBJ rather than modelled by hand. The
     * mesh is built with its outer face on +Y, which would normally call for
     * {@code AXLE_Y} (90) like the Blender meshes, but in game that pointed the rim at the
     * suspension, so it is registered with {@link WheelDefinition#AXLE_Y_FLIPPED} (270)
     * like the soviet meshes instead.</p>
     *
     * <p>Mesh bounds: 1.792 x 0.6286 x 1.792 blocks (28.7 x 10.1 x 28.7 px), mesh radius
     * 0.896 - the same 8% margin under the registered radius that {@code soviet_wheel}
     * uses. Centred on 0.5 in all three axes, so {@code offset} stays {@code Vec3.ZERO}.
     * Width over diameter is 0.32, about right for a 315/80R22.5 casing.</p>
     *
     * <p>Built from a single 32x32 atlas - tread, chrome, dark recess and machined steel
     * packed into the four quadrants - so its {@code block.mtl} keeps one material and its
     * {@code item.json} declares one texture key, {@code tire_0}.</p>
     */
    public static final DeferredItem<Item> SEMI_TRUCK_TIRE = wheel(
            "semi-truck_tire",
            WheelDefinition.simple(0.96875F, WheelDefinition.AXLE_Y_FLIPPED, 0.6F)
    );

    /**
     * Ural military off-road tyre - a tall black carcass on a dark rusty multi-piece
     * steel rim. Sixteen tread lugs alternate which shoulder they overhang so the two
     * rows interlock across the crown, which is what separates a military tyre from the
     * semi's continuous ribbed band. The rim carries a bolt circle and a protruding hub;
     * the reverse carries a rusty ring, a recessed brake drum and six bolt heads.
     *
     * <p>Radius 1.09375 sits between Offroad's {@code tire} (0.96875) and
     * {@code large_tire} (1.25), about 13% larger than {@code semi-truck_tire}. Nothing
     * forces a wheel onto a tier - the radius is just a float on the tire component, and
     * {@code soviet_small_wheel} already runs 0.8.</p>
     *
     * <p>Mesh bounds: 2.0029 x 0.8202 x 2.0029 blocks, mesh radius 1.012 - the same 7.5%
     * margin under the registered radius that {@code soviet_wheel} and
     * {@code semi-truck_tire} use. Centred on 0.5 in all three axes, so {@code offset}
     * stays {@code Vec3.ZERO}. Width over diameter is 0.405, deliberately fatter than the
     * semi's 0.32 because the reference is a military truck casing.</p>
     *
     * <p>Same parametric generator as {@code semi-truck_tire}, so it needs
     * {@link WheelDefinition#AXLE_Y_FLIPPED} (270) rather than {@code AXLE_Y}. Minimum
     * friction 0.65, behind only the tractor tyres, on the strength of that tread.</p>
     *
     * <p>Built from a single 32x32 atlas - black rubber, rusty steel, groove shadow and a
     * lighter hub metal in the four quadrants - so its {@code block.mtl} keeps one
     * material and its {@code item.json} declares one texture key, {@code tire_0}.</p>
     */
    public static final DeferredItem<Item> URAL_TIRE = wheel(
            "ural_tire",
            WheelDefinition.simple(1.09375F, WheelDefinition.AXLE_Y_FLIPPED, 0.65F)
    );

    /**
     * Semi-truck tire one size down - the same mesh as {@code semi-truck_tire} written out
     * at 82.6% scale, so a rig can run smaller steer wheels and full-size drive wheels
     * from one visual family. Offroad counterpart: {@code small_tire} (0.75).
     *
     * <p>Registered at 0.8 rather than 0.75, following {@code soviet_small_wheel}, which is
     * already the lineup's precedent for a small wheel sitting slightly above its tier.
     * That keeps the 7.5% margin between mesh and registered radius every generated wheel
     * here uses, and it keeps the step down from {@code semi-truck_tire} modest.</p>
     *
     * <p>Mesh bounds: 1.4798 x 0.5191 x 1.4798 blocks, mesh radius 0.7399. Centred on 0.5
     * in all three axes, so {@code offset} stays {@code Vec3.ZERO}. Width over diameter is
     * unchanged from its parent, because the scaling is uniform.</p>
     *
     * <p>Identical topology to {@code semi-truck_tire} - 762 quads, one 32x32 atlas, one
     * material, one texture key {@code tire_0} - and identical friction at 0.6. Like its
     * parent it needs {@link WheelDefinition#AXLE_Y_FLIPPED} (270).</p>
     */
    public static final DeferredItem<Item> SMALL_SEMI_TRUCK_TIRE = wheel(
            "small-semi-truck_tire",
            WheelDefinition.simple(0.8F, WheelDefinition.AXLE_Y_FLIPPED, 0.6F)
    );

    /**
     * Agricultural tractor tyre - a very tall black carcass on a dished red steel rim,
     * carrying eleven deep chevron lugs. Offroad counterpart: {@code large_tire} (1.25),
     * which makes it the largest wheel in the lineup.
     *
     * <p><b>The tread.</b> Eleven chevrons, each a pair of arms climbing from opposite
     * shoulders and converging on the same angle at the crown, each arm a five step
     * staircase sweeping 30 degrees against a 32.7 degree lug pitch. That overlap is what
     * closes the tyre: viewed along the axle, consecutive lugs cover each other's gaps, so
     * the wheel is solid from every direction while still showing 16.9 degrees of clear
     * groove between neighbouring chevrons. Closure is verified numerically at build time
     * over 33 radii by 2160 angles rather than by a formula - an approximate formula is
     * exactly what once let a ring of daylight through {@code ural_tire}. Relief is 13.7%
     * of the wheel radius, roughly twice what the ural carries.</p>
     *
     * <p><b>Three tones of rubber.</b> Carcass, groove floor and a lighter worn crown on
     * the lugs. A tread moulded in one flat black only exists in the shading, and vanishes
     * completely head-on and in silhouette. Fitting that fifth tile is the only reason
     * this is the first wheel here on a 64x64 atlas instead of 32x32; it still keeps one
     * material and one texture key, {@code tire_0}.</p>
     *
     * <p>Mesh bounds: 2.312 x 0.8561 x 2.3091 blocks, mesh radius 1.156 - the usual 7.5%
     * margin under the registered radius. Centred on 0.5 in all three axes, so
     * {@code offset} stays {@code Vec3.ZERO}. Width over diameter is 0.37. Minimum
     * friction 0.7, the highest in the lineup, which is what a farm tyre is for.</p>
     *
     * <p>Same parametric generator family as {@code semi-truck_tire} and {@code ural_tire},
     * so it needs {@link WheelDefinition#AXLE_Y_FLIPPED} (270) rather than
     * {@code AXLE_Y}.</p>
     */
    public static final DeferredItem<Item> TRACTOR_TIRE = wheel(
            "tractor_tire",
            WheelDefinition.simple(1.25F, WheelDefinition.AXLE_Y_FLIPPED, 0.7F)
    );

    /**
     * The tractor tyre's front-axle partner - the same mesh written out at 77.5% scale,
     * for a tractor whose rear axle runs {@code tractor_tire}.
     * Offroad counterpart: {@code tire} (0.96875).
     *
     * <p>Identical topology, tread and atlas to {@code tractor_tire}; only the scale factor
     * differs, so the chevrons stay in proportion and the two read as one set.</p>
     *
     * <p>Mesh bounds: 1.792 x 0.6636 x 1.7897 blocks, mesh radius 0.896 - the same figure
     * as {@code soviet_wheel} and {@code semi-truck_tire}, at the same 7.5% margin under
     * the registered radius. Centred on 0.5 in all three axes, so {@code offset} stays
     * {@code Vec3.ZERO}. Width over diameter is 0.37 and minimum friction matches its
     * larger partner at 0.7.</p>
     */
    public static final DeferredItem<Item> SMALL_TRACTOR_TIRE = wheel(
            "small-tractor_tire",
            WheelDefinition.simple(0.96875F, WheelDefinition.AXLE_Y_FLIPPED, 0.7F)
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
