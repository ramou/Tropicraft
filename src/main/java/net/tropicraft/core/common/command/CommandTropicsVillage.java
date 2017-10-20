package net.tropicraft.core.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.tropicraft.Tropicraft;
import net.tropicraft.core.common.capability.PlayerDataInstance;
import net.tropicraft.core.common.capability.WorldDataInstance;
import net.tropicraft.core.common.dimension.WorldProviderTropicraft;
import net.tropicraft.core.common.town.ISimulationTickable;
import net.tropicraft.core.common.town.ManagedLocation;
import net.tropicraft.core.common.worldgen.village.TownKoaVillage;
import net.tropicraft.core.common.worldgen.village.TownKoaVillageGenHelper;

public class CommandTropicsVillage extends CommandBase {

    @Override
    public String getName() {
        return "tc_village";
    }

    @Override
    public String getUsage(ICommandSender commandSender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender commandSender,
                        String[] args) throws CommandException {
        EntityPlayerMP player = this.getCommandSenderAsPlayer(commandSender);

        if (args.length > 0) {
            if (args[0].equals("village_new")) {
                int x = MathHelper.floor(player.posX);
                int z = MathHelper.floor(player.posZ);
                int y = player.world.getHeight(x, z);

                if (y < WorldProviderTropicraft.MID_HEIGHT) y = WorldProviderTropicraft.MID_HEIGHT + 1;

                TownKoaVillage village = new TownKoaVillage();

                //WorldDirector wd = WorldDirectorManager.instance().getCoroUtilWorldDirector(player.world);
                //questionable ID setting
                //int newID = wd.lookupTickingManagedLocations.size();
                //TODO: temp until world cap added
                int newID = player.world.rand.nextInt(9999);
                village.initData(newID, player.world.provider.getDimension(), new BlockPos(x, y, z));
                village.initFirstTime();
                //wd.addTickingLocation(village);
            } else if (args[0].equals("village_try")) {

                WorldDataInstance storage = player.world.getCapability(Tropicraft.WORLD_DATA_INSTANCE, null);
                if (storage != null) {
                    storage.test++;
                    System.out.println(storage.test);
                    //return;
                }

                int x = MathHelper.floor(player.posX);
				int z = MathHelper.floor(player.posZ);
				int y = player.world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
				if (y < WorldProviderTropicraft.MID_HEIGHT) y = WorldProviderTropicraft.MID_HEIGHT+1;
				boolean result = TownKoaVillageGenHelper.hookTryGenVillage(new BlockPos(x, y, z), player.world);
                if (!result) {
                    System.out.println("failed to gen village");
                }
            }/* else if (args[0].equals("village_clear")) {
				WorldDirector wd = WorldDirectorManager.instance().getCoroUtilWorldDirector(player.world);
				
				for (Entry<Integer, ISimulationTickable> entry : wd.lookupTickingManagedLocations.entrySet()) {
					entry.getValue().cleanup();
					wd.removeTickingLocation(entry.getValue());
				}
			} else if (args[0].equals("village_regen")) {
				WorldDirector wd = WorldDirectorManager.instance().getCoroUtilWorldDirector(player.world);
				Iterator it = wd.lookupTickingManagedLocations.values().iterator();
				while (it.hasNext()) {
					ManagedLocation ml = (ManagedLocation) it.next();
					ml.initFirstTime();
				}
			} else if (args[0].equals("village_repopulate")) {
				WorldDirector wd = WorldDirectorManager.instance().getCoroUtilWorldDirector(player.world);
				Iterator it = wd.lookupTickingManagedLocations.values().iterator();
				while (it.hasNext()) {
					
					ManagedLocation ml = (ManagedLocation) it.next();
					if (ml instanceof TownKoaVillage) {
						//((TownKoaVillage) ml).spawnEntities();
					}
				}
			}*/
        }
    }

}
