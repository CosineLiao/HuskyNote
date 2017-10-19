package husky.note.huskynote.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/10/19.
 */

public class NoteList
{
    public static final int TYPE_SIMPLE = 0;
    public static final int TYPE_DETAIL = TYPE_SIMPLE + 1;


    private RecyclerView mNoteListView;

    public NoteList(Context context, View recyclerView)
    {
        this.mNoteListView = (RecyclerView) mNoteListView;
		
    }
}
