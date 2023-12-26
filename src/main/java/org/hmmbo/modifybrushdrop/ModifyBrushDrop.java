package org.hmmbo.modifybrushdrop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Objects;

public final class ModifyBrushDrop extends JavaPlugin {
    public static ModifyBrushDrop instance;
    public static Plugin pl;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        pl = this;
        getConfig().options().copyDefaults();
        File qfile = new File(getDataFolder(), "config.yml");
        if (!qfile.exists()) {
            saveResource("config.yml", false);
        }
       List<Drop> d = Drop.getDrops();
        BrushEvent md = new BrushEvent(d);
        Bukkit.getPluginManager().registerEvents(md,this);
        Objects.requireNonNull(getCommand("brushdrop")).setExecutor(md);
        Objects.requireNonNull(getCommand("brushdrop")).setTabCompleter(new CmdTab());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
