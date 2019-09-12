package com.example.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
         "Pear", "Grape", "Strawberry", "Apple", "Banana", "Orange", "Watermelon",
         "Pear", "Grape", "Strawberry", "Apple"};

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this, android.R.layout.simple_list_item_1, data);

        initFruits();
        FruitAdapter fruitAdapter = new FruitAdapter(MainActivity.this,
                R.layout.fruit_item, fruitList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(fruitAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(),
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFruits() {
        for (int i = 0 ; i <5 ; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.bg_1);
            fruitList.add(apple);

            Fruit orange = new Fruit("Orange", R.drawable.bg_2);
            fruitList.add(orange);

            Fruit banana = new Fruit("Banana", R.drawable.bg_3);
            fruitList.add(banana);

            Fruit watermelon = new Fruit("Watermelon", R.drawable.bg_4);
            fruitList.add(watermelon);

            Fruit mango = new Fruit("Mango", R.drawable.bg_5);
            fruitList.add(mango);
        }

    }
}
