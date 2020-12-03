package life.midorin.info.lifers.commands;

import life.midorin.info.lifers.Lifers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unLock implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player) sender;
        for (int i = 0; i< Lifers.player_data.get(player).size(); i++)
            player.sendMessage(Lifers.player_data.get(player).get(i));
        return true;
    }
}
