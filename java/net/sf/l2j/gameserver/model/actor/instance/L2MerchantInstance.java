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
package net.sf.l2j.gameserver.model.actor.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

import Dev.Config.ConfigDev;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ThreadPool;
import net.sf.l2j.gameserver.datatables.BuyListTable;
import net.sf.l2j.gameserver.datatables.HennaTable;
import net.sf.l2j.gameserver.datatables.MultisellData;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.custom.IconTable;
import net.sf.l2j.gameserver.model.L2Augmentation;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.buylist.NpcBuyList;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.itemcontainer.Inventory;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.BuyList;
import net.sf.l2j.gameserver.network.serverpackets.HennaEquipList;
import net.sf.l2j.gameserver.network.serverpackets.HennaRemoveList;
import net.sf.l2j.gameserver.network.serverpackets.InventoryUpdate;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.OpenUrl;
import net.sf.l2j.gameserver.network.serverpackets.SellList;
import net.sf.l2j.gameserver.network.serverpackets.ShopPreviewList;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;
import net.sf.l2j.gameserver.network.serverpackets.SpecialCamera;
import net.sf.l2j.util.StringUtil;

/**
 * L2Merchant type, it got buy/sell methods && bypasses.<br>
 * It is used as extends for classes such as L2Fisherman, L2CastleChamberlain, etc.
 */
public class L2MerchantInstance extends L2NpcInstance
{
	public L2MerchantInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public String getHtmlPath(int npcId, int val)
	{
		String filename = "";
		
		if (val == 0)
			filename = "" + npcId;
		else
			filename = npcId + "-" + val;
		
		return "data/html/merchant/" + filename + ".htm";
	}
	
	private final void showWearWindow(Player player, int val)
	{
		final NpcBuyList buyList = BuyListTable.getInstance().getBuyList(val);
		if (buyList == null || !buyList.isNpcAllowed(getNpcId()))
			return;
		
		player.tempInventoryDisable();
		player.sendPacket(new ShopPreviewList(buyList, player.getAdena(), player.getExpertiseIndex()));
	}
	
	protected final void showBuyWindow(Player player, int val)
	{
		final NpcBuyList buyList = BuyListTable.getInstance().getBuyList(val);
		if (buyList == null || !buyList.isNpcAllowed(getNpcId()))
			return;
		
		player.tempInventoryDisable();
		player.sendPacket(new BuyList(buyList, player.getAdena(), (getIsInTown()) ? getCastle().getTaxRate() : 0));
	}
	
	@Override
	public void onBypassFeedback(Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command
		
		if (actualCommand.equalsIgnoreCase("Buy"))
		{
			if (st.countTokens() < 1)
				return;
			
			showBuyWindow(player, Integer.parseInt(st.nextToken()));
		}
		
		else if (actualCommand.equalsIgnoreCase("OpenUrl"))
		{
			String path = command.substring(5).trim();
			
			player.sendPacket(new OpenUrl(path));

		}
		
		else if (actualCommand.startsWith("visualTest"))
		{
			if(ConfigDev.ENABLE_VISUAL_SET)
			{
				if (player.getVisualTest() > 0)
				{
					player.sendMessage("You are already trying on a uniform, please wait till it finishes.");
					return;
				}
				
				if (ConfigDev.ENABLE_VISUAL_EFFECT)
				{
					L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
					if (skill != null)
					{
						MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
						player.sendPacket(MSU);
						player.broadcastPacket(MSU);
						player.useMagic(skill, false, false);
						int uniform = Integer.parseInt(command.substring(11));
						player.setVisualTest(uniform);
						player.broadcastPacket(new SocialAction(player, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						ThreadPool.schedule(() -> player.setVisualTest(0), 1000 * ConfigDev.VISUAL_TEST_SEGUNDS);
						sendVisualTime(player);
						VisualHtml(player);
						if (ConfigDev.CAMERA_ENABLED)
						{
							player.sendPacket(new SpecialCamera(player.getObjectId(), ConfigDev.CAMERA_DISTANCE, ConfigDev.CAMERA_POV, ConfigDev.CAMERA_ANGLE, ConfigDev.CAMERA_SPEED, ConfigDev.CAMERA_DELAY));
						}else {
							
						}
					}
				}else{
					int uniform = Integer.parseInt(command.substring(11));
					player.setVisualTest(uniform);
					ThreadPool.schedule(() -> player.setVisualTest(0), 1000 * ConfigDev.VISUAL_TEST_SEGUNDS);
					sendVisualTime(player);
					VisualHtml(player);
				}
			}else
				player.sendMessage("Sorry, admin has disabled skins.");	
		}
		
		else if (command.startsWith("visualHtml"))
		{
			
			VisualHtml(player);
			
		}
		
		
		
		else if (actualCommand.equalsIgnoreCase("Sell"))
		{
			player.sendPacket(new SellList(player));
		}
		else if (actualCommand.equalsIgnoreCase("Wear") && Config.ALLOW_WEAR)
		{
			if (st.countTokens() < 1)
				return;
			
			showWearWindow(player, Integer.parseInt(st.nextToken()));
		}
		else if (actualCommand.equalsIgnoreCase("Multisell"))
		{
			if (st.countTokens() < 1)
				return;
			
			MultisellData.getInstance().separateAndSend(st.nextToken(), player, false, getCastle().getTaxRate());
		}
		else if (actualCommand.equalsIgnoreCase("Multisell_Shadow"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			
			if (player.getLevel() < 40)
				html.setFile("data/html/common/shadow_item-lowlevel.htm");
			else if (player.getLevel() < 46)
				html.setFile("data/html/common/shadow_item_mi_c.htm");
			else if (player.getLevel() < 52)
				html.setFile("data/html/common/shadow_item_hi_c.htm");
			else
				html.setFile("data/html/common/shadow_item_b.htm");
			
			html.replace("%objectId%", getObjectId());
			player.sendPacket(html);
		}
		else if (actualCommand.equalsIgnoreCase("Exc_Multisell"))
		{
			if (st.countTokens() < 1)
				return;
			
			MultisellData.getInstance().separateAndSend(st.nextToken(), player, true, getCastle().getTaxRate());
		}
		else if (command.equals("Draw"))
			player.sendPacket(new HennaEquipList(player, HennaTable.getInstance().getAvailableHenna(player.getClassId().getId())));
		else if (command.equals("RemoveList"))
		{
			boolean hasHennas = false;
			for (int i = 1; i <= 3; i++)
			{
				if (player.getHenna(i) != null)
					hasHennas = true;
			}
			
			if (hasHennas)
				player.sendPacket(new HennaRemoveList(player));
			else
				player.sendPacket(SystemMessageId.SYMBOL_NOT_FOUND);
		}
		else if (command.startsWith("buy_str"))
		{
			Augments(player, 1070930999, 0, 0);
			return;
		}
		else if (command.startsWith("buy_int"))
		{
			Augments(player, 1071062077, 0, 0);
			return;
		}
		else if (command.startsWith("buy_men"))
		{
			Augments(player, 1071123995, 0, 0);
			return;
		}
		else if (command.startsWith("buy_con"))
		{
			Augments(player, 1070992947, 0, 0);
			return;
		}
		else if (actualCommand.equalsIgnoreCase("Augmenter_str"))
		{
			ItemInstance rhand = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

			if (rhand == null)
			{
				player.sendMessage(player.getName() + " have to equip a weapon.");
				return;
			}
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			StringBuilder list = new StringBuilder(1500);

			ItemInstance item = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

			StringUtil.append(list, "<img src=\"" + IconTable.getIcon(item.getItemId()) + "\" width=32 height=32>");

			html.setFile("data/html/mods/donate/Buy-STR.htm");

			html.replace("%list%", list.toString());
			html.replace("%objectId%", getObjectId());
			player.sendPacket(html);
		}
		else if (actualCommand.equalsIgnoreCase("Augmenter_int"))
		{
			ItemInstance rhand = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);
			
			if (rhand == null)
			{
				player.sendMessage(player.getName() + " have to equip a weapon.");
				return;
			}
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			StringBuilder list = new StringBuilder(1500);

			ItemInstance item = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

			StringUtil.append(list, "<img src=\"" + IconTable.getIcon(item.getItemId()) + "\" width=32 height=32>");

			html.setFile("data/html/mods/donate/Buy-INT.htm");

			html.replace("%list%", list.toString());
			html.replace("%objectId%", getObjectId());
			player.sendPacket(html);
		}
		else if (actualCommand.equalsIgnoreCase("Augmenter_men"))
		{
			ItemInstance rhand = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

			if (rhand == null)
			{
				player.sendMessage(player.getName() + " have to equip a weapon.");
				return;
			}
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			StringBuilder list = new StringBuilder(1500);

			ItemInstance item = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

			StringUtil.append(list, "<img src=\"" + IconTable.getIcon(item.getItemId()) + "\" width=32 height=32>");

			html.setFile("data/html/mods/donate/Buy-MEN.htm");

			html.replace("%list%", list.toString());
			html.replace("%objectId%", getObjectId());
			player.sendPacket(html);
		}
		else if (actualCommand.equalsIgnoreCase("Augmenter_con"))
		{
			ItemInstance rhand = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

			if (rhand == null)
			{
				player.sendMessage(player.getName() + " have to equip a weapon.");
				return;
			}
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			StringBuilder list = new StringBuilder(1500);

			ItemInstance item = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

			StringUtil.append(list, "<img src=\"" + IconTable.getIcon(item.getItemId()) + "\" width=32 height=32>");

			html.setFile("data/html/mods/donate/Buy-CON.htm");

			html.replace("%list%", list.toString());
			html.replace("%objectId%", getObjectId());
			player.sendPacket(html);
		}
		else
			super.onBypassFeedback(player, command);
	}
	
	public void Augments(Player player, int attributes, int idaugment, int levelaugment)
	{
		ItemInstance rhand = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);

		if (rhand == null)
		{
			player.sendMessage(player.getName() + " have to equip a weapon.");
			return;
		}
		else if (rhand.getItem().getCrystalType().getId() == 0 || rhand.getItem().getCrystalType().getId() == 1 || rhand.getItem().getCrystalType().getId() == 2)
		{
			player.sendMessage("You can't augment under " + rhand.getItem().getCrystalType() + " Grade Weapon!");
			return;
		}
		else if (rhand.isHeroItem())
		{
			player.sendMessage("You Cannot be add Augment On " + rhand.getItemName() + " !");
			return;
		}
		else if (player.getInventory().getInventoryItemCount(Config.DONATE_TICKET, -1) < Config.AUGM_PRICE)
		{
			player.sendMessage("You do not have enough Ticket Donate.");
			return;
		}
		if (!rhand.isAugmented())
		{
			player.sendMessage("Purchase made successfully, check your weapon.");
			AugmentWeaponDatabase(player, attributes, idaugment, levelaugment);
		}
		else
		{
			player.sendMessage("You Have Augment on weapon!");
			return;
		}
	}

	public void AugmentWeaponDatabase(Player player, int attributes, int id, int level)
	{
		ItemInstance item = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);
		L2Augmentation augmentation = new L2Augmentation(attributes, id, level);
		augmentation.applyBonus(player);
		player.destroyItemByItemId("Consume", Config.DONATE_TICKET, Config.AUGM_PRICE, player, true);
		item.setAugmentation(augmentation);
		player.disarmWeapons();

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
			statement.setInt(1, item.getObjectId());
			statement.setInt(2, attributes);
			statement.setInt(3, id);
			statement.setInt(4, level);
			InventoryUpdate iu = new InventoryUpdate();
			player.sendPacket(iu);
			statement.execute();
			statement.close();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	private static void sendVisualTime(Player activeChar)
	{
		activeChar.sendMessage("Visual Time: " + ConfigDev.VISUAL_TEST_SEGUNDS + " seconds");
	}
	
	public static void VisualHtml(Player activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/mods/menu/Visual.htm");
		activeChar.sendPacket(html);
	}
}