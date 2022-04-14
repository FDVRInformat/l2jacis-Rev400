package phantom.ai.walker;

import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.serverpackets.PrivateStoreMsgSell;
import net.sf.l2j.util.Rnd;

import phantom.FakePlayer;
import phantom.FakePlayerConfig;
import phantom.ai.FakePlayerAI;

public class PrivateStoreSellAI extends FakePlayerAI
{
	public PrivateStoreSellAI(FakePlayer character)
	{
		super(character);
		setup();
	}
	
	@Override
	public void setup()
	{
		_fakePlayer.setIsRunning(true);
	}
	
	@Override
	public void thinkAndAct()
	{
		setBusyThinking(true);
		handleDeath();
		
		if ((FakePlayerConfig.PHANTOM_PRIVATE_STORE))
		{
			_fakePlayer.addItem(":", getPrivateSell(), 1, _fakePlayer, true);
			for (ItemInstance itemDrop : _fakePlayer.getInventory().getItems())
			{
				_fakePlayer.getSellList().addItem(itemDrop.getObjectId(), 1, Rnd.get(100456789, 150456789));
			}
			_fakePlayer.getSellList().setTitle(getPrivateSell_Title());
			_fakePlayer.getSellList().setPackaged(Player.StoreType.SELL == Player.StoreType.PACKAGE_SELL);
			_fakePlayer.sitDown();
			
			_fakePlayer.setPrivateStoreType(1);
			_fakePlayer.getPrivateStoreType();
			_fakePlayer.broadcastUserInfo();
			_fakePlayer.broadcastPacket(new PrivateStoreMsgSell(_fakePlayer));
		}
	}
	
	static int getPrivateSell()
	{
		return FakePlayerConfig.LIST_PRIVATE_SELL.get(Rnd.get(FakePlayerConfig.LIST_PRIVATE_SELL.size())).intValue();
	}
	
	static final List<String> list_msg_sell = new ArrayList<>();
	
	@SuppressWarnings("null")
	static String getPrivateSell_Title()
	{
		String msg = null;
		if (msg == null)
		{
			msg = FakePlayerConfig.PHANTOM_PRIVATE_SELL_TITLE.get(Rnd.get(FakePlayerConfig.PHANTOM_PRIVATE_SELL_TITLE.size()));
		}
		if (list_msg_sell.contains(msg))
		{
			boolean gerar = true;
			while ((list_msg_sell.size() < FakePlayerConfig.PHANTOM_PRIVATE_SELL_TITLE.size()) && (gerar))
			{
				msg = FakePlayerConfig.PHANTOM_PRIVATE_SELL_TITLE.get(Rnd.get(FakePlayerConfig.PHANTOM_PRIVATE_SELL_TITLE.size()));
				if (!list_msg_sell.contains(msg))
				{
					list_msg_sell.add(msg);
					gerar = false;
					return msg;
				}
				if (list_msg_sell.size() == FakePlayerConfig.PHANTOM_PRIVATE_SELL_TITLE.size())
				{
					gerar = false;
					return "";
				}
			}
		}
		else if (!list_msg_sell.contains(msg))
		{
			list_msg_sell.add(msg);
			return msg;
		}
		return msg;
	}
}