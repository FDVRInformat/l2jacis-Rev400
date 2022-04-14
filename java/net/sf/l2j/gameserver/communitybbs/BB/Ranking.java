/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.communitybbs.BB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ThreadPool;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.util.StringUtil;

/**
 * @author Juvenil Walker
 *
 */
public class Ranking
{
	public static final int PERIOD = 1 * 60 * 5000; // 1 minutes
	
	Map<String, RankingHolder> pvp = new HashMap<>();
	Map<String, RankingHolder> pk = new HashMap<>();
	Map<String, RankingHolder> classname = new HashMap<>();
	
	public Ranking()
	{
		ThreadPool.scheduleAtFixedRate(() -> generate(), 0, PERIOD);
	}
	
	public void generate()
	{
		pvpList();
		pkList();
	}
	
	private void pvpList()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement ps = con.prepareStatement("SELECT char_name, pvpkills, online, onlinetime FROM characters WHERE pvpkills>0 AND accesslevel=0 ORDER BY pvpkills DESC LIMIT 18");
			ResultSet rs = ps.executeQuery();
			
			pvp.clear();
			while (rs.next())
			{
				RankingHolder rh = new RankingHolder();
				rh.name = rs.getString("char_name");
				rh.pvpKills = rs.getInt("pvpkills");
				rh.onlineTime = onlineTime(rs.getInt("onlinetime"));
				
				pvp.put(rh.name, rh);
			}
			
			rs.close();
			ps.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void pkList()
	{
		try (Connection con =L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement ps = con.prepareStatement("SELECT char_name, pkkills, online, onlinetime FROM characters WHERE pkkills>0 AND accesslevel=0 ORDER BY pkkills DESC LIMIT 18");
			ResultSet rs = ps.executeQuery();
			
			pk.clear();
			
			while (rs.next())
			{
				RankingHolder rh = new RankingHolder();
				rh.name = rs.getString("char_name");
				rh.pkKills = rs.getInt("pkkills");
				rh.onlineTime = onlineTime(rs.getInt("onlinetime"));
				
				pk.put(rh.name, rh);
			}
			
			rs.close();
			ps.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getPvp()
	{
		pvp.forEach((k, v) ->
		{
			Player p = L2World.getInstance().getPlayer(v.name);
			
			if (p != null)
			{
				
				v.pvpKills = p.getPvpKills();
			}
		});
		
		List<RankingHolder> ranking = pvp.values().stream().sorted((o1, o2) -> o2.pvpKills.compareTo(o1.pvpKills)).limit(18).collect(Collectors.toList());
		
		final StringBuilder sb = new StringBuilder(2000);
		
		int pos = 1;
		for (RankingHolder rh : ranking)
		{
			StringUtil.append(sb, "<table border=0 cellspacing=0 cellpadding=2 height=19 width=610><tr><td FIXWIDTH=5></td>");
			StringUtil.append(sb, "<td FIXWIDTH=20>" + pos + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=160>" + rh.name + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=165>" + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=80>" + rh.pvpKills + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=70>" + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=150>" + rh.onlineTime + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=65>" + (L2World.getInstance().getPlayer(rh.name) != null ? "<font color=99FF00>Online</font>" : "<font color=CC0000>Offline</font>") + "</td>");
			StringUtil.append(sb, "</tr></table>");
			pos++;
		}
		
		return sb.toString();
	}
	
	public String getPk()
	{
		pk.forEach((k, v) ->
		{
			Player p = L2World.getInstance().getPlayer(v.name);
			
			if (p != null)
			{
				v.pkKills = p.getPkKills();
			}
		});
		
		List<RankingHolder> ranking = pk.values().stream().sorted((o1, o2) -> o2.pkKills.compareTo(o1.pkKills)).limit(18).collect(Collectors.toList());
		
		final StringBuilder sb = new StringBuilder(2000);
		
		int pos = 1;
		for (RankingHolder rh : ranking)
		{
			StringUtil.append(sb, "<table border=0 cellspacing=0 cellpadding=2 height=19 width=610><tr><td FIXWIDTH=5></td>");
			StringUtil.append(sb, "<td FIXWIDTH=20>" + pos + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=160>" + rh.name + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=165>" + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=80>" + rh.pkKills + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=70>" + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=150>" + rh.onlineTime + "</td>");
			StringUtil.append(sb, "<td FIXWIDTH=65>" + (L2World.getInstance().getPlayer(rh.name) != null ? "<font color=99FF00>Online</font>" : "<font color=CC0000>Offline</font>") + "</td>");
			StringUtil.append(sb, "</tr></table>");
			pos++;
		}
		
		return sb.toString();
	}
	
	private static class RankingHolder
	{
		String name;
		Integer pvpKills;
		Integer pkKills;
		String onlineTime;
		
		public RankingHolder()
		{
			// Nothing.
		}
	}
	
	public String onlineTime(long seconds)
	{
		long remainingTime = seconds;
		
		final int days = (int) remainingTime / (24 * 3600);
		remainingTime = remainingTime - (days * 3600 * 24);
		
		final int hours = (int) (remainingTime / 3600);
		remainingTime = remainingTime - (hours * 3600);
		
		final int minutes = (int) ((remainingTime % 3600) / 60);
		remainingTime = remainingTime - (minutes * 60);
		
		seconds = remainingTime;
		String info = "";
		
		if (days > 0)
		{
			info = days + " Days";
		}
		else if (hours > 0)
		{
			info = hours + " Hours";
		}
		else if (minutes > 0)
		{
			info = minutes + " Minutes";
		}
		else if (info == "")
		{
			if (seconds > 0)
			{
				info = seconds + " Seconds";
			}
			else
			{
				info = "N/A";
			}
		}
		
		return info;
	}
	
	public static Ranking getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final Ranking _instance = new Ranking();
	}
}
