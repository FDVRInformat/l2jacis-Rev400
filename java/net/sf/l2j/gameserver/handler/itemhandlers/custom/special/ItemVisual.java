package net.sf.l2j.gameserver.handler.itemhandlers.custom.special;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;

import Dev.Config.ConfigDev;

/**
 * @author COMBATE
 *
 */
public class ItemVisual implements IItemHandler
{
	@Override
	public void useItem(L2Playable playable, ItemInstance item, boolean forceUse)
	{
		if(ConfigDev.ENABLE_VISUAL_SET)
		{
			if (!(playable instanceof Player))
				
				return;
			
			Player player = (Player) playable;
			int itemId = item.getItemId();
			
			switch(itemId)
			{
				//Item_ID ItemHander Config/data/xml/item/ItemHander
				case 30000:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR1;
							itemId = ConfigDev.VISUAL_CHEST1;
							player.setVisual(player.getVisual() == 1 ? 0 : 1);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME1);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));


						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR1;
						itemId = ConfigDev.VISUAL_CHEST1;
						player.setVisual(player.getVisual() == 1 ? 0 : 1);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME1);
					}
					break;
					
				case 30001:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR2;
							itemId = ConfigDev.VISUAL_CHEST2;
							player.setVisual(player.getVisual() == 2 ? 0 : 2);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME2);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
							
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR2;
						itemId = ConfigDev.VISUAL_CHEST2;
						player.setVisual(player.getVisual() == 2 ? 0 : 2);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME2);
					}
					break;
				case 30002:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR3;
							itemId = ConfigDev.VISUAL_CHEST3;
							itemId = ConfigDev.VISUAL_LEGS3;
							itemId = ConfigDev.VISUAL_GLOVES3;
							itemId = ConfigDev.VISUAL_BOOTS3;
							player.setVisual(player.getVisual() == 3 ? 0 : 3);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME3);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR3;
						itemId = ConfigDev.VISUAL_CHEST3;
						itemId = ConfigDev.VISUAL_LEGS3;
						itemId = ConfigDev.VISUAL_GLOVES3;
						itemId = ConfigDev.VISUAL_BOOTS3;
						player.setVisual(player.getVisual() == 3 ? 0 : 3);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME3);
					}
					break;
				case 30003:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR4;
							itemId = ConfigDev.VISUAL_CHEST4;
							itemId = ConfigDev.VISUAL_LEGS4;
							itemId = ConfigDev.VISUAL_GLOVES4;
							itemId = ConfigDev.VISUAL_BOOTS4;
							player.setVisual(player.getVisual() == 4 ? 0 : 4);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME4);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR4;
						itemId = ConfigDev.VISUAL_CHEST4;
						itemId = ConfigDev.VISUAL_LEGS4;
						itemId = ConfigDev.VISUAL_GLOVES4;
						itemId = ConfigDev.VISUAL_BOOTS4;
						player.setVisual(player.getVisual() == 4 ? 0 : 4);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME4);
					}
					break;
		
				case 30004:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR5;
							itemId = ConfigDev.VISUAL_CHEST5;
							itemId = ConfigDev.VISUAL_LEGS5;
							itemId = ConfigDev.VISUAL_GLOVES5;
							itemId = ConfigDev.VISUAL_BOOTS5;
							player.setVisual(player.getVisual() == 5 ? 0 : 5);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME5);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR5;
						itemId = ConfigDev.VISUAL_CHEST5;
						itemId = ConfigDev.VISUAL_LEGS5;
						itemId = ConfigDev.VISUAL_GLOVES5;
						itemId = ConfigDev.VISUAL_BOOTS5;
						player.setVisual(player.getVisual() == 5 ? 0 : 5);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME5);
					}
					break;
					
				case 30005:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR6;
							itemId = ConfigDev.VISUAL_CHEST6;
							itemId = ConfigDev.VISUAL_LEGS6;
							itemId = ConfigDev.VISUAL_GLOVES6;
							itemId = ConfigDev.VISUAL_BOOTS6;
							player.setVisual(player.getVisual() == 6 ? 0 : 6);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME6);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR6;
						itemId = ConfigDev.VISUAL_CHEST6;
						itemId = ConfigDev.VISUAL_LEGS6;
						itemId = ConfigDev.VISUAL_GLOVES6;
						itemId = ConfigDev.VISUAL_BOOTS6;
						player.setVisual(player.getVisual() == 6 ? 0 : 6);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME6);
					}
					break;
					
				case 30006:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR7;
							itemId = ConfigDev.VISUAL_CHEST7;
							itemId = ConfigDev.VISUAL_LEGS7;
							itemId = ConfigDev.VISUAL_GLOVES7;
							itemId = ConfigDev.VISUAL_BOOTS7;
							player.setVisual(player.getVisual() == 7 ? 0 : 7);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME7);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR7;
						itemId = ConfigDev.VISUAL_CHEST7;
						itemId = ConfigDev.VISUAL_LEGS7;
						itemId = ConfigDev.VISUAL_GLOVES7;
						itemId = ConfigDev.VISUAL_BOOTS7;
						player.setVisual(player.getVisual() == 7 ? 0 : 7);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME7);
					}
					break;
					
				case 30007:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR8;
							itemId = ConfigDev.VISUAL_CHEST8;
							itemId = ConfigDev.VISUAL_LEGS8;
							itemId = ConfigDev.VISUAL_GLOVES8;
							itemId = ConfigDev.VISUAL_BOOTS8;
							player.setVisual(player.getVisual() == 8 ? 0 : 8);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME8);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR8;
						itemId = ConfigDev.VISUAL_CHEST8;
						itemId = ConfigDev.VISUAL_LEGS8;
						itemId = ConfigDev.VISUAL_GLOVES8;
						itemId = ConfigDev.VISUAL_BOOTS8;
						player.setVisual(player.getVisual() == 8 ? 0 : 8);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME8);
					}
					break;
					
				case 30008:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR9;
							itemId = ConfigDev.VISUAL_CHEST9;
							itemId = ConfigDev.VISUAL_LEGS9;
							itemId = ConfigDev.VISUAL_GLOVES9;
							itemId = ConfigDev.VISUAL_BOOTS9;
							player.setVisual(player.getVisual() == 9 ? 0 : 9);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME9);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR9;
						itemId = ConfigDev.VISUAL_CHEST9;
						itemId = ConfigDev.VISUAL_LEGS9;
						itemId = ConfigDev.VISUAL_GLOVES9;
						itemId = ConfigDev.VISUAL_BOOTS9;
						player.setVisual(player.getVisual() == 9 ? 0 : 9);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME9);
					}
					break;
					
				case 30009:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR10;
							itemId = ConfigDev.VISUAL_CHEST10;
							itemId = ConfigDev.VISUAL_LEGS10;
							itemId = ConfigDev.VISUAL_GLOVES10;
							itemId = ConfigDev.VISUAL_BOOTS10;
							player.setVisual(player.getVisual() == 10 ? 0 : 10);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME10);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR10;
						itemId = ConfigDev.VISUAL_CHEST10;
						itemId = ConfigDev.VISUAL_LEGS10;
						itemId = ConfigDev.VISUAL_GLOVES10;
						itemId = ConfigDev.VISUAL_BOOTS10;
						player.setVisual(player.getVisual() == 10 ? 0 : 10);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME10);
					}
					break;
					
				case 30010:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR11;
							itemId = ConfigDev.VISUAL_CHEST11;
							itemId = ConfigDev.VISUAL_LEGS11;
							itemId = ConfigDev.VISUAL_GLOVES11;
							itemId = ConfigDev.VISUAL_BOOTS11;
							player.setVisual(player.getVisual() == 11 ? 0 : 11);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME11);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR11;
						itemId = ConfigDev.VISUAL_CHEST11;
						itemId = ConfigDev.VISUAL_LEGS11;
						itemId = ConfigDev.VISUAL_GLOVES11;
						itemId = ConfigDev.VISUAL_BOOTS11;
						player.setVisual(player.getVisual() == 11 ? 0 : 11);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME11);
					}
					break;
					
				case 30011:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR12;
							itemId = ConfigDev.VISUAL_CHEST12;
							itemId = ConfigDev.VISUAL_LEGS12;
							itemId = ConfigDev.VISUAL_GLOVES12;
							itemId = ConfigDev.VISUAL_BOOTS12;
							player.setVisual(player.getVisual() == 12 ? 0 : 12);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME12);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR12;
						itemId = ConfigDev.VISUAL_CHEST12;
						itemId = ConfigDev.VISUAL_LEGS12;
						itemId = ConfigDev.VISUAL_GLOVES12;
						itemId = ConfigDev.VISUAL_BOOTS12;
						player.setVisual(player.getVisual() == 12 ? 0 : 12);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME12);
					}
					break;
					
				case 30012:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR13;
							itemId = ConfigDev.VISUAL_CHEST13;
							itemId = ConfigDev.VISUAL_LEGS13;
							itemId = ConfigDev.VISUAL_GLOVES13;
							itemId = ConfigDev.VISUAL_BOOTS13;
							player.setVisual(player.getVisual() == 13 ? 0 : 13);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME13);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR13;
						itemId = ConfigDev.VISUAL_CHEST13;
						itemId = ConfigDev.VISUAL_LEGS13;
						itemId = ConfigDev.VISUAL_GLOVES13;
						itemId = ConfigDev.VISUAL_BOOTS13;
						player.setVisual(player.getVisual() == 13 ? 0 : 13);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME13);
					}
					break;
					
				case 30013:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR14;
							itemId = ConfigDev.VISUAL_CHEST14;
							itemId = ConfigDev.VISUAL_LEGS14;
							itemId = ConfigDev.VISUAL_GLOVES14;
							itemId = ConfigDev.VISUAL_BOOTS14;
							player.setVisual(player.getVisual() == 14 ? 0 : 14);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME14);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR14;
						itemId = ConfigDev.VISUAL_CHEST14;
						itemId = ConfigDev.VISUAL_LEGS14;
						itemId = ConfigDev.VISUAL_GLOVES14;
						itemId = ConfigDev.VISUAL_BOOTS14;
						player.setVisual(player.getVisual() == 14 ? 0 : 14);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME14);
					}
					break;
					
				case 30014:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR15;
							itemId = ConfigDev.VISUAL_CHEST15;
							itemId = ConfigDev.VISUAL_LEGS15;
							itemId = ConfigDev.VISUAL_GLOVES15;
							itemId = ConfigDev.VISUAL_BOOTS15;
							player.setVisual(player.getVisual() == 15 ? 0 : 15);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME15);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR15;
						itemId = ConfigDev.VISUAL_CHEST15;
						itemId = ConfigDev.VISUAL_LEGS15;
						itemId = ConfigDev.VISUAL_GLOVES15;
						itemId = ConfigDev.VISUAL_BOOTS15;
						player.setVisual(player.getVisual() == 15 ? 0 : 15);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME15);
					}
					break;
					
				case 30015:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR16;
							itemId = ConfigDev.VISUAL_CHEST16;
							itemId = ConfigDev.VISUAL_LEGS16;
							itemId = ConfigDev.VISUAL_GLOVES16;
							itemId = ConfigDev.VISUAL_BOOTS16;
							player.setVisual(player.getVisual() == 16 ? 0 : 16);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME16);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR16;
						itemId = ConfigDev.VISUAL_CHEST16;
						itemId = ConfigDev.VISUAL_LEGS16;
						itemId = ConfigDev.VISUAL_GLOVES16;
						itemId = ConfigDev.VISUAL_BOOTS16;
						player.setVisual(player.getVisual() == 16 ? 0 : 16);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME16);
					}
					break;
					
				case 30016:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR17;
							itemId = ConfigDev.VISUAL_CHEST17;
							itemId = ConfigDev.VISUAL_LEGS17;
							itemId = ConfigDev.VISUAL_GLOVES17;
							itemId = ConfigDev.VISUAL_BOOTS17;
							player.setVisual(player.getVisual() == 17 ? 0 : 17);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME17);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR17;
						itemId = ConfigDev.VISUAL_CHEST17;
						itemId = ConfigDev.VISUAL_LEGS17;
						itemId = ConfigDev.VISUAL_GLOVES17;
						itemId = ConfigDev.VISUAL_BOOTS17;
						player.setVisual(player.getVisual() == 17 ? 0 : 17);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME17);
					}
					break;
					
				case 30017:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR18;
							itemId = ConfigDev.VISUAL_CHEST18;
							itemId = ConfigDev.VISUAL_LEGS18;
							itemId = ConfigDev.VISUAL_GLOVES18;
							itemId = ConfigDev.VISUAL_BOOTS18;
							player.setVisual(player.getVisual() == 18 ? 0 : 18);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME18);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR18;
						itemId = ConfigDev.VISUAL_CHEST18;
						itemId = ConfigDev.VISUAL_LEGS18;
						itemId = ConfigDev.VISUAL_GLOVES18;
						itemId = ConfigDev.VISUAL_BOOTS18;
						player.setVisual(player.getVisual() == 18 ? 0 : 18);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME18);
					}
					break;
					
				case 30018:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR19;
							itemId = ConfigDev.VISUAL_CHEST19;
							itemId = ConfigDev.VISUAL_LEGS19;
							itemId = ConfigDev.VISUAL_GLOVES19;
							itemId = ConfigDev.VISUAL_BOOTS19;
							player.setVisual(player.getVisual() == 19 ? 0 : 19);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME19);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR19;
						itemId = ConfigDev.VISUAL_CHEST19;
						itemId = ConfigDev.VISUAL_LEGS19;
						itemId = ConfigDev.VISUAL_GLOVES19;
						itemId = ConfigDev.VISUAL_BOOTS19;
						player.setVisual(player.getVisual() == 19 ? 0 : 19);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME19);
					}
					break;
				case 30019:
					if (ConfigDev.ENABLE_VISUAL_EFFECT)
					{
						L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
						if (skill != null)
						{
							MagicSkillUse MSU = new MagicSkillUse(player, player, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
							player.sendPacket(MSU);
							player.broadcastPacket(MSU);
							player.useMagic(skill, false, false);
							itemId = ConfigDev.VISUAL_HAIR20;
							itemId = ConfigDev.VISUAL_CHEST20;
							itemId = ConfigDev.VISUAL_LEGS20;
							itemId = ConfigDev.VISUAL_GLOVES20;
							itemId = ConfigDev.VISUAL_BOOTS20;
							player.setVisual(player.getVisual() == 20 ? 0 : 20);
							playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME20);
							playable.broadcastPacket(new SocialAction(playable, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						}
					}else{
						itemId = ConfigDev.VISUAL_HAIR20;
						itemId = ConfigDev.VISUAL_CHEST20;
						itemId = ConfigDev.VISUAL_LEGS20;
						itemId = ConfigDev.VISUAL_GLOVES20;
						itemId = ConfigDev.VISUAL_BOOTS20;
						player.setVisual(player.getVisual() == 20 ? 0 : 20);
						playable.sendMessage("You have activated " + ConfigDev.VISUAL_NAME20);
					}
					break;
			}
		}
		else
			playable.sendMessage("Sorry, admin has disabled skins.");
	}
	
}
