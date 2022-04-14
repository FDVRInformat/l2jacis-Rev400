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
package net.sf.l2j.gameserver.model.zone.type;

import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.zone.L2SpawnZone;
import net.sf.l2j.gameserver.model.zone.ZoneId;

/**
 * A Town zone
 * @author durgus
 */
public class L2TownZone extends L2SpawnZone
{
	private int _townId;
	private int _taxById;
	private boolean _isPeaceZone;
	private boolean _checkClasses;
	private static List<String> _classes = new ArrayList<>();
	//private static final int DISMOUNT_DELAY = 1;
	
	public L2TownZone(int id)
	{
		super(id);
		
		_taxById = 0;
		
		// Default peace zone
		_isPeaceZone = true;
		
		_checkClasses = false;
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("townId"))
			_townId = Integer.parseInt(value);
		else if (name.equals("taxById"))
			_taxById = Integer.parseInt(value);
		else if (name.equals("isPeaceZone"))
			_isPeaceZone = Boolean.parseBoolean(value);
		else if (name.equals("checkClasses"))
			_checkClasses = Boolean.parseBoolean(value);
		else if (name.equals("Classes"))
		{
			String[] propertySplit = value.split(",");
			for (String i : propertySplit)
				 _classes.add(i);
		}
		else
			super.setParameter(name, value);
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		if (character instanceof Player)
		{
			// PVP possible during siege, now for siege participants only
			// Could also check if this town is in siege, or if any siege is going on
			if (((Player) character).getSiegeState() != 0 && Config.ZONE_TOWN == 1)
				return;
			
			if (_checkClasses)
			{
				if (_classes != null && _classes.contains(""+ (((Player) character).getClassId().getId())))
				{
					((Player) character).teleToLocation(83597, 147888, -3405, 0);
					((Player) character).sendMessage("Your class is not allowed in this zone.");
					return;
				}
			}
			
			//if (((Player) character).getMountType() == 2)
			//{
			//	((Player) character).sendPacket(SystemMessageId.AREA_CANNOT_BE_ENTERED_WHILE_MOUNTED_WYVERN);
			//	((Player) character).enteredNoLanding(DISMOUNT_DELAY);
			//}
		}
		
		if (_isPeaceZone && Config.ZONE_TOWN != 2)
			character.setInsideZone(ZoneId.PEACE, true);
		
		character.setInsideZone(ZoneId.TOWN, true);
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		if (_isPeaceZone)
			character.setInsideZone(ZoneId.PEACE, false);
		
		character.setInsideZone(ZoneId.TOWN, false);
	}
	
	@Override
	public void onDieInside(L2Character character)
	{
	}
	
	@Override
	public void onReviveInside(L2Character character)
	{
	}
	
	/**
	 * Returns this zones town id (if any)
	 * @return
	 */
	public int getTownId()
	{
		return _townId;
	}
	
	/**
	 * Returns this town zones castle id
	 * @return
	 */
	public final int getTaxById()
	{
		return _taxById;
	}
	
	public final boolean isPeaceZone()
	{
		return _isPeaceZone;
	}
}