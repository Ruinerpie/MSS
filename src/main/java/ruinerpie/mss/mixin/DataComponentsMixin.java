package ruinerpie.mss.mixin;

import ruinerpie.mss.preset.StackPreset;
import net.minecraft.core.component.DataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DataComponents.class)
public abstract class DataComponentsMixin {

    @ModifyArg(
        method = "lambda$static$1",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"),
        index = 1,
        require = 0
    )
    private static int mss$expandCodecMax(int originalMax) {
        return Math.max(originalMax, StackPreset.BULK.max());
    }
}
