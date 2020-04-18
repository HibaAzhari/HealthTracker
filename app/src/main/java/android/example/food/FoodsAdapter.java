package android.example.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.FoodsAdapterViewHolder> {

    private List<String> foodData;

    private final FoodsAdapterOnLongClickHandler longClickHandler;

    public interface FoodsAdapterOnLongClickHandler {
        void onLongClick(int deletedFoodId);
    }




    public FoodsAdapter(FoodsAdapterOnLongClickHandler longClickHandler){
        this.longClickHandler = longClickHandler;
    }

    public class FoodsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public final TextView foodData_tv;

        public FoodsAdapterViewHolder(View view){
            super(view);
            foodData_tv = view.findViewById(R.id.tv_food_data);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            longClickHandler.onLongClick(getAdapterPosition());
            return true;
        }
    }

    @Override
    public FoodsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.foods_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachToParent);
        return new FoodsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodsAdapterViewHolder foodsAdapterViewholder, int position) {
        String thisFoodData = foodData.get(position);
        foodsAdapterViewholder.foodData_tv.setText(thisFoodData);
    }

    @Override
    public int getItemCount() {
        if(foodData == null) return 0;
        return foodData.size();
    }

    public String getItem(int id) {
        return foodData.get(id);
    }

    public void setFoodData(List<String> foodData) {
        this.foodData = foodData;
        notifyDataSetChanged();
    }

    public void addFoodItem(String label, int calories){
        foodData.add(label + "\nCalories: " + calories);
        notifyDataSetChanged();
    }

    public void removeFoodItem(int itemId){
        foodData.remove(itemId);
        notifyDataSetChanged();
    }
}