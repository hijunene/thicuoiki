package tin.ngolamquang.thicuoiky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FoodItems extends BaseAdapter {
    Context context;
    List<Food> foods;

    public FoodItems(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_monan, null);

        TextView txtId = convertView.findViewById(R.id.txtId);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtType = convertView.findViewById(R.id.txtType);

        Food food = (Food) getItem(position);

        txtId.setText(food.getId() + "");
        txtName.setText(food.getName());
        txtType.setText(food.getType());


        return convertView;
    }
}
