package Dev.VoteSystem;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.model.actor.Player;

@VoteSiteInfo(voteSite = VoteSite.HOPZONE, apiKey = "HopzoneApiKey")
public class HopzoneVoteReward extends VoteRewardSite {

    @Override
    protected String getEndpoint(Player player) {
       
    	return String.format(Config.VOTE_HOPZONE, getApiKey(), player.getIpAddress());

    }

    @Override
    protected boolean hasVoted(Player player) {
        String serverResponse = VoteApiService.getApiResponse(getEndpoint(player));
        if(serverResponse.length() == 0){
            player.sendMessage("Something went wrong with this request. Report it to the administrator.");
            return false;
        }
        JsonElement jelement = new JsonParser().parse(serverResponse);
        JsonObject topObject = jelement.getAsJsonObject();
        JsonPrimitive votedObject = topObject.getAsJsonPrimitive("voted");
        return votedObject.getAsBoolean();
    }
}