# PvZCraft

## 01/05/2026 - 03/05/2026
- Created Sun item
- Created Peashooter Entity
- Created Pea Item/Entity
- Made Peashooter shoot Peas

## 04/05/2026
- Added new Peashooter model
- Fixed Peashooter shooting animation not displaying
- Improved Peashooter shooting animation
- Added Plant Item 
- Made it so Peashooters only shoot Zombies when they step in the lawn
- Created .json for Peashooter to display in GUI
- Replaced Peashooter spawn egg with the Peashooter (Plant) Item
- Stopped Day and Weather cycles

## 05/05/2026
- Made Plants spawn in the middle of a 2x2 square
- Adjusted Peashooter Entity dimensions
- Adjusted Pea Entity dimensions
- Fixed Peashooter not instantly spawning facing North


## 06/05/2026 - 12/05/2026
- Created Zombie Model
- Created Zombie Item
- Created Zombie Entity
- Fixed some bugs

## 18/05/2026
- Created PlantEntity and PvZZombieEntity classes
- Added three lawn blocks
- Updated Front Lawn

## 21/05/2026
- Fixed Zombie and Plant spawns
- Changed the default menu to match PvZ's main menu better
- Started working on the levels logic in Adventure mode
- Added Roof and Pool to the house (basically finished house)

## 22/05/2026
- Improved LevelManager
- Created LevelData, SpawnProfileData and LevelDataLoader
- Added test level

## 23/05/2026
- Improved LevelManager and levels logic
- Organized and cleaned up a lot of code

## 24/05/2026
- Improved code and levels logic
- Added Constants.java to store constants
- Added HudRenderer.java to display overlays during levels
- Added Huge Wave, Final Wave and Level overlays

## 25/05/2026
- Improved bug where Peas would go through zombies (still happens but wayyy less)
- Fixed Plants taking 30 ticks to start shooting new targets (changed it to 5 ticks)
- Added seed hotbar on the top left and removed the default hotbar
- Added Ready, Set, Plant overlay

## 26/05/2026
- Code cleanup
- Bug fixes

## 07/06/2026 - 08/06/2026
- More code organizing
- Add Seed Packet texture to the seed slots in the hotbar
- Fixed the bug "Invalid Player Data" where map wasn't getting completely cleared
- Adjusted RegularZombie and Peashooter parameters (speed, shooting interval)
- Added cooldowns to placing plants
- Added Sun mechanics
- Added performance stats to the GUI

## 09/06/2026 - 14/06/2026
- Improved Hotbar significantly (visually and code)
- Added Fence Bottom block and added it to the map
- Added Fence Top block and added it to the map
- Added Lawnmowers

## 17/06/2026
- Fixed FacingBlock class so it works generically for every block
- Fixed hitbox for Fence Top
- Added Dirt Rock Block and Dirt Block
- Replaced all lanes except the middle one with the new dirt blocks in Level 1-1 
- Made Zombies and Lawnmower spawn only in the middle lane in Level 1-1

## Todo
(Before moving on to next level)
- Add RegularZombie Animations
- Do Artwork for Level 1-0 and 1-1
- Remove cooldowns in Level 1-0
- Do Artwork for Level Progress
- Add Level Progress logic
- Add timeout to Sun (and make it slowly start fading as it reaches the end of its life)
- Add Tutorial dialogue to Level 1-1
- Add original sounds (don't forget to add them to .gitignore)
- Add Artwork for when Zombies reach our door
- Improve the level finish logic
- Add Seed Packet Item
- Make the last Zombie drop the reward upon dying
- Display the sun cost for each plant in the seed slots
- Do Artwork for "User"'s House (probably just display in default font)
- Make it so that leaving mid-game saves the game, then asks if you want to resume the level or restart

In the future:
- Add the panning "animation?" where it shows the player the Zombies that will spawn in that level