# ural_tire

Ural military off-road tyre: a tall black carcass on a dark rusty multi-piece
steel rim. Generated parametrically by `build_ural.py` rather than modelled by
hand, then written straight out as OBJ.

## Files

| file | size | notes |
| --- | --- | --- |
| `block.obj` | 224472 B | 758 faces, 3208 vertices |
| `block.mtl` | 112 B | one material, `map_Kd #tire_0` |
| `item.json` | 1156 B | `neoforge:obj` loader, `flip_v: true` |
| `../ural_tire.json` | 47 B | stub pointing at `item.json` |
| `../../../textures/block/ural_tire/tire_0.png` | 1587 B | 32x32 atlas |

## Size

Registered radius **1.09375**, mesh radius **1.012** - a 7.5% margin, the same
relationship `soviet_wheel` and `semi-truck_tire` use. That sits between
Offroad's `tire` (0.96875) and `large_tire` (1.25), about 13% larger than
`semi-truck_tire`. Nothing forces a wheel onto a tier: the radius is just a
float on the tire component, and `soviet_small_wheel` already runs 0.8.

Mesh bounds 2.0029 x 0.8202 x 2.0029 blocks, centred on 0.5 in all three axes,
so `offset` stays `Vec3.ZERO`. Width over diameter is 0.405, deliberately fatter
than the semi's 0.32 because the reference is a military truck casing.

## Orientation

Registered with `AXLE_Y_FLIPPED` (270), not `AXLE_Y` (90). The generator builds
the mesh with its outer face on +Y, which looks like it should call for
`AXLE_Y`, but `semi-truck_tire` shipped that way and mounted with its face
pointing at the suspension. This generator produces flipped-convention meshes.
Do not "correct" this to 90.

## Texture atlas

One 32x32 atlas split into four 16x16 quadrants:

| quadrant | offset | used by |
| --- | --- | --- |
| rubber | (0, 0) | carcass, sidewall bands, tread lugs |
| rust | (16, 0) | rim barrel, flange, face dish, reverse ring |
| shadow | (0, 16) | the groove floor between the lugs |
| hub | (16, 16) | bolts, hub boss, cap, brake drum |

Rubber sits at base 23 so the carcass reads as properly black rather than dark
grey. Rust is brown oxide over dark metal with heavier blooms and a few bare
patches. The hub tile is deliberately lighter than the rust: caps facing away
from the light pick up ambient only, and a darker hub turned the reverse into a
flat dark disc.

## Parts

| group | primitive | notes |
| --- | --- | --- |
| `tyre_carcass` | annulus 4.40-7.05, z 5.35-10.65 | tall sidewall; the rim reaches only 55% of the radius |
| `tyre_groove_floor` | annulus 7.00-7.38 | carries the shadow tile, only ever seen between lugs |
| `tyre_band_back` / `tyre_band_front` | annulus 6.10-6.80 | stands in for the moulded lettering ring |
| `tyre_lug` x16 | rotbox 7.02-7.90 | alternately pushed to each shoulder, 0.40 overhang |
| `rim_barrel` | annulus 3.00-4.43 | |
| `rim_flange` | annulus 3.80-4.55, z 9.90-10.60 | outer lip |
| `rim_face_dish` | 16-gon r 3.90 | solid disc, no hand holes - the reference rim is closed |
| `hub_bolt` x8 | hex r 0.38 on a 2.85 bolt circle | |
| `hub_flange` / `hub_boss` / `hub_cap` / `hub_star` | stacked prisms | the protruding centre |
| `rim_back_ring` | annulus 2.05-4.35 | |
| `hub_drum` | 16-gon r 2.07 | recessed brake drum |
| `hub_back_boss` / `hub_back_bolt` x6 | prisms | relief so the reverse reads as metal |

## The tread

Sixteen lugs, alternating between z 4.95-8.58 and z 7.42-11.05, so each one
overhangs its own shoulder by 0.40 and the two rows interlock across the crown.
That stagger is the whole point; a continuous ribbed band, like the semi's,
reads as a road tyre instead of a military one.

Viewed exactly along the axle, the gaps between lugs show background. That is
not a hole in the mesh - any lugged tread has open shoulder grooves, and the
scalloped silhouette is what makes the tread legible at item scale. The QA hole
metric therefore samples at 0.90 blocks, inside the groove floor at 0.936, so it
still catches a genuine puncture without flagging the tread itself.

## Regenerating

```
python3 build_ural.py     # writes out/block.obj, out/block.mtl, out/tire_0.png
python3 render_ural.py    # face, back, iso, back_iso and edge QA views
```

The build fails loudly on a UV that escapes its atlas quadrant, on a degenerate
face, and on two caps sharing a z plane with overlapping radii. That last check
was added after `semi-truck_tire` had to have its reverse rebuilt, and it is the
reason this wheel's back plate and brake drum do not z-fight.
