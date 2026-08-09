# ural_tire

Ural / KrAZ style military truck wheel: a tall deflatable-looking carcass with
very aggressive alternating lugs, a multi-piece steel rim in dark rusty metal,
and a protruding splined hub.

The tyre is black, the rim is dark rusty metal, and the tread is real geometry
rather than a texture trick.

## Registration

```java
URAL_TIRE = wheel("ural_tire",
        WheelDefinition.simple(1.09375F, WheelDefinition.AXLE_Y_FLIPPED, 0.65F));
```

The registered radius is one notch above the Offroad `tire` tier (0.96875) and
below `large_tire` (1.25), which makes it slightly larger than `semi-truck_tire`
as intended.

`AXLE_Y_FLIPPED` is required. The generator emits its meshes with the detailed
outer face on `+Y` after the export rotation, and `AXLE_Y` renders them facing
inwards.

## Mesh

| | |
|---|---|
| faces | 846, all quads |
| vertices | 3208 |
| scale factor | 2.02856 |
| mesh radius | 1.0120 blocks |
| bounds X | -0.4936 .. 1.4936 |
| bounds Y | 0.0918 .. 0.9082 |
| bounds Z | -0.4936 .. 1.4936 |
| width / diameter | 0.403 |
| `block.obj` | 229290 bytes, md5 `24acc120ea1f15aaf7595a40d3124b0e` |
| `tire_0.png` | 1587 bytes, md5 `f34ac92eeb47a43ad055879adb38d745` |

In OBJ space the axle runs along `+Y`, so the radial distance of a vertex is
`hypot(x - 0.5, z - 0.5)`.

`block.obj` and `tire_0.png` are uploaded through the web UI rather than
committed with the rest of the wheel, because the GitHub contents API is
text-only. Without them the item falls back to the missing-model cube.

## Quads only

The OBJ must not contain a face with more than four corners. Minecraft renders
quads, and the NeoForge OBJ loader does not assemble a larger polygon: it drops
it, silently, with no warning and no missing-model cube. The face is simply not
there, and you see through the model into whatever is behind it.

This is what made the hub, the bolts and the face dish transparent. Every
`prism` was writing its two caps as a single 16-gon, octagon or hexagon, so
every prism in the wheel was an open tube in game while looking perfectly solid
in any offline renderer that triangulates polygons on its own.

`poly` now fans anything larger into quads, and `prism` routes its caps through
`poly` rather than appending them to the face list directly, which is how they
had been bypassing the shared path in the first place. The caps are planar and
convex, so a fan from corner 0 is exact and keeps the original winding. Nothing
moves; the face count rose from 758 purely from retessellation.

## Geometry

Design space, centre `C = 8.0`, 16 units to the block, axle along `+Z`, low `z`
is the back of the wheel. These are the numbers before the 2.02856 scale.

| group | shape | radius | z |
|---|---|---|---|
| `tyre_carcass` | annulus | 4.40 .. 7.05 | 5.15 .. 10.85 |
| `tyre_tread_base` | annulus | 6.95 .. 7.35 | 5.20 .. 10.80 |
| `tyre_band_back` | annulus | 6.10 .. 6.80 | 4.98 .. 5.22 |
| `tyre_band_front` | annulus | 6.10 .. 6.80 | 10.78 .. 11.02 |
| `tyre_lug` x16 | rotated box | 7.00 .. 7.82, half-width 1.60 | even 5.25 .. 8.75, odd 7.25 .. 10.75 |
| `rim_barrel` | annulus | 3.00 .. 4.43 | 5.45 .. 10.55 |
| `rim_flange` | annulus | 3.80 .. 4.55 | 9.90 .. 10.60 |
| `rim_face_dish` | 16-gon | 3.90 | 9.30 .. 9.95 |
| `rim_back_ring` | annulus | 2.05 .. 4.35 | 5.30 .. 5.90 |
| `hub_drum` | 16-gon | 2.07 | 5.70 .. 7.20 |
| `hub_back_boss` | 8-gon | 1.10 | 5.42 .. 5.78 |
| `hub_back_bolt` x6 | 6-gon | 0.26 on a 1.55 ring | 5.55 .. 5.78 |
| `hub_bolt` x8 | 6-gon | 0.38 on a 2.85 ring | 9.93 .. 10.38 |
| `hub_flange` | 16-gon | 2.20 | 9.90 .. 10.15 |
| `hub_boss` | 16-gon | 1.90 | 10.10 .. 10.90 |
| `hub_cap` | 8-gon | 1.15 | 10.85 .. 11.25 |
| `hub_star` | 8-gon | 0.60 | 11.20 .. 11.42 |

The lugs alternate front and back around the wheel, which is what gives the
staggered military tread. Even and odd lugs interpenetrate in the shared band
`z 7.25 .. 8.75`; that is harmless because they have different orientations and
no coplanar faces. Two lugs of the same parity are 45 degrees apart and their
root half-angle is `asin(1.60 / 7.00) = 13.2 degrees`, so they never collide.

## Why the tread is closed

A ring of discrete lugs standing on open air is see-through: you look straight
between them and out the far side. This is what the first version of this mesh
got wrong.

On the midline between two neighbours, 11.25 degrees off each, a ray at radius
`r` is inside a lug while `r * sin(11.25) <= HALF` and
`LUG_R0 <= r * cos(11.25) <= LUG_R1`. So the lugs themselves close an annulus
from `LUG_R0 / cos(11.25)` outwards, and something continuous has to carry the
surface up to that inner edge.

Two conditions follow, and both are asserted at build time:

- **The base ring has to reach `LUG_R0 / cos(11.25) = 7.1371`.** The ring is a
  16-gon, not a circle, so what counts is its **apothem**, not its radius, and
  its edge midpoints land at exactly the same 11.25 degrees where the lugs
  start latest. `7.35 * cos(11.25) = 7.2088`, which clears 7.1371.
  Checking the circumradius instead is an off-by-an-apothem that leaves a
  0.075 px ring of daylight at radius 7.06.
- **`HALF` has to be at least `LUG_R1 * tan(11.25) = 1.5555`**, otherwise the
  lugs stop covering the midline before they reach their own outer face.
  `HALF = 1.60`.

The payoff is that closing the tread costs no relief at all. The escape is
angular, not radial: widening the lugs shuts every sightline while the grooves
stay exactly as deep and the front-to-back stagger is untouched.

The disc is therefore solid out to `LUG_R1 = 7.82` px, which is 0.9913 blocks,
or 97.95 percent of the outer radius. Beyond that the silhouette is scalloped,
because each lug has a flat outer face at 7.82 while its corners reach
`hypot(1.60, 7.82) = 7.982`. That scallop is the tread itself seen edge on, and
for 16 box lugs that fully close it cannot be smaller than `1 / cos(11.25)`,
about 2 percent of the radius.

## QA

`build_ural.py` refuses to emit a mesh that fails any of these:

- **no face has more than four corners**, because the loader drops those
- every face samples inside its own atlas tile
- no degenerate faces
- no two groups share a z plane over an overlapping radius, so nothing z-fights
- the two tread closure conditions above

`render_ural.py` renders `face`, `back`, `iso`, `back_iso` and `edge` with a
z-buffer and backface culling, and counts unpainted pixels inside 0.985 of the
radius. All five views report zero.

`diag.py` adds the three checks that the hole count cannot make:

- **manifold and winding**, per primitive: every directed edge must be matched
  by exactly one opposite edge. Catches missing faces and inverted windings.
- **see-through**, rasterising with culling off and flagging any pixel whose
  nearest surface faces away from the camera. Zero in every view.
- **background by radius**, which is what found the tread windows. The hole
  count and the see-through test both pass on a mesh with windows in it,
  because a window has geometry behind it and is not an inverted face. Only
  tallying background pixels by radius in the axial view shows daylight
  reaching in. It reports zero below 0.99, with the remainder confined to the
  0.99 to 1.02 scallop.

The reported tread overhang is `-0.0127` blocks on both sides, meaning the lugs
sit inside the sidewall bands rather than poking out past them.

One caveat worth keeping in mind: these renderers triangulate polygons
themselves, so for a while they were validating a mesh the game never loads.
That is why the quad rule is enforced in the builder rather than in the
renderer. An offline preview cannot see this class of bug at all.

## Textures

One 32x32 RGB atlas, `tire_0.png`, four 16x16 tiles, no alpha channel anywhere:

| tile | offset | used by |
|---|---|---|
| `rubber` | 0,0 | carcass, sidewall bands, lugs |
| `rust` | 16,0 | rim barrel, flange, face dish, back ring |
| `shadow` | 0,16 | tread base, so groove floors read as deep |
| `hub` | 16,16 | drum, bosses, bolts, cap, star |

Generated from `random.seed(7719)`, so the atlas is reproducible.

## Rebuilding

```
python3 build_ural.py && python3 render_ural.py && python3 diag.py
```

`build_ural.py` writes `block.obj`, `block.mtl` and `tire_0.png`. The OBJ writes
`vt u (1.0 - v)` because `item.json` sets `flip_v: true`.

Note that the renderer framing constants are tuned per wheel size. `SPAN` and
the hole radius both have to be retuned whenever `TARGET_R` changes, and the
hole radius in particular must not exceed `LUG_R1` in block units, or the
legitimate scallop between the lug corners gets counted as damage.
