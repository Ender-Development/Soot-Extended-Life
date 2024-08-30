
// Auto generated groovyscript example file
// MODS_LOADED: soot

log.info 'mod \'soot\' detected, running script'

// Alchemical Mixer:
// The Alchemical Mixer is a device that can be used to mix fluids and ash to create new fluids. The Mixer requires a globe
// to be placed on top of it, and pedestals to be placed around it. The Mixer will then mix the fluids and ash in the
// pedestals to create new fluids.

mods.soot.alchemical_mixer.removeByOutput(fluid('antimony'))
// mods.soot.alchemical_mixer.removeAll()

mods.soot.alchemical_mixer.recipeBuilder()
    .fluidInput(fluid('water') * 10, fluid('lava') * 10, fluid('iron') * 16, fluid('gold') * 16)
    .fluidOutput(fluid('dawnstone') * 16)
    .setAspect('dawnstone', 1, 2)
    .setAspect('copper', 2, 3)
    .register()


// Still:
// The Still is a device that can be used to distill fluids. The Still requires a heat source to operate.

mods.soot.still.removeByInput(fluid('boiling_wort'))
mods.soot.still.removeByOutput(fluid('inner_fire'))
// mods.soot.still.removeAll()

mods.soot.still.recipeBuilder()
    .fluidInput(fluid('water') * 10)
    .input(item('minecraft:iron_ingot'))
    .fluidOutput(fluid('lava') * 10)
    .catalystConsumed(10)
    .register()

mods.soot.still.recipeBuilder()
    .fluidInput(fluid('iron') * 10)
    .fluidOutput(fluid('copper') * 2)
    .register()


// Still Catalyst:
// Tweak the values of various Catalysts used in the Still. If a catalyst is not defined, it will have a default value of
// 1000.

mods.soot.still_catalyst.removeCatalyst(ore('logWood'))
// mods.soot.still_catalyst.removeAll()

mods.soot.still_catalyst.addCatalyst(item('minecraft:glass'), 50)
mods.soot.still_catalyst.addCatalyst(item('minecraft:iron_ingot'), 600)

mods.soot.still_catalyst.getAmount(item('minecraft:snowball'))

