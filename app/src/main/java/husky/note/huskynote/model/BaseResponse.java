package husky.note.huskynote.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/10/18.
 */

public class BaseResponse
{
    @SerializedName("OK")
    boolean isOk;
    @SerializedName("Msg")
    public String msg;

    public boolean isOk()
    {
        return isOk;
    }

    public String getMsg()
    {
        return msg;
    }

    @Override
    public String toString()
    {
        return "BaseRespose{" + "isOk=" + isOk + ", msg='" +  msg + '\'' + '}';
    }
}
