package ruinerpie.mss;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MSS implements ModInitializer {
    public static final String MOD_ID = "mss";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        StorageConfig.load();
    }
}
