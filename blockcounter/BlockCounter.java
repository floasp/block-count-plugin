package me.floasp.blockcounter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class BlockCounter extends JavaPlugin{
	@Override
	public void onEnable() {
//		getLogger().info("onEnable has been invoked!");
		
//		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//		    playerList.put(player.getName(), playerData(player));
//		}
		
//		for(Material m: Material.values()) {
//			if(m.isBlock()) {
//				getServer().broadcastMessage("Material: " + m);
//				System.out.println("Material: " + m);
//			}
////			getServer().broadcastMessage("Material: " + m);
////			System.out.println("Material: " + m + " " + m.toString() + " " + m.name());
//		}
		CountResultAll result = new CountResultAll();
		getServer().broadcastMessage("" + result.count.size());
		System.out.println("" + result.count.size());
		
	}
	
	@Override
	public void onLoad() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("countores")) { 
			
			if (args.length == 4) { 
				try {
					int startx = Integer.parseInt(args[0]);
					int startz = Integer.parseInt(args[1]);
					int sizex = Integer.parseInt(args[2]);
					int sizez = Integer.parseInt(args[3]);
					
					World world = Bukkit.getWorlds().get(0);
					world.getSeed();
					
					//countOres(world, startx, startz, sizex, sizez);
					CountResult result = new CountResult();
					result.startx = startx;
					result.startz = startz;
					result.sizex = sizex;
					result.sizez = sizez;

					int partsize = 32;
					
					int nzp = (int)(sizez / partsize);
					int nxp = (int)(sizex / partsize);

					int extraz = sizez % partsize == 0 ? 0 : 1;
					int extrax = sizex % partsize == 0 ? 0 : 1;
					
					int np = (nzp + extraz)*(nxp + extrax);
					getServer().broadcastMessage("Scheduling " + np + " chunks...");
					
					for(int zp = 0; zp < nzp; zp++) {
						for(int xp = 0; xp < nxp; xp++) {
							scheduleCounting(world, startx + xp * partsize, startz + zp * partsize, partsize, partsize, result, zp*nxp + xp, np);
						}
					}
					
					
//					scheduleCounting(world, startx, startz, sizex, sizez, result);
					
					return true;
				}
				catch(NumberFormatException ex) {
					getServer().broadcastMessage("Error in number format");
					return false; 
				}
			}
			else {
				getServer().broadcastMessage("Wrong arg length. Is: " + args.length);
			}
//			else if(args.length == 1) {
//				
//				return true;
//			}
		}
		if (cmd.getName().equalsIgnoreCase("biomecountall")) {
			if (args.length == 4) { 
				try {
					int startx = Integer.parseInt(args[0]);
					int startz = Integer.parseInt(args[1]);
					int sizex = Integer.parseInt(args[2]);
					int sizez = Integer.parseInt(args[3]);
					
					World world = Bukkit.getWorlds().get(0);
					
					BiomeCounter result = new BiomeCounter(getServer(), this);
					result.startx = startx;
					result.startz = startz;
					result.sizex = sizex;
					result.sizez = sizez;

					int partsize = 16;
					
					int nzp = (int)(sizez / partsize);
					int nxp = (int)(sizex / partsize);

					int extraz = sizez % partsize == 0 ? 0 : 1;
					int extrax = sizex % partsize == 0 ? 0 : 1;
					
					int np = (nzp + extraz)*(nxp + extrax);
					getServer().broadcastMessage("Scheduling " + np + " chunks...");
					
					for(int zp = 0; zp < nzp; zp++) {
						for(int xp = 0; xp < nxp; xp++) {
							result.scheduleBiomeCountingAll(world, startx + xp * partsize, startz + zp * partsize, partsize, partsize, result, zp*nxp + xp, np);
						}
					}
					
					return true;
				}
				catch(NumberFormatException ex) {
					getServer().broadcastMessage("Error in number format");
					return false; 
				}
			}
			else {
				getServer().broadcastMessage("Wrong arg length. Is: " + args.length);
			}
//			else if(args.length == 1) {
//				
//				return true;
//			}
		}
		if (cmd.getName().equalsIgnoreCase("countall")) {
			if (args.length == 4) { 
				try {
					int startx = Integer.parseInt(args[0]);
					int startz = Integer.parseInt(args[1]);
					int sizex = Integer.parseInt(args[2]);
					int sizez = Integer.parseInt(args[3]);
					
					World world = Bukkit.getWorlds().get(0);
					
					CountResultAll result = new CountResultAll();
					result.startx = startx;
					result.startz = startz;
					result.sizex = sizex;
					result.sizez = sizez;

					int partsize = 32;
					
					int nzp = (int)(sizez / partsize);
					int nxp = (int)(sizex / partsize);

					int extraz = sizez % partsize == 0 ? 0 : 1;
					int extrax = sizex % partsize == 0 ? 0 : 1;
					
					int np = (nzp + extraz)*(nxp + extrax);
					getServer().broadcastMessage("Scheduling " + np + " chunks...");
					
					for(int zp = 0; zp < nzp; zp++) {
						for(int xp = 0; xp < nxp; xp++) {
							scheduleCountingAll(world, startx + xp * partsize, startz + zp * partsize, partsize, partsize, result, zp*nxp + xp, np);
						}
					}
					
					
//					scheduleCounting(world, startx, startz, sizex, sizez, result);
					
					return true;
				}
				catch(NumberFormatException ex) {
					getServer().broadcastMessage("Error in number format");
					return false; 
				}
			}
			else {
				getServer().broadcastMessage("Wrong arg length. Is: " + args.length);
			}
//			else if(args.length == 1) {
//				
//				return true;
//			}
		}
		
		if (cmd.getName().equalsIgnoreCase("countoresold")) { 
			
			if (args.length == 4) { 
				try {
					int startx = Integer.parseInt(args[0]);
					int startz = Integer.parseInt(args[1]);
					int sizex = Integer.parseInt(args[2]);
					int sizez = Integer.parseInt(args[3]);
					
					World world = Bukkit.getWorlds().get(0);
					
					countOresOld(world, startx, startz, sizex, sizez);
					
					return true;
				}
				catch(NumberFormatException ex) {
					getServer().broadcastMessage("Error in number format");
					return false; 
				}
			}
			else {
				getServer().broadcastMessage("Wrong arg length. Is: " + args.length);
			}
//			else if(args.length == 1) {
//				
//				return true;
//			}
		}

		return false; 
	}

	private void scheduleCountingAll(World world, int startx, int startz, int sizex, int sizez, CountResultAll result, int part, int parts) {
        // Create the task anonymously and schedule to run it once, after 20 ticks
		new BukkitRunnable() {
            @Override
            public void run() {
                // What you want to schedule goes here
            	countAll(world, startx, startz, sizex, sizez, result, part, parts);
            }
        }.runTaskLater(this, part);
	}

	private void countAll(World world, int startx, int startz, int sizex, int sizez, CountResultAll result, int part, int parts) {
		
		long start = System.currentTimeMillis();
		
//		long block_count = ((long)sizex * (long)sizez * 384);

		for(int y = -64; y < 320; y++) {
			for(int z = startz; z < startz + sizez; z++) {
				for(int x = startx; x < startx + sizex; x++) {
					Location loc = new Location(world, x, y, z);
					Material curr_mat = loc.getBlock().getType();
					
					result.addBlock(curr_mat, y);
				}
			}
		}
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		getServer().broadcastMessage("Finished part " + (part + 1) + "/" + parts + " in " + timeElapsed + " ms.");
		
		if(part + 1 == parts) {
			
			String filename = String.valueOf(world.getSeed()) + ".txt";
			
			try {
				File myObj = new File(filename);
				if (myObj.createNewFile()) {
					getServer().broadcastMessage("File created: " + myObj.getName());
					System.out.println("File created: " + myObj.getName());
				} else {
					getServer().broadcastMessage("File already exists. Overwriting file.");
					System.out.println("File already exists.");
				}
			} 
			catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			try {
				FileWriter myWriter = new FileWriter(filename);

				myWriter.write("Parts: " + parts + "\n");
				myWriter.write("Part size: " + sizex + "\n");
				myWriter.write("Area: X=" + result.startx + ", Z=" + result.startz + ", sizeX=" + result.sizex + ", sizeZ=" + result.sizez + "\n");

				String materialString = "height";
				
				for(int i = 0; i < result.materials.size(); i++) {
					materialString += "," + result.materials.get(i).toString();
				}
				myWriter.write(materialString + "\n");

				String sumString = "sum";
				
				for(int i = 0; i < result.materials.size(); i++) {
					sumString += "," + result.count_all.get(i).toString();
				}
				
				myWriter.write(sumString + "\n");
				
				for(int y = -64; y < 320; y++) {

					String heightCountString = String.valueOf(y);
					
					for(int i = 0; i < result.materials.size(); i++) {
						heightCountString += "," + String.valueOf(result.count.get(i)[y+64]);
					}
					
					myWriter.write(heightCountString  + "\n");
				}

				myWriter.close();
				
				System.out.println("Successfully wrote to output file.");
				getServer().broadcastMessage("Successfully wrote to output file.");
			} 
			catch (IOException e) {
				System.out.println("An error occurred.");
				getServer().broadcastMessage("An error occurred.");
				e.printStackTrace();
			}
		}
	}
	
	private void scheduleCounting(World world, int startx, int startz, int sizex, int sizez, CountResult result, int part, int parts) {
        // Create the task anonymously and schedule to run it once, after 20 ticks
		new BukkitRunnable() {
            @Override
            public void run() {
                // What you want to schedule goes here
				countOres(world, startx, startz, sizex, sizez, result, part, parts);
            }
        }.runTaskLater(this, part);
	}
	
	// coal, copper, lapis, iron, redstone, diamond, gold, emerald, amethyst
	private void countOres(World world, int startx, int startz, int sizex, int sizez, CountResult result, int part, int parts) {
		
		long start = System.currentTimeMillis();
		
//		long block_count = ((long)sizex * (long)sizez * 384);
//		getServer().broadcastMessage("");
//		getServer().broadcastMessage("Checking " + block_count + " blocks...");

		for(int y = -64; y < 320; y++) {
			for(int z = startz; z < startz + sizez; z++) {
				for(int x = startx; x < startx + sizex; x++) {
					Location loc = new Location(world, x, y, z);
					Material curr_mat = loc.getBlock().getType();
					
					if(curr_mat == Material.COAL_ORE || curr_mat == Material.DEEPSLATE_COAL_ORE) {
						result.count_coal[y+64]++;
						result.count_all_coal++;
					}
					else if(curr_mat == Material.COPPER_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						result.count_copper[y+64]++;
						result.count_all_copper++;
					}
					else if(curr_mat == Material.LAPIS_ORE || curr_mat == Material.DEEPSLATE_LAPIS_ORE) {
						result.count_lapis[y+64]++;
						result.count_all_lapis++;
					}
					else if(curr_mat == Material.IRON_ORE || curr_mat == Material.DEEPSLATE_IRON_ORE) {
						result.count_iron[y+64]++;
						result.count_all_iron++;
					}
					else if(curr_mat == Material.REDSTONE_ORE || curr_mat == Material.DEEPSLATE_REDSTONE_ORE) {
						result.count_redstone[y+64]++;
						result.count_all_redstone++;
					}
					else if(curr_mat == Material.DIAMOND_ORE || curr_mat == Material.DEEPSLATE_DIAMOND_ORE) {
						result.count_diamond[y+64]++;
						result.count_all_diamond++;
					}
					else if(curr_mat == Material.GOLD_ORE || curr_mat == Material.DEEPSLATE_GOLD_ORE) {
						result.count_gold[y+64]++;
						result.count_all_gold++;
					}
					else if(curr_mat == Material.EMERALD_ORE || curr_mat == Material.DEEPSLATE_EMERALD_ORE) {
						result.count_emerald[y+64]++;
						result.count_all_emerald++;
					}
					else if(curr_mat == Material.AMETHYST_BLOCK || curr_mat == Material.BUDDING_AMETHYST) {
						result.count_amethyst[y+64]++;
						result.count_all_amethyst++;
					}
				}
			}
		}
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		getServer().broadcastMessage("Finished part " + (part + 1) + "/" + parts + " in " + timeElapsed + " ms.");
//		getServer().broadcastMessage("Coal Ore: " + result.count_all_coal);
//		getServer().broadcastMessage("Copper Ore: " + result.count_all_copper);
//		getServer().broadcastMessage("Lapis Ore: " + result.count_all_lapis);
//		getServer().broadcastMessage("Iron Ore: " + result.count_all_iron);
//		getServer().broadcastMessage("Redstone Ore: " + result.count_all_redstone);
//		getServer().broadcastMessage("Diamond Ore: " + result.count_all_diamond);
//		getServer().broadcastMessage("Gold Ore: " + result.count_all_gold);
//		getServer().broadcastMessage("Emerald Ore: " + result.count_all_emerald);
//		getServer().broadcastMessage("Amethyst: " + result.count_all_amethyst);
		
		if(part + 1 == parts) {
			getServer().broadcastMessage("Coal Ore: " + result.count_all_coal);
			getServer().broadcastMessage("Copper Ore: " + result.count_all_copper);
			getServer().broadcastMessage("Lapis Ore: " + result.count_all_lapis);
			getServer().broadcastMessage("Iron Ore: " + result.count_all_iron);
			getServer().broadcastMessage("Redstone Ore: " + result.count_all_redstone);
			getServer().broadcastMessage("Diamond Ore: " + result.count_all_diamond);
			getServer().broadcastMessage("Gold Ore: " + result.count_all_gold);
			getServer().broadcastMessage("Emerald Ore: " + result.count_all_emerald);
			getServer().broadcastMessage("Amethyst: " + result.count_all_amethyst);
			
			String filename = String.valueOf(world.getSeed()) + ".txt";
			
			try {
				File myObj = new File(filename);
				if (myObj.createNewFile()) {
					getServer().broadcastMessage("File created: " + myObj.getName());
					System.out.println("File created: " + myObj.getName());
				} else {
					getServer().broadcastMessage("File already exists. Overwriting file.");
					System.out.println("File already exists.");
				}
			} 
			catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			try {
				FileWriter myWriter = new FileWriter(filename);

				myWriter.write("Parts: " + parts + "\n");
				myWriter.write("Part size: " + sizex + "\n");
				myWriter.write("Area: X=" + result.startx + ", Z=" + result.startz + ", sizeX=" + result.sizex + ", sizeZ=" + result.sizez + "\n");
				
				myWriter.write("sum," + result.count_all_coal + "," + 
										result.count_all_copper + "," + 
										result.count_all_lapis + "," + 
										result.count_all_iron + "," + 
										result.count_all_redstone + "," + 
										result.count_all_diamond + "," + 
										result.count_all_gold + "," + 
										result.count_all_emerald + "," + 
										result.count_all_amethyst + "\n");
				
				for(int y = -64; y < 320; y++) {
					myWriter.write(y + "," + result.count_coal[y+64] + "," + 
											result.count_copper[y+64] + "," + 
											result.count_lapis[y+64] + "," + 
											result.count_iron[y+64] + "," + 
											result.count_redstone[y+64] + "," + 
											result.count_diamond[y+64] + "," + 
											result.count_gold[y+64] + "," + 
											result.count_emerald[y+64] + "," + 
											result.count_amethyst[y+64] + "\n");
				}

				myWriter.close();
				
				System.out.println("Successfully wrote to output file.");
				getServer().broadcastMessage("Successfully wrote to output file.");
			} 
			catch (IOException e) {
				System.out.println("An error occurred.");
				getServer().broadcastMessage("An error occurred.");
				e.printStackTrace();
			}
		}
	}
	
	// coal, copper, lapis, iron, redstone, diamond, gold, emerald, amethyst
	private void countOresOld(World world, int startx, int startz, int sizex, int sizez) {
		
		long start = System.currentTimeMillis();
		
		long block_count = ((long)sizex * (long)sizez * 384);
		getServer().broadcastMessage("");
		getServer().broadcastMessage("Checking " + block_count + " blocks...");

		int[] count_coal = new int[384];
		int[] count_copper = new int[384];
		int[] count_lapis = new int[384];
		int[] count_iron = new int[384];
		int[] count_redstone = new int[384];
		int[] count_diamond = new int[384];
		int[] count_gold = new int[384];
		int[] count_emerald = new int[384];
		int[] count_amethyst = new int[384];
		int count_all_coal = 0;
		int count_all_copper = 0;
		int count_all_lapis = 0;
		int count_all_iron = 0;
		int count_all_redstone = 0;
		int count_all_diamond = 0;
		int count_all_gold = 0;
		int count_all_emerald = 0;
		int count_all_amethyst = 0;

		for(int y = -64; y < 320; y++) {
			for(int z = startz; z < startz + sizez; z++) {
				for(int x = startx; x < startx + sizex; x++) {
					Location loc = new Location(world, x, y, z);
					Material curr_mat = loc.getBlock().getType();
					
					if(curr_mat == Material.COAL_ORE || curr_mat == Material.DEEPSLATE_COAL_ORE) {
						count_coal[y+64]++;
						count_all_coal++;
					}
					else if(curr_mat == Material.COPPER_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						count_copper[y+64]++;
						count_all_copper++;
					}
					else if(curr_mat == Material.LAPIS_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						count_lapis[y+64]++;
						count_all_lapis++;
					}
					else if(curr_mat == Material.IRON_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						count_iron[y+64]++;
						count_all_iron++;
					}
					else if(curr_mat == Material.REDSTONE_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						count_redstone[y+64]++;
						count_all_redstone++;
					}
					else if(curr_mat == Material.DIAMOND_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						count_diamond[y+64]++;
						count_all_diamond++;
					}
					else if(curr_mat == Material.GOLD_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						count_gold[y+64]++;
						count_all_gold++;
					}
					else if(curr_mat == Material.EMERALD_ORE || curr_mat == Material.DEEPSLATE_COPPER_ORE) {
						count_emerald[y+64]++;
						count_all_emerald++;
					}
					else if(curr_mat == Material.AMETHYST_BLOCK || curr_mat == Material.BUDDING_AMETHYST) {
						count_amethyst[y+64]++;
						count_all_amethyst++;
					}
				}
			}
		}
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		getServer().broadcastMessage("Finished in " + timeElapsed + " ms.");
		getServer().broadcastMessage("Coal Ore: " + count_all_coal);
		getServer().broadcastMessage("Copper Ore: " + count_all_copper);
		getServer().broadcastMessage("Lapis Ore: " + count_all_lapis);
		getServer().broadcastMessage("Iron Ore: " + count_all_iron);
		getServer().broadcastMessage("Redstone Ore: " + count_all_redstone);
		getServer().broadcastMessage("Diamond Ore: " + count_all_diamond);
		getServer().broadcastMessage("Gold Ore: " + count_all_gold);
		getServer().broadcastMessage("Emerald Ore: " + count_all_emerald);
		getServer().broadcastMessage("Amethyst: " + count_all_amethyst);
		
//		result.count_coal = count_coal;
//		result.count_copper = count_copper;
//		result.count_lapis = count_lapis;
//		result.count_iron = count_iron;
//		result.count_redstone = count_redstone;
//		result.count_diamond = count_diamond;
//		result.count_gold = count_gold;
//		result.count_emerald = count_emerald;
//		result.count_amethyst = count_amethyst;
//		result.count_all_coal = count_all_coal;
//		result.count_all_copper = count_all_copper;
//		result.count_all_lapis = count_all_lapis;
//		result.count_all_iron = count_all_iron;
//		result.count_all_redstone = count_all_redstone;
//		result.count_all_diamond = count_all_diamond;
//		result.count_all_gold = count_all_gold;
//		result.count_all_emerald = count_all_emerald;
//		result.count_all_amethyst = count_all_amethyst;
		
//		return result;
	}
}
