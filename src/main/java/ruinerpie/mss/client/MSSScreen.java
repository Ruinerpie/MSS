package ruinerpie.mss.client;

import ruinerpie.mss.StorageConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class MSSScreen extends Screen {

    private static final int[] PRESETS = {64, 127, 128, 256, 512, 1024, 2048, 4096};
    private static final int DEFAULT_VALUE = 64;

    private final Screen parent;

    public MSSScreen(Screen parent) {
        super(Component.literal("MoreStorageStack"));
        this.parent = parent;
    }

    @Override
    public boolean isInGameUi() {
        return true;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        int btnW = 80;
        int btnH = 20;
        int gap = 6;
        int totalW = 4 * btnW + 3 * gap;
        int startX = cx - totalW / 2;
        int startY = cy - 20;

        for (int i = 0; i < PRESETS.length; i++) {
            int value = PRESETS[i];
            int x = startX + (i % 4) * (btnW + gap);
            int y = startY + (i / 4) * (btnH + gap);

            boolean selected = StorageConfig.getMaxStackSize() == value;
            String label = String.valueOf(value);

            this.addRenderableWidget(new MSSButton(
                x, y, btnW, btnH,
                Component.literal(label),
                b -> {
                    StorageConfig.setMaxStackSize(value);
                    StorageConfig.save();
                    this.rebuildWidgets();
                },
                selected
            ));
        }

        this.addRenderableWidget(new MSSButton(
            cx - 100, cy + 56, 200, 20,
            Component.literal("Reset to Default (64)"),
            b -> {
                StorageConfig.setMaxStackSize(DEFAULT_VALUE);
                StorageConfig.save();
                this.rebuildWidgets();
            },
            false
        ));

        this.addRenderableWidget(new MSSButton(
            cx - 100, cy + 80, 200, 20,
            Component.literal("Done"),
            b -> this.onClose(),
            false
        ));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
        super.extractRenderState(extractor, mouseX, mouseY, delta);

        int cx = this.width / 2;
        int cy = this.height / 2;

        extractor.centeredText(this.font,
                Component.literal("MoreStorageStack").withStyle(ChatFormatting.BOLD),
                cx, cy - 90, 0xFFFFFF);

        extractor.centeredText(this.font,
                Component.literal("Max Stack Size: ")
                        .append(Component.literal(String.valueOf(StorageConfig.getMaxStackSize()))
                                .withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)),
                cx, cy - 70, 0xFFFFFF);

        boolean safe = StorageConfig.getActivePreset().id().equals("safe");
        Component preset = Component.literal("Preset: ").append(
                Component.literal(safe ? "SAFE" : "BULK")
                        .withStyle(safe ? ChatFormatting.GREEN : ChatFormatting.RED));
        extractor.centeredText(this.font, preset, cx, cy - 56, 0xFFFFFF);

        String hint = safe
                ? "SAFE: Recommended for stable survival & servers."
                : "BULK: High-capacity stack size (128 - 4096).";
        extractor.centeredText(this.font,
                Component.literal(hint).withStyle(ChatFormatting.GRAY),
                cx, cy + 34, 0xAAAAAA);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    private static class MSSButton extends Button {
        private final boolean selected;

        public MSSButton(int x, int y, int width, int height, Component message, OnPress onPress, boolean selected) {
            super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
            this.selected = selected;
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
            int x = this.getX();
            int y = this.getY();
            int w = this.getWidth();
            int h = this.getHeight();

            boolean hovered = this.isHoveredOrFocused();

            int bg;
            int border;
            int textColor;
            if (!this.active) {
                bg = 0xFF0F0020;
                border = 0xFF25002E;
                textColor = 0xFF555555;
            } else if (this.selected) {
                bg = 0xFF23002A;
                border = 0xFFFF0000;
                textColor = 0xFFFF0000;
            } else if (hovered) {
                bg = 0xFF1E0024;
                border = 0xFFFF0000;
                textColor = 0xFFFFFFFF;
            } else {
                bg = 0xFF1B0020;
                border = 0xFF25002E;
                textColor = 0xFFA0A0A0;
            }

            graphics.fill(x, y, x + w, y + h, bg);
            graphics.outline(x, y, w, h, border);
            Font font = Minecraft.getInstance().font;
            String text = this.getMessage().getString();
            graphics.text(font, text, x + (w - font.width(text)) / 2, y + (h - 8) / 2, textColor, false);
        }
    }
}
