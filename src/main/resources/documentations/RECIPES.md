# recipes.yml
The configuration of the custom items crafting recipes

## Shaped recipes

### Example template:

<pre>
[id]:
  type: shaped
  shape:
    - " E "
    - " S "
    - " N "
  ingredients:
    E: ELYTRA
    S: NETHER_STAR
    N: NETHERITE_BOOTS
  result: "flying_boots"
</pre>

### type
- **Description:** The recipe type (currently only 'shaped' and 'shapeless' are supported)

### shape 
- **Description:** This sets the shape of the recipe, a.k.a. where you should put which items when crafting

### ingredients
- **Description:** This sets which character represents which material

### result
- **Description:** This is the id of the custom item that will be the result of this recipe

## Shapeless recipes

### Example template:

<pre>
[id]:
  type: shapeless
  ingredients:
    - ELYTRA
    - NETHER_STAR
    - NETHERITE_BOOTS
  result: "flying_boots"
</pre>

### type
- **Description:** The recipe type (currently only 'shaped' and 'shapeless' are supported)

### ingredients
- **Description:** This sets the materials in the recipe (since this is a shapeless recipe, you can't set a shape, so it doesn't matter what shape you put the material in)

### result
- **Description:** This is the id of the custom item that will be the result of this recipe