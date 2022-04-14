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
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;

/**
 * @author Juvenil Walker
 *
 */
public class RaidList
{
	protected static final Logger LOGGER = Logger.getLogger(RaidList.class.getName());
	
	private static final String SELECT_RAID_DATA = "SELECT id, name, level FROM npc WHERE type='RaidBoss' AND EXISTS (SELECT * FROM raidboss_spawnlist WHERE raidboss_spawnlist.boss_id = npc.id) ORDER BY `level` ";
	private static final String SELECT_SPAWN = "SELECT respawn_time, spwan_time, random_time FROM raidboss_spawnlist WHERE boss_id=";
	
	private final StringBuilder _raidList = new StringBuilder();
	
	public RaidList(String rfid)
	{
		loadFromDB(rfid);
	}
	
	private void loadFromDB(String rfid)
	{
		int type = Integer.parseInt(rfid);
		int stpoint = 0;
		int pos = 0;
		String sort = "";
		if (Config.DEBUG)
		{
			sort = "DESC";
		}
		else
		{
			sort = "ASC";
		}
		for (int count = 1; count != type; count++)
		{
			stpoint += 18;
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(SELECT_RAID_DATA + sort + " Limit " + stpoint + ", " + 18);
			ResultSet result = statement.executeQuery();
			pos = stpoint;
			
			while (result.next())
			{
				int npcid = result.getInt("id");
				String npcname = result.getString("name");
				int rlevel = result.getInt("level");
				PreparedStatement statement2 = con.prepareStatement(SELECT_SPAWN + npcid);
				ResultSet result2 = statement2.executeQuery();
				
				while (result2.next())
				{
					pos++;
					boolean rstatus = false;
					long respawn = result2.getLong("respawn_time");
					if (respawn == 0)
					{
						rstatus = true;
					}
					int mindelay = result2.getInt("spwan_time");
					int maxdelay = result2.getInt("random_time");
					mindelay = mindelay / 60 / 60;
					maxdelay = maxdelay / 60 / 60;
					addRaidToList(pos, npcname, rlevel, mindelay, maxdelay, rstatus);
				}
				result2.close();
				statement2.close();
			}
			
			result.close();
			statement.close();
		}
		catch (Exception e)
		{
			LOGGER.warning(RaidList.class.getName() + ": Error Loading DB ");
			if (Config.DEBUG)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void addRaidToList(int pos, String npcname, int rlevel, int mindelay, int maxdelay, boolean rstatus)
	{
		_raidList.append("<table border=0 cellspacing=0 cellpadding=2 width=630 height=" + 18 + ">");
		_raidList.append("<tr>");
		_raidList.append("<td FIXWIDTH=5></td>");
		_raidList.append("<td FIXWIDTH=25>" + pos + "</td>");
		_raidList.append("<td FIXWIDTH=270>" + npcname + "</td>");
		_raidList.append("<td FIXWIDTH=50>" + rlevel + "</td>");
		_raidList.append("<td FIXWIDTH=120 align=center>" + mindelay + " - " + maxdelay + "</td>");
		_raidList.append("<td FIXWIDTH=50 align=center>" + ((rstatus) ? "<font color=99FF00>Alive</font>" : "<font color=CC0000>Dead</font>") + "</td>");
		_raidList.append("<td FIXWIDTH=5></td>");
		_raidList.append("</tr>");
		_raidList.append("</table>");
		_raidList.append("<img src=\"L2UI.Squaregray\" width=\"630\" height=\"1\">");
	}
	
	public String loadRaidList()
	{
		return _raidList.toString();
	}	
}
