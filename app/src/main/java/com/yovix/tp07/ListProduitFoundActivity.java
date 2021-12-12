package com.yovix.tp07;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ListProduitFoundActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Context myContext;
    //private ArrayList<Product> myCollection;

    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.product_list);

        DBHandler dbHandler = new DBHandler(ListProduitFoundActivity.this);
        //myCollection = dbHandler.listProduct();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        ArrayList<Product> result_intent = (ArrayList<Product>) bundle.getSerializable("founded_list");
        ListView myList = findViewById(R.id.mylist);
        ArrayAdapter<Product> itemsAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, result_intent);
        myList.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DetailPage.class);
                i.putExtra("ref", result_intent.get(position).getRef());
                i.putExtra("design", result_intent.get(position).getDesign());
                i.putExtra("prix", String.valueOf(result_intent.get(position).getPrix()));
                i.putExtra("qty", String.valueOf(result_intent.get(position).getQty()));
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
