package plugin.main;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuild {

    private final ItemStack stack = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
    private ItemMeta meta;

    public ItemBuild() {}

    public ItemBuild(Material m, String name, List<String> lore ) {
        stack.setType(m);
        meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(null);
        stack.setItemMeta(meta);
    }
    public ItemBuild setItem(Material m, String name, List<String> lore) {
        stack.setType(m);
        meta = stack.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(name);
        meta.setCustomModelData(null);
        for(ItemFlag flag : stack.getItemMeta().getItemFlags()) {
            meta.removeItemFlags(flag);
        }
        for(Enchantment en : stack.getEnchantments().keySet()) {
            meta.removeEnchant(en);
        }
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuild setCustomModelData(Integer data) {
        meta.setCustomModelData(data);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuild addEnchantment(Enchantment en, int level) {
        meta.addEnchant(en, level, true);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuild removeEnchantment(Enchantment en) {
        meta.removeEnchant(en);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuild addItemFlag(ItemFlag... flag) {
        meta.addItemFlags(flag);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuild removeItemFlag(ItemFlag... flag) {
        meta.removeItemFlags(flag);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemStack getItem() {
        return stack;
    }

    public ItemBuild setItemMeta(ItemMeta meta) {
        stack.setItemMeta(meta);
        return this;
    }
}
