package ruinerpie.mss.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MSSClient implements ClientModInitializer {

    private static KeyMapping.Category getCategory() {
        if (FabricLoader.getInstance().isModLoaded("pancreas")) {
            try {
                Class<?> clazz = Class.forName("ruinerpie.pancreas.Pancreas");
                Field field = clazz.getField("CATEGORY_PANCREAS");
                return (KeyMapping.Category) field.get(null);
            } catch (Throwable ignored) {
            }
        }
        Identifier id = Identifier.fromNamespaceAndPath("rex", "main");
        try {
            for (Field f : KeyMapping.Category.class.getDeclaredFields()) {
                if (List.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    List<?> list = (List<?>) f.get(null);
                    for (Object item : list) {
                        if (item instanceof KeyMapping.Category cat && cat.id().equals(id)) {
                            return cat;
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            return KeyMapping.Category.register(id);
        } catch (Throwable t) {
            return new KeyMapping.Category(id);
        }
    }

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = getCategory();
        KeyMapping openKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.mss.config",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openKey.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new MSSScreen(null));
                }
            }
        });
    }
}
