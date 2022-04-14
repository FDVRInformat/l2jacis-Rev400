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

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.GameTimeController;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.BaseBBSManager;
import net.sf.l2j.gameserver.communitybbs.BB.GrandBossList;
import net.sf.l2j.gameserver.communitybbs.BB.HeroList;
import net.sf.l2j.gameserver.communitybbs.BB.RaidList;
import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.ShowBoard;

/**
 * @author Juvenil Walker
 *
 */
public class RaidListBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, Player player)
	{
		
		String ip = "N/A";
		String account = "N/A";
		
		StringTokenizer clientinfo = new StringTokenizer(player.getClient().toString(), " ]:-[");
		clientinfo.nextToken();
		clientinfo.nextToken();
		clientinfo.nextToken();
		account = clientinfo.nextToken();
		clientinfo.nextToken();
		ip = clientinfo.nextToken();
		clientinfo = null;
		
		String path = "data/html/CommunityBoard/top/";
		String filepath = "";
		String content = "";
		
		if (command.equals("_bbstop") | command.equals("_bbshome"))
		{
			filepath = path + "index.htm";
			content = HtmCache.getInstance().getHtm(filepath);
			separateAndSend(content, player);
			// ===========================================================================
			if (content != null)
			{
				content = content.replace("%username%", player.getName());
				content = content.replace("%charId%", String.valueOf(player.getObjectId()));
				content = content.replace("%name%", player.getName());
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
				content = content.replace("%end_premium%", endPremium(player));
				content = content.replace("%ACC%", player.getAccountName());
				content = content.replace("%level%", String.valueOf(player.getLevel()));
				final L2Clan playerClan = ClanTable.getInstance().getClan(player.getClanId());
				if (playerClan != null)
				{
					content = content.replace("%clan%", playerClan.getName());
				}
				else
				{
					content = content.replace("%clan%", "no Clan");
				}
				content = content.replace("%xp%", String.valueOf(player.getExp()));
				content = content.replace("%sp%", String.valueOf(player.getSp()));
				content = content.replace("%class%", player.getTemplate().getClassName());
				content = content.replace("%ordinal%", String.valueOf(player.getClassId().ordinal()));
				content = content.replace("%classid%", String.valueOf(player.getClassId()));
				content = content.replace("%x%", String.valueOf(player.getX()));
				content = content.replace("%y%", String.valueOf(player.getY()));
				content = content.replace("%z%", String.valueOf(player.getZ()));
				content = content.replace("%currenthp%", String.valueOf((int) player.getCurrentHp()));
				content = content.replace("%maxhp%", String.valueOf(player.getMaxHp()));
				content = content.replace("%karma%", String.valueOf(player.getKarma()));
				content = content.replace("%currentmp%", String.valueOf((int) player.getCurrentMp()));
				content = content.replace("%maxmp%", String.valueOf(player.getMaxMp()));
				content = content.replace("%pvpflag%", String.valueOf(player.getPvpFlag()));
				content = content.replace("%currentcp%", String.valueOf((int) player.getCurrentCp()));
				content = content.replace("%maxcp%", String.valueOf(player.getMaxCp()));
				content = content.replace("%pvpkills%", String.valueOf(player.getPvpKills()));
				content = content.replace("%pkkills%", String.valueOf(player.getPkKills()));
				content = content.replace("%currentload%", String.valueOf(player.getCurrentLoad()));
				content = content.replace("%maxload%", String.valueOf(player.getMaxLoad()));
				content = content.replace("%patk%", String.valueOf(player.getPAtk(null)));
				content = content.replace("%matk%", String.valueOf(player.getMAtk(null, null)));
				content = content.replace("%pdef%", String.valueOf(player.getPDef(null)));
				content = content.replace("%mdef%", String.valueOf(player.getMDef(null, null)));
				content = content.replace("%accuracy%", String.valueOf(player.getAccuracy()));
				content = content.replace("%evasion%", String.valueOf(player.getEvasionRate(null)));
				content = content.replace("%critical%", String.valueOf(player.getCriticalHit(null, null)));
				content = content.replace("%runspeed%", String.valueOf(player.getRunSpeed()));
				content = content.replace("%patkspd%", String.valueOf(player.getPAtkSpd()));
				content = content.replace("%matkspd%", String.valueOf(player.getMAtkSpd()));
				content = content.replace("%access%", String.valueOf(player.getAccessLevel().getLevel()));
				content = content.replace("%account%", account);
				content = content.replace("%ip%", ip);
				if (player.isNoble())
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
				BaseBBSManager.separateAndSend(content, player);
			}
			else
			{
				ShowBoard sb = new ShowBoard("<html><body><br><br><br><br></body></html>", "101");
				player.sendPacket(sb);
				sb = null;
				player.sendPacket(new ShowBoard(null, "102"));
				player.sendPacket(new ShowBoard(null, "103"));
			}
			
		}
		
		// ===========================================================================
		else if (command.startsWith("_bbstop;"))
		{
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			
			String file = st.nextToken();
			filepath = path + file + ".htm";
			File filecom = new File(filepath);
			if (!(filecom.exists()))
			{
				content = "<html><body><br><br><center>The command " + command + " points to file(" + filepath + ") that NOT exists.</center></body></html>";
				separateAndSend(content, player);
				return;
			}
			content = HtmCache.getInstance().getHtm(filepath);
			
			if (content.isEmpty())
			{
				content = "<html><body><br><br><center>Content Empty: The command " + command + " points to an invalid or empty html file(" + filepath + ").</center></body></html>";
			}
			switch (file)
			{
				/**
				 * case "toppvp": TopPlayers pvp = new TopPlayers(file); content = content.replaceAll("%toppvp%", pvp.loadTopList()); break; case "toppk": TopPlayers pk = new TopPlayers(file); content = content.replaceAll("%toppk%", pk.loadTopList()); break; case "toprbrank": TopPlayers raid = new
				 * TopPlayers(file); content = content.replaceAll("%toprbrank%", raid.loadTopList()); break; case "topadena": TopPlayers adena = new TopPlayers(file); content = content.replaceAll("%topadena%", adena.loadTopList()); break; case "toponline": TopPlayers online = new TopPlayers(file);
				 * content = content.replaceAll("%toponline%", online.loadTopList()); break;
				 **/
				case "heroes":
					HeroList hr = new HeroList();
					content = content.replaceAll("%heroelist%", hr.loadHeroeList());
					break;
				
				case "boss":
					GrandBossList gb = new GrandBossList();
					content = content.replaceAll("%gboss%", gb.loadGrandBossList());
					break;
				case "stats":
					content = content.replace("%online%", " " + L2World.getInstance().getAllPlayers());
					content = content.replace("%servercapacity%", Integer.toString(Config.MAXIMUM_ONLINE_USERS));
					if (Config.DEBUG)
					{
						content = content.replace("%serveronline%", getRealOnline());
					}
					else
					{
						content = content.replace("%serveronline%", "");
					}
					break;
				default:
					break;
				
			}
			
			if (file.startsWith("raid"))
			{
				String rfid = file.substring(4);
				RaidList rd = new RaidList(rfid);
				content = content.replaceAll("%raidlist%", rd.loadRaidList());
			}
			if (content.isEmpty())
			{
				content = "<html><body><br><br><center>404 :File not found or empty: " + filepath + " your command is " + command + "</center></body></html>";
			}
			separateAndSend(content, player);
		}
		else
		
		{
			super.parseCmd(command, player);
		}
		
	}
	
	public String getRealOnline()
	{
		int counter = 0;
		for (Player onlinePlayer : L2World.getInstance().getAllPlayers().values())
		{
			if ((onlinePlayer.getClient() != null))
			{
				counter++;
			}
		}
		String realOnline = "<tr><td fixwidth=11></td><td FIXWIDTH=280>Players Active</td><td FIXWIDTH=470><font color=26e600>" + counter + "</font></td></tr>" + "<tr><td fixwidth=11></td><td FIXWIDTH=280>Players Shops</td><td FIXWIDTH=470><font color=26e600>" + (L2World.getInstance().getAllPlayers().values()) + "</font></td></tr>";
		return realOnline;
	}
	
	@Override
	protected String getFolder()
	{
		return "top/";
	}
	
	public static RaidListBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RaidListBBSManager INSTANCE = new RaidListBBSManager();
	}
	
	private static String endPremium(Player player)
	{
		Date setVipEndTime = new Date(player.getVipEndTime());
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String data = dateformat.format(setVipEndTime);
		
		return data;
	}
}
