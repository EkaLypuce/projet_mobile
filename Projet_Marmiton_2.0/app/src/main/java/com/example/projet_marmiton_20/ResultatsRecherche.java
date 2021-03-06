package com.example.projet_marmiton_20;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.koushikdutta.async.future.DoneCallback;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ResultatsRecherche extends AppCompatActivity {

    ListView liste;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats_recherche);
        liste = findViewById(R.id.listView);
        txt = findViewById(R.id.resultat);
        Intent i1 = getIntent();
        String nb = i1.getIntExtra("nombrePersonne",1)+"";
        String recherche = i1.getStringExtra("nomRecette")+" for "+nb+" servings";
        txt.setText(recherche);



        String nameRecette = i1.getStringExtra("nomRecette");
        nameRecette = nameRecette.replace(" ","_");
        String genreRecette = i1.getStringExtra("genreRecette");


        String uri = "https://api.spoonacular.com/recipes/search?apiKey=806171bec5aa4d00be74d2304fb7c6fb&query="+nameRecette+"&cuisine="+genreRecette+"&number=100";
        JsonObject json = new JsonObject();
        try {
            json = Ion.with(this)
                .load(uri)
                .asJsonObject().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Recette> recettes = new ArrayList<>();
        JsonArray array = json.getAsJsonArray("results");
        for(int i=0;i<array.size();i++){
            JsonObject j = array.get(i).getAsJsonObject();
            int z=1;
            if(j.get("image")==null) {
                    Recette re = new Recette(j.get("title").getAsString(),j.get("readyInMinutes").getAsInt(), j.get("id").toString(),i1.getIntExtra("nombrePersonne",z),j.get("servings").getAsInt());
                    recettes.add(re);
            }else{
                    Recette re = new Recette(j.get("title").getAsString(), j.get("image").getAsString(), j.get("readyInMinutes").getAsInt(), j.get("id").toString(),i1.getIntExtra("nombrePersonne",z),j.get("servings").getAsInt());
                    recettes.add(re);
            }

        }
        MyClassAdapter adapter = new MyClassAdapter(this,R.layout.activity_list_recette,recettes);
        liste.setAdapter(adapter);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1 = new Intent(ResultatsRecherche.this,Preparation.class);
                Recette re = (Recette) parent.getItemAtPosition(position);
                i1.putExtra("id",re.getId());
                i1.putExtra("image",re.getImageRecette());
                i1.putExtra("name",re.getNomRecette());
                i1.putExtra("time",re.getTempPreparation());
                i1.putExtra("nbpers",re.getnbpers());
                i1.putExtra("persrecette",re.getPersrecette());
                startActivity(i1);
            }
        });


    }


}















