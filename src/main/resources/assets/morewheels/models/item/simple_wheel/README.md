# simple_wheel model files

`block.obj` and the three PNGs in `../../../textures/block/simple_wheel/` are uploaded
separately (see the repository README).

## The source mesh was standing up - it had to be fixed

The file exported from Blockbench (`med_simple_wheel.obj`) had its **axle along Z**, i.e.
the wheel stood upright like a coin on a table, and it was centred on
`(0.0, 0.75, 0.0555)` instead of the block centre. Every other wheel in this mod - and
what the Offroad renderer and the item display transforms expect - has the axle along
**+Y** with the mesh centred on the block.

Rather than compensating with `rotation`/`offset` at runtime (which would fight the spin
animation, since spin happens around the model's own axle), the geometry was baked:

1. Rotate +90 degrees about X: `(x, y, z) -> (x, -z, y)`
2. Translate the bounding box centre onto `(0.5, 0.5, 0.5)`

Vertex normals were rotated with the same matrix. `mtllib` was repointed at `block.mtl`.

Bounds of the committed `block.obj`:

| axis | min | max | size (blocks) | size (px) |
|------|-----|-----|---------------|-----------|
| X | -0.2167 | 1.2167 | 1.4334 | 22.9 |
| Y | 0.2133 | 0.7867 | 0.5734 | 9.2 |
| Z | -0.2167 | 1.2167 | 1.4334 | 22.9 |

Mesh radius 0.7167, registered radius 0.75 (Offroad's `small_tire` tier) - a 4% margin.
96 verts / 72 faces.

## Three textures

This is the first wheel with more than one material:

| material | placeholder | texture |
|----------|-------------|---------|
| `m_ffd78dad-8b29-8f58-4dbf-879be810cecb` | `#med_tyre` | `med_tyre.png` |
| `m_2a62227a-1d2a-ebbd-6586-29ef85df6db5` | `#rim_hub` | `rim_hub.png` |
| `m_09412423-3297-4f41-b0df-1ef7ada589f1` | `#bake_disc` | `bake_disc.png` |

The material names must match the `usemtl` lines in `block.obj` exactly, and every
placeholder needs a matching key in `item.json`. If one key is missing the whole model
falls back to the missing texture.

All three PNGs are 32x32 and already at native resolution - do not rescale them.
