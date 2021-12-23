package tin.ngolamquang.thicuoiky;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    FoodItems foodItems;
    ListView lvFood;
    EditText edtName, editType;
    List<Food> foods;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addControls();

        addEvents();
    }

    public void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food newFood = new Food();

                newFood.setType(editType.getText().toString());
                newFood.setName(edtName.getText().toString());

                addNewFood(newFood);
            }
        });

        lvFood.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alerDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có muốn xóa ?")
                        .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Food food = foods.get(position);

                                if(food != null){
                                    removeFood(food);
                                }
                            }
                        }).create();

                alerDialog.show();

                return true;
            }
        });
    }

    public void addControls() {
        edtName = findViewById(R.id.edtName);
        editType = findViewById(R.id.edtType);
        lvFood = findViewById(R.id.lvFood);
        edtName = findViewById(R.id.edtName);
        editType = findViewById(R.id.edtType);
        btnAdd = findViewById(R.id.btnAdd);

        lvFood.setAdapter(foodItems);
    }

    public boolean removeFood(Food food){
        String whereClause  = "ma = ?";
        String[] whereArgs = new String[] {String.valueOf(food.getId())};

        sqLiteDatabase.delete("monan", whereClause, whereArgs);

        foods.remove(food);
        foodItems.notifyDataSetChanged();

        return true;
    }

    public void init(){
        sqLiteDatabase = DBCongfigUtil.getDatabase();
        foods = getFood();

        foodItems = new FoodItems(MainActivity.this, foods);
    }

    public boolean addNewFood(Food newFood) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("tenmonan", newFood.getName());
        contentValues.put("loaimonan", newFood.getType());

        sqLiteDatabase.insert("monan", null, contentValues);

        foods.clear();
        foods.addAll(getFood());

        foodItems.notifyDataSetChanged();

        return true;
    }

    public List<Food> getFood(){
        List<Food> foods = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("monan", null, null,null, null,null,null);

        while (cursor.moveToNext()){
            Food newFood = new Food();

            newFood.setId(cursor.getInt(0));
            newFood.setName(cursor.getString(1));
            newFood.setType(cursor.getString(2));

            foods.add(newFood);
        }

        return foods;

    }


}