package com.josemarcellio.jemoji.core.listener;

import com.josemarcellio.jemoji.core.JEmoji;
import com.josemarcellio.jemoji.core.inventory.InventoryMenu;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.Bukkit;
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
        for (String string : file.getConfigurationSection ( "emoji" ).getKeys ( false )) {
            String emoji = file.getString ( "emoji." + string + ".set-emoji" );
            String permission = file.getString ( "emoji." + string + ".permission" );
            int price = file.getInt ( "emoji." + string + ".price" );
            String executor = file.getString ( "emoji." + string + ".executor" );
            if (event.getCurrentItem ().getItemMeta ().getDisplayName ().equals ( Utility.getColor ( emoji ) )) {
                if (player.hasPermission ( "jemoji.*" ) || player.hasPermission ( permission ) || permission.equalsIgnoreCase ( "none" ) || price < 0) {
                    return;
                }
                if (plugin.getServer ().getPluginManager ().isPluginEnabled ( "Vault" )) {
                    if (JEmoji.getEconomy ().getMoney ( player ) >= price) {
                        JEmoji.getEconomy ().takeMoney ( player, price );
                        Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), executor.replace("{player}", player.getName()).replace("{permission}", permission));
                        player.sendMessage ( Utility.getColor ( file.getString ( "purchase-message-success" ).replace ( "{emoji}", emoji ).replace ( "{price}", String.valueOf ( price ) ) ) );
                        Utility.playSound(player, file.getString("purchase-sound-success"));
                        if (file.getBoolean ( "close-gui-after-purchase-emoji" )) {
                            player.closeInventory();
                        }
                    } else {
                        Utility.playSound(player, file.getString("purchase-sound-failed"));
                        player.sendMessage ( Utility.getColor ( file.getString ( "purchase-message-failed" ).replace ( "{emoji}", emoji ).replace ( "{price}", String.valueOf ( price ) ) ) );
                    }
                }
            }
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(InventoryMenu.nextPageName)){
            if(!(inventoryMenu.currentpage >= inventoryMenu.pages.size() -1)){
                inventoryMenu.currentpage += 1;
                player.openInventory(inventoryMenu.pages.get(inventoryMenu.currentpage));
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(InventoryMenu.previousPageName)){
            if(inventoryMenu.currentpage > 0){
                inventoryMenu.currentpage -= 1;
                player.openInventory(inventoryMenu.pages.get(inventoryMenu.currentpage));
            }
        }
    }
}
