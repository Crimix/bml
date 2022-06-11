[![](https://img.shields.io/endpoint?url=https%3A%2F%2Fcurseforge-badge-shields-io-caaw7pcenm0t.runkit.sh%2Fdownloads%3FprojectId%3D371791%26mode%3Dfull)](https://minecraft.curseforge.com/projects/bml)
[![](https://img.shields.io/endpoint?url=https%3A%2F%2Fcurseforge-badge-shields-io-caaw7pcenm0t.runkit.sh%2Fversions%3FprojectId%3D371791)](https://minecraft.curseforge.com/projects/bml)   
[![](https://img.shields.io/endpoint?url=https%3A%2F%2Fmodrinth-badge-shields-io-s1co4c2czdpy.runkit.sh/%2Fdownloads%3FprojectId%3DFbqCRYwN%26mode%3Dfull)](https://minecraft.curseforge.com/projects/bml)
[![](https://img.shields.io/endpoint?url=https%3A%2F%2Fmodrinth-badge-shields-io-s1co4c2czdpy.runkit.sh%2Fversions%3FprojectId%3DFbqCRYwN)](https://minecraft.curseforge.com/projects/bml)
# BML
BML or Black_dog's Modding Library is a mod I will be using as the core for my mods going forward.
It contains logic and utils that can ease my devloping of mods.

## Mixin Troubleshooting
Read this if you get crashes when depending on BML and trying to launch in-dev.
As a workaround, disable refmaps by defining the `mixin.env.disableRefMap`
JVM argument to `true`.

Using this under both the client and server run config
```
// Disable mixin refmaps
property 'mixin.env.disableRefMap', 'true'
```
