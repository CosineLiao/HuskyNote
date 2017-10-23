package husky.note.huskynote.model;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SyncEvent
{
    private boolean isSucceed;

    public SyncEvent(boolean isSucceed)
    {
        this.isSucceed = isSucceed;
    }

    public boolean isSucceed()
    {
        return isSucceed;
    }
}
