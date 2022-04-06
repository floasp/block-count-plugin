package me.floasp.blockcounter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;

class CountResultAll {
	public int startx;
	public int startz;
	public int sizex;
	public int sizez;

	ArrayList<int[]> count;
	ArrayList<Integer> count_all;
	ArrayList<Material> materials;
	
	public CountResultAll() {
		startx = 0;
		startz = 0;
		sizex = 0;
		sizez = 0;
		
		this.count = new ArrayList<int[]>();
		this.count_all = new ArrayList<Integer>();
		this.materials = new ArrayList<Material>();
		
	}
	
	public void addBlock(Material block, int height) {
		int index = this.materials.indexOf(block);
		if(index >= 0) {
			this.count.get(index)[height+64]++;
			this.count_all.set(index, this.count_all.get(index) + 1);
		}
		else {
			this.count.add(new int[384]);
			this.count_all.add(1);
			this.materials.add(block);
			
			index = this.materials.indexOf(block);
			this.count.get(index)[height+64]++;
		}
	}
	
	public void writeResult(FileWriter myWriter) throws IOException {
		String materialString = "height";
		
		for(int i = 0; i < this.materials.size(); i++) {
			materialString += "," + this.materials.get(i).toString();
		}
		myWriter.write(materialString + "\n");

		String sumString = "sum";
		
		for(int i = 0; i < this.materials.size(); i++) {
			sumString += "," + this.count_all.get(i).toString();
		}
		
		myWriter.write(sumString + "\n");
		
		for(int y = -64; y < 320; y++) {

			String heightCountString = String.valueOf(y);
			
			for(int i = 0; i < this.materials.size(); i++) {
				heightCountString += "," + String.valueOf(this.count.get(i)[y+64]);
			}
			
			myWriter.write(heightCountString  + "\n");
		}
	}
}