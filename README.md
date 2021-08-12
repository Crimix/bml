[![](http://cf.way2muchnoise.eu/full_371791_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/bml)
[![](http://cf.way2muchnoise.eu/versions/371791.svg)](https://www.curseforge.com/minecraft/mc-mods/bml)
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
