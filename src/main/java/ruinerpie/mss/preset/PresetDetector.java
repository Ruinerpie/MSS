package ruinerpie.mss.preset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PresetDetector {
    private PresetDetector() {}
    private static final Logger LOG = LoggerFactory.getLogger("mss");

    public static StackPreset detect(int value) {
        StackPreset preset = StackPreset.fromValue(value);
        if (preset == StackPreset.BULK) {
            LOG.warn("BULK preset active - max_stack_size = {}", value);
        } else {
            LOG.info("SAFE preset active - max_stack_size = {}", value);
        }
        return preset;
    }
}
