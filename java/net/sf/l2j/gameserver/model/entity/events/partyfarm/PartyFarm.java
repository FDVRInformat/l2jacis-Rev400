package net.sf.l2j.gameserver.model.entity.events.partyfarm;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.util.Broadcast;

/**
 * @author Juvenil Walker
 */
public class PartyFarm
{
	protected static final Logger LOGGER = Logger.getLogger(PartyFarm.class.getName());
	public static L2Spawn _monster;
	
	protected static int _npcId = 0;
	protected static int _npcX = 0;
	protected static int _npcY = 0;
	protected static int _npcZ = 0;
	protected static int _npcHeading = 0;
	
	public static int _bossHeading = 0;
	public static String _eventName = " Party Farm";
	public static boolean _started = false;
	public static boolean _aborted = false;
	protected static boolean _finish = false;
	static PartyFarm _instance;
	
	public static void bossSpawnMonster()
	{
		spawnMonsters();
		
		Broadcast.gameAnnounceToOnlinePlayers("Party Farm: " + Config.PARTY_FARMANNONCER);
		Broadcast.gameAnnounceToOnlinePlayers("Party Farm: " + Config.PARTY_FARM_MESSAGE_TEXT);
		Broadcast.gameAnnounceToOnlinePlayers("Party Farm: Duration: " + Config.EVENT_BEST_FARM_TIME + " minute(s)!");
		
		_aborted = false;
		_started = true;
		
		waiter(Config.EVENT_BEST_FARM_TIME * 60 * 1000);
		if (!_aborted)
			Finish_Event();
	}
	
	public static void Finish_Event()
	{
		Broadcast.gameAnnounceToOnlinePlayers("Party Farm: Finished!");
		
		unSpawnMonsters();
		_started = false;
		
		InitialPartyFarm.getInstance().StartCalculationOfNextEventTime();
		
		for (Player player : L2World.getInstance().getL2Players())
		{
			if (player != null && player.isOnline())
			{
				CreatureSay cs = new CreatureSay(player.getObjectId(), Say2.PARTY, "Party Farm", ("Next Party Farm: " + InitialPartyFarm.getInstance().getNextTime()) + " (GMT-3)."); // 8D
				player.sendPacket(cs);
			}
		}
	}
	
	public static void spawnMonsters()
	{
		for (int i = 0; i < Config.MONSTER_LOCS_COUNT; i++)
		{
			int[] coord = Config.MONSTER_LOCS[i];
			monsters.add(spawnNPC(coord[0], coord[1], coord[2], Config.monsterId));
		}
	}
	
	public static boolean is_started()
	{
		return _started;
	}
	
	public static boolean is_finish()
	{
		return _finish;
		
	}
	
	protected static L2Spawn spawnNPC(int xPos, int yPos, int zPos, int npcId)
	{
		NpcTemplate template = NpcTable.getInstance().getTemplate(npcId);
		try
		{
			final L2Spawn spawn = new L2Spawn(template);
			spawn.setLocx(xPos);
			spawn.setLocy(yPos);
			spawn.setLocz(zPos);
			spawn.setHeading(0);
			spawn.setRespawnDelay(Config.PARTY_FARM_MONSTER_DALAY);
			SpawnTable.getInstance().addNewSpawn(spawn, false);
			spawn.init();
			spawn.getLastSpawn().setTitle("Event Champion");
			spawn.getLastSpawn().isAggressive();
			spawn.getLastSpawn().decayMe();
			spawn.getLastSpawn().spawnMe(spawn.getLastSpawn().getX(), spawn.getLastSpawn().getY(), spawn.getLastSpawn().getZ());
			
			return spawn;
		}
		catch (Exception e)
		{
		}
		return null;
	}
	
	protected static ArrayList<L2Spawn> monsters = new ArrayList<>();
	
	protected static void unSpawnMonsters()
	{
		for (L2Spawn s : monsters)
		{
			if (s == null)
			{
				monsters.remove(s);
				return;
			}
			
			s.getNpc().deleteMe();
			s.stopRespawn();
			SpawnTable.getInstance().deleteSpawn(s, true);
			
		}
	}
	
	protected static void waiter(long interval)
	{
		long startWaiterTime = System.currentTimeMillis();
		int seconds = (int) (interval / 1000L);
		while (((startWaiterTime + interval) > System.currentTimeMillis()) && !_aborted)
		{
			seconds--;
			switch (seconds)
			{
				case 3600:
					if (_started)
					{
						Broadcast.gameAnnounceToOnlinePlayers("[Party Farm]: " + (seconds / 60 / 60) + " Time event finish!");
					}
					
					break;
				case 60:
				case 120:
				case 180:
				case 240:
				case 300:
				case 600:
				case 900:
				case 1800:
					if (_started)
					{
						Broadcast.gameAnnounceToOnlinePlayers("[Party Farm]: " + (seconds / 60) + " minute(s) event finish!");
					}
					break;
				case 1:
				case 2:
				case 3:
				case 10:
				case 15:
				case 30:
					if (_started)
					{
						Broadcast.gameAnnounceToOnlinePlayers("[Party Farm]: " + seconds + " second(s) event finish!");
					}
					
					break;
			}
			long startOneSecondWaiterStartTime = System.currentTimeMillis();
			while ((startOneSecondWaiterStartTime + 1000L) > System.currentTimeMillis())
			{
				try
				{
					Thread.sleep(1L);
				}
				catch (InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
		}
	}
}
