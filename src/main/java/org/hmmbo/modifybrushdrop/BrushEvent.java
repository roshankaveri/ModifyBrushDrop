package org.hmmbo.modifybrushdrop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.block.data.Brushable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.awt.peer.LabelPeer;
import java.util.ArrayList;
import java.util.List;

public class BrushEvent implements Listener , CommandExecutor {
    public static List<Drop> d;
    List<Location> bd = new ArrayList<>();

    public BrushEvent(List<Drop> d) {
        this.d = d;
    }

    public static void update(List<Drop> d){
        BrushEvent.d = d;
    }

    @EventHandler
    public void onBrush(PlayerInteractEvent e){

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() != Material.SUSPICIOUS_GRAVEL &&
                e.getClickedBlock().getType() != Material.SUSPICIOUS_SAND) return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.BRUSH) return;
        if(d.isEmpty()){return;}
        if(!e.getPlayer().hasPermission("brushdrop.customdrops")){return;}
        Drop drop = Drop.getRandom(d);
        BrushableBlock block = (BrushableBlock) e.getClickedBlock().getState();
        Brushable brushable = (Brushable) block.getBlockData();
        if(brushable.getDusted() == brushable.getMaximumDusted()){
            Bukkit.getScheduler().runTaskLater(ModifyBrushDrop.pl ,f ->{

                bd.remove(e.getClickedBlock().getLocation());
            },100);
        }
        if(bd.contains(e.getClickedBlock().getLocation())){return;}
        block.setItem(drop.itemStack);

        bd.add(e.getClickedBlock().getLocation());
        block.update(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        if(!p.hasPermission("brushdrop.commands")){return false;}
        if(args.length == 3) {
            if (args[0].equalsIgnoreCase("drops")) {
                if (args[1].equalsIgnoreCase("add")) {
                    Drop.set(p.getInventory().getItemInMainHand(), args[2]);
                    BrushEvent.update(Drop.getDrops());
                    p.sendMessage("[BrushDrops] Added MainHand ItemStack To List");
                }
                if (args[1].equalsIgnoreCase("remove")) {
                    Drop.remove(args[2]);
                    BrushEvent.update(Drop.getDrops());
                    p.sendMessage("[BrushDrops] Removed ItemStack From List");

                }
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args[1].equalsIgnoreCase("sand")) {
                    p.getInventory().addItem(Drop.giveSus(Material.SUSPICIOUS_SAND.toString(), args[2]));
                }
                if (args[1].equalsIgnoreCase("gravel")) {
                    p.getInventory().addItem(Drop.giveSus(Material.SUSPICIOUS_GRAVEL.toString(), args[2]));
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("drops")) {
                if (args[1].equalsIgnoreCase("list")) {
                    Drop.list(args[0], p);
                }
            }
        }
        else{
            p.sendMessage("[BlockDrop] : Unknown Command");
        }
        return false;
    }
}
