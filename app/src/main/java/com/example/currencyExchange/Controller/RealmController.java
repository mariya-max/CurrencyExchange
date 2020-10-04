package com.example.currencyExchange.Controller;

import android.content.Context;

import com.example.currencyExchange.Model.Valute;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmController {

    private Realm realm;

    public RealmController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void removeAll() {
        realm.beginTransaction();
        RealmResults<Valute> results = realm.where(Valute.class).findAll();
        results.clear();
        realm.commitTransaction();
    }

    public void saveAll(List<Valute> valuateRealms) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(valuateRealms);
        realm.commitTransaction();
    }

    public RealmResults<Valute> getAllValuate() {
        return realm.where(Valute.class).findAll();
    }

    public Valute getValuateById(String id) {
        return realm.where(Valute.class).equalTo("id", id).findFirst();
    }
}
