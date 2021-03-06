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
package net.sf.l2j.gameserver.datatables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.L2GameServerPacket;
import net.sf.l2j.gameserver.network.serverpackets.PlaySound;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

/**
 * This class stores references to all online game masters. (access level > 100)
 */
public class GmListTable
{
	private static Logger _log = Logger.getLogger(GmListTable.class.getName());
	
	private final Map<Player, Boolean> _gmList;
	
	public static GmListTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public List<Player> getAllGms(boolean includeHidden)
	{
		List<Player> tmpGmList = new ArrayList<>();
		
		for (Map.Entry<Player, Boolean> entry : _gmList.entrySet())
		{
			if (includeHidden || !entry.getValue())
				tmpGmList.add(entry.getKey());
		}
		
		return tmpGmList;
	}
	
	public List<String> getAllGmNames(boolean includeHidden)
	{
		List<String> tmpGmList = new ArrayList<>();
		
		for (Map.Entry<Player, Boolean> entry : _gmList.entrySet())
		{
			String name = entry.getKey().getName();
			if (!entry.getValue())
				tmpGmList.add(name);
			else if (includeHidden)
				tmpGmList.add(name + " (invis)");
		}
		
		return tmpGmList;
	}
	
	protected GmListTable()
	{
		_gmList = new ConcurrentHashMap<>();
	}
	
	/**
	 * Add a Player player to the Set _gmList
	 * @param player
	 * @param hidden
	 */
	public void addGm(Player player, boolean hidden)
	{
		if (Config.DEBUG)
			_log.fine("added gm: " + player.getName());
		
		_gmList.put(player, hidden);
	}
	
	public void deleteGm(Player player)
	{
		if (Config.DEBUG)
			_log.fine("deleted gm: " + player.getName());
		
		_gmList.remove(player);
	}
	
	/**
	 * GM will be displayed on clients gmlist
	 * @param player
	 */
	public void showGm(Player player)
	{
		if (_gmList.containsKey(player))
			_gmList.put(player, false);
	}
	
	/**
	 * GM will no longer be displayed on clients gmlist
	 * @param player
	 */
	public void hideGm(Player player)
	{
		if (_gmList.containsKey(player))
			_gmList.put(player, true);
	}
	
	public boolean isGmOnline(boolean includeHidden)
	{
		for (Map.Entry<Player, Boolean> entry : _gmList.entrySet())
		{
			if (includeHidden || !entry.getValue())
				return true;
		}
		
		return false;
	}
	
	public void sendListToPlayer(Player player)
	{
		if (isGmOnline(player.isGM()))
		{
			player.sendPacket(SystemMessageId.GM_LIST);
			
			for (String name : getAllGmNames(player.isGM()))
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.GM_S1).addString(name));
		}
		else
		{
			player.sendPacket(SystemMessageId.NO_GM_PROVIDING_SERVICE_NOW);
			player.sendPacket(new PlaySound("systemmsg_e.702"));
		}
	}
	
	public static void broadcastToGMs(L2GameServerPacket packet)
	{
		for (Player gm : getInstance().getAllGms(true))
			gm.sendPacket(packet);
	}
	
	public static void broadcastMessageToGMs(String message)
	{
		for (Player gm : getInstance().getAllGms(true))
			gm.sendMessage(message);
	}
	
	private static class SingletonHolder
	{
		protected static final GmListTable _instance = new GmListTable();
	}
}