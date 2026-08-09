# semi-truck_tire

Semi-truck tire on a polished multi-slot rim.

## Files

| file | size |
| --- | --- |
| `block.obj` | 208623 B |
| `block.mtl` | 118 B |
| `item.json` | 1172 B |
| `../../../textures/block/semi-truck_tire/tire_0.png` | 1319 B |

`block.obj` and `tire_0.png` are uploaded through the web UI rather than
committed with the rest of the wheel, because the GitHub contents API is
text-only - the same limitation recorded in `soviet_wheel/README.md`. Worth
remembering whenever this wheel is regenerated: the two binaries do not travel
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
| faces | 762, all quads |
| vertices | 2936 |

Centred on 0.5 in every axis, so `offset` stays `Vec3.ZERO`. The mesh radius
keeps the same 8% margin under the registered radius that `soviet_wheel` uses.

Width over diameter is 0.32, which is about right for a 315/80R22.5 truck
casing (0.29).

### Orientation

The mesh is built with its outer face on +Y, which would normally call for
`AXLE_Y` (90) like the Blender wheels. In game that pointed the rim at the
suspension, so the item is registered with `AXLE_Y_FLIPPED` (270) instead, the
same constant the soviet meshes use. The mesh itself was not mirrored.

### Quads only

The OBJ must not contain a face with more than four corners. Minecraft renders
quads, and the NeoForge OBJ loader does not assemble a larger polygon: it drops
it silently, with no warning and no missing-model cube, so the face is simply
not there and you see through the model.

This wheel carried 30 such faces, two caps on each of its prisms: `hub_drum`,
`hub_back_boss`, `hub_plate`, `hub_axle_cap`, `rim_hand_hole_recess` and the
ten `hub_lug_nut` hexes. Every one of them was an open tube in game. The defect
was found on `ural_tire`, where the hub is larger and it was obvious; the same
caps here were quietly wrong for exactly the same reason.

`poly` now fans anything larger into quads, and `prism` routes its caps through
`poly` instead of appending them straight to the face list. The caps are planar
and convex, so the fan is exact and keeps the winding. No vertex moves; the
face count rose from 706 purely from retessellation, and the build now fails if
an oversized face ever reaches the file again.

## Texture

One 32x32 atlas, `tire_0.png`, four quadrants:

| region | contents |
| --- | --- |
| (0,0) - (16,16) | tread rubber |
| (16,0) - (32,16) | brushed chrome |
| (0,16) - (16,32) | dark recess, seen through the hand holes |
| (16,16) - (32,32) | machined steel, the brake drum on the reverse |

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
| `rim_back_ring` | annulus, r 2.60 - 5.20, the chrome visible on the reverse |
| `hub_drum` | 16-gon prism, r 2.62, recessed steel brake drum |
| `hub_back_boss` | octagonal prism, r 1.05, centre boss on the reverse |
| `rim_web` | 8 spokes bridging hub and face ring |
| `hub_plate` | octagonal prism, r 2.75 |
| `hub_lug_nut` | 10 hex prisms at r 2.05 |
| `hub_axle_cap` | octagonal prism, r 1.30 |

Radii are quoted in the original 16 px design space; the mesh is scaled by
1.82857 on the way out.

### The reverse side

The back of the rim was originally one solid dark plate (`rim_back_face`,
r 5.10) spanning the full bore. It hid the barrel completely and rendered as a
featureless black disc, which read in game as a hole in the model rather than
as the back of a wheel. It is now the three groups above: a chrome ring whose
bore reveals a recessed steel drum, with a small chrome boss at the centre.

`rim_back_ring` stops at r 5.20 on purpose. At r 5.30 its outer wall would be
exactly coincident with the inner wall of `tyre_carcass` and the two would
z-fight; at 5.20 the edge is buried inside `rim_barrel` instead.
