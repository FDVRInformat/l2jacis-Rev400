/*
 * Copyright (C) 2004-2013 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.model.entity.events.tournaments.properties;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.l2j.commons.config.ExProperties;

public class ArenaConfig
{
	protected static final Logger _log = Logger.getLogger(ArenaConfig.class.getName());
	
	private static final String ARENA_FILE = "./events/tournament.properties";
	
	// tournament
	public static boolean TOURNAMENT_EVENT_START;
	public static boolean TOURNAMENT_EVENT_TIME;
	public static boolean TOURNAMENT_EVENT_SUMMON;
	public static boolean TOURNAMENT_EVENT_ANNOUNCE;
	
	public static int TOURNAMENT_TIME;
	public static String[] TOURNAMENT_EVENT_INTERVAL_BY_TIME_OF_DAY;
	
	public static String MSG_TEAM1;
	public static String MSG_TEAM2;
	
	public static int ARENA_NPC;
	
	public static int NPC_locx;
	public static int NPC_locy;
	public static int NPC_locz;
	public static int NPC_Heading;
	
	public static int Tournament_locx;
	public static int Tournament_locy;
	public static int Tournament_locz;
	
	public static boolean ARENA_MESSAGE_ENABLED;
	public static String ARENA_MESSAGE_TEXT;
	public static int ARENA_MESSAGE_TIME;
	
	public static int ARENA_EVENT_COUNT_1X1;
	public static int[][] ARENA_EVENT_LOCS_1X1;
	
	public static int ARENA_REWARD_ID;
	public static int ARENA_WIN_REWARD_COUNT_1X1;
	public static int ARENA_LOST_REWARD_COUNT_1X1;
	
	public static int ARENA_CHECK_INTERVAL;
	public static int ARENA_CALL_INTERVAL;
	
	public static int ARENA_WAIT_INTERVAL_1X1;
	
	public static String TOURNAMENT_ID_RESTRICT;
	public static List<Integer> TOURNAMENT_LISTID_RESTRICT;
	public static boolean ARENA_SKILL_PROTECT;
	public static List<Integer> ARENA_SKILL_LIST = new ArrayList<>();
	public static List<Integer> ARENA_DISABLE_SKILL_LIST = new ArrayList<>();
	public static List<Integer> ARENA_STOP_SKILL_LIST = new ArrayList<>();
	public static List<Integer> ARENA_DISABLE_SKILL_LIST_PERM = new ArrayList<>();
	public static boolean ARENA_PROTECT;
	
	public static void init()
	{
		ExProperties tournament = load(ARENA_FILE);
		
		TOURNAMENT_EVENT_START = tournament.getProperty("TournamentStartOn", true);
		TOURNAMENT_EVENT_TIME = tournament.getProperty("TournamentAutoEvent", false);
		TOURNAMENT_EVENT_SUMMON = tournament.getProperty("TournamentSummon", false);
		TOURNAMENT_EVENT_ANNOUNCE = tournament.getProperty("TournamenAnnounce", false);
		
		TOURNAMENT_EVENT_INTERVAL_BY_TIME_OF_DAY = tournament.getProperty("TournamentStartTime", "20:00").split(",");
		
		TOURNAMENT_TIME = Integer.parseInt(tournament.getProperty("TournamentEventTime", "1"));
		
		MSG_TEAM1 = tournament.getProperty("TitleTeam_1", "Team [1]");
		MSG_TEAM2 = tournament.getProperty("TitleTeam_2", "Team [2]");
		
		ARENA_NPC = Integer.parseInt(tournament.getProperty("NPCRegister", "1"));
		
		NPC_locx = Integer.parseInt(tournament.getProperty("Locx", "1"));
		NPC_locy = Integer.parseInt(tournament.getProperty("Locy", "1"));
		NPC_locz = Integer.parseInt(tournament.getProperty("Locz", "1"));
		NPC_Heading = Integer.parseInt(tournament.getProperty("Heading", "1"));
		
		Tournament_locx = Integer.parseInt(tournament.getProperty("TournamentLocx", "1"));
		Tournament_locy = Integer.parseInt(tournament.getProperty("TournamentLocy", "1"));
		Tournament_locz = Integer.parseInt(tournament.getProperty("TournamentLocz", "1"));
		
		ARENA_MESSAGE_ENABLED = Boolean.parseBoolean(tournament.getProperty("ScreenArenaMessageEnable", "false"));
		ARENA_MESSAGE_TEXT = tournament.getProperty("ScreenArenaMessageText", "Welcome to L2J server!");
		ARENA_MESSAGE_TIME = Integer.parseInt(tournament.getProperty("ScreenArenaMessageTime", "10")) * 1000;
		
		String[] arenaLocs3x3 = tournament.getProperty("ArenasLoc", "").split(";");
		String[] locSplit3x3 = null;
		ARENA_EVENT_COUNT_1X1 = arenaLocs3x3.length;
		ARENA_EVENT_LOCS_1X1 = new int[ARENA_EVENT_COUNT_1X1][3];
		for (int i = 0; i < ARENA_EVENT_COUNT_1X1; i++)
		{
			locSplit3x3 = arenaLocs3x3[i].split(",");
			for (int j = 0; j < 3; j++)
			{
				ARENA_EVENT_LOCS_1X1[i][j] = Integer.parseInt(locSplit3x3[j].trim());
			}
		}
		
		ARENA_REWARD_ID = tournament.getProperty("ArenaRewardId", 57);
		ARENA_WIN_REWARD_COUNT_1X1 = tournament.getProperty("ArenaWinRewardCount", 1);
		ARENA_LOST_REWARD_COUNT_1X1 = tournament.getProperty("ArenaLostRewardCount", 1);
		
		ARENA_CHECK_INTERVAL = tournament.getProperty("ArenaBattleCheckInterval", 15) * 1000;
		ARENA_CALL_INTERVAL = tournament.getProperty("ArenaBattleCallInterval", 60);
		
		ARENA_WAIT_INTERVAL_1X1 = tournament.getProperty("ArenaBattleWaitInterval", 20);
		
		TOURNAMENT_ID_RESTRICT = tournament.getProperty("ItemsRestriction");
		
		TOURNAMENT_LISTID_RESTRICT = new ArrayList<>();
		for (String id : TOURNAMENT_ID_RESTRICT.split(","))
			TOURNAMENT_LISTID_RESTRICT.add(Integer.parseInt(id));
		
		ARENA_SKILL_PROTECT = Boolean.parseBoolean(tournament.getProperty("ArenaSkillProtect", "false"));
		
		for (String id : tournament.getProperty("ArenaDisableSkillList", "0").split(","))
		{
			ARENA_SKILL_LIST.add(Integer.parseInt(id));
		}
		
		for (String id : tournament.getProperty("DisableSkillList", "0").split(","))
		{
			ARENA_DISABLE_SKILL_LIST_PERM.add(Integer.parseInt(id));
		}
		
		for (String id : tournament.getProperty("ArenaDisableSkillList_noStart", "0").split(","))
		{
			ARENA_DISABLE_SKILL_LIST.add(Integer.parseInt(id));
		}
		
		for (String id : tournament.getProperty("ArenaStopSkillList", "0").split(","))
		{
			ARENA_STOP_SKILL_LIST.add(Integer.parseInt(id));
		}
		
		ARENA_PROTECT = Boolean.parseBoolean(tournament.getProperty("ArenaProtect", "true"));
		
	}
	
	public static ExProperties load(String filename)
	{
		return load(new File(filename));
	}
	
	public static ExProperties load(File file)
	{
		ExProperties result = new ExProperties();
		
		try
		{
			result.load(file);
		}
		catch (IOException e)
		{
			_log.warning("Error loading config : " + file.getName() + "!");
		}
		return result;
	}
}