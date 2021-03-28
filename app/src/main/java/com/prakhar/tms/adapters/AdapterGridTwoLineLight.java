package com.prakhar.tms.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.StorageReference;
import com.prakhar.tms.R;
import com.prakhar.tms.modals.BiddingVehicle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterGridTwoLineLight extends FirebaseRecyclerAdapter<BiddingVehicle, AdapterGridTwoLineLight.myViewholder> {


    List<StorageReference> BidImgs;

    public AdapterGridTwoLineLight(@NonNull FirebaseRecyclerOptions<BiddingVehicle> options) {
        super(options);
        Log.e("BidVehicleRecView", options.getSnapshots().toString());

//        BidImgs = bidImgs;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_two_line_light, parent, false);

        return new myViewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, int position, @NonNull BiddingVehicle model) {
        holder.Title.setText(model.getBiddingTitle());
        holder.Price.setText(model.getBiddingPrice().toString());

        Log.e("BidData", model.getBiddingTitle());
        Log.e("BidData", model.getBiddingPrice().toString());

        Picasso.get().load(R.drawable.image_6).into(holder.vehicleImg);
    }

    class myViewholder extends RecyclerView.ViewHolder
    {
         ImageView vehicleImg;
         TextView Title, Price;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            vehicleImg = (ImageView) itemView.findViewById(R.id.image);
            Title = (TextView) itemView.findViewById(R.id.name);
            Price = (TextView) itemView.findViewById(R.id.brief);

        }
    }
}

    /*private List<StorageReference> items = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, StorageReference obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterGridTwoLineLight(Context context, List<StorageReference> items, DatabaseReference myRef) {
        this.items = items;
        ctx = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        this.myRef = myRef;
    }

    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView brief;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            brief = (TextView) v.findViewById(R.id.brief);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_two_line_light, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        Picasso.get().load(String.valueOf(items.get(position))).into();
        Query baseQuery = firebaseDatabase.getReference().child("bid_vehicle_details").limitToLast(50);

        StorageReference obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.name.setText("Eicher T500");
            view.brief.setText("Bid Image");
            Tools.displayImageOriginal(ctx, view.image, items.get(position));
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public StorageReference getItem(int position) {
        return items.get(position);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }
*/
