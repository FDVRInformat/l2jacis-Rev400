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
package Dev.Dungeon;


import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.gameserver.datatables.DoorTable;
import net.sf.l2j.gameserver.model.actor.instance.L2DoorInstance;


/**
 * @author Juvenil Walker
 *
 */
public class Instance
{
	private int id;
	private List<DoorTable> doors;
	
	public Instance(int id)
	{
		this.id = id;
		doors = new ArrayList<>();
	}
	
	public void openDoors(List<Integer> doors)
	{
		for (int doorId : doors)
		{
			L2DoorInstance doorInstance = DoorTable.getInstance().getDoor(doorId);
			
			if (doorInstance != null)
			{
				doorInstance.openMe();
			}
		}
	}
	

	
	public void closeDoors(List<Integer> doors)
	{
		for (int doorId : doors)
		{
			L2DoorInstance doorInstance = DoorTable.getInstance().getDoor(doorId);
			
			if (doorInstance != null)
			{
				doorInstance.closeMe();
			}
		}
	}
	
	public void addDoor(DoorTable door)
	{
		doors.add(door);
	}
	
	public List<DoorTable> getDoors()
	{
		return doors;
	}
	
	public int getId()
	{
		return id;
	}
}
