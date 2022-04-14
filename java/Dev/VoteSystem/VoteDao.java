package Dev.VoteSystem;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoteDao {

    public static final Logger LOGGER = Logger.getLogger(VoteDao.class.getName());

    public static void addVotedRecord(VotedRecord votedRecord) {
        try (Connection con = L2DatabaseFactory.getInstance().getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO VoteSystem (accountname, ipaddress, datetimevoted, votesitename) VALUES (?,?,?,?);");
            ps.setString(1, votedRecord.getAccountName());
            ps.setString(2, votedRecord.getIpAddress());
            ps.setLong(3, votedRecord.getDateTimeVoted());
            ps.setString(4, votedRecord.getVoteSiteName());
            ps.execute();
            ps.close();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Couldn't add voted record", e);
        }
    }

    public static boolean canVoteOnSite(Player player, VoteSite voteSite) {
        try (Connection con = L2DatabaseFactory.getInstance().getConnection())
        {
            PreparedStatement ps = con.prepareStatement("select datetimevoted from VoteSystem " +
                    "where (accountname = ? or ipaddress = ?) and votesitename = ? and datetimevoted + ? > ? " +
                    "order by datetimevoted " +
                    "desc limit 1");

            ps.setString(1, player.getAccountName());
            ps.setString(2, player.getIpAddress());
            ps.setString(3, voteSite.getName());
            ps.setLong(4, 43200000);
            ps.setLong(5, System.currentTimeMillis());
            ResultSet rs = ps.executeQuery();

            if (!rs.next())
            {
                return true;
            }
            player.setLastVotedTimestamp(voteSite, rs.getLong("datetimevoted"));
            rs.close();
            ps.close();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Couldn't check if user can vote", e);
        }

        return false;
    }
}