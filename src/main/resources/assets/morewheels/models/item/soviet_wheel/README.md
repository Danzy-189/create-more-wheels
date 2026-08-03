# soviet_wheel model files

`block.obj` and `../../../textures/block/soviet_wheel/tire_0.png` are binary/large assets
and are uploaded separately (see the repository README).

Measured bounds of `block.obj`:

| axis | min | max | size (blocks) | size (px) |
|------|-----|-----|---------------|-----------|
| X | -0.3963 | 1.3963 | 1.7927 | 28.7 |
| Y | 0.0498 | 0.5852 | 0.5354 | 8.6 |
| Z | -0.3963 | 1.3963 | 1.7927 | 28.7 |

Mesh radius 0.896, registered radius 0.96875 (matching Offroad's `tire`).
Horizontally centred on 0.5, so no `offset` is needed.

`block.mtl` must keep `map_Kd #tire_0`; the `#name` placeholder resolves against the
`textures` block of `item.json`.
