package husky.note.huskynote.utils;

import android.content.Context;
import android.widget.Toast;

import husky.note.huskynote.R;

/**
 * Created by Administrator on 2017/10/18.
 */

public class ToastUtils
{
    public static void show(Context context, String showMsg)
    {
        Toast.makeText(context, showMsg, Toast.LENGTH_SHORT).show();
    }

    public static void showNetworkError(Context context)
    {
        show(context, context.getString(R.string.network_error));
    }
}
