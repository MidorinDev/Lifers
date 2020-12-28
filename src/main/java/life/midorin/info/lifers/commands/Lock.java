package life.midorin.info.lifers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Lock implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        /*Player p = (Player) sender;
        if (!Lifers.player_data.containsKey(p)) Lifers.player_data.put(p, new ArrayList<>());
        Lifers.player_data.get(p).add("chest-" + "b");
        p.sendMessage(Messages.PREFIX + ChatColor.YELLOW + "ドアを登録しました。");*/
        return true;
    }
}
