# soviet_large_wheel model files

This is the mesh that was briefly registered as `soviet_wheel`; it was promoted to the
large tier once a smaller mesh arrived.

`block.obj` and `../../../textures/block/soviet_large_wheel/tire_0.png` are binary/large
assets and are uploaded separately (see the repository README).

Measured bounds of `block.obj`:

| axis | min | max | size (blocks) | size (px) |
|------|-----|-----|---------------|-----------|
| X | -0.6794 | 1.6794 | 2.3588 | 37.7 |
| Y | -0.0347 | 0.6697 | 0.7045 | 11.3 |
| Z | -0.6794 | 1.6794 | 2.3588 | 37.7 |

Mesh radius 1.179, registered radius 1.25 (matching Offroad's `large_tire`).
Horizontally centred on 0.5, so no `offset` is needed.

`block.mtl` must keep `map_Kd #tire_0`; the `#name` placeholder resolves against the
`textures` block of `item.json`.
