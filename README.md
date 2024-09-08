# bt4-research
What I have gathered from analyzing Budokai Tenkaichi 4, the fanmade BT3 mod.

* [Inaccessible Songs](https://www.reddit.com/r/BudokaiTenkaichi4/comments/1f3kkdv/dbzbt4_songs_outside_of_bgm_select/)
* [Unlockable Characters](https://www.reddit.com/r/BudokaiTenkaichi4/comments/1f0iafg/dbzbt4_unlockable_characters/)

# Recent Discoveries
With the 4th (technically the 3rd, since BETA 13.3 wasn't actually released, but 13.4 was) revision of BETA 13, Ultra Instinct -Sign- Goku has been added.

His character slot is right next to Super Saiyan 2 Vegeta (GT)'s. How convenient.

# Character Costume PAK Breakdown
To optimize the game (and also make it harder to datamine), the team decided to split the PAK components in several sections.

So, for example, the HUD Portraits section has the ``000_hud.dbt`` files of each character, based on [their pre-determined order](https://github.com/ViveTheModder/bt4-research/blob/main/csv-bt4/characters.csv). 

![image](https://github.com/user-attachments/assets/8956b085-afb4-4e63-9e7b-41e6d3b64ba4)

You may notice the presence of ``025_dmg_textures.dbt`` and ``051_face_blink_dmg.dbt``, which are not normally part of BT3 Costume PAKs.
These files were added to every character costume (with the exception of those ending with ``_dmg.pak``), in order to simulate how the battle-damaged textures are loaded in Budokai Tenkaichi 2.

The results are pretty clear, as transformations and fusions load faster, even when the game is paused during the transformation/fusion sequence.

On top of that, the game only loads 100 LPS files at a time, rather than 200, based on the language the player has selected.

# Provided CSV Files
* ``bgm.csv`` -> Background Music (includes inaccessible tracks)
* ``characters.csv`` -> Character List (includes inaccessible characters; check IDs 240-249)
* ``items.csv`` -> Z-Items (includes inacessible Orange Potaras)
* ``maps.csv`` -> Maps (all maps are accessible besides Dying Namek, which is used for Dragon History)
* ``names.csv`` -> Dragon History Scenarios
* ``sagas.csv`` -> Dragon History Sagas

These CSV files can then be used for [Swag Studio](https://github.com/ViveTheModder/swag-studio) to get plenty of information out of BT4's Dragon History.
I have not provided the GSC files they use, though. That is not the point of this repository. Otherwise, I would have called it ``bt4-filedump`` or something.

However, I did include a ``gsc-logs`` folder in the repo, for those who want to know what happens during each scenario before they get to play them.