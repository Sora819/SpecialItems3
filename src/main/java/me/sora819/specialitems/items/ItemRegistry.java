package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.reflections.Reflections;

import java.util.*;

public class ItemRegistry {
    private static final Map<String, ICustomItem> itemMap = new HashMap<>();

    public static void registerCustomItems() {
        itemMap.clear();

        String packageName = ItemRegistry.class.getPackage().getName();
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends ICustomItem>> classes = reflections.getSubTypesOf(ICustomItem.class);

        for (Class<? extends ICustomItem> clazz : classes) {
            try {
                ICustomItem item = clazz.getDeclaredConstructor().newInstance();
                if (TimerTask.class.isAssignableFrom(clazz) && item.getInterval() != null) {
                    SpecialItems.runAtIntervals((TimerTask) item, item.getInterval());
                }
                itemMap.put(item.getID().toLowerCase(), item);
                SpecialItems.getInstance().getLogger().info(LocalizationHandler.getMessage("success.item_load") + item.getID());
            } catch (Exception e) {
                SpecialItems.getInstance().getLogger().warning(LocalizationHandler.getMessage("error.item_load") + clazz.getSimpleName());
                SpecialItems.getInstance().getLogger().warning(e.toString());
            }
        }
    }

    public static ICustomItem getItem(String id) {
        return itemMap.get(id);
    }

    public static Set<ICustomItem> getItems() {
        return new HashSet<>(itemMap.values());
    }

    public static Set<String> getItemIDs() {
        return itemMap.keySet();
    }
}
