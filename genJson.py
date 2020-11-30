import shutil
from os import path


MOD_ID = "strangery"


BLOCKS = [
    "foodium_ore",
    "randomium_ore",
    "loose_stone",
    "bebsofyr_ore",
    "bebsofyr_block"
]


ITEMS = [
    "unwieldy_stick",
    "foodium",
    "bun",
    "cheese",
    "rice",
    "french_fries",
    "cheese_burger",
    "ice_cream",
    "chicken_fried_rice",
    "chicken_tendies",
    "korean_bbq",
    "sushi",
    "pho",
    "ramen",
    "pizza",
    "rock_candy",
    "bebsofyr_ingot",
    "bebsofyr_boots",
    "bebsofyr_leggings",
    "bebsofyr_chestplate",
    "bebsofyr_helmet"
]


TOOLS = [
    "bebsofyr_pickaxe",
    "bebsofyr_shovel",
    "bebsofyr_axe",
    "bebsofyr_hoe",
    "bebsofyr_sword"
]


BOWS = [
]


models_dir = f"src/main/resources/assets/{MOD_ID}/models/"
item_models_dir = f"{models_dir}item/"
block_models_dir = f"{models_dir}block/"
block_states_dir = f"src/main/resources/assets/{MOD_ID}/blockstates/"

placeholder_texture_path = f"src/main/resources/assets/{MOD_ID}/textures/placeholder_texture.png"
item_textures_dir = f"src/main/resources/assets/{MOD_ID}/textures/item/"

def write_texture(name):
    texture_path = f"{item_textures_dir}{name}.png"

    if not path.exists(texture_path):
        shutil.copyfile(placeholder_texture_path, texture_path)

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
    write_json(f"{block_models_dir}{name}.json", block_json(name))
    write_json(f"{item_models_dir}{name}.json", item_block_json(name))
    write_json(f"{block_states_dir}{name}.json", blockstate_json(name))


def write_item(name):
    write_json(f"{item_models_dir}{name}.json", item_json(name))
    write_texture(name)


def write_tool(name):
    write_json(f"{item_models_dir}{name}.json", tool_json(name))


def write_bow(name):
    write_json(f"{item_models_dir}{name}.json", bow_json(name))


for block in BLOCKS:
    write_block(block)

for item in ITEMS:
    write_item(item)

for tool in TOOLS:
    write_tool(tool)

for bow in BOWS:
    write_bow(bow)