# semi-truck_tire

Semi-truck tire on a polished multi-slot rim.

## Files

| file | size |
| --- | --- |
| `block.obj` | 184076 B |
| `block.mtl` | 118 B |
| `item.json` | 1172 B |
| `../../../textures/block/semi-truck_tire/tire_0.png` | 1062 B |

`block.obj` and `tire_0.png` were uploaded through the web UI rather than
committed with the rest of the wheel, because the GitHub contents API is
text-only - the same limitation recorded in `soviet_wheel/README.md`. Worth
remembering if this wheel is ever regenerated: the two binaries will not travel
with an ordinary API commit, and without them the item falls back to the
missing-model cube.

## Mesh

Generated parametrically rather than modelled by hand, then converted to OBJ.

| property | value |
| --- | --- |
| bounds X | -0.3960 .. 1.3960 (1.7920 blocks, 28.7 px) |
| bounds Y | 0.1857 .. 0.8143 (0.6286 blocks, 10.1 px) |
| bounds Z | -0.3960 .. 1.3960 (1.7920 blocks, 28.7 px) |
| mesh radius | 0.896 |
| registered radius | 0.96875 (`tire`) |
| faces | 632 |
| vertices | 2632 |
| axle | +Y, outer face up, so `AXLE_Y` (90) |

Centred on 0.5 in every axis, so `offset` stays `Vec3.ZERO`. The mesh radius
keeps the same 8% margin under the registered radius that `soviet_wheel` uses.

Width over diameter is 0.32, which is about right for a 315/80R22.5 truck
casing (0.29).

## Texture

One 32x32 atlas, `tire_0.png`:

| region | contents |
| --- | --- |
| (0,0) - (16,16) | tread rubber |
| (16,0) - (32,16) | brushed chrome |
| (0,16) - (16,32) | dark recess |

`block.mtl` keeps the `#tire_0` placeholder, which resolves against the
`textures` block of `item.json`.

## Parts

| group | geometry |
| --- | --- |
| `tyre_carcass` | 16-sided annulus, r 5.30 - 7.55 |
| `tyre_rib_a` .. `tyre_rib_d` | four rib bands, r 7.35 - 7.84 |
| `rim_barrel` | annulus, r 3.70 - 5.50 |
| `rim_face_ring` | annulus, r 4.05 - 5.35 |
| `rim_hand_hole_recess` | 16-gon prism, r 4.20 |
| `rim_back_face` | 16-gon prism, r 5.10 |
| `rim_web` | 8 spokes bridging hub and face ring |
| `hub_plate` | octagonal prism, r 2.75 |
| `hub_lug_nut` | 10 hex prisms at r 2.05 |
| `hub_axle_cap` | octagonal prism, r 1.30 |

Radii are quoted in the original 16 px design space; the mesh is scaled by
1.82857 on the way out.
