package life.midorin.info.lifers.util;

import life.midorin.info.lifers.Lifers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig
{
    public static File dataFile;
    public static FileConfiguration data;
    public static void create(String file_name)
    {
        dataFile = new File(Lifers.plugin.getDataFolder(), file_name + ".yml");
        data = new YamlConfiguration();
        try
        {
            if (!dataFile.exists())
            {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
                data.load(dataFile);
            }
            else System.out.println(Messages.PREFIX + ChatColor.RED + "File creation failure.");
        }
        catch (IOException | InvalidConfigurationException ex)
        { ex.printStackTrace(); }
    }
    public static void delete(String file_name)
    {
        dataFile = new File(Lifers.plugin.getDataFolder(), file_name + ".yml");
        data = new YamlConfiguration();
        if (dataFile.exists())
        {
            dataFile.delete();
        }
        else System.out.println(Messages.PREFIX + ChatColor.RED + "File erasure failure.");
    }
    public static void reload()
    {
        Lifers.plugin.reloadConfig();
        System.out.println(Messages.PREFIX + ChatColor.GREEN + "File reload completed.");
    }
    public static FileConfiguration getConfig()
    {
        return data;
    }
}