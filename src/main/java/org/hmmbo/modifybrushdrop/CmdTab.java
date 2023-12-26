package org.hmmbo.modifybrushdrop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 1){
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("drops","give"), new ArrayList<>());
        }else  if(args.length == 2){
            if(args[0].equalsIgnoreCase("drops")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList("add", "remove", "list"), new ArrayList<>());
            }else if(args[0].equalsIgnoreCase("give")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList("sand", "gravel"), new ArrayList<>());
            }
            }else  if(args.length == 3){
            return StringUtil.copyPartialMatches(args[2], Arrays.asList("Item_Name"), new ArrayList<>());
        }



        return null;
    }
}
