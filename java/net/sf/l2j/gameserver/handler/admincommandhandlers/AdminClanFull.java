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
package net.sf.l2j.gameserver.handler.admincommandhandlers;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Player;

/**
 * @author Bluur
 *
 */
public class AdminClanFull implements IAdminCommandHandler
{
    private static final String[] commands = 
    {
    	"admin_clanfull"
    };

    private static final int reputation = 30000000;
    private static final byte level = 8;
    
    //id skills
    private static final int[] clanSkills =
    {
        370,
        371,
        372,
        373,
        374,
        375,
        376,
        377,
        378,
        379,
        380,
        381,
        382,
        383,
        384,
        385,
        386,
        387,
        388,
        389,
        390,
        391
    };
    
    @Override
    public boolean useAdminCommand(String command, Player activeChar)
    {
        L2Object target = activeChar.getTarget();
        Player player = null;
        
        if (target != null && target instanceof Player)
            player = (Player) target;
        else
            return false;
            
        if (player.isClanLeader())
        {
             player.getClan().changeLevel(level);    
             player.getClan().addReputationScore(reputation);
           
             for (int s : clanSkills)
             {
                 L2Skill clanSkill = SkillTable.getInstance().getInfo(s, SkillTable.getInstance().getMaxLevel(s));
                 player.getClan().addNewSkill(clanSkill);
             }
         
             player.getClan().updateClanInDB();
             player.sendSkillList();
             player.sendMessage("Your clan Level/Skills/Reputation updated by GM!");        
             activeChar.sendMessage("Clan successfully updated.");
        }
        else
            activeChar.sendMessage("The player is not the leader of the clan.");
          
        return true;
    }

    @Override
    public String[] getAdminCommandList()
    {
        return commands;
    }
}
