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
import net.sf.l2j.gameserver.datatables.MultisellData;
import net.sf.l2j.gameserver.model.actor.Player;

/**
 * @author Juvenil Walker
 *
 */
public class ShopBBSManager extends BaseBBSManager
{
	protected ShopBBSManager()
	{
	}
	
	
	@Override
	public void parseCmd(String command, Player player)
	{
		if (command.startsWith("_bbsmultisell;"))
		{
			/**
			 * Usage: For ex: bypass _bbsmultisell;shop;9910 Where "shop" - its html where need back after open multisell, cuz if we dont enter this value html just "freeze" any bypass doesnt works. Where "9910" its multisell id, and also we need in multisell value: <list isCommunity="true"> to
			 * attemp phx bypass to other multisell.
			 */
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			OpenPagBBSManager.getInstance().parseCmd("_bbspag;" + st.nextToken(), player);
			MultisellData.getInstance().separateAndSend("" + Integer.parseInt(st.nextToken()), player, true, 0);
			
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
	
	public static ShopBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ShopBBSManager INSTANCE = new ShopBBSManager();
	}
}
