import shutil
from os import path


MOD_ID = "strangery"


BLOCKS = [
    "foodium_ore",
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
    "rock_candy"
]


TOOLS = [
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
        f"{{"
        f"  \"parent\": \"block/cube_all\","
        f"  \"textures\": {{"
        f"      \"all\": \"{MOD_ID}:block/{name}\""
        f"  }}"
        f"}}"
    )


def item_block_json(name):
    return (
        f"{{"
        f"  \"parent\": \"{MOD_ID}:block/{name}\""
        f"}}"
    )


def blockstate_json(name):
    return (
        f"{{"
        f"  \"variants\": {{"
        f"      \"\": {{ \"model\": \"{MOD_ID}:block/{name}\" }}"
        f"  }}"
        f"}}"
    )


def item_json(name):
    return (
        f"{{"
        f"  \"parent\": \"item/generated\","
        f"  \"textures\": {{"
        f"      \"layer0\": \"{MOD_ID}:item/{name}\""
        f"  }}"
        f"}}"
    )


def tool_json(name):
    return (
        f"{{"
        f"  \"parent\": \"item/handheld\","
        f"  \"textures\": {{"
        f"      \"layer0\": \"{MOD_ID}:item/{name}\""
        f"  }}"
        f"}}"
    )


def bow_json(name):
    return (
        f"{{"
        f"   \"parent\": \"item/generated\","
        f"    \"textures\": {{"
        f"        \"layer0\": \"awaken:item/{name}\""
        f"    }},"
        f"    \"display\": {{"
        f"        \"thirdperson_righthand\": {{"
        f"            \"rotation\": [ -80, 260, -40 ],"
        f"            \"translation\": [ -1, -2, 2.5 ],"
        f"            \"scale\": [ 0.9, 0.9, 0.9 ]"
        f"        }},"
        f"        \"thirdperson_lefthand\": {{"
        f"            \"rotation\": [ -80, -280, 40 ],"
        f"            \"translation\": [ -1, -2, 2.5 ],"
        f"            \"scale\": [ 0.9, 0.9, 0.9 ]"
        f"        }},"
        f"        \"firstperson_righthand\": {{"
        f"            \"rotation\": [ 0, -90, 25 ],"
        f"            \"translation\": [ 1.13, 3.2, 1.13],"
        f"            \"scale\": [ 0.68, 0.68, 0.68 ]"
        f"        }},"
        f"        \"firstperson_lefthand\": {{"
        f"            \"rotation\": [ 0, 90, -25 ],"
        f"            \"translation\": [ 1.13, 3.2, 1.13],"
        f"            \"scale\": [ 0.68, 0.68, 0.68 ]"
        f"        }}"
        f"    }},"
        f"    \"overrides\": ["
        f"        {{"
        f"            \"predicate\": {{"
        f"                \"pulling\": 1"
        f"            }},"
        f"            \"model\": \"awaken:item/{name}_pulling_0\""
        f"        }},"
        f"        {{"
        f"            \"predicate\": {{"
        f"                \"pulling\": 1,"
        f"                \"pull\": 0.65"
        f"            }},"
        f"            \"model\": \"awaken:item/{name}_pulling_1\""
        f"        }},"
        f"        {{"
        f"            \"predicate\": {{"
        f"                \"pulling\": 1,"
        f"                \"pull\": 0.9"
        f"            }},"
        f"            \"model\": \"awaken:item/{name}_pulling_2\""
        f"        }}"
        f"    ]"
        f"}}"
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