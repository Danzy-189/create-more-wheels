# soviet_small_wheel model files

`block.obj` and `../../../textures/block/soviet_small_wheel/tire_0.png` are binary/large
assets and are uploaded separately (see the repository README).

Measured bounds of `block.obj`:

| axis | min | max | size (blocks) | size (px) |
|------|-----|-----|---------------|-----------|
| X | -0.2563 | 1.1313 | 1.3875 | 22.2 |
| Y | 0.0625 | 0.6975 | 0.6350 | 10.2 |
| Z | -0.2563 | 1.1313 | 1.3875 | 22.2 |

Geometric radius is ~0.694 blocks; the shipped `radius` is `0.8`.

`block.mtl` must keep `map_Kd #tire_0` - the `#name` placeholder is resolved from
the `textures` block of `item.json`. A real file path will not load.
