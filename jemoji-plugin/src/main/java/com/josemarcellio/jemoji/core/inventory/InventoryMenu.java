package com.josemarcellio.jemoji.core.inventory;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryMenu {

    private JavaPlugin plugin;

    public InventoryMenu(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ArrayList<Inventory> pages = new ArrayList<> ();
    public UUID uuid;
    public int currentpage = 0;
    public static HashMap<UUID, InventoryMenu> players = new HashMap<> ();
    public InventoryMenu(ArrayList<ItemStack> items, String name, Player p){
        this.uuid = UUID.randomUUID();
        Inventory inventory = getBlankPage(name);
        for (ItemStack item : items) {
            if (inventory.firstEmpty () == 28) {
                pages.add ( inventory );
                inventory = getBlankPage ( name );
            }
            inventory.addItem ( item );
        }
        pages.add(inventory);
        p.openInventory(pages.get(currentpage));
        players.put(p.getUniqueId(), this);
    }

    public static final String nextPageName = Utility.getColor("&7Next Page &e&l→");
    public static final String previousPageName = Utility.getColor("&e&l← &7Previous Page");

    private Inventory getBlankPage(String name){
        Inventory inventory = Bukkit.createInventory(null, 36, name);

        ItemStack nextpage = XMaterial.ARROW.parseItem();
        if(nextpage != null) {
            ItemMeta meta = nextpage.getItemMeta();
            meta.setDisplayName ( nextPageName );
            nextpage.setItemMeta ( meta );
        }

        ItemStack previouspage = XMaterial.ARROW.parseItem();
        if (previouspage != null) {
            ItemMeta meta = previouspage.getItemMeta ();
            meta.setDisplayName ( previousPageName );
            previouspage.setItemMeta ( meta );
        }

            inventory.setItem ( 35, nextpage );
            inventory.setItem ( 27, previouspage );

        return inventory;
    }

    public void open(Player player) {
        ArrayList<ItemStack> items = new ArrayList<> ();
        FileConfiguration file = plugin.getConfig();
        for (String string : file.getConfigurationSection ( "emoji" ).getKeys ( false )) {
            String name = file.getString("emoji." + string + ".name");
            String emoji = file.getString ( "emoji." + string + ".set-emoji" );
            String material = file.getString ( "emoji." + string + ".icon" );
            String permission = file.getString ( "emoji." + string + ".permission" );
            int price = file.getInt ( "emoji." + string + ".price" );
            List<String> loree = file.getStringList ( "emoji." + string + ".lore" );
            String status;
            if (player.hasPermission(permission) || player.hasPermission("jemoji.*")) {
                status = file.getString("status-owned");
            } else {
                status = file.getString("status-purchase");
            }
            String[] split = material.split ( ";" );
            ItemStack itemStack;
            if(split[0].equals("head")) {
                itemStack = XMaterial.PLAYER_HEAD.parseItem();
                if (itemStack != null) {
                    itemStack.setDurability ( (short) 3);
                    SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta ();
                    SkullUtils.applySkin ( skullMeta, split[1] );
                    skullMeta.setDisplayName ( Utility.getPlaceholder (player, name.replace("{emoji}", emoji) ) );
                    skullMeta.setLore ( loree.stream ().map ( lore -> Utility.getPlaceholder(player, lore.replace("{prefix}", string).replace("{emoji}", emoji).replace("{price}", String.valueOf(price)).replace("{permission}", permission).replace("{status}", status) )).collect ( Collectors.toList () ) );
                    itemStack.setItemMeta ( skullMeta );
                    items.add ( itemStack );
                }
            } else {
                itemStack = XMaterial.valueOf ( material ).parseItem ();
                if (itemStack != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta ();
                    itemMeta.setDisplayName ( Utility.getPlaceholder (player, name.replace("{emoji}", emoji) ) );
                    itemMeta.setLore ( loree.stream ().map ( lore -> Utility.getPlaceholder(player, lore.replace("{prefix}", string).replace("{emoji}", emoji).replace("{price}", String.valueOf(price)).replace("{permission}", permission).replace("{status}", status) )).collect ( Collectors.toList () ) );
                    itemStack.setItemMeta ( itemMeta );
                    items.add ( itemStack );
                }
            }
            new InventoryMenu ( items, Utility.getPlaceholder(player, file.getString("gui-name")), player );
        }
    }
}