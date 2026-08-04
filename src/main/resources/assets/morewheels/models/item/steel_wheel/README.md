# steel_wheel model files

`block.obj` is uploaded separately (see the repository README). This wheel ships **no
texture of its own** - it reuses `../../../textures/block/steel_small_wheel/small_tire_rework.png`.

Measured bounds of `block.obj`:

| axis | min | max | size (blocks) | size (px) |
|------|-----|-----|---------------|-----------|
| X | -0.6247 | 1.6247 | 2.2494 | 36.0 |
| Y | 0.1303 | 0.8801 | 0.7498 | 12.0 |
| Z | -0.6247 | 1.6247 | 2.2494 | 36.0 |

Mesh radius is **1.1247**, so the registered radius is 1.125 - a 1:1 match with the
geometry, same approach as `steel_small_wheel`. Centred on 0.5 in X and Z, so no
`offset` is needed.

This is a uniform 1.5x scale-up of `steel_small_wheel`: identical 116 verts / 128 faces
and the same three objects (`belt_tire`, `core`, `spokes`).

## Orientation

`rotation = [90, 0, 0]` (`WheelDefinition.AXLE_Y`), the same as `steel_small_wheel`.
Blender-authored meshes need 90; the Blockbench-authored soviet meshes need 270.

## Material name has a .001 suffix

The OBJ references `m_a1331e58-40a9-8096-4155-344fe1c3d5c6.001` (Blender de-duplicated the
material when the mesh was copied), so `block.mtl` declares that exact name including the
suffix. The original export also pointed `map_Kd` at a real file name
(`steel_small_tire.png`) instead of a `#key` placeholder; that has been replaced with
`#small_tire_rework` so the NeoForge OBJ loader resolves the texture through `item.json`.
