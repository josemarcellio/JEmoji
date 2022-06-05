package com.josemarcellio.jemoji.core.listener;

import com.josemarcellio.jemoji.JEmoji;
import com.josemarcellio.jemoji.core.inventory.InventoryMenu;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final JEmoji plugin;

    public InventoryClickListener(JEmoji plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        FileConfiguration file = plugin.getConfig ();
        if (!InventoryMenu.players.containsKey(player.getUniqueId())) return;
        InventoryMenu inventoryMenu = InventoryMenu.players.get(player.getUniqueId());
        if (player.getOpenInventory().getTitle().equals( Utility.getColor(file.getString("gui-name")))) {
            event.setCancelled(true);
        }
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(InventoryMenu.nextPageName)){
            event.setCancelled(true);
            if(!(inventoryMenu.currentpage >= inventoryMenu.pages.size() -1)){
                inventoryMenu.currentpage += 1;
                player.openInventory(inventoryMenu.pages.get(inventoryMenu.currentpage));
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(InventoryMenu.previousPageName)){
            event.setCancelled(true);
            if(inventoryMenu.currentpage > 0){
                inventoryMenu.currentpage -= 1;
                player.openInventory(inventoryMenu.pages.get(inventoryMenu.currentpage));
            }
        }
    }
}
