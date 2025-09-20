# Dungeon Crawler Simulator
Week 2 of my IT-course focused on OOP Java, so I made this small Dungeon Crawler sim/game
(heavily inspired by [Dark and Darker](https://store.steampowered.com/app/2016590)).

## How to run the game
NOTE: This project was built and tested using Oracle OpenJDK 25
### IntelliJ
1. Clone the repository.
2. Goto **File** -> **Open** -> Select **DungeonCrawler**
3. If your IDE doesn't automatically recognize the `lib` directory, goto **File** -> **Project structure...** ->
**Libraries** -> Hit the `+` and add the `json-20240303.jar`
4. Run from `Main.java`
### Visual Studio Code / VSCode
1. Clone the repository.
2. Goto **File** -> **Open Folder...** -> Select **DungeonCrawler**
3. Create a new directory in root called `.vscode` and inside of it create a `settings.json`
4. Paste the following content to the `settings.json`:
```json
{
    "java.project.sourcePaths": [
        "src"
    ],
    "java.project.referencedLibraries": [
        "lib\\json-20240303.jar"
    ]
}
```
5. Run from `Main.java`
## The game
Progress through the dungeon by looting and slaying monsters (currently only skeletons)!

There is currently no win condition game ends (read: crashes) when you progress through all generated modules.
## Missing features
The game is now runnable and somewhat playable, here is alist of missing features:
* [ ] Update win condition
* [x] Make Strength affect damage dealt by player
* [ ] Introduce Altars to post combat (give status effect for next combat encounter, Will cost)
* [ ] Introduce Dodge, Miss and Crit to combat encounter
* [ ] Add abilities
* [ ] Add perks
* [ ] Introduce status effects from abilities & perks to combat encounters
* [ ] Implement resting option "Pray" (to reset certain abilities and perks) 
* [ ] Add Artifacts (weapons with added status effects)

## UML / Sequence Diagram Assignment
Every participant was asked to create a UML diagram and a sequence diagram.
### UML Diagram
Not created yet.
### Sequence Diagram
![Tux, the Linux mascot](/UML/initializegame.png)
