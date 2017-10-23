package husky.note.huskynote.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SyncState extends BaseResponse
{
    @SerializedName("LaseSyncUsn")
    private int mLastSyncUsn;

    @SerializedName("LastSyncTime")
    private long mLastSyncTime;

    public int getLastSyncUsn()
    {
        return mLastSyncUsn;
    }

    public long getLastSyncTime()
    {
        return mLastSyncTime;
    }
}
