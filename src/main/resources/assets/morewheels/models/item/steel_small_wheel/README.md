# steel_small_wheel model files

`block.obj` and `../../../textures/block/steel_small_wheel/small_tire_rework.png` are
uploaded separately (see the repository README).

Measured bounds of `block.obj`:

| axis | min | max | size (blocks) | size (px) |
|------|-----|-----|---------------|-----------|
| X | -0.2500 | 1.2500 | 1.5000 | 24.0 |
| Y | 0.1875 | 0.8209 | 0.6334 | 10.1 |
| Z | -0.2500 | 1.2500 | 1.5000 | 24.0 |

Mesh radius is exactly **0.75**, matching Offroad's `small_tire`, so the registered
radius is 0.75 with no margin. Centred on 0.5 in X and Z, so no `offset` is needed.

Objects: `belt_tire`, `core`, `spokes`. 116 verts / 128 faces - by far the cheapest
mesh in the set.

## Orientation

This mesh uses `rotation = [90, 0, 0]` (`WheelDefinition.AXLE_Y`), **not** the `[270, 0, 0]`
the soviet meshes use. It was authored in Blender, whose default axis handedness leaves the
decorated face pointing the opposite way, so 270 buries the spokes against the suspension.

## Texture key is different

This mesh came out of Blender with the material placeholder `#small_tire_rework`
(not `#tire_0`), so `item.json` declares:

```json
"textures": { "small_tire_rework": "morewheels:block/steel_small_wheel/small_tire_rework" }
```

The key in `item.json` must match the `map_Kd #<key>` placeholder in `block.mtl`
exactly, otherwise the model renders untextured.

The texture is a 32x32 PNG and is already at its native resolution - do not rescale it.
