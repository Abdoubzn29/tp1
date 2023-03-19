package com.example.tp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Purchase> ListAchat;



    private static EditText itemEditText;
    private EditText qtyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListAchat = new ArrayList<>();
        ListAchat.add(new Purchase("10 kg Farine", 10.0));
        ListAchat.add(new Purchase("10 L Huile", 10.0));
        ListAchat.add(new Purchase("10 kg Tomate", 10.0));
        ListAchat.add(new Purchase("10 L Eau", 10.0));
        ListAchat.add(new Purchase("100 g Poivre Noir", 100.0));
        ListAchat.add(new Purchase("10 Levures", 10.0));

        ListView MyAchatsView = findViewById(R.id.List_Achat);
        MyAdapter adapter = new MyAdapter(this, ListAchat);
        MyAchatsView.setAdapter(adapter);

        itemEditText = findViewById(R.id.MyText1);
        qtyEditText = findViewById(R.id.MyText2);

        Button addButton = findViewById(R.id.b1);
        addButton.setOnClickListener(v -> {
            String item = itemEditText.getText().toString();
            String qty = qtyEditText.getText().toString();
            ListAchat.add(new Purchase(item, Double.parseDouble(qty)));
            adapter.notifyDataSetChanged();
            itemEditText.getText().clear();
            qtyEditText.getText().clear();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public class MyAdapter extends ArrayAdapter<Purchase> {

        private final Activity context;
        private final ArrayList<Purchase> listAchats;

        public MyAdapter(Activity context, ArrayList<Purchase> listAchats) {
            super(context, R.layout.my_line, listAchats);
            this.context = context;
            this.listAchats = listAchats;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.my_line, null);
            }
            TextView itemName = convertView.findViewById(R.id.item_name);
            TextView itemQte = convertView.findViewById(R.id.item_qty);
            itemName.setText(listAchats.get(position).getItem());
            itemQte.setText(String.valueOf(listAchats.get(position).getQty()));

            Button deleteButton = convertView.findViewById(R.id.delete_button);
            deleteButton.setTag(position);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int position = (Integer) v.getTag();

                    // Remove the item from the listAchats
                    listAchats.remove(position);

                    // Notify the adapter that the data set has changed
                    notifyDataSetChanged();
                }

            });

            Button editButton = convertView.findViewById(R.id.edit_button);
            editButton.setTag(position);
            editButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    int position = (Integer) v.getTag();

                    // Get the purchase at the selected position
                    Purchase selectedPurchase = listAchats.get(position);

                    // Create a dialog to edit the purchase
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Edit Purchase");

                    // Inflate the layout for the dialog and get the EditText views
                    LayoutInflater inflater = context.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.edit_purchease, null);
                    EditText itemNameEdit = dialogView.findViewById(R.id.item_name_edit);
                    EditText itemQteEdit = dialogView.findViewById(R.id.item_qty_edit);

                    // Set the EditText views to the selected purchase values
                    itemNameEdit.setText(selectedPurchase.getItem());
                    itemQteEdit.setText(String.valueOf(selectedPurchase.getQty()));

                    builder.setView(dialogView);
                    builder.setPositiveButton("Save", (dialog, which) -> {
                        // Get the edited values from the EditText views
                        String editedItem = itemNameEdit.getText().toString();
                        Double editedQty = Double.parseDouble(itemQteEdit.getText().toString());

                        // Update the selected purchase with the edited values
                        selectedPurchase.setItem(editedItem);
                        selectedPurchase.setQty(editedQty);

                        // Notify the adapter that the data set has changed
                        notifyDataSetChanged();
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    builder.show();
                }
            });

            return convertView;
        }

    }
}









