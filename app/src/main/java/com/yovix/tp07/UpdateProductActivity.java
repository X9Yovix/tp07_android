package com.yovix.tp07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateProductActivity extends Activity {
    private DBHandler dbHandler;

    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.update_layout);

        EditText ref_input_update = findViewById(R.id.ref_input_update);
        EditText design_input_update = findViewById(R.id.design_input_update);
        EditText prix_input_update = findViewById(R.id.prix_input_update);
        EditText qty_input_update = findViewById(R.id.qty_input_update);
        Button btn_annuler = findViewById(R.id.btn_annuler);
        Button btn_maj = findViewById(R.id.btn_maj);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        ArrayList<Product> result_intent = (ArrayList<Product>) bundle.getSerializable("content_intent");

        ref_input_update.setText(result_intent.get(0).getRef());
        design_input_update.setText(result_intent.get(0).getDesign());
        prix_input_update.setText(String.valueOf(result_intent.get(0).getPrix()));
        qty_input_update.setText(String.valueOf(result_intent.get(0).getQty()));

        ref_input_update.setEnabled(false);

        dbHandler = new DBHandler(UpdateProductActivity.this);

        btn_maj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ref = ref_input_update.getText().toString();
                String new_design = design_input_update.getText().toString();
                String new_prix = prix_input_update.getText().toString();
                String new_qty = qty_input_update.getText().toString();

                Product p = new Product(ref, new_design, Float.parseFloat(new_prix), Integer.parseInt(new_qty));
                dbHandler.updateProduct(p);

                Toast.makeText(getApplicationContext(), "le produit a été mis à jour", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ListProduitActivity.class);
                finish();
                DetailPage.fa.finish();
                startActivity(i);
            }
        });

        btn_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
