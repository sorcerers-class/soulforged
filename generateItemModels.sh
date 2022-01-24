#!/bin/bash
cd src/main/resources/assets/soulforged/models/item
while IFS= read -r line; do
	echo "{
  \"parent\": \"minecraft:item/generated\",
  \"textures\": {
    \"layer0\": \"soulforged:$line_head\"
  }
}" > $line_head.json
echo "{
  \"parent\": \"minecraft:item/generated\",
  \"textures\": {
    \"layer0\": \"soulforged:$line_binding\"
  }
}" > $line_binding.json
echo "{
  \"parent\": \"minecraft:item/generated\",
  \"textures\": {
    \"layer0\": \"soulforged:$line_handle\"
  }
}" > $line_handle.json
done < textures.txt