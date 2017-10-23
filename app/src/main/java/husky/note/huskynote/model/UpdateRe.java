package husky.note.huskynote.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/10/18.
 */

public class UpdateRe extends BaseResponse
{
    @SerializedName("Usn")
    int usn;

    public int getUsn()
    {
        return usn;
    }
}
