package com.grottworkshop.gwsbaselibrary.widget.swipelayout;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.List;

/**
 *
 *
 * Created by fgrott on 10/7/2014.
 */
public abstract class CursorSwipeAdapter extends CursorAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {

    private SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    /**
     * Instantiates a new Cursor swipe adapter.
     *
     * @param context the context
     * @param c the c
     * @param autoRequery the auto requery
     */
    protected CursorSwipeAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    /**
     * Instantiates a new Cursor swipe adapter.
     *
     * @param context the context
     * @param c the c
     * @param flags the flags
     */
    protected CursorSwipeAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean convertViewIsNull = convertView == null;
        View v = super.getView(position, convertView, parent);
        if(convertViewIsNull){
            mItemManger.initialize(v, position);
        }else{
            mItemManger.updateConvertView(v, position);
        }
        return v;
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public SwipeItemMangerImpl.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(SwipeItemMangerImpl.Mode mode) {
        mItemManger.setMode(mode);
    }
}
