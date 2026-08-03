# soviet_wheel model files

`block.obj` and `../../../textures/block/soviet_wheel/tire_0.png` are binary/large assets
and are uploaded separately (see the repository README).

Measured bounds of `block.obj`:

| axis | min | max | size (blocks) | size (px) |
|------|-----|-----|---------------|-----------|
| X | -0.6794 | 1.6794 | 2.3588 | 37.7 |
| Y | -0.0347 | 0.6697 | 0.7045 | 11.3 |
| Z | -0.6794 | 1.6794 | 2.3588 | 37.7 |

Horizontally centred on 0.5, so no `offset` is needed.

Model radius ~1.18 vs Offroad's `tire` at 0.96875. The registered radius follows the
model. To make it a true drop-in for vanilla `tire`, scale the mesh by 0.82 in Blockbench
(`Edit -> Scale`, pivot `Origin`) and set the radius to `0.96875F`.

`block.mtl` must keep `map_Kd #tire_0`; the `#name` placeholder resolves against the
`textures` block of `item.json`.
