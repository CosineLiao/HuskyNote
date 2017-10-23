package husky.note.huskynote.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;

/**
 * Created by Administrator on 2017/10/18.
 */

public class User extends BaseResponse
{
    @SerializedName("UserId")
    String userId = "";
    @SerializedName("Username")
    String userName = "";
    @Column(name = "email")
    @SerializedName("Email")
    String email = "";
    @SerializedName("Verified")
    boolean isVerified;
    @SerializedName("Logo")
    String avatar = "";

    public String getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getEmail()
    {
        return email;
    }

    public boolean isVerified()
    {
        return isVerified;
    }

    public String getAvatar()
    {
        Account current = Account.getCurrent();
        if (current == null)
        {
            return null;
        }
        String host = current.getHost();
        if (host.equals("https://leanote.com"))
        {
            return avatar;
        }
        else
        {
            return host + avatar;
        }
    }
}
