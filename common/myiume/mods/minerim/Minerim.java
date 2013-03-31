/*******************************************************************************
 * Copyright (c) 2012 myiume
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     myiume - Minerim Lead Developer
 *     Dev2140 - Minerim Developer
 ******************************************************************************/
package myiume.mods.minerim;
import cpw.mods.fml.comon.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "Minerim", name = "Minerim", dependencies = "required-after:Forge@[7.0,);required-after:FML@[5.0.5,)")
@NetworkMod(channels = { "Minerim" }, versionBounds = "[0.1,)", clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Minerim {
  public static BlockMinerimRock minerimRockBlock;
  @SidedProxy(clientSide = "myiume.mods.minerim.client.ClientProxy", serverSide = "myiume.mods.minerim.CommonProxy")
  public static CommonProxy proxy;
  @Instance("Minerim")
  public static Minerim instance;
  private int blockId;
  
  @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        Version.init(event.getVersionProperties());
        event.getModMetadata().version = Version.fullVersionString();
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        try
        {
            cfg.load();
            blockId = cfg.getBlock("minerimRock", 975).getInt(975);
            ChestChangerType.buildItems(cfg, 19501);
        }
        catch (Exception e)
        {
            FMLLog.log(Level.SEVERE, e, "Minerim has a problem loading its configuration");
        }
        finally
        {
            cfg.save();
        }
    }

    @Init
    public void load(FMLInitializationEvent evt)
    {
        minerimRockBlock = new BlockMinerimRock(blockId);
        GameRegistry.registerBlock(minerimRockBlock, ItemMinerimRock.class, "BlockMinerimRock");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @PostInit
    public void modsLoaded(FMLPostInitializationEvent evt)
    {
    }

}
  
