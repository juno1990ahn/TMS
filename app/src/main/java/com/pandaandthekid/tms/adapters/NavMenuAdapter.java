package com.pandaandthekid.tms.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandaandthekid.tms.INavigateActivity;
import com.pandaandthekid.tms.MemorizeActivity;
import com.pandaandthekid.tms.R;
import com.pandaandthekid.tms.TMSActivity;
import com.pandaandthekid.tms.menu.NavMenuItem;
import com.pandaandthekid.tms.verses.TMSBundle;

public class NavMenuAdapter extends RecyclerView.Adapter<NavMenuAdapter.ViewHolder> {

    private NavMenuItem[] menuData;
    private Context context;

    public NavMenuAdapter(Context context) {
        this.context = context;
        Resources resources = context.getResources();

        this.menuData = new NavMenuItem[]{
                new NavMenuItem(NavMenuItem.ACTIVITY_LINK, resources.getString(R.string.home), TMSActivity.class),
                new NavMenuItem(NavMenuItem.ACTIVITY_LINK, resources.getString(R.string.topic_a), MemorizeActivity.class, TMSBundle.A_PACK),
                new NavMenuItem(NavMenuItem.ACTIVITY_LINK, resources.getString(R.string.topic_b), MemorizeActivity.class, TMSBundle.B_PACK),
                new NavMenuItem(NavMenuItem.ACTIVITY_LINK, resources.getString(R.string.topic_c), MemorizeActivity.class, TMSBundle.C_PACK),
                new NavMenuItem(NavMenuItem.ACTIVITY_LINK, resources.getString(R.string.topic_d), MemorizeActivity.class, TMSBundle.D_PACK),
                new NavMenuItem(NavMenuItem.ACTIVITY_LINK, resources.getString(R.string.topic_e), MemorizeActivity.class, TMSBundle.E_PACK)
        };
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NavMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element
        final NavMenuItem menuItem = menuData[position];
        TextView itemLabel = ((TextView) holder.view.findViewById(R.id.menu_item_text));
        itemLabel.setText(menuItem.label);

        if (menuItem.type == NavMenuItem.ACTIVITY_LINK) {
            if (menuItem.activity.equals(TMSActivity.class)) {
                itemLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemLabel.getTextSize() * 1.3f);
            }
            if (context instanceof TMSActivity && menuItem.activity.equals(TMSActivity.class)) {
                itemLabel.setTextColor(ContextCompat.getColor(context, R.color.dark_fill_shape));
                itemLabel.setTypeface(null, Typeface.BOLD);
            } else if (context instanceof MemorizeActivity && menuItem.activity.equals(MemorizeActivity.class) && ((MemorizeActivity) context).getCurrentPack().equals(menuItem.metadata.get(NavMenuItem.PACK))) {
                itemLabel.setTextColor(ContextCompat.getColor(context, R.color.dark_fill_shape));
                itemLabel.setTypeface(null, Typeface.BOLD);
            } else {
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Intent intent = new Intent(context, menuItem.activity);
                        if (menuItem.metadata != null) {
                            intent.putExtra(TMSBundle.CHOSEN_PACK, (TMSBundle) menuItem.metadata.get(NavMenuItem.PACK));
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                context.startActivity(intent);
                            }
                        }, 250);

                        ((INavigateActivity) context).closeDrawer();
                    }
                });
            }
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return menuData.length;
    }

    @Override
    public int getItemViewType(int position) {
        return menuData[position].type;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
        }
    }
}
