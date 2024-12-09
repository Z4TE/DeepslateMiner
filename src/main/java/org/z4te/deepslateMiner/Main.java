package org.z4te.deepslateMiner;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll();

    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Material block = event.getBlock().getType();
        ItemStack item = event.getItemInHand();
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (isInstantBreakable(block, item, player)) {
            event.setInstaBreak(true);
            Objects.requireNonNull(location.getWorld()).playSound(location, Sound.BLOCK_DEEPSLATE_BREAK, SoundCategory.BLOCKS, 1, 1);
        }

    }

    private boolean isInstantBreakable(Material block, ItemStack item, Player player) {
        return block == Material.DEEPSLATE
                && item.containsEnchantment(Enchantment.EFFICIENCY)
                && item.getEnchantmentLevel(Enchantment.EFFICIENCY) >= 5
                && player.hasPotionEffect(PotionEffectType.HASTE)
                // ぬるぽ対策
                && Objects.requireNonNull(player.getPotionEffect(PotionEffectType.HASTE)).getAmplifier() >= 1
                && item.getType() == Material.NETHERITE_PICKAXE;
    }

}
