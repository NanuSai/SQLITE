package com.saiproject.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtcomputerName,edtcomputerType;
    Button btnAdd,btnDelete;
    ListView listView;




    List<Computer> allComputers;
    ArrayList<String> computerNames;

    MySQLiteHandler dbHandler;
    ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtcomputerName = findViewById(R.id.edtComputerName);
        edtcomputerType = findViewById(R.id.edtComputerType);

        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);

        listView = findViewById(R.id.listView);


        dbHandler = new MySQLiteHandler(this);
        allComputers = dbHandler.getAllComputers();             //Since no data has been poured, it just initialized it
        computerNames = new ArrayList<>();



        //Put data in ComputerNames given it's not empty

        if(allComputers.size() > 0) {

                for(int i = 0 ; i < allComputers.size() ; i++){

                    Computer computer = allComputers.get(i);
                    computerNames.add(computer.getComputerName() + " - "+computer.getComputerType());
            }
        }



        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,computerNames);
        listView.setAdapter(adapter);




        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){



            case R.id.btnAdd:
                String compName = edtcomputerName.getText().toString();
                String compType = edtcomputerType.getText().toString();
                Computer computer = new Computer(compName,compType);

                if(compName.length() == 0 || compType.length() == 0)
                    return;

                allComputers.add(computer); //Add computer in computer list
                dbHandler.addComputer(computer); //Add computer in the Handler Class
                computerNames.add(computer.getComputerName() + " - "+ computer.getComputerType()); // Update values
                edtcomputerType.setText("");
                edtcomputerName.setText("");
                break;



            case R.id.btnDelete:

                if(allComputers.isEmpty())
                    return;

                computerNames.remove(0); //Remove first element
                dbHandler.deleteComputer(allComputers.get(0)); //Delete the computer in the handler class (database)
                allComputers.remove(0); //Remove from our context
                break;

        }


            adapter.notifyDataSetChanged(); // Update the adapter
    }




}
