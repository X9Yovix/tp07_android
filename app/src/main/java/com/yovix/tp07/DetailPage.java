package com.yovix.tp07;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class DetailPage extends AppCompatActivity {
    private ArrayList<Product> content_intent;
    private DBHandler dbHandler;

    @SuppressLint("StaticFieldLeak")
    public static Activity fa;

    protected void onCreate(Bundle instance) {
        fa = this;
        super.onCreate(instance);
        setContentView(R.layout.detail_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView placeholder_ref = findViewById(R.id.placeholder_ref);
        TextView placeholder_design = findViewById(R.id.placeholder_design);
        TextView placeholder_prix = findViewById(R.id.placeholder_prix);
        TextView placeholder_qty = findViewById(R.id.placeholder_qty);

        Intent intent = getIntent();
        String ref = intent.getStringExtra("ref");
        String design = intent.getStringExtra("design");
        String prix = intent.getStringExtra("prix");
        String qty = intent.getStringExtra("qty");

        placeholder_ref.setText(ref);
        placeholder_design.setText(design);
        placeholder_prix.setText(prix);
        placeholder_qty.setText(qty);

        Button btn_edit = findViewById(R.id.btn_edit);
        Button btn_delete = findViewById(R.id.btn_delete);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content_intent = new ArrayList<>();
                Product p = new Product(ref, design, Float.parseFloat(prix), Integer.parseInt(qty));
                content_intent.add(p);

                Intent i = new Intent(getApplicationContext(), UpdateProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("content_intent", content_intent);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        dbHandler = new DBHandler(DetailPage.this);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailPage.this);
                alert.setTitle("Attention! ");
                alert.setMessage("etes vous sur de vouloir supprimer ce produit?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteProduct(placeholder_ref.getText().toString());
                        Toast.makeText(getApplicationContext(), "le produit a été supprimé ", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent i = new Intent(getApplicationContext(), ListProduitActivity.class);
                        startActivity(i);
                    }
                });
                alert.setNegativeButton(android.R.string.no, null);
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
