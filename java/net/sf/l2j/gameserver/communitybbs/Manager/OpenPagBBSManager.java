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
package net.sf.l2j.gameserver.communitybbs.Manager;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.communitybbs.BaseBBSManager;
import net.sf.l2j.gameserver.model.actor.Player;

/**
 * @author Juvenil Walker
 *
 */
public class OpenPagBBSManager extends BaseBBSManager
{
	protected OpenPagBBSManager()
	{
	}
	
	@Override
	public void parseCmd(String command, Player player)
	{
		if (command.equals("_bbspag"))
		{
			loadStaticHtm("index.htm", player);
		}
		else if (command.startsWith("_bbspag;"))
		{
			final StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			
			loadStaticHtm(st.nextToken(), player);
		}
		else
		{
			super.parseCmd(command, player);
		}
	}
	
	@Override
	protected String getFolder()
	{
		return "top/";
	}
	
	public static OpenPagBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final OpenPagBBSManager INSTANCE = new OpenPagBBSManager();
	}
}
