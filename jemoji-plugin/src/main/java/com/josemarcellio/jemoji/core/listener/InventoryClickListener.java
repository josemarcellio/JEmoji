package com.josemarcellio.jemoji.core.listener;

import com.josemarcellio.jemoji.common.executor.Executor;
import com.josemarcellio.jemoji.core.JEmoji;
import com.josemarcellio.jemoji.core.executor.*;
import com.josemarcellio.jemoji.core.inventory.InventoryMenu;
import com.josemarcellio.jemoji.core.util.ServerUtil;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickListener implements Listener {

    private final JEmoji plugin;
    private final List<Executor> executor = new ArrayList<> ();

    public InventoryClickListener(JEmoji plugin) {
        this.plugin = plugin;
        executor.add(new CloseInventoryExecutor () );
        executor.add(new ConsoleCommandExecutor () );
        executor.add(new MessageExecutor () );
        executor.add(new PlayerChatExecutor () );
        executor.add(new PlayerCommandExecutor () );
        executor.add(new SoundExecutor () );
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
            if (event.getCurrentItem ().getItemMeta ().getDisplayName ().equals ( Utility.getColor ( emoji ) )) {
                if (player.hasPermission ( "jemoji.*" ) || player.hasPermission ( permission ) || permission.equalsIgnoreCase ( "none" ) || price < 0) {
                    return;
                }
                if (ServerUtil.getPluginEnabled ("Vault")) {
                    if (JEmoji.getEconomy ().getMoney ( player ) >= price) {
                        JEmoji.getEconomy ().takeMoney ( player, price );
                        file.getStringList("emoji." + string + ".executor").forEach(exec -> {
                            for (Executor executor : executor) {
                                if (exec.startsWith("[success-" + executor.getExecutor () + "]")) {
                                    int req = 10 + executor.getExecutor ().length();
                                    String cmd = exec.substring(req);
                                    executor.execute(plugin, player, Utility.getColor(cmd).replace("{player}", player.getName()).replace("{permission}", permission).replace("{emoji}", emoji).replace("{price}", String.valueOf(price)));
                                }
                            }
                        });
                    } else {
                        file.getStringList("emoji." + string + ".executor").forEach(exec -> {
                            for (Executor executor : executor) {
                                if (exec.startsWith("[failed-" + executor.getExecutor () + "]")) {
                                    int req = 9 + executor.getExecutor ().length();
                                    String cmd = exec.substring(req);
                                    executor.execute(plugin, player, Utility.getColor(cmd).replace("{player}", player.getName()).replace("{permission}", permission).replace("{emoji}", emoji).replace("{price}", String.valueOf(price)));
                                }
                            }
                        });
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
