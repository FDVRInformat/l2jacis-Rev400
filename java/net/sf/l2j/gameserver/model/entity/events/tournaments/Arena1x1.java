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
package net.sf.l2j.gameserver.model.entity.events.tournaments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.ThreadPool;
import net.sf.l2j.gameserver.datatables.DoorTable;
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.instance.L2DoorInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;
import net.sf.l2j.gameserver.model.base.ClassId;
import net.sf.l2j.gameserver.model.entity.events.teamvsteam.TvTConfig;
import net.sf.l2j.gameserver.model.entity.events.tournaments.properties.ArenaConfig;
import net.sf.l2j.gameserver.model.entity.events.tournaments.properties.ArenaTask;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.util.Broadcast;
import net.sf.l2j.util.Rnd;

/**
 * @author Juvenil Walker
 */
public class Arena1x1 implements Runnable
{
	protected static final Logger _log = Logger.getLogger(Arena1x1.class.getName());
	
	// list of participants
	public static List<Pair> registered;
	// number of Arenas
	int free = ArenaConfig.ARENA_EVENT_COUNT_1X1;
	// Arenas
	Arena[] arenas = new Arena[ArenaConfig.ARENA_EVENT_COUNT_1X1];
	// list of fights going on
	Map<Integer, String> fights = new HashMap<>(ArenaConfig.ARENA_EVENT_COUNT_1X1);
	
	public Arena1x1()
	{
		registered = new ArrayList<>();
		int[] coord;
		for (int i = 0; i < ArenaConfig.ARENA_EVENT_COUNT_1X1; i++)
		{
			coord = ArenaConfig.ARENA_EVENT_LOCS_1X1[i];
			arenas[i] = new Arena(i, coord[0], coord[1], coord[2]);
		}
	}
	
	public static Arena1x1 getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	public boolean register(Player player)
	{
		for (Pair p : registered)
		{
			if ((p.getLeader() == player) || (p.getAssist() == player))
			{
				player.sendMessage("Tournament: " + player.getName() + " You already registered!");
				return false;
			}
			if ((p.getLeader() == player) || (p.getAssist() == player))
			{
				player.sendMessage("Tournament: Your partner " + player.getName() + " already registered!");
				return false;
			}
			
		}
		return registered.add(new Pair(player, player));
	}
	
	public boolean isRegistered(Player player)
	{
		for (Pair p : registered)
		{
			if ((p.getLeader() == player) || (p.getAssist() == player))
			{
				return true;
			}
		}
		return false;
	}
	
	public Map<Integer, String> getFights()
	{
		return fights;
	}
	
	public boolean remove(Player player)
	{
		for (Pair p : registered)
		{
			if ((p.getLeader() == player) || (p.getAssist() == player))
			{
				p.removeMessage();
				registered.remove(p);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public synchronized void run()
	{
		boolean load = true;
		
		// while server is running
		while (load)
		{
			if (!ArenaTask.is_started())
				load = false;
			
			// if no have participants or arenas are busy wait 1 minute
			if (registered.size() < 2 || free == 0)
			{
				try
				{
					Thread.sleep(ArenaConfig.ARENA_CALL_INTERVAL * 1000);
				}
				catch (InterruptedException e)
				{
				}
				continue;
			}
			List<Pair> opponents = selectOpponents();
			if (opponents != null && opponents.size() == 2)
			{
				Thread T = new Thread(new EvtArenaTask(opponents));
				T.setDaemon(true);
				T.start();
			}
			// wait 1 minute for not stress server
			try
			{
				Thread.sleep(ArenaConfig.ARENA_CALL_INTERVAL * 1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	private List<Pair> selectOpponents()
	{
		List<Pair> opponents = new ArrayList<>();
		Pair pairOne = null, pairTwo = null;
		int tries = 3;
		do
		{
			int first = 0, second = 0;
			if (getRegisteredCount() < 2)
				return opponents;
			
			if (pairOne == null)
			{
				first = Rnd.get(getRegisteredCount());
				pairOne = registered.get(first);
				if (pairOne.check())
				{
					opponents.add(0, pairOne);
					registered.remove(first);
				}
				else
				{
					pairOne = null;
					registered.remove(first);
					return null;
				}
				
			}
			if (pairTwo == null)
			{
				second = Rnd.get(getRegisteredCount());
				pairTwo = registered.get(second);
				if (pairTwo.check())
				{
					opponents.add(1, pairTwo);
					registered.remove(second);
				}
				else
				{
					pairTwo = null;
					registered.remove(second);
					return null;
				}
				
			}
		}
		while ((pairOne == null || pairTwo == null) && --tries > 0);
		return opponents;
	}
	
	public void clear()
	{
		registered.clear();
	}
	
	public int getRegisteredCount()
	{
		return registered.size();
	}
	
	private class Pair
	{
		Player leader;
		Player assist;
		
		public Pair(Player leader, Player assist)
		{
			this.leader = leader;
			this.assist = assist;
		}
		
		public Player getAssist()
		{
			return assist;
		}
		
		public Player getLeader()
		{
			return leader;
		}
		
		public boolean check()
		{
			if ((leader == null || !leader.isOnline()))
			{
				if (assist != null || assist.isOnline())
					assist.sendMessage("Tournament: You participation in Event was Canceled.");
				
				return false;
			}
			
			else if (((assist == null || !assist.isOnline()) && (leader != null || leader.isOnline())))
			{
				leader.sendMessage("Tournament: You participation in Event was Canceled.");
				
				if (assist != null || assist.isOnline())
					assist.sendMessage("Tournament: You participation in Event was Canceled.");
				
				return false;
			}
			return true;
		}
		
		public boolean isDead()
		{
			
			if (ArenaConfig.ARENA_PROTECT)
			{
				if (leader != null && leader.isOnline() && leader.isArenaAttack() && !leader.isDead() && !leader.isInsideZone(ZoneId.ARENA_EVENT))
					leader.logout();
				if (assist != null && assist.isOnline() && assist.isArenaAttack() && !assist.isDead() && !assist.isInsideZone(ZoneId.ARENA_EVENT))
					assist.logout();
			}
			if ((leader == null || leader.isDead() || !leader.isOnline() || !leader.isInsideZone(ZoneId.ARENA_EVENT) || !leader.isArenaAttack()) && (assist == null || assist.isDead() || !assist.isOnline() || !assist.isInsideZone(ZoneId.ARENA_EVENT) || !assist.isArenaAttack()))
				return false;
			return !(leader.isDead() && assist.isDead());
		}
		
		public boolean isAlive()
		{
			if ((leader == null || leader.isDead() || !leader.isOnline() || !leader.isInsideZone(ZoneId.ARENA_EVENT) || !leader.isArenaAttack()) && (assist == null || assist.isDead() || !assist.isOnline() || !assist.isInsideZone(ZoneId.ARENA_EVENT) || !assist.isArenaAttack()))
				return false;
			
			return !(leader.isDead() && assist.isDead());
		}
		
		public void teleToLocation(int x, int y, int z)
		{
			if ((leader != null) && leader.isOnline())
			{
				
				leader.setCurrentCp(leader.getMaxCp());
				leader.setCurrentHp(leader.getMaxHp());
				leader.setCurrentMp(leader.getMaxMp());
				
				if (leader.inObserverMode())
				{
					leader.setLastCords(x, y, z);
					leader.leaveOlympiadObserverMode();
				}
				else
				{
					leader.teleToLocation(x, y, z, 0);
				}
				leader.broadcastUserInfo();
			}
			
			if ((assist != null) && assist.isOnline())
			{
				assist.setCurrentCp(assist.getMaxCp());
				assist.setCurrentHp(assist.getMaxHp());
				assist.setCurrentMp(assist.getMaxMp());
				
				if (assist.inObserverMode())
				{
					assist.setLastCords(x, y + 50, z);
					assist.leaveOlympiadObserverMode();
				}
				else
				{
					assist.teleToLocation(x, y + 50, z, 0);
				}
				assist.broadcastUserInfo();
			}
		}
		
		public void rewards()
		{
			if ((leader != null) && leader.isOnline())
			{
				leader.addItem("Arena_Event", ArenaConfig.ARENA_REWARD_ID, ArenaConfig.ARENA_WIN_REWARD_COUNT_1X1, leader, true);
			}
			
			sendPacket("Congratulations, your team won the event!", 5);
		}
		
		public void rewardsLost()
		{
			if ((leader != null) && leader.isOnline())
			{
				leader.addItem("Arena_Event", ArenaConfig.ARENA_REWARD_ID, ArenaConfig.ARENA_LOST_REWARD_COUNT_1X1, leader, true);
			}
			
			sendPacket("your team lost the event! =(", 5);
		}
		
		public void setInTournamentEvent(boolean val)
		{
			if (leader != null && leader.isOnline())
				leader.setInArenaEvent(val);
			
			if (assist != null && assist.isOnline())
				assist.setInArenaEvent(val);
			
		}
		
		public void removeMessage()
		{
			if ((leader != null) && leader.isOnline())
			{
				leader.sendMessage("Tournament: Your participation has been removed.");
				leader.setArenaProtection(false);
				leader.setArena1x1(false);
			}
			
			if ((assist != null) && assist.isOnline())
			{
				assist.sendMessage("Tournament: Your participation has been removed.");
				assist.setArenaProtection(false);
				leader.setArena1x1(false);
			}
		}
		
		public void setArenaProtection(boolean val)
		{
			if ((leader != null) && leader.isOnline())
			{
				leader.setArenaProtection(val);
				leader.setArena1x1(val);
			}
			
			if ((assist != null) && assist.isOnline())
			{
				assist.setArenaProtection(val);
				leader.setArena1x1(val);
			}
		}
		
		public void revive()
		{
			if ((leader != null) && leader.isOnline() && leader.isDead())
			{
				leader.doRevive();
			}
			if ((assist != null) && assist.isOnline() && assist.isDead())
			{
				assist.doRevive();
			}
			
		}
		
		public void setImobilised(boolean val)
		{
			if ((leader != null) && leader.isOnline())
			{
				leader.setIsInvul(val);
				leader.setStopArena(val);
			}
			
			if ((assist != null) && assist.isOnline())
			{
				assist.setIsInvul(val);
				assist.setStopArena(val);
			}
		}
		
		public void setArenaAttack(boolean val)
		{
			if (leader != null && leader.isOnline())
			{
				leader.setArenaAttack(val);
				leader.broadcastUserInfo();
			}
			
			if (assist != null && assist.isOnline())
			{
				assist.setArenaAttack(val);
				assist.broadcastUserInfo();
			}
			
		}
		
		public void removePet()
		{
			if ((leader != null) && leader.isOnline())
			{
				
				if (leader.getPet() != null)
				{
					L2Summon summon = leader.getPet();
					if (summon != null)
					{
						summon.unSummon(summon.getOwner());
					}
					if (summon instanceof L2PetInstance)
					{
						summon.unSummon(leader);
					}
				}
				if ((leader.getMountType() == 1) || (leader.getMountType() == 2))
				{
					leader.dismount();
				}
			}
			if ((assist != null) && assist.isOnline())
			{
				
				if (assist.getPet() != null)
				{
					L2Summon summon = assist.getPet();
					if (summon != null)
					{
						summon.unSummon(summon.getOwner());
					}
					if (summon instanceof L2PetInstance)
					{
						summon.unSummon(assist);
					}
				}
				if ((assist.getMountType() == 1) || (assist.getMountType() == 2))
				{
					assist.dismount();
				}
			}
			
		}
		
		public void removeSkills()
		{
			if (!(leader.getClassId() == ClassId.shillenElder || leader.getClassId() == ClassId.shillienSaint || leader.getClassId() == ClassId.bishop || leader.getClassId() == ClassId.cardinal || leader.getClassId() == ClassId.elder || leader.getClassId() == ClassId.evaSaint))
			{
				for (L2Effect effect : leader.getAllEffects())
				{
					if (ArenaConfig.ARENA_STOP_SKILL_LIST.contains(effect.getSkill().getId()))
						leader.stopSkillEffects(effect.getSkill().getId());
				}
			}
			
			if (!(assist.getClassId() == ClassId.shillenElder || assist.getClassId() == ClassId.shillienSaint || assist.getClassId() == ClassId.bishop || assist.getClassId() == ClassId.cardinal || assist.getClassId() == ClassId.elder || assist.getClassId() == ClassId.evaSaint))
			{
				for (L2Effect effect : assist.getAllEffects())
				{
					if (ArenaConfig.ARENA_STOP_SKILL_LIST.contains(effect.getSkill().getId()))
						assist.stopSkillEffects(effect.getSkill().getId());
				}
			}
		}
		
		public void sendPacket(String message, int duration)
		{
			if (leader != null && leader.isOnline())
				leader.sendPacket(new ExShowScreenMessage(message, duration * 1000));
			
		}
		
		public void inicarContagem(int duration)
		{
			if (leader != null && leader.isOnline())
				ThreadPool.schedule(new countdown(leader, duration), 0);
			
		}
		
		public void sendPacketinit(String message, int duration)
		{
			if (leader != null && leader.isOnline())
				leader.sendPacket(new ExShowScreenMessage(message, duration * 1000, ExShowScreenMessage.SMPOS.MIDDLE_LEFT, false));
			
		}
	}
	
	private class EvtArenaTask implements Runnable
	{
		public final Pair pairOne;
		public final Pair pairTwo;
		private final int pOneX, pOneY, pOneZ, pTwoX, pTwoY, pTwoZ;
		private Arena arena;
		
		public EvtArenaTask(List<Pair> opponents)
		{
			pairOne = opponents.get(0);
			pairTwo = opponents.get(1);
			Player leader = pairOne.getLeader();
			pOneX = leader.getX();
			pOneY = leader.getY();
			pOneZ = leader.getZ();
			leader = pairTwo.getLeader();
			pTwoX = leader.getX();
			pTwoY = leader.getY();
			pTwoZ = leader.getZ();
		}
		
		@Override
		public void run()
		{
			free--;
			portPairsToArena();
			pairOne.inicarContagem(ArenaConfig.ARENA_WAIT_INTERVAL_1X1);
			pairTwo.inicarContagem(ArenaConfig.ARENA_WAIT_INTERVAL_1X1);
			try
			{
				Thread.sleep(ArenaConfig.ARENA_WAIT_INTERVAL_1X1 * 1000);
			}
			catch (InterruptedException e1)
			{
			}
			pairOne.sendPacketinit("Started. Good Fight!", 3);
			pairTwo.sendPacketinit("Started. Good Fight!", 3);
			pairOne.setImobilised(false);
			pairTwo.setImobilised(false);
			pairOne.setArenaAttack(true);
			pairTwo.setArenaAttack(true);
			
			while (check())
			{
				// check players status each seconds
				try
				{
					Thread.sleep(ArenaConfig.ARENA_CHECK_INTERVAL);
				}
				catch (InterruptedException e)
				{
					break;
				}
			}
			finishDuel();
			free++;
		}
		
		private void finishDuel()
		{
			fights.remove(arena.id);
			rewardWinner();
			pairOne.revive();
			pairTwo.revive();
			pairOne.teleToLocation(pOneX, pOneY, pOneZ);
			pairTwo.teleToLocation(pTwoX, pTwoY, pTwoZ);
			pairOne.setInTournamentEvent(false);
			pairTwo.setInTournamentEvent(false);
			pairOne.setArenaProtection(false);
			pairTwo.setArenaProtection(false);
			pairOne.setArenaAttack(false);
			pairTwo.setArenaAttack(false);
			arena.setFree(true);
			openDoors(TvTConfig.TVT_DOORS_IDS_TO_OPEN);
		}
		
		private void rewardWinner()
		{
			if (pairOne.isAlive() && !pairTwo.isAlive())
			{
				Player leader1 = pairOne.getLeader();
				Player leader2 = pairTwo.getLeader();
				pairOne.rewards();
				pairTwo.rewardsLost();
				
				if (leader1.getClan() != null && leader2.getClan() != null && ArenaConfig.TOURNAMENT_EVENT_ANNOUNCE)
					Broadcast.gameAnnounceToOnlinePlayers("Tournament: " + "(1X1): (" + leader1.getClan().getName() + " VS " + leader2.getClan().getName() + ") ~> " + leader1.getClan().getName() + " win!");
				
				if (ArenaConfig.TOURNAMENT_EVENT_ANNOUNCE)
				{
					Broadcast.gameAnnounceToOnlinePlayers("Tournament: " + "(" + leader1.getName() + " VS " + leader2.getName() + ") ~> " + leader1.getName() + " win!");
				}
			}
			else if (pairTwo.isAlive() && !pairOne.isAlive())
			{
				Player leader1 = pairTwo.getLeader();
				Player leader2 = pairOne.getLeader();
				if (leader1.getClan() != null && leader2.getClan() != null && ArenaConfig.TOURNAMENT_EVENT_ANNOUNCE)
					Broadcast.gameAnnounceToOnlinePlayers("Tournament: " + "(1X1): (" + leader1.getClan().getName() + " VS " + leader2.getClan().getName() + ") ~> " + leader1.getClan().getName() + " win!");
				if (ArenaConfig.TOURNAMENT_EVENT_ANNOUNCE)
				{
					Broadcast.gameAnnounceToOnlinePlayers("Tournament: " + "(" + leader1.getName() + " VS " + leader2.getName() + ") ~> " + leader1.getName() + " win!");
				}
				pairTwo.rewards();
				pairOne.rewardsLost();
			}
			
		}
		
		private boolean check()
		{
			return (pairOne.isDead() && pairTwo.isDead());
		}
		
		private void portPairsToArena()
		{
			for (Arena arena : arenas)
			{
				if (arena.isFree)
				{
					this.arena = arena;
					arena.setFree(false);
					pairOne.removePet();
					pairTwo.removePet();
					pairOne.teleToLocation(arena.x - 750, arena.y, arena.z);
					pairTwo.teleToLocation(arena.x + 750, arena.y, arena.z);
					pairOne.setImobilised(true);
					pairTwo.setImobilised(true);
					pairOne.setInTournamentEvent(true);
					pairTwo.setInTournamentEvent(true);
					pairOne.removeSkills();
					pairTwo.removeSkills();
					// Closes all doors specified in configs for Tour
					closeDoors(TvTConfig.TVT_DOORS_IDS_TO_CLOSE);
					fights.put(this.arena.id, pairOne.getLeader().getName() + " vs " + pairTwo.getLeader().getName());
					Broadcast.gameAnnounceToOnlinePlayers("Tournament: " + "(" + pairTwo.getLeader().getName() + " VS " + pairOne.getLeader().getName() + ")");
					break;
				}
			}
		}
	}
	
	private class Arena
	{
		protected int x, y, z;
		protected boolean isFree = true;
		int id;
		
		public Arena(int id, int x, int y, int z)
		{
			this.id = id;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public void setFree(boolean val)
		{
			isFree = val;
		}
	}
	
	protected class countdown implements Runnable
	{
		private final Player _player;
		private int _time;
		
		public countdown(Player player, int time)
		{
			_time = time;
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isOnline())
			{
				
				switch (_time)
				{
					case 300:
					case 240:
					case 180:
					case 120:
					case 57:
						if (_player.isOnline())
						{
							_player.sendPacket(new ExShowScreenMessage("The battle starts in 60 second(s)..", 4000));
							_player.sendMessage("60 second(s) to start the battle.");
						}
						break;
					case 45:
						if (_player.isOnline())
						{
							_player.sendPacket(new ExShowScreenMessage("" + _time + " ..", 3000));
							_player.sendMessage(_time + " second(s) to start the battle!");
						}
						break;
					case 27:
						if (_player.isOnline())
						{
							_player.sendPacket(new ExShowScreenMessage("The battle starts in 30 second(s)..", 4000));
							_player.sendMessage("30 second(s) to start the battle.");
						}
						break;
					case 20:
						if (_player.isOnline())
						{
							_player.sendPacket(new ExShowScreenMessage("" + _time + " ..", 3000));
							_player.sendMessage(_time + " second(s) to start the battle!");
						}
						break;
					case 15:
						if (_player.isOnline())
						{
							_player.sendPacket(new ExShowScreenMessage("" + _time + " ..", 3000));
							_player.sendMessage(_time + " second(s) to start the battle!");
						}
						break;
					case 10:
						if (_player.isOnline())
							_player.sendMessage(_time + " second(s) to start the battle!");
						break;
					case 5:
						if (_player.isOnline())
							_player.sendMessage(_time + " second(s) to start the battle!");
						break;
					case 4:
						if (_player.isOnline())
							_player.sendMessage(_time + " second(s) to start the battle!");
						break;
					case 3:
						if (_player.isOnline())
							_player.sendMessage(_time + " second(s) to start the battle!");
						break;
					case 2:
						if (_player.isOnline())
							_player.sendMessage(_time + " second(s) to start the battle!");
						break;
					case 1:
						if (_player.isOnline())
							_player.sendMessage(_time + " second(s) to start the battle!");
						break;
				}
				if (_time > 1)
				{
					ThreadPool.schedule(new countdown(_player, _time - 1), 1000);
				}
			}
		}
	}
	
	/**
	 * Open doors specified in configs
	 * @param doors
	 */
	public static void openDoors(List<Integer> doors)
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
	
	/**
	 * Close doors specified in configs
	 * @param doors
	 */
	public static void closeDoors(List<Integer> doors)
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
	
	private static class SingletonHolder
	{
		protected static final Arena1x1 INSTANCE = new Arena1x1();
	}
}
