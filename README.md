# bt4-research
What I have gathered from analyzing Budokai Tenkaichi 4, the fanmade BT3 mod.

* [Inaccessible Songs](https://www.reddit.com/r/BudokaiTenkaichi4/comments/1f3kkdv/dbzbt4_songs_outside_of_bgm_select/)
* [Unlockable Characters](https://www.reddit.com/r/BudokaiTenkaichi4/comments/1f0iafg/dbzbt4_unlockable_characters/)

# AFS Information
As explained in the [repo about The Legacy of Tenkaichi](https://github.com/ViveTheModder/dbz-tlot-research), TioMedusin implemented an **older means of obfuscation/inaccessibility**.

Around BETA 12, the team **removed the header information from each AFS file**, reminiscent of the **VOL files** used in Budokai Tenkaichi 1.

Before, **no source code changes were required**, but safe to assume, they made use of the ``dbz4.bin`` file to **calculate the file addresses and file sizes**.

Not all of its contents are known, but here is some basic background about each AFS:

* ``pzs4us0.afs`` -> 1202627200 bytes (~1.12 GiB), 50503 files, 101256 bytes of raw data from ``dbz4.bin``
* ``pzs4us1.afs`` -> 2073868288 bytes (~1.93 GiB), 03400 files, 007050 bytes of raw data from ``dbz4.bin``
* ``pzs4us2.afs`` -> 1693839360 bytes (~1.57 GiB), 32775 files, 065800 bytes of raw data from ``dbz4.bin``
* ``pzs4us3.afs`` -> 0677789696 bytes (~0.63 GiB), 30000 files, 060260 bytes of raw data from ``dbz4.bin``

``pzs4us0.afs`` mainly contains **character audio** (**49994 ADX files**, which is 98.99% of what the AFS contains).

``pzs4us2.afs`` mainly contains **menu audio** as well as numerous **character files** (costumes, animations, effects).

Fortunately, it is [possible to extract ADX files from a VOL](https://github.com/ViveTheModder/bt4-research/blob/main/extract-vol-contents/adx-from-vol.jar) (AFS with no header or metadata). However, the results are **far from perfect**.

![image](https://github.com/user-attachments/assets/5f8b5854-8ed2-422c-9a96-cd4e6f59a9ba)

![image](https://github.com/user-attachments/assets/ae9d6c8b-bd14-4963-bb3d-3910d7804787)

Some ADX files, typically long ones, will **end prematurely**, because the [ADX file format](https://en.wikipedia.org/wiki/ADX_(file_format)) does **not store the file size anywhere** in the file.

![image](https://github.com/user-attachments/assets/854bfec8-01ac-45cb-83dc-4ad836ff0997)

On top of that, the information it uses to determine the number of blocks (and therefore the number of bytes occupied by those blocks) is **incorrect**.

![image](https://github.com/user-attachments/assets/da95dfad-21fc-4baf-bc8f-cad6a56b53d9)

In this example, the **bytes of data** is just the **file size minus the header** (40 bytes). The rest of the parameters are given in the file, and when used to determine the bytes of data, it is anything but helpful.

This is why in my (proof of concept) program, the file size is rounded to the nearest multiple of 32, in the hopes that it picks up more data.

But, as stated earlier, it will end prematurely and **not pick everything up**, which is why the **resulting ADX files are not really meant to be used** for others' mods (aka stealing, which I do **NOT condone**).

In addition, the program is also **painfully slow**, given that the only means of performance would come from performing all these file operations in byte arrays (can't get faster than that).

![image](https://github.com/user-attachments/assets/4570e7b5-8aa6-4829-b7e7-05574d4bbf25)

Here, I stopped the program too late, because it had already gotten to all the ADX files, but the search kept going.

![image](https://github.com/user-attachments/assets/700e8412-64ad-4dd5-a843-047157f1ce7f)

![image](https://github.com/user-attachments/assets/d84500aa-ad09-41d5-84ec-ddc5d3f838ee)

![image](https://github.com/user-attachments/assets/844f51d9-4d5d-4eec-a49d-03d11d64fb9e)

# Character Costume PAK Breakdown
To **optimize** the game (and also make it **harder to datamine**), the team decided to **split the PAK components** in several sections.

So, for example, the HUD Portraits section has the ``000_hud.dbt`` files of each character, based on [their pre-determined order](https://github.com/ViveTheModder/bt4-research/blob/main/csv-bt4/characters.csv). 

![image](https://github.com/user-attachments/assets/8956b085-afb4-4e63-9e7b-41e6d3b64ba4)

You may notice the presence of ``025_dmg_textures.dbt`` and ``051_face_blink_dmg.dbt``, which are not normally part of BT3 Costume PAKs.
These files were **added to every character costume** (with the exception of those ending with ``_dmg.pak``), in order **to simulate how the battle-damaged textures are loaded** in Budokai Tenkaichi 2.

The results are pretty clear, as **transformations and fusions load faster, even when the game is paused** during the transformation/fusion sequence.

On top of that, the game **only loads 100 LPS files** at a time, rather than 200, **based on the language** the player has selected.

# Provided CSV Files
* ``bgm.csv`` -> Background Music (includes inaccessible tracks)
* ``characters.csv`` -> Character List (includes inaccessible characters; check IDs 240-249)
* ``items.csv`` -> Z-Items (includes inacessible Orange Potaras)
* ``maps.csv`` -> Maps (all maps are accessible besides Dying Namek, which is used for Dragon History)
* ``names.csv`` -> Dragon History Scenarios
* ``sagas.csv`` -> Dragon History Sagas

These CSV files can then be used for [Swag Studio](https://github.com/ViveTheModder/swag-studio) to get plenty of information out of BT4's Dragon History.
I have **NOT provided the GSC files they use**, though. That is not the point of this repository. Otherwise, I would have called it ``bt4-filedump`` or something.

However, I did include a ``gsc-logs`` folder in the repo, for those who want to know what happens during each scenario before they get to play them.
