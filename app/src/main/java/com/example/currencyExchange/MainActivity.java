package com.example.currencyExchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.currencyExchange.Api.Api;
import com.example.currencyExchange.Controller.RealmController;
import com.example.currencyExchange.Model.Valute;

import java.util.ArrayList;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textValuate;
    private TextView result;
    private Button buttonCalculate;
    private Button buttonUpdate;
    private RealmController controller;
    private ConvertResponseToList convertResponseToList;
    private AdapterValuate adapter;
    private RealmResults<Valute> valuateResults;
    private Valute valute;
    private String nameValuate;
    private double converter;
    private Integer value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getSavedData(savedInstanceState);
        getValuateFromDb();
        initRecyclerView();

        if (valuateResults == null ||valuateResults.size()<1) {
            sendRequest();
        }
        buttonCalculateClick();
        buttonUpdateClick();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("idValue", valute.getId());
        outState.putDouble("converter", converter);
        super.onSaveInstanceState(outState);
    }

    private void init() {
        editText = findViewById(R.id.editText);
        textValuate = findViewById(R.id.textValuta);
        result = findViewById(R.id.result);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonUpdate = findViewById(R.id.buttonResult);
        controller = new RealmController(this);
        convertResponseToList = new ConvertResponseToList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new AdapterValuate(valuateResults);
        adapter.setOnClickListener(new AdapterValuate.ClickListener() {
            @Override
            public void onItemClick(View view, String id) {
                valute = controller.getValuateById(id);
                nameValuate = valute.getCharCode() + " / " + valute.getName();
                textValuate.setText(nameValuate);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void getSavedData(Bundle savedInstanceState) {
        String idValue = null;
        double results = 0;

        if (savedInstanceState != null) {
            idValue = savedInstanceState.getString("idValue");
            valute= controller.getValuateById(idValue);

            results = savedInstanceState.getDouble("converter");
        }

        if (valute != null) {
            nameValuate = valute.getCharCode() + " / " + valute.getName();
            textValuate.setText(nameValuate);
        }
        if (results != 0) {
            result.setText(String.valueOf(results));
        } else {
            result.setText("Выберите валюту");
        }
    }

    public void sendRequest() {
        Api.getDataApi().getValuateList().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() == null) return;
                ArrayList<Valute> valuateList; valuateList = convertResponseToList.convert(response.body());
                refreshDb(valuateList);
                getValuateFromDb();
                adapter.updateList(valuateResults);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void refreshDb(ArrayList<Valute> valuateList) {
        controller.removeAll();
        controller.saveAll(valuateList);
    }

    public void getValuateFromDb() {
        valuateResults = controller.getAllValuate();
    }

    public void buttonCalculateClick() {
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameValuate != null) {
                    value = Integer.valueOf(editText.getText().toString());
                    converter = (double) Math.round((value / valute.getValue() * valute.getNominal()) * 100000d) / 100000d;
                    result.setText(String.valueOf(converter));
                } else {
                    result.setText("Выберите валюту");
                }
            }
        });
    }

    public void buttonUpdateClick() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }
}