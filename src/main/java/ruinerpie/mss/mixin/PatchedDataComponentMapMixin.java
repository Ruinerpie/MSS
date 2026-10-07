package ruinerpie.mss.mixin;

import ruinerpie.mss.StorageConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PatchedDataComponentMap.class)
public class PatchedDataComponentMapMixin {

    @ModifyReturnValue(method = "get", at = @At("RETURN"))
    private <T> T mss$scaleMaxStack(T original, DataComponentType<? extends T> type) {
        if (type == DataComponents.MAX_STACK_SIZE && original instanceof Integer count) {
            if (count <= 1) {
                return original;
            }
            return (T) Integer.valueOf(Math.max(count, StorageConfig.getMaxStackSize()));
        }
        return original;
    }
}
