package com.yovix.tp07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_add = findViewById(R.id.btn_add);
        Button btn_list = findViewById(R.id.btn_list);
        Button btn_find = findViewById(R.id.btn_find);

        btn_add.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_find.setOnClickListener(this);

        dbHandler = new DBHandler(MainActivity.this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add: {
                Intent i1 = new Intent(this, AjouterProduitActivity.class);
                startActivity(i1);
                break;
            }
            case R.id.btn_list: {
                Intent i2 = new Intent(this, ListProduitActivity.class);
                startActivity(i2);
                break;
            }
            case R.id.btn_find: {
                EditText editT = new EditText(MainActivity.this);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Chercher Produit");
                alert.setIcon(android.R.drawable.ic_menu_search);
                alert.setMessage("Donner nom du produit");
                alert.setView(editT);
                alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String product_name = editT.getText().toString();

                        if (editT.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Input est vide", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<Product> values_product = dbHandler.findProduct(product_name);

                            if (values_product.size() == 0) {
                                Toast.makeText(getApplicationContext(), "Produit non trouvé", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent i = new Intent(getApplicationContext(), ListProduitFoundActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("founded_list", values_product);

                                i.putExtras(bundle);
                                startActivity(i);

                                /*
                                Intent i = new Intent(getApplicationContext(), DetailPage.class);
                                i.putExtra("ref", values_product.get(0).getRef());
                                i.putExtra("design", values_product.get(0).getDesign());
                                i.putExtra("prix", String.valueOf(values_product.get(0).getPrix()));
                                i.putExtra("qty", String.valueOf(values_product.get(0).getQty()));
                                startActivity(i);

                                 */
                            }
                        }
                    }
                });
                alert.setNegativeButton("Annuler", null);
                alert.show();
                break;
            }
            default:
                System.out.println("none");
        }
    }

}