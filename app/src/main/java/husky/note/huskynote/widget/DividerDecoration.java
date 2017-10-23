package husky.note.huskynote.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/10/20.
 */

public class DividerDecoration extends RecyclerView.ItemDecoration
{
    private int mDividerSize;

    public DividerDecoration(int mDividerSize)
    {
        this.mDividerSize = mDividerSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // 返回itemView在adapter中的位置
        int top = position == 0 ? mDividerSize : 0;
        outRect.set(mDividerSize, top, mDividerSize, mDividerSize); // 设置四个方向的分割宽度
    }
}
