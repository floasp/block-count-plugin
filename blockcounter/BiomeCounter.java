package me.floasp.blockcounter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BiomeCounter {
	public int startx;
	public int startz;
	public int sizex;
	public int sizez;
	
	ArrayList<CountResultAll> biomeResults;
	ArrayList<Biome> biomes;
	
	Server server;
	Plugin plugin;
	
	public BiomeCounter(Server server, Plugin plugin) {
		this.biomeResults = new ArrayList<CountResultAll>();
		this.biomes = new ArrayList<Biome>();
		this.server = server;
		this.plugin = plugin;
	}
	
	public Server getServer() {
		return this.server;
	}
	
	public void addBlock(Material block, Biome biome, int height) {
		int index = this.biomes.indexOf(biome);
		if(index >= 0) {
			this.biomeResults.get(index).addBlock(block, height);
		}
		else {
			this.biomeResults.add(new CountResultAll());
			this.biomes.add(biome);
			
			index = this.biomes.indexOf(biome);
			this.biomeResults.get(index).startx = this.startx;
			this.biomeResults.get(index).startz = this.startz;
			this.biomeResults.get(index).sizex = this.sizex;
			this.biomeResults.get(index).sizez = this.sizez;
			this.biomeResults.get(index).addBlock(block, height);
		}
	}
	
	
	public void scheduleBiomeCountingAll(World world, int startx, int startz, int sizex, int sizez, BiomeCounter result, int part, int parts) {
        // Create the task anonymously and schedule to run it once, after 20 ticks
		new BukkitRunnable() {
            @Override
            public void run() {
                // What you want to schedule goes here
            	biomeCountAll(world, startx, startz, sizex, sizez, result, part, parts);
            }
        }.runTaskLater(this.plugin, part);
	}

	private void biomeCountAll(World world, int startx, int startz, int sizex, int sizez, BiomeCounter result, int part, int parts) {
		
		long start = System.currentTimeMillis();

		for(int y = -64; y < 320; y++) {
			for(int z = startz; z < startz + sizez; z++) {
				for(int x = startx; x < startx + sizex; x++) {
					Location loc = new Location(world, x, y, z);
					Material curr_mat = loc.getBlock().getType();
					Biome biome = loc.getBlock().getBiome();
					
					result.addBlock(curr_mat, biome, y);
				}
			}
		}
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		getServer().broadcastMessage("Finished part " + (part + 1) + "/" + parts + " in " + timeElapsed + " ms.");
		
		if(part + 1 == parts) {
			createFile(world, result, parts);
		}
	}
	
	private void createFile(World world, BiomeCounter result, int parts) {
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

			for(CountResultAll biome_result: this.biomeResults) {
				int index = this.biomeResults.indexOf(biome_result);
				myWriter.write("Biome: " + this.biomes.get(index).toString() + "\n");
				biome_result.writeResult(myWriter);
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
