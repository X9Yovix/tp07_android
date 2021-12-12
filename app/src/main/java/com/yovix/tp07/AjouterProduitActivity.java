package com.yovix.tp07;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjouterProduitActivity extends Activity {
    private EditText ref, design, prix, qty;
    private DBHandler dbHandler;

    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.add_layout);

        ref = findViewById(R.id.ref_input_update);
        design = findViewById(R.id.design_input_update);
        prix = findViewById(R.id.prix_input_update);
        qty = findViewById(R.id.qty_input_update);

        Button btn_enre = findViewById(R.id.btn_maj);
        Button btn_annuler = findViewById(R.id.btn_annuler);

        dbHandler = new DBHandler(AjouterProduitActivity.this);

        btn_enre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ref_str = ref.getText().toString();
                String design_str = design.getText().toString();
                float prix_float = Float.parseFloat(prix.getText().toString());
                float new_value_prix = (float) Math.round(prix_float * 100) / 100;
                int qty_int = Integer.parseInt(qty.getText().toString());
                if (ref_str.isEmpty() && design_str.isEmpty() && prix.getText().toString().isEmpty() && qty.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    dbHandler.insertProduct(ref_str, design_str, new_value_prix, qty_int);
                    ref.setText("");
                    design.setText("");
                    prix.setText("");
                    qty.setText("");
                    Toast.makeText(getApplicationContext(), "le produit a été ajouté", Toast.LENGTH_SHORT).show();
                }
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
