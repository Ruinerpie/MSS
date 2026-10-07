package ruinerpie.mss.preset;

public enum StackPreset {
    SAFE("safe", 64, 127),
    BULK("bulk", 128, 4096);

    private final String id;
    private final int min, max;

    StackPreset(String id, int min, int max) {
        this.id = id;
        this.min = min;
        this.max = max;
    }

    public String id() { return id; }
    public int min() { return min; }
    public int max() { return max; }

    public static StackPreset fromValue(int value) {
        return value <= SAFE.max ? SAFE : BULK;
    }

    public static int byteSafeMax() { return 127; }
}
