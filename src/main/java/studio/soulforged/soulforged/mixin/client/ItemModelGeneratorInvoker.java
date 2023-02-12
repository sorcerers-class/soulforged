package studio.soulforged.soulforged.mixin.client;

import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(ItemModelGenerator.class)
public interface ItemModelGeneratorInvoker {
    @Invoker List<ModelElement> callAddLayerElements(int layer, String key, SpriteContents spriteContents);
}
