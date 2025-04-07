# Getting Started
Get started by downloading the wheel file from [releases](https://github.com/MrReds1324/Opt/releases) and then installing
it to your local python environment by doing ```pip install <path to wheel>```. *Note that this requires python 3.13 to
be installed which can be found at the [official python page](https://www.python.org/downloads/)

Once installed the program can be ran though the command line interface ```maplestory-opt```.
- ```-h``` - Print the help text.
- ```-c/--config-path``` - Path to the configuration file. To see an example file see the sample config, and for all options see the configs package.
- ```--chunk-size``` - The size of the chunk used to compute result combinations. Higher numbers may be faster, but will consumer more memory, defaults to 10.
- ```--result-count``` - The number of results to print to the console, defaults to 10.


## Notes
Your base values should exclude (M)Attack, Boss Damage, Crit Damage, Damage, and IED from the following sources.
- Weapon Potentials and Soul
- Secondary Potentials
- Emblem Potentials
- Familiars
- Hyper Stats
- Legion Grid (The squares you cover, not the character effects)
- Familiars (**DO** include your familiar badges)

### Potentials
All potentials are calculated at their maximum values

### Familiars
All familiar lines are calculated at their maximum values

### Links
All link skills are calculated at maximum level/stacks.


Link Skills applied to both your ```used_link_skills``` and ```disabled_link_skills``` in your config means the used link
skill will always be part of your link combination, but will not have the damage included in the calculation. This is useful
for applying Angelic Buster link without it skewing your regular dpm optimization.
