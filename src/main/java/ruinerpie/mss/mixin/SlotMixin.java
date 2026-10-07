package ruinerpie.mss.mixin;

import ruinerpie.mss.StorageConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slot.class)
public class SlotMixin {

    @ModifyReturnValue(method = "getMaxStackSize()I", at = @At("RETURN"))
    private int mss$scaleSlotMax(int original) {
        if (original <= 1) {
            return 1;
        }
        return Math.max(original, StorageConfig.getMaxStackSize());
    }

    @ModifyReturnValue(method = "getMaxStackSize(Lnet/minecraft/world/item/ItemStack;)I", at = @At("RETURN"))
    private int mss$scaleSlotMaxForStack(int original, ItemStack stack) {
        if (stack.getMaxStackSize() <= 1) {
            return 1;
        }
        return Math.max(original, Math.min(StorageConfig.getMaxStackSize(), stack.getMaxStackSize()));
    }
}
