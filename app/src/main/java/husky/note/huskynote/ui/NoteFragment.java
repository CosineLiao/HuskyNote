package husky.note.huskynote.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import husky.note.huskynote.R;

/**
 * Created by Administrator on 2017/10/19.
 */

public class NoteFragment extends Fragment
{
    private static final String TAG = "NoteFragment";

    private static NoteFragment noteFragment;
    @BindView(R.id.recycler_view)
    RecyclerView mNoteListView;

    Unbinder unbinder;

    private View contentView;

    public static NoteFragment getInstance()
    {
        if (noteFragment == null)
        {
            noteFragment = new NoteFragment();
        }

        return noteFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_view_type)
        {
            Log.d(TAG, "onOptionsItemSelected");
        }
        return super.onOptionsItemSelected(item);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        contentView = inflater.inflate(R.layout.fragment_note, container, false);
        unbinder = ButterKnife.bind(this, contentView);

        return contentView;
    }




    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
