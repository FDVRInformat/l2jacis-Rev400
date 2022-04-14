package Dev.VoteSystem;

public enum VoteSite {

    HOPZONE("Hopzone"),
    TOPZONE("Topzone"),
    NETWORK("L2Network");

    private final String _name;

    VoteSite(String name)
    {
        _name = name;
    }

    public String getName(){
        return _name;
    }
}