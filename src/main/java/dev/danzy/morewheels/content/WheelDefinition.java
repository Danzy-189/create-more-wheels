package dev.danzy.morewheels.content;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Mirror of Offroad's {@code TireLike} record.
 *
 * <p>We deliberately do not compile against Offroad. Instead the values are
 * serialised to JSON and parsed back with the codec that Offroad registered for
 * the {@code offroad:tire} data component, which keeps this addon buildable
 * without the Aeronautics source tree.</p>
 *
 * @param radius          wheel radius in blocks; drives suspension + rolling maths
 * @param rotation        euler degrees applied as X, then Y, then Z
 * @param offset          translation applied after the rotation
 * @param minimumFriction lower friction bound on the contact patch
 * @param model           optional partial model; null uses the item model
 */
public record WheelDefinition(
        float radius,
        Vec3 rotation,
        Vec3 offset,
        float minimumFriction,
        @Nullable ResourceLocation model
) {
    /** Default orientation for a wheel authored lying flat with its axle along +Y. */
    public static final Vec3 AXLE_Y = new Vec3(90.0D, 0.0D, 0.0D);

    /** Same as {@link #AXLE_Y} but flipped, so the outer face points outwards. */
    public static final Vec3 AXLE_Y_FLIPPED = new Vec3(270.0D, 0.0D, 0.0D);

    public static WheelDefinition simple(float radius, Vec3 rotation, float minimumFriction) {
        return new WheelDefinition(radius, rotation, Vec3.ZERO, minimumFriction, null);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("radius", this.radius);
        json.add("rotation", vec(this.rotation));
        json.add("offset", vec(this.offset));
        json.addProperty("minimumFriction", this.minimumFriction);
        if (this.model != null) {
            json.addProperty("model", this.model.toString());
        }
        return json;
    }

    private static JsonArray vec(Vec3 vec) {
        JsonArray array = new JsonArray();
        array.add(vec.x);
        array.add(vec.y);
        array.add(vec.z);
        return array;
    }
}
