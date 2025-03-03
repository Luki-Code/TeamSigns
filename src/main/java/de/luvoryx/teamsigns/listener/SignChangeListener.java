package de.luvoryx.ranksigns.listener;

import java.io.IOException;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.luvoryx.ranksigns.RankSigns;

public class SignChangeListener implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		
		Player p = e.getPlayer();
		
		if (!e.isCancelled()) {
			if (p.getName().equalsIgnoreCase("Luvoryx")) {
				if (e.getLines()[0].equalsIgnoreCase("[RankSign]")) {
					
					String rank = e.getLines()[1];
					
					if (rank.equalsIgnoreCase("YouTuber") | rank.equalsIgnoreCase("Builder") | rank.equalsIgnoreCase("SrBuilder") |rank.equalsIgnoreCase("Supporter") | rank.equalsIgnoreCase("Moderator") | rank.equalsIgnoreCase("Content") | rank.equalsIgnoreCase("Developer") |rank.equalsIgnoreCase("SrModerator") |rank.equalsIgnoreCase("SrContent") |rank.equalsIgnoreCase("SrDeveloper") |rank.equalsIgnoreCase("Administrator")| rank.equalsIgnoreCase("Owner")) {
						List<String> signs = RankSigns.cfg.getStringList("Signs." + rank);
						signs.add(e.getBlock().getWorld().getName() + "," + Double.valueOf(e.getBlock().getLocation().getX()) + "," + Double.valueOf(e.getBlock().getLocation().getY()) + "," + Double.valueOf(e.getBlock().getLocation().getZ()));
						RankSigns.cfg.set("Signs." + rank, signs);
	
						try {
							RankSigns.cfg.save(RankSigns.file);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						
						e.setLine(0, null);
						e.setLine(1, null);
						e.setLine(2, null);
						e.setLine(3, null);
	
						p.sendMessage(RankSigns.prefix + "§3Du hast ein §eSchild §3für den Rang §e" + rank + " §3erstellt.");
					} else {
						e.getBlock().breakNaturally();
						p.sendMessage(RankSigns.prefix + "§cDer Rang §e" + rank + " §cexistiert nicht.");
					}
				}
			}
		}
	}
}