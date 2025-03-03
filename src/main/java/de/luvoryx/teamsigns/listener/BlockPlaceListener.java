package de.luvoryx.ranksigns.listener;

import java.io.IOException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.luvoryx.ranksigns.RankSigns;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		
		if (!e.isCancelled()) {
			if (p.getName().equalsIgnoreCase("Luvoryx")) {
				if (e.getBlock().getType() == Material.SKULL) {
					
					String rank = "";
					
					Skull skull = (Skull) e.getBlock().getState();
					if (skull.getOwner() != null) {
						rank = skull.getOwner();
						
						if (rank.equalsIgnoreCase("YouTuber") | rank.equalsIgnoreCase("Builder") | rank.equalsIgnoreCase("SrBuilder") |rank.equalsIgnoreCase("Supporter") | rank.equalsIgnoreCase("Moderator") | rank.equalsIgnoreCase("Content") | rank.equalsIgnoreCase("Developer") |rank.equalsIgnoreCase("SrModerator") |rank.equalsIgnoreCase("SrContent") |rank.equalsIgnoreCase("SrDeveloper") |rank.equalsIgnoreCase("Administrator")| rank.equalsIgnoreCase("Owner")) {
							List<String> heads = RankSigns.cfg.getStringList("Heads." + rank);
							heads.add(e.getBlock().getWorld().getName() + "," + Double.valueOf(e.getBlock().getLocation().getX()) + "," + Double.valueOf(e.getBlock().getLocation().getY()) + "," + Double.valueOf(e.getBlock().getLocation().getZ()));
							RankSigns.cfg.set("Heads." + rank, heads);
		
							try {
								RankSigns.cfg.save(RankSigns.file);
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							
							skull.setOwner("MHF_Question");
							skull.update();
		
							p.sendMessage(RankSigns.prefix + "§3Du hast einen §eKopf §3für den Rang §e" + rank + " §3erstellt.");
						} else {
							e.getBlock().breakNaturally();
							p.sendMessage(RankSigns.prefix + "§cDer Rang §e" + rank + " §cexistiert nicht.");
						}
					}
				}
			}
		}
	}
}