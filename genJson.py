import shutil
from os import path

MOD_ID = "strangery"

BLOCKS = [
#     "foodium_ore",
#     "randomium_ore",
#     "loose_stone",
#     "bebsofyr_ore",
#     "bebsofyr_block",
#     "oily_black_stone",
#     "moonstone_ore",
#     "moonstone_block",
#     "sunstone_ore",
#     "sunstone_block"
]

ITEMS = [
#     "unwieldy_stick",
#     "foodium",
#     "bun",
#     "cheese",
#     "rice",
#     "french_fries",
#     "cheese_burger",
#     "ice_cream",
#     "chicken_fried_rice",
#     "chicken_tendies",
#     "korean_bbq",
#     "sushi",
#     "pho",
#     "ramen",
#     "pizza",
#     "rock_candy",
#     "bebsofyr_ingot",
#     "moonstone_fragment",
#     "sunstone_fragment",
#     "celestial_steel_ingot"
#     "lee_sin_blindfold",
#     "sans_bone"
]

TOOLS = [
#     ("bebsofyr", "bebsofyr_ingot"),
#     ("celestial_steel", "celestial_steel_ingot")
]

ARMOR = [
#     ("bebsofyr", "bebsofyr_ingot"),
#     ("celestial_steel", "celestial_steel_ingot")
]

BOWS = [
]

SRC_DIR = "./src"
RESOURCES_DIR = f"{SRC_DIR}/main/resources"
MODELS_DIR = f"{RESOURCES_DIR}/assets/{MOD_ID}/models/"
ITEM_MODELS_DIR = f"{MODELS_DIR}item/"
BLOCK_MODELS_DIR = f"{MODELS_DIR}block/"
BLOCKSTATES_DIR = f"{RESOURCES_DIR}/assets/{MOD_ID}/blockstates/"
DATA_DIR = f"{RESOURCES_DIR}/data/{MOD_ID}/recipes"

PLACEHOLDER_TEXTURE_PATH = f"src/main/resources/assets/{MOD_ID}/textures/placeholder_texture.png"
ITEM_TEXTURES_DIR = f"src/main/resources/assets/{MOD_ID}/textures/item/"

TEMPLATES_DIR = "./templates"

TOOL_TYPES = [
    "pickaxe",
    "shovel",
    "axe",
    "hoe",
    "sword"
]
TOOL_MODEL_TEMPLATE = (
    lambda x: f"{ITEM_MODELS_DIR}/{x}.json",
    open(f"{TEMPLATES_DIR}/models/items/tool.json", "r").read()
)
TOOL_RECIPE_TEMPLATES = [
    (
        f"{DATA_DIR}/material_name_{tool_type}.json",
        open(f"{TEMPLATES_DIR}/recipes/tools/{tool_type}.json", "r").read()
    )
    for tool_type in TOOL_TYPES
]

ARMOR_TYPES = [
    "helmet",
    "chestplate",
    "leggings",
    "boots"
]
ARMOR_RECIPE_TEMPLATES = [
    (
        f"{DATA_DIR}/material_name_{armor_type}.json",
        open(f"{TEMPLATES_DIR}/recipes/armor/{armor_type}.json", "r").read()
    )
    for armor_type in ARMOR_TYPES
]


def write_texture(name):
    texture_path = f"{ITEM_TEXTURES_DIR}{name}.png"

    if not path.exists(texture_path):
        shutil.copyfile(PLACEHOLDER_TEXTURE_PATH, texture_path)


def write_json(path, json):
    f = open(path, "w")
    f.write(json)
    f.close()


def block_json(name):
    return (
        f"{{\n"
        f"    \"parent\": \"block/cube_all\",\n"
        f"    \"textures\": {{\n"
        f"        \"all\": \"{MOD_ID}:block/{name}\"\n"
        f"    }}\n"
        f"}}\n"
    )


def item_block_json(name):
    return (
        f"{{\n"
        f"    \"parent\": \"{MOD_ID}:block/{name}\"\n"
        f"}}\n"
    )


def blockstate_json(name):
    return (
        f"{{\n"
        f"    \"variants\": {{\n"
        f"        \"\": {{ \"model\": \"{MOD_ID}:block/{name}\" }}\n"
        f"    }}\n"
        f"}}\n"
    )


def item_json(name):
    return (
        f"{{\n"
        f"    \"parent\": \"item/generated\",\n"
        f"    \"textures\": {{\n"
        f"        \"layer0\": \"{MOD_ID}:item/{name}\"\n"
        f"    }}\n"
        f"}}\n"
    )


def tool_json(name):
    return (
        f"{{\n"
        f"    \"parent\": \"item/handheld\",\n"
        f"    \"textures\": {{\n"
        f"        \"layer0\": \"{MOD_ID}:item/{name}\"\n"
        f"    }}\n"
        f"}}\n"
    )


def bow_json(name):
    return (
        f"{{\n"
        f"    \"parent\": \"item/generated\",\n"
        f"    \"textures\": {{\n"
        f"        \"layer0\": \"awaken:item/{name}\"\n"
        f"    }},\n"
        f"    \"display\": {{\n"
        f"        \"thirdperson_righthand\": {{\n"
        f"            \"rotation\": [ -80, 260, -40 ],\n"
        f"            \"translation\": [ -1, -2, 2.5 ],\n"
        f"            \"scale\": [ 0.9, 0.9, 0.9 ]\n"
        f"        }},\n"
        f"        \"thirdperson_lefthand\": {{\n"
        f"            \"rotation\": [ -80, -280, 40 ],\n"
        f"            \"translation\": [ -1, -2, 2.5 ],\n"
        f"            \"scale\": [ 0.9, 0.9, 0.9 ]\n"
        f"        }},\n"
        f"        \"firstperson_righthand\": {{\n"
        f"            \"rotation\": [ 0, -90, 25 ],\n"
        f"            \"translation\": [ 1.13, 3.2, 1.13],\n"
        f"            \"scale\": [ 0.68, 0.68, 0.68 ]\n"
        f"        }},"
        f"        \"firstperson_lefthand\": {{\n"
        f"            \"rotation\": [ 0, 90, -25 ],\n"
        f"            \"translation\": [ 1.13, 3.2, 1.13],\n"
        f"            \"scale\": [ 0.68, 0.68, 0.68 ]\n"
        f"        }}\n"
        f"    }},\n"
        f"    \"overrides\": [\n"
        f"        {{\n"
        f"            \"predicate\": {{\n"
        f"                \"pulling\": 1\n"
        f"            }},\n"
        f"            \"model\": \"awaken:item/{name}_pulling_0\"\n"
        f"        }},\n"
        f"        {{\n"
        f"            \"predicate\": {{\n"
        f"                \"pulling\": 1,\n"
        f"                \"pull\": 0.65\n"
        f"            }},\n"
        f"            \"model\": \"awaken:item/{name}_pulling_1\"\n"
        f"        }},\n"
        f"        {{\n"
        f"            \"predicate\": {{\n"
        f"                \"pulling\": 1,\n"
        f"                \"pull\": 0.9\n"
        f"            }},\n"
        f"            \"model\": \"awaken:item/{name}_pulling_2\"\n"
        f"        }}\n"
        f"    ]\n"
        f"}}\n"
    )


def write_block(name):
    write_json(f"{BLOCK_MODELS_DIR}{name}.json", block_json(name))
    write_json(f"{ITEM_MODELS_DIR}{name}.json", item_block_json(name))
    write_json(f"{BLOCKSTATES_DIR}{name}.json", blockstate_json(name))


def write_item(name):
    write_json(f"{ITEM_MODELS_DIR}{name}.json", item_json(name))
    write_texture(name)


def write_tool(name):
    write_json(f"{ITEM_MODELS_DIR}{name}.json", tool_json(name))


def write_bow(name):
    write_json(f"{ITEM_MODELS_DIR}{name}.json", bow_json(name))


def write_tools(material_name, item_name):
    for (json_path, json) in TOOL_RECIPE_TEMPLATES:
        write_json(
            json_path.replace("material_name", material_name),
            json
                .replace("namespace", MOD_ID)
                .replace("material_name", material_name)
                .replace("material_item", item_name)
        )

    for tool_type in TOOL_TYPES:
        write_json(
            TOOL_MODEL_TEMPLATE[0](f"{material_name}_{tool_type}"),
            TOOL_MODEL_TEMPLATE[1]
                .replace("namespace", MOD_ID)
                .replace("material_name", material_name)
                .replace("tool", tool_type)
        )


def write_armor(material_name, item_name):
    for (json_path, json) in ARMOR_RECIPE_TEMPLATES:
        write_json(
            json_path.replace("material_name", material_name),
            json
                .replace("namespace", MOD_ID)
                .replace("material_name", material_name)
                .replace("material_item", item_name)
        )

    for armor_type in ARMOR_TYPES:
        write_item(f"{material_name}_{armor_type}")


for block in BLOCKS:
    write_block(block)

for item in ITEMS:
    write_item(item)

for (material, item) in TOOLS:
    write_tools(material, item)

for (material, item) in ARMOR:
    write_armor(material, item)

for bow in BOWS:
    write_bow(bow)
