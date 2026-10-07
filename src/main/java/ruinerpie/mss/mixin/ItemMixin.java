package ruinerpie.mss.mixin;

import ruinerpie.mss.StorageConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public abstract class ItemMixin {

    @ModifyReturnValue(method = "getDefaultMaxStackSize", at = @At("RETURN"))
    private int mss$scaleDefaultMaxStackSize(int original) {
        if (original <= 1) {
            return 1;
        }
        return Math.max(original, StorageConfig.getMaxStackSize());
    }
}
