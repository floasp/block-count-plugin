package me.floasp.blockcounter;

class CountResult { 
	public int startx;
	public int startz;
	public int sizex;
	public int sizez;
	
	public int[] count_coal;
	public int[] count_copper;
	public int[] count_lapis;
	public int[] count_iron;
	public int[] count_redstone;
	public int[] count_diamond;
	public int[] count_gold;
	public int[] count_emerald;
	public int[] count_amethyst;
	
	public int count_all_coal;
	public int count_all_copper;
	public int count_all_lapis;
	public int count_all_iron;
	public int count_all_redstone;
	public int count_all_diamond;
	public int count_all_gold;
	public int count_all_emerald;
	public int count_all_amethyst;
	
	public CountResult() {
		startx = 0;
		startz = 0;
		sizex = 0;
		sizez = 0;
		
		count_coal = new int[384];
		count_copper = new int[384];
		count_lapis = new int[384];
		count_iron = new int[384];
		count_redstone = new int[384];
		count_diamond = new int[384];
		count_gold = new int[384];
		count_emerald = new int[384];
		count_amethyst = new int[384];
		
		count_all_coal = 0;
		count_all_copper = 0;
		count_all_lapis = 0;
		count_all_iron = 0;
		count_all_redstone = 0;
		count_all_diamond = 0;
		count_all_gold = 0;
		count_all_emerald = 0;
		count_all_amethyst = 0;
	}
} 