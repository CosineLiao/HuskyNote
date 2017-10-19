package husky.note.huskynote;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Administrator on 2017/10/18.
 */

public class HuskyApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        // 有两个init()方法，参数不相同
        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.init(getApplicationContext());
    }
}
