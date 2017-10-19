package husky.note.huskynote.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Administrator on 2017/10/18.
 */

@Database(name = AppDataBase.DB_NAME, version = AppDataBase.VERSION)
public class AppDataBase
{
    public static final String DB_NAME = "Husky_db";
    public static final int VERSION = 1;
}
