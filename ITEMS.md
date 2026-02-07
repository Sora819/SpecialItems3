# items.yml

The configuration of the custom items

## Flying Boots

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

---

## Repair Talisman

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

### effect_stackable

* **Default:** `false`
* **Description:** If `true`, multiple equipped instances of this item can stack their effect. If `false`, only one instance applies.

### repair_amount

* **Default:** `1`
* **Description:** How much durability is repaired per tick.

### interval

* **Default:** `1`
* **Description:** How often the repair effect runs (seconds).

---

## Healing Talisman

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

### effect_stackable

* **Default:** `false`
* **Description:** If `true`, multiple equipped instances of this item can stack their effect. If `false`, only one instance applies.

### heal_amount

* **Default:** `1`
* **Description:** The amount of health restored per tick (1 hp = half a heart).

### interval

* **Default:** `1`
* **Description:** How often the healing effect runs (seconds).

---

## Feeding Talisman

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

### effect_stackable

* **Default:** `false`
* **Description:** If `true`, multiple equipped instances of this item can stack their effect. If `false`, only one instance applies.

### feed_amount

* **Default:** `1`
* **Description:** The amount of hunger restored per tick.

### interval

* **Default:** `1`
* **Description:** How often the feeding effect runs (seconds).

---

## Explosive Pickaxe

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

---

## Lucky Charm

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

### effect_stackable

* **Default:** `true`
* **Description:** If `true`, multiple equipped instances of this item can stack their effect. If `false`, only one instance applies.

---

## Soul Stealing Sword

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

---

## Mob Catcher

### craftable

* **Default:** `true`
* **Description:** Is the item craftable (if false, the plugin won't register the crafting recipe)

### entity_blacklist

* **Default:** `["ENDER_DRAGON", "WITHER"]`
* **Description:** A list of entity types that cannot be captured by the item. Values must match Bukkit/Spigot `EntityType` enum names (e.g. `WITHER`, `ENDER_DRAGON`).
