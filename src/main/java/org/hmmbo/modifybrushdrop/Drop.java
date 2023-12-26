package org.hmmbo.modifybrushdrop;

import org.bukkit.Material;
import org.bukkit.block.BrushableBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.loot.Lootable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Drop {
    Double chance;
    ItemStack itemStack;

    public Drop(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static List<Drop> getDrops(){
        List<Drop> ld = new ArrayList<>();
        if(ModifyBrushDrop.instance.getConfig().getConfigurationSection("LootTable") == null){return ld;}
        for(String key :  ModifyBrushDrop.instance.getConfig().getConfigurationSection("LootTable").getKeys(false)){
            ItemStack item = ItemStack.deserialize(ModifyBrushDrop.instance.getConfig().getConfigurationSection("LootTable." + key).getValues(false));
            ld.add(new Drop(item));
        }
        return ld;
    }

    public static Drop getRandom(List<Drop> list){
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }
    public static void set(ItemStack i, String name){
        ModifyBrushDrop.instance.getConfig().set("LootTable."+name,i.serialize());
        ModifyBrushDrop.instance.saveConfig();
        ModifyBrushDrop.instance.reloadConfig();
    }
    public static void remove(String name){
        ModifyBrushDrop.instance.getConfig().set("LootTable."+name,null);
        ModifyBrushDrop.instance.saveConfig();
        ModifyBrushDrop.instance.reloadConfig();
    }
    public static void list(String name, Player player){
        int i = 1;
       for(String key : ModifyBrushDrop.instance.getConfig().getConfigurationSection("LootTable").getKeys(false)){
           player.sendMessage(i + " | " +key);
       }
    }

    public static ItemStack giveSus(String mat, String key){
        ItemStack sand = new ItemStack(Material.getMaterial(mat));
        BlockStateMeta sandMeta = (BlockStateMeta) sand.getItemMeta();
        BrushableBlock blockState = (BrushableBlock) sandMeta.getBlockState();
        ItemStack item = ItemStack.deserialize(ModifyBrushDrop.instance.getConfig().getConfigurationSection("LootTable." + key).getValues(false));
        blockState.setItem(item);
        sandMeta.setBlockState(blockState);
        sand.setItemMeta(sandMeta);
        return sand;
    }

}
