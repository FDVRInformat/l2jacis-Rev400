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
package net.sf.l2j.gameserver.communitybbs;

import net.sf.l2j.gameserver.communitybbs.BufferManager.BuffBBSManager;
import net.sf.l2j.gameserver.communitybbs.BufferManager.DanceBBSManager;
import net.sf.l2j.gameserver.communitybbs.BufferManager.ExtraBBSManager;
import net.sf.l2j.gameserver.communitybbs.BufferManager.OpitionsBufferBSSManager;
import net.sf.l2j.gameserver.communitybbs.BufferManager.SongsBBSManager;
import net.sf.l2j.gameserver.communitybbs.BufferManager.VipBuffBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.BuyPremiumBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.OpenPagBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.RaidListBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.RankingBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.ShopBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.TeleportBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.TopBBSManager;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.L2GameClient;

public class CommunityBoard
{
	protected CommunityBoard()
	{
	}
	
	public static CommunityBoard getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public void handleCommands(L2GameClient client, String command)
	{
		final Player activeChar = client.getActiveChar();
		if (activeChar == null)
			return;
		
		if (command.startsWith("_bbshome"))
			TopBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbstele"))
			TeleportBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbspag"))
			OpenPagBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsmultisell"))
			ShopBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsbuff"))
			BuffBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsdark"))
			DanceBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsextra"))
			ExtraBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbself"))
			SongsBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsvip"))
			VipBuffBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbscancelbuff"))
			OpitionsBufferBSSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsheal"))
			OpitionsBufferBSSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsranking"))
			RankingBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbstop"))
			RaidListBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsPremium30"))
			BuyPremiumBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsPremium90"))
			BuyPremiumBBSManager.getInstance().parseCmd(command, activeChar);

		else
			BaseBBSManager.separateAndSend("<html><body><br><br><center>The command: " + command + " isn't implemented.</center></body></html>", activeChar);
	}
	
	private static class SingletonHolder
	{
		protected static final CommunityBoard _instance = new CommunityBoard();
	}
}