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

import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.BaseBBSManager;
import net.sf.l2j.gameserver.communitybbs.BB.GrandBossList;
import net.sf.l2j.gameserver.communitybbs.BB.HeroList;
import net.sf.l2j.gameserver.communitybbs.BB.Ranking;
import net.sf.l2j.gameserver.model.actor.Player;

/**
 * @author Juvenil Walker
 *
 */
public class RankingBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, Player activeChar)
	{
		
		if (command.startsWith("_bbsranking"))
		{
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			final String currentCommand = st.nextToken();
			if (currentCommand.equals("pvp"))
			{
				showPvpList(activeChar);
			}
			else if (currentCommand.equals("pk"))
			{
				showPkList(activeChar);
			}
			else if (currentCommand.equals("hero"))
			{
				showHerosList(activeChar);
			}
			else if (currentCommand.equals("boss"))
			{
				showGrandBossList(activeChar);
			}
		}
		else
			super.parseCmd(command, activeChar);
	}
	
	private static void showPvpList(Player activeChar)
	{
		String content = HtmCache.getInstance().getHtm(CB_PATH + "top/rankingPvP.htm");
		content = content.replaceAll("%pvp_list%", Ranking.getInstance().getPvp());
		separateAndSend(content, activeChar);
	}
	
	private static void showPkList(Player activeChar)
	{
		String content = HtmCache.getInstance().getHtm(CB_PATH + "top/rankingPk.htm");
		content = content.replaceAll("%pk_list%", Ranking.getInstance().getPk());
		separateAndSend(content, activeChar);
	}
	
	private static void showHerosList(Player activeChar)
	{
		String content = HtmCache.getInstance().getHtm(CB_PATH + "top/Hero_List.htm");
		content = content.replaceAll("%pk_list%", Ranking.getInstance().getPk());
		HeroList hr = new HeroList();
		content = content.replaceAll("%heroelist%", hr.loadHeroeList());
		separateAndSend(content, activeChar);
	}
	
	private static void showGrandBossList(Player activeChar)
	{
		String content = HtmCache.getInstance().getHtm(CB_PATH + "top/GradBoss.htm");
		content = content.replaceAll("%pk_list%", Ranking.getInstance().getPk());
		GrandBossList gb = new GrandBossList();
		content = content.replaceAll("%gboss%", gb.loadGrandBossList());
		separateAndSend(content, activeChar);
	}
	
	public static RankingBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RankingBBSManager INSTANCE = new RankingBBSManager();
	}
}
