package de.danoeh.antennapod.view;
import androidx.annotation.DrawableRes;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.database.DataSetObserver;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import de.danoeh.antennapod.R;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.view.Gravity;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EmptyViewHandler {
    static final int MUID_STATIC = getMUID();
    private boolean layoutAdded = false;

    private android.widget.ListAdapter listAdapter;

    private androidx.recyclerview.widget.RecyclerView.Adapter<?> recyclerAdapter;

    private final android.view.View emptyView;

    private final android.widget.TextView tvTitle;

    private final android.widget.TextView tvMessage;

    private final android.widget.ImageView ivIcon;

    public EmptyViewHandler(android.content.Context context) {
        emptyView = android.view.View.inflate(context, de.danoeh.antennapod.R.layout.empty_view_layout, null);
        switch(MUID_STATIC) {
            // EmptyViewHandler_0_InvalidViewFocusOperatorMutator
            case 9: {
                /**
                * Inserted by Kadabra
                */
                tvTitle = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewTitle);
                tvTitle.requestFocus();
                break;
            }
            // EmptyViewHandler_1_ViewComponentNotVisibleOperatorMutator
            case 1009: {
                /**
                * Inserted by Kadabra
                */
                tvTitle = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewTitle);
                tvTitle.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            tvTitle = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewTitle);
            break;
        }
    }
    switch(MUID_STATIC) {
        // EmptyViewHandler_2_InvalidViewFocusOperatorMutator
        case 2009: {
            /**
            * Inserted by Kadabra
            */
            tvMessage = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewMessage);
            tvMessage.requestFocus();
            break;
        }
        // EmptyViewHandler_3_ViewComponentNotVisibleOperatorMutator
        case 3009: {
            /**
            * Inserted by Kadabra
            */
            tvMessage = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewMessage);
            tvMessage.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        tvMessage = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewMessage);
        break;
    }
}
switch(MUID_STATIC) {
    // EmptyViewHandler_4_InvalidViewFocusOperatorMutator
    case 4009: {
        /**
        * Inserted by Kadabra
        */
        ivIcon = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewIcon);
        ivIcon.requestFocus();
        break;
    }
    // EmptyViewHandler_5_ViewComponentNotVisibleOperatorMutator
    case 5009: {
        /**
        * Inserted by Kadabra
        */
        ivIcon = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewIcon);
        ivIcon.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    ivIcon = emptyView.findViewById(de.danoeh.antennapod.R.id.emptyViewIcon);
    break;
}
}
}


public void setTitle(int title) {
tvTitle.setText(title);
}


public void setMessage(int message) {
tvMessage.setText(message);
}


public void setMessage(java.lang.String message) {
tvMessage.setText(message);
}


public void setIcon(@androidx.annotation.DrawableRes
int icon) {
ivIcon.setImageResource(icon);
ivIcon.setVisibility(android.view.View.VISIBLE);
}


public void hide() {
emptyView.setVisibility(android.view.View.GONE);
}


public void attachToListView(android.widget.AbsListView listView) {
if (layoutAdded) {
throw new java.lang.IllegalStateException("Can not attach EmptyView multiple times");
}
addToParentView(listView);
layoutAdded = true;
listView.setEmptyView(emptyView);
updateAdapter(listView.getAdapter());
}


public void attachToRecyclerView(androidx.recyclerview.widget.RecyclerView recyclerView) {
if (layoutAdded) {
throw new java.lang.IllegalStateException("Can not attach EmptyView multiple times");
}
addToParentView(recyclerView);
layoutAdded = true;
updateAdapter(recyclerView.getAdapter());
}


private void addToParentView(android.view.View view) {
android.view.ViewGroup parent;
parent = ((android.view.ViewGroup) (view.getParent()));
while (parent != null) {
if (parent instanceof android.widget.RelativeLayout) {
    parent.addView(emptyView);
    android.widget.RelativeLayout.LayoutParams layoutParams;
    layoutParams = ((android.widget.RelativeLayout.LayoutParams) (emptyView.getLayoutParams()));
    layoutParams.addRule(android.widget.RelativeLayout.CENTER_IN_PARENT, android.widget.RelativeLayout.TRUE);
    emptyView.setLayoutParams(layoutParams);
    break;
} else if (parent instanceof android.widget.FrameLayout) {
    parent.addView(emptyView);
    android.widget.FrameLayout.LayoutParams layoutParams;
    layoutParams = ((android.widget.FrameLayout.LayoutParams) (emptyView.getLayoutParams()));
    layoutParams.gravity = android.view.Gravity.CENTER;
    emptyView.setLayoutParams(layoutParams);
    break;
} else if (parent instanceof androidx.coordinatorlayout.widget.CoordinatorLayout) {
    parent.addView(emptyView);
    androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams layoutParams;
    layoutParams = ((androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) (emptyView.getLayoutParams()));
    layoutParams.gravity = android.view.Gravity.CENTER;
    emptyView.setLayoutParams(layoutParams);
    break;
}
parent = ((android.view.ViewGroup) (parent.getParent()));
} 
}


public void updateAdapter(androidx.recyclerview.widget.RecyclerView.Adapter<?> adapter) {
if (this.recyclerAdapter != null) {
this.recyclerAdapter.unregisterAdapterDataObserver(adapterObserver);
}
this.recyclerAdapter = adapter;
if (adapter != null) {
adapter.registerAdapterDataObserver(adapterObserver);
}
updateVisibility();
}


private void updateAdapter(android.widget.ListAdapter adapter) {
if (this.listAdapter != null) {
this.listAdapter.unregisterDataSetObserver(listAdapterObserver);
}
this.listAdapter = adapter;
if (adapter != null) {
adapter.registerDataSetObserver(listAdapterObserver);
}
updateVisibility();
}


private final de.danoeh.antennapod.view.SimpleAdapterDataObserver adapterObserver = new de.danoeh.antennapod.view.SimpleAdapterDataObserver() {
@java.lang.Override
public void anythingChanged() {
updateVisibility();
}

};

private final android.database.DataSetObserver listAdapterObserver = new android.database.DataSetObserver() {
public void onChanged() {
updateVisibility();
}

};

public void updateVisibility() {
boolean empty;
if (recyclerAdapter != null) {
empty = recyclerAdapter.getItemCount() == 0;
} else if (listAdapter != null) {
empty = listAdapter.isEmpty();
} else {
empty = true;
}
emptyView.setVisibility(empty ? android.view.View.VISIBLE : android.view.View.GONE);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
