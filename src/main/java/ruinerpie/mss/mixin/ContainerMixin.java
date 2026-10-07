package ruinerpie.mss.mixin;

import ruinerpie.mss.StorageConfig;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Container.class)
public interface ContainerMixin {

    @Overwrite
    default int getMaxStackSize() {
        return StorageConfig.getMaxStackSize();
    }

    @Overwrite
    default int getMaxStackSize(ItemStack stack) {
        if (stack.getMaxStackSize() <= 1) {
            return 1;
        }
        return Math.min(this.getMaxStackSize(), stack.getMaxStackSize());
    }
}
