# FIT2099 Assignment (Semester 1, 2025)

```
`7MM"""YMM  `7MMF'      `7MM"""Yb. `7MM"""YMM  `7MN.   `7MF'    MMP""MM""YMM `7MMF'  `7MMF'`7MMF'`7MN.   `7MF' .g8"""bgd  
  MM    `7    MM          MM    `Yb. MM    `7    MMN.    M      P'   MM   `7   MM      MM    MM    MMN.    M .dP'     `M  
  MM   d      MM          MM     `Mb MM   d      M YMb   M           MM        MM      MM    MM    M YMb   M dM'       `  
  MMmmMM      MM          MM      MM MMmmMM      M  `MN. M           MM        MMmmmmmmMM    MM    M  `MN. M MM           
  MM   Y  ,   MM      ,   MM     ,MP MM   Y  ,   M   `MM.M           MM        MM      MM    MM    M   `MM.M MM.    `7MMF'
  MM     ,M   MM     ,M   MM    ,dP' MM     ,M   M     YMM           MM        MM      MM    MM    M     YMM `Mb.     MM  
.JMMmmmmMMM .JMMmmmmMMM .JMMmmmdP' .JMMmmmmMMM .JML.    YM         .JMML.    .JMML.  .JMML..JMML..JML.    YM   `"bmmmdPY  
```


# REQ 3: LightTicker - Time-based World Phases

## Time-based World Phases

In the *Valley of the Inheritree*, the flow of time dynamically influences the game world. The day is divided into three distinct phases that cycle every 24 turns:

- **Turn 1–8**: *Dawnveil*
- **Turn 9–16**: *Duskfall*
- **Turn 17–24**: *Crimson Eclipse*
- **Turn 25 and beyond**: The cycle resets to *Dawnveil*

Each phase introduces unique environmental conditions and alters the behavior of actors and game systems.

## WorldTrigger

The `WorldTrigger` is a dedicated actor responsible for:

- Tracking the current time phase.
- Triggering transitions between phases.

---

## Phase Effects

### Dawnveil Phase

- Merchants that disappeared during *Duskfall* and *Crimson Eclipse* return to their original positions.
- All remaining **Crimson Wells** are removed from the map.
- The world enters a calm state, allowing safer navigation and interaction.

### Duskfall Phase

- All merchants temporarily leave the map (go to sleep) and are unavailable until the next *Dawnveil*.

### Crimson Eclipse Phase

- **Crimson Wells** spawn randomly on tiles marked as cursed (e.g., Blight).
- If the player stands on a Crimson Well for one full turn, a **reward potion** appears on the ground.
  - The potion is randomly either:
    - A potion that **increases** the player's current health by 50%.
    - A potion that **decreases** the player's current health by 50%.
- The Crimson Well disappears after rewarding the potion and the ground reverts to its original tile (e.g., Blight).
- Any unused Crimson Wells are automatically removed at the end of the *Crimson Eclipse* phase (handled at the start of the next *Dawnveil* phase).

---


# REQ 4: Spriggle - The Farmer's Assistant

## 📜 Story / Scenario

The farmer has an assistant named **Spriggle**. To allow the farmer to focus on attacking enemies, **Spriggle** helps in various supportive behaviors throughout the game world.

---

## 🛠️ Spriggle's Behaviors

### ✅ 1. Pick Up Item
- **Behavior**: If Spriggle’s current or nearby location contains an item, it will pick it up.
- **Logic**:
    - If the item is **pet-edible** and Spriggle's health is **below 50%**, the item is added to **Spriggle's inventory**.
    - Otherwise, it is added to the **farmer's inventory**.
- **Purpose**: Saves the farmer from using actions to pick up items.

---

### 💊 2. Consume an Item
- **Condition**: If Spriggle's health falls **below 30%**.
- **Behavior**: Consumes an item from its own inventory and restores health to **maximum**.
- **Purpose**: Enables Spriggle to stay longer on the map.

---

### 🧹 4. Cure Cursed Ground
- **Behavior**: Spriggle cures a cursed ground tile.
- **Cost**: Loses **5 stamina points**.
- **Purpose**: Reduces cursed ground, allowing for more beneficial actions nearby.

---

## ⛏️ Mining Materials & Crafting Traps

Spriggle can **pick up mining materials** and add them to the **farmer’s inventory**. The farmer can then craft traps to boost attack capabilities.

### 🪵 Wood Trap
- **Material**: `Wood`
- **Location**: Soil ground
- **Effect**: Enemy stepping on the trap receives **15 damage**.
- **Persistence**: Trap **stays** on the map.

### 🧪 Stagger Trap
- **Material**: `Magical Potion`
- - **Location**: Cursed ground (i.e. Blight)
- **Effect**: Enemy is **staggered** and vanishes for **3 turns**, then returns.
- **Persistence**: Trap **stays** on the map.

### 💣 Bomb Trap
- **Material**: `Explosive Powder`
- - **Location**: No restriction
- **Effect**: Instantly **kills** enemy on contact.
- **Persistence**: **Single-use only**.

> **Note**: The **farmer is intelligent** enough to avoid traps. Only enemies are affected.

[Approved by TAs]
