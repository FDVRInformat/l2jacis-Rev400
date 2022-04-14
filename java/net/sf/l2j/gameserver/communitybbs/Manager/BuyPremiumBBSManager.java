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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.GameTimeController;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.BaseBBSManager;
import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.instance.L2DungeonManagerInstance;
import net.sf.l2j.gameserver.network.serverpackets.EtcStatusUpdate;

/**
 * @author Juvenil Walker
 *
 */
public class BuyPremiumBBSManager extends BaseBBSManager
{
	//VIP Level - 1

	private final int VIP_DAYS1 = 30;
	private final int VIP_DAYS2 = 90;
	

	
	@Override
	public void parseCmd(String command, Player activeChar)
	{
		String path = "data/html/CommunityBoard/top/";
		String filepath = "";
		String content = "";
		String ip = "N/A";
		String account = "N/A";
		StringTokenizer clientinfo = new StringTokenizer(activeChar.getClient().toString(), " ]:-[");
		ip = clientinfo.nextToken();
		account = clientinfo.nextToken();
		
		if (command.equals("_bbsPremium30"))
		{
			
			if(activeChar.getInventory().getInventoryItemCount(Config.DONATE_COIN_ID, 0) >= 30)
			{
				activeChar.getInventory().destroyItemByItemId("Donate Coin", Config.DONATE_COIN_ID, 30, activeChar, null);
				if (activeChar.isVip())
				{
					long daysleft = (activeChar.getVipEndTime() - Calendar.getInstance().getTimeInMillis()) / 86400000L;
					activeChar.setEndTime("vip", (int)(daysleft + VIP_DAYS1));
					activeChar.sendMessage("Congratulations, You just received another " + VIP_DAYS1 + " day of VIP.");
				}
				else
				{
					activeChar.setVip(true);
					activeChar.setEndTime("vip", VIP_DAYS1);
					activeChar.sendMessage("Congrats, you just became VIP per " + VIP_DAYS1 + " day.");
				}

				activeChar.broadcastUserInfo();
				activeChar.sendPacket(new EtcStatusUpdate(activeChar));
				activeChar.broadcastUserInfo();
			}
			else
			{
				activeChar.sendMessage("You do not have enough " + VIP_DAYS1 + " Donate Coin's.");
			}
		}	
		
		else if (command.equals("_bbsPremium90"))
		{
			
			if(activeChar.getInventory().getInventoryItemCount(Config.DONATE_COIN_ID, 0) >= 100)
			{
				activeChar.getInventory().destroyItemByItemId("Donate Coin", Config.DONATE_COIN_ID, 100, activeChar, null);
				if (activeChar.isVip())
				{
					long daysleft = (activeChar.getVipEndTime() - Calendar.getInstance().getTimeInMillis()) / 86400000L;
					activeChar.setEndTime("vip", (int)(daysleft + VIP_DAYS2));
					activeChar.sendMessage("Congratulations, You just received another " + VIP_DAYS2 + " day of VIP.");
				}
				else
				{
					activeChar.setVip(true);
					activeChar.setEndTime("vip", VIP_DAYS2);
					activeChar.sendMessage("Congrats, you just became VIP per " + VIP_DAYS2 + " day.");
				}

				activeChar.broadcastUserInfo();
				activeChar.sendPacket(new EtcStatusUpdate(activeChar));
				activeChar.broadcastUserInfo();
			}
			else
			{
				activeChar.sendMessage("You do not have enough " + "100" + " Donate Coin's.");
			}
			
		}
		filepath = path + "index.htm";
		content = HtmCache.getInstance().getHtm(filepath);
		separateAndSend(content, activeChar);
		// ===========================================================================
		if (content != null)
		{
			content = content.replace("%username%", activeChar.getName());
			content = content.replace("%charId%", String.valueOf(activeChar.getObjectId()));
			content = content.replace("%name%", activeChar.getName());
			content = content.replace("%rate_xp%", String.valueOf(Config.RATE_XP));
			content = content.replace("%rate_sp%", String.valueOf(Config.RATE_SP));
			content = content.replace("%rate_party_xp%", String.valueOf(Config.RATE_PARTY_XP));
			content = content.replace("%rate_adena%", String.valueOf(Config.RATE_DROP_ADENA));
			content = content.replace("%rate_party_sp%", String.valueOf(Config.RATE_PARTY_SP));
			content = content.replace("%rate_items%", String.valueOf(Config.RATE_DROP_ITEMS));
			content = content.replace("%rate_spoil%", String.valueOf(Config.RATE_DROP_SPOIL));
			content = content.replace("%rate_drop_manor%", String.valueOf(Config.RATE_DROP_MANOR));
			content = content.replace("%pet_rate_xp%", String.valueOf(Config.PET_XP_RATE));
			content = content.replace("%sineater_rate_xp%", String.valueOf(Config.SINEATER_XP_RATE));
			content = content.replace("%pet_food_rate%", String.valueOf(Config.PET_FOOD_RATE));
			content = content.replace("%ACC%", activeChar.getAccountName());
			content = content.replace("%level%", String.valueOf(activeChar.getLevel()));
			final L2Clan playerClan = ClanTable.getInstance().getClan(activeChar.getClanId());
			if (playerClan != null)
			{
				content = content.replace("%clan%", playerClan.getName());
			}
			else
			{
				content = content.replace("%clan%", "no Clan");
			}
			
			if (activeChar.isVip())
			{
				content = content.replace("%end_premium%", endPremium(activeChar));
			}
			else
			{
				content = content.replace("%end_premium%", "no Premium");
			}
			
			content = content.replace("%xp%", String.valueOf(activeChar.getExp()));
			content = content.replace("%sp%", String.valueOf(activeChar.getSp()));
			content = content.replace("%class%", activeChar.getTemplate().getClassName());
			content = content.replace("%ordinal%", String.valueOf(activeChar.getClassId().ordinal()));
			content = content.replace("%classid%", String.valueOf(activeChar.getClassId()));
			content = content.replace("%x%", String.valueOf(activeChar.getX()));
			content = content.replace("%y%", String.valueOf(activeChar.getY()));
			content = content.replace("%z%", String.valueOf(activeChar.getZ()));
			content = content.replace("%currenthp%", String.valueOf((int) activeChar.getCurrentHp()));
			content = content.replace("%maxhp%", String.valueOf(activeChar.getMaxHp()));
			content = content.replace("%karma%", String.valueOf(activeChar.getKarma()));
			content = content.replace("%currentmp%", String.valueOf((int) activeChar.getCurrentMp()));
			content = content.replace("%maxmp%", String.valueOf(activeChar.getMaxMp()));
			content = content.replace("%pvpflag%", String.valueOf(activeChar.getPvpFlag()));
			content = content.replace("%currentcp%", String.valueOf((int) activeChar.getCurrentCp()));
			content = content.replace("%maxcp%", String.valueOf(activeChar.getMaxCp()));
			content = content.replace("%pvpkills%", String.valueOf(activeChar.getPvpKills()));
			content = content.replace("%pkkills%", String.valueOf(activeChar.getPkKills()));
			content = content.replace("%currentload%", String.valueOf(activeChar.getCurrentLoad()));
			content = content.replace("%maxload%", String.valueOf(activeChar.getMaxLoad()));
			content = content.replace("%patk%", String.valueOf(activeChar.getPAtk(null)));
			content = content.replace("%matk%", String.valueOf(activeChar.getMAtk(null, null)));
			content = content.replace("%pdef%", String.valueOf(activeChar.getPDef(null)));
			content = content.replace("%mdef%", String.valueOf(activeChar.getMDef(null, null)));
			content = content.replace("%accuracy%", String.valueOf(activeChar.getAccuracy()));
			content = content.replace("%evasion%", String.valueOf(activeChar.getEvasionRate(null)));
			content = content.replace("%critical%", String.valueOf(activeChar.getCriticalHit(null, null)));
			content = content.replace("%runspeed%", String.valueOf(activeChar.getRunSpeed()));
			content = content.replace("%patkspd%", String.valueOf(activeChar.getPAtkSpd()));
			content = content.replace("%matkspd%", String.valueOf(activeChar.getMAtkSpd()));
			content = content.replace("%access%", String.valueOf(activeChar.getAccessLevel().getLevel()));
			content = content.replace("%dungeon%", L2DungeonManagerInstance.getPlayerStatus(activeChar, 1));
			content = content.replace("%account%", account);
			content = content.replace("%ip%", ip);
			if (activeChar.isNoble())
			{
				content = content.replace("%noble%", "Is Noble");
			}
			else
			{
				content = content.replace("%noble%", "Not Noble");
			}
			final int t = GameTimeController.getInstance().getGameTime();
			final int HH = t / 60;
			final int mm = t % 60;
			
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, HH);
			cal.set(Calendar.MINUTE, mm);
			content = content.replace("%time%", String.valueOf(format.format(cal.getTime())));
			format = null;
			cal = null;
			BaseBBSManager.separateAndSend(content, activeChar);
		}
	}
	
	private static String endPremium(Player player)
	{
		Date setVipEndTime = new Date(player.getVipEndTime());
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String data = dateformat.format(setVipEndTime);
		
		return data;
	}
	
	public static BuyPremiumBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final BuyPremiumBBSManager INSTANCE = new BuyPremiumBBSManager();
	}
}

