package me.sora819.specialitems.utils;

import me.sora819.specialitems.SpecialItems;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class NBTManager {
    @SuppressWarnings("rawtypes")
    private static final PersistentDataType[] persistentDataTypes = new PersistentDataType[]{
            PersistentDataType.STRING,
            PersistentDataType.INTEGER,
            PersistentDataType.SHORT,
            PersistentDataType.LONG,
            PersistentDataType.DOUBLE,
            PersistentDataType.FLOAT,
            PersistentDataType.BOOLEAN,
            PersistentDataType.BYTE,
            PersistentDataType.BYTE_ARRAY,
            PersistentDataType.INTEGER_ARRAY,
            PersistentDataType.LONG_ARRAY,
    };

    @SuppressWarnings("rawtypes")
    private static final Map<Class, PersistentDataType> typeToPersistentDataTypeMap = Map.ofEntries(
            Map.entry(String.class, PersistentDataType.STRING),
            Map.entry(Integer.class, PersistentDataType.INTEGER),
            Map.entry(Short.class, PersistentDataType.SHORT),
            Map.entry(Long.class, PersistentDataType.LONG),
            Map.entry(Double.class, PersistentDataType.DOUBLE),
            Map.entry(Float.class, PersistentDataType.FLOAT),
            Map.entry(Boolean.class, PersistentDataType.BOOLEAN),
            Map.entry(Byte.class, PersistentDataType.BYTE),
            Map.entry(byte[].class, PersistentDataType.BYTE_ARRAY),
            Map.entry(int[].class, PersistentDataType.INTEGER_ARRAY)
    );

    @SuppressWarnings("rawtypes")
    static public String getNBT(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        PersistentDataContainer nbt = meta.getPersistentDataContainer();

        HashMap<NamespacedKey, Object> nbtMap = new HashMap<>();

        for (NamespacedKey key : nbt.getKeys()) {
            for (PersistentDataType type : persistentDataTypes) {
                if (nbt.has(key, type)) {
                    nbtMap.put(key, nbt.get(key, type));
                    break;
                }
            }
        }

        return nbtMap.toString();
    }

    static public <DataType> void setNBT(ItemStack item, String key, DataType value) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        NamespacedKey nKey = new NamespacedKey(SpecialItems.getInstance(), key);
        meta.getPersistentDataContainer().set(nKey, typeToPersistentDataTypeMap.get(value.getClass()), value);

        item.setItemMeta(meta);
    }

    static public void removeNBT(ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        NamespacedKey nKey = new NamespacedKey(SpecialItems.getInstance(), key);
        meta.getPersistentDataContainer().remove(nKey);

        item.setItemMeta(meta);
    }

    @SuppressWarnings("rawtypes")
    static public <DataType> DataType getNBT(ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        NamespacedKey nKey = new NamespacedKey(SpecialItems.getInstance(), key);

        if (!meta.getPersistentDataContainer().has(nKey)) {
            return null;
        }

        for (PersistentDataType type : persistentDataTypes) {
            if (meta.getPersistentDataContainer().has(nKey, type)) {
                DataType nbt = (DataType) meta.getPersistentDataContainer().get(nKey, type);
                return nbt;
            }
        }

        return null;
    }
}
