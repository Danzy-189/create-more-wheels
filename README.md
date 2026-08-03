# Create: More Wheels

Addon for **Create: Aeronautics / Offroad** that adds extra wheels for the Wheel Mount.
The physics are Offroad's - this mod only contributes new models, sizes and tuning values.

| | |
|---|---|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.235+ |
| Requires | Create: Aeronautics (Offroad), Sable 2.0.0+ |
| Java | 21 |

## How it works

Offroad drives every wheel from a single data component, `offroad:tire`, holding a
`TireLike` record: `radius`, `rotation`, `offset`, `model`, `minimumFriction`.
`WheelMountInventory#canInsertItem` accepts **any** item carrying that component, and
`WheelMountRenderer` draws the item model. So an addon needs no mixins and no physics code.

This mod deliberately has **no compile dependency** on Offroad:

1. `WheelDefinition` mirrors `TireLike` and serialises itself to JSON.
2. `TireComponentAttacher` looks up `offroad:tire` in `BuiltInRegistries.DATA_COMPONENT_TYPE`
   and parses that JSON with the component's own codec.
3. `ModifyDefaultComponentsEvent` attaches the result to every registered wheel.

Result: `gradle build` works on a clean machine without the Aeronautics source tree or
any private Maven, which is what makes CI builds fast.

## Adding a wheel

One line in `ModItems`:

```java
public static final DeferredItem<Item> MY_WHEEL = wheel(
        "my_wheel",
        WheelDefinition.simple(1.1F, WheelDefinition.AXLE_Y, 0.5F)
);
```

Then add, using `soviet_small_wheel` as the template:

- `assets/morewheels/models/item/my_wheel.json` - one-line parent wrapper
- `assets/morewheels/models/item/my_wheel/item.json` - OBJ loader + display transforms
- `assets/morewheels/models/item/my_wheel/block.obj` + `block.mtl`
- `assets/morewheels/textures/block/my_wheel/tire_0.png`
- lang entries and a recipe

### Orientation cheat sheet

`WheelMountRenderer` always spins around **Z**; `rotation` only has to bring the axle onto Z.
Euler order is X, then Y, then Z, and the matrices compose so Y is applied to the model first -
which is why a 180 on Y can never flip a wheel authored with its axle on Y.

| model axle | rotation |
|---|---|
| +Y, outer face up | `[90, 0, 0]` (`AXLE_Y`) |
| +Y, outer face down | `[270, 0, 0]` (`AXLE_Y_FLIPPED`) |
| +Z | `[0, 0, 0]` |
| +X | `[0, 90, 0]` |

## Reference wheel sizes (Offroad)

| tire | radius | diameter (px) |
|---|---|---|
| small_tire | 0.75 | 24 |
| tire | 0.96875 | 31 |
| large_tire | 1.25 | 40 |
| monstrous_tire | 2.0 | 64 |
| soviet_small_wheel (this mod) | 0.8 | 25.6 |

## Building

CI builds every push and uploads the jar as an artifact - see the **Build** workflow.
Push a `v*` tag to cut a GitHub release.

Locally, the repo intentionally ships **no** `gradle-wrapper.jar`. Generate the wrapper once:

```bash
gradle wrapper --gradle-version 8.12
./gradlew build
```

Run the game with `./gradlew runClient`. Note that Create, Sable and Offroad are not on the
runtime classpath by default - drop them into `run/mods/` to test wheels in a real game.

## Fast iteration without rebuilding

Geometry, scale and orientation can be tuned live with a resource pack that overrides an
existing Offroad tire, plus:

```
/give @s offroad:small_tire[offroad:tire={radius:0.8f,rotation:[270.0d,0.0d,0.0d]}]
```

Edit the OBJ, press F3+T, no restart. Move the verified numbers into `ModItems` afterwards.

## Missing binary assets

`block.obj` and `tire_0.png` for `soviet_small_wheel` must be uploaded manually (the GitHub
contents API used to bootstrap this repo is text-only). Drop them at:

```
src/main/resources/assets/morewheels/models/item/soviet_small_wheel/block.obj
src/main/resources/assets/morewheels/textures/block/soviet_small_wheel/tire_0.png
```

## License

MIT for this addon's code. Offroad / Aeronautics / Sable remain under their own licenses -
Sable in particular is PolyForm Shield 1.0.0, so do not vendor their sources here.
