# bt4-research
What I have gathered from analyzing Budokai Tenkaichi 4, the fanmade BT3 mod.

* [Inaccessible Songs](https://www.reddit.com/r/BudokaiTenkaichi4/comments/1f3kkdv/dbzbt4_songs_outside_of_bgm_select/)
* [Unlockable Characters](https://www.reddit.com/r/BudokaiTenkaichi4/comments/1f0iafg/dbzbt4_unlockable_characters/)

# Recent Discoveries
With the 4th (technically the 3rd, since BETA 13.3 wasn't actually released, but 13.4 was) revision of BETA 13, Ultra Instinct -Sign- Goku has been added.

His character slot is right next to Super Saiyan 2 Vegeta (GT)'s. How convenient.

# AFS Information
As explained in the [repo about The Legacy of Tenkaichi](https://github.com/ViveTheModder/dbz-tlot-research), TioMedusin implemented an older means of obfuscation/inaccessibility.

Around BETA 12, the team has removed the header information from each AFS file, reminiscent of the VOL files used in Budokai Tenkaichi 1.

Before, no source code changes were required, but safe to assume, they made use of the ``dbz4.bin`` file to calculate the file addresses and file sizes.

Its contents are currently unknown, but here is some basic background about each AFS:

* ``pzs4us0.afs`` -> 1113313280 bytes (~1.04 GB), 50503 files, 101256 bytes of raw data from ``dbz4.bin``
* ``pzs4us1.afs`` -> 2028277760 bytes (~1.89 GB), 03400 files, 007050 bytes of raw data from ``dbz4.bin``
* ``pzs4us2.afs`` -> 2097973248 bytes (~1.95 GB), 36191 files, 072632 bytes of raw data from ``dbz4.bin``

Based on initial observation, the number of raw data bytes is over double the number of files. Wonder where that will get us...

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