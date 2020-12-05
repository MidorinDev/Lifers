package life.midorin.info.lifers.util;

import org.bukkit.Material;

public enum MaterialType {
    CHEST("CHEST",Material.CHEST, null),
    DOOR("DOOR",Material.IRON_DOOR,null);

    private final String name;
    private final Material material;
    private final MaterialType basic;

    MaterialType(String name, Material material, MaterialType basic) {
        this.name = name;
        this.material = material;
        this.basic = basic;
    }

    public String getName() {
        return name;
    }

    public MaterialType getBasic() {
        return basic == null ? this : basic;
    }

}
