package studio.magemonkey.genesis.addon.inventorycommands;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import studio.magemonkey.genesis.api.GenesisAddonConfig;
import studio.magemonkey.genesis.api.GenesisAddonConfigurable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryCommands extends GenesisAddonConfigurable {
    public static InventoryCommands gsm;

    private GSMItems       items;
    private PlayerListener listener;


    public InventoryCommands() {
        gsm = this;
    }

    public GSMItems getGSMItems() {
        return this.items;
    }

    public void reload() {
        getAddonConfig().reload();
        addDefaultConfig();
        this.items.reload(this);
        this.listener.reload(this);
    }

    public void genesisReloaded(CommandSender sender) {
        reload();
    }

    public void disableAddon() {
    }

    @Override
    public void genesisFinishedLoading() {
    }

    public void enableAddon() {
        reloadConfig();
        addDefaultConfig();

        this.items = new GSMItems(this);
        this.listener = new PlayerListener(this);
        getServer().getPluginManager().registerEvents(this.listener, this);

        Objects.requireNonNull(getCommand("gsm")).setExecutor(new Commander(this));
    }

    public String getAddonName() {
        return "GuiShopManager";
    }

    public String getRequiredGenesisVersion() {
        return "1.0.0";
    }

    private void addDefaultConfig() {
        GenesisAddonConfig ac = getAddonConfig();
        FileConfiguration  c  = getConfig();


        if (getConfig().getString("CreationVersion") == null) {
            List<String> compass = new ArrayList<>();
            compass.add("name:&aQuick Warp &7(Right Click) &6[x]");
            compass.add("type:COMPASS");
            compass.add("amount:1");

            List<String> book = new ArrayList<>();
            book.add("name:&6[o] &4&lGenesis &r&6Menu &6[o]");
            book.add("lore:&7Right Click to open the Menu!");
            book.add("type:BOOK");
            book.add("amount:1");

            List<String> feather = new ArrayList<>();
            feather.add("name:&e&lRight Click to &2&l&ntoggle Fly");
            feather.add("lore:&8A command example.");
            feather.add("type:FEATHER");
            feather.add("amount:1");
            feather.add("enchantment:DURABILITY#1");
            feather.add("hideflag:all");

            List<String> command_commands = new ArrayList<>();
            command_commands.add("fly %player%");

            c.set("Items.GenesisMenu.Look", book);
            c.set("Items.GenesisMenu.GiveOnJoin", Boolean.TRUE);
            c.set("Items.GenesisMenu.InventoryLocation", 1);
            c.set("Items.GenesisMenu.OpenShop", "menu");

            c.set("Items.Servers.Look", compass);
            c.set("Items.Servers.GiveOnJoin", Boolean.TRUE);
            c.set("Items.Servers.InventoryLocation", 9);
            c.set("Items.Servers.OpenShop", "bungeecordservers");
            c.set("Items.Servers.World", "spawn:hub:world1");

            c.set("Items.Command.Look", feather);
            c.set("Items.Command.GiveOnJoin", Boolean.TRUE);
            c.set("Items.Command.InventoryLocation", 5);
            c.set("Items.Command.Command", command_commands);
        }


        ac.addDefault("CreationVersion", getDescription().getVersion());
        ac.addDefault("Settings.JoinDelay", -1);
        ac.addDefault("Settings.AllowPlaceItems", Boolean.FALSE);
        ac.addDefault("Settings.AllowMoveItems", Boolean.FALSE);
        ac.addDefault("Settings.AllowDropItems", Boolean.FALSE);
        ac.addDefault("Settings.AcceptLeftClick", Boolean.FALSE);
        ac.addDefault("Settings.DropItemsOnDeath", Boolean.FALSE);
        ac.addDefault("Settings.GetItemsOnRespawn", Boolean.TRUE);
        ac.addDefault("Settings.ClearInvOnJoin", Boolean.FALSE);
        ac.addDefault("Settings.ClearInvOnWorldChange", Boolean.FALSE);
        getAddonConfig().save();
    }

    @Override
    public boolean saveConfigOnDisable() {
        return false;
    }

}