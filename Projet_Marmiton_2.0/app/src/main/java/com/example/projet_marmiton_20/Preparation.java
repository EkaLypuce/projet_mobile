package com.example.projet_marmiton_20;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Preparation extends AppCompatActivity {

    ImageView image;
    TextView name;
    TextView temps;
    TextView preparation;
    TextView ingred;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette);
        image = findViewById(R.id.imageRecette);
        name = findViewById(R.id.nomRecette);
        temps = findViewById(R.id.tempPreparation);
        preparation = findViewById(R.id.recette);
        ingred = findViewById(R.id.recette2);
        ingred.setMovementMethod(new ScrollingMovementMethod());
        preparation.setMovementMethod(new ScrollingMovementMethod());
        Picasso.get().load("https://spoonacular.com/recipeImages/" + getIntent().getStringExtra("image")).into(image);
        name.setText(getIntent().getStringExtra("name"));
        int a = 0;
        String tps = getIntent().getIntExtra("time", a) + "min, for "+getIntent().getIntExtra("nbpers",1)+" servings";
        temps.setText(tps);
        JsonArray json = new JsonArray();
        try {
            json = Ion.with(this)
                    .load("https://api.spoonacular.com/recipes/" + getIntent().getStringExtra("id") + "/analyzedInstructions?apiKey=806171bec5aa4d00be74d2304fb7c6fb")
                    .asJsonArray().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < json.size(); i++) {
            JsonObject js = (JsonObject) json.get(i);
            String name = js.get("name").toString();
            name = name.replaceAll("\"","");
            preparation.append(name + "\n" + "\n");
            JsonArray ar = (JsonArray) js.get("steps");
            JsonObject step = (JsonObject) ar.get(i);
            String stepstring = step.get("step").toString();
            stepstring = stepstring.replaceAll("\"","");
            preparation.append(stepstring + "\n" + "\n");
        }


    JsonObject ingredients = new JsonObject();
    try {
        ingredients = Ion.with(this)
                .load("https://api.spoonacular.com/recipes/"+getIntent().getStringExtra("id")+"/ingredientWidget.json?apiKey=806171bec5aa4d00be74d2304fb7c6fb")
                .asJsonObject().get();
    } catch(
    ExecutionException ex) {
        ex.printStackTrace();
    } catch(InterruptedException ex) {
        ex.printStackTrace();
    }
    JsonArray ar = (JsonArray) ingredients.get("ingredients");
    for(int k=0;k<ar.size();k++){
        JsonObject js = (JsonObject) ar.get(k);
        JsonObject j = (JsonObject) js.get("amount");
        JsonObject s = (JsonObject) j.get("metric");
        DecimalFormat dt = new DecimalFormat("0.00");
        double value = s.get("value").getAsDouble();
        double nbpers = getIntent().getIntExtra("nbpers",1);
        double persrecette = getIntent().getIntExtra("persrecette",1);
        double quantity = (value*nbpers)/persrecette;
        String name = js.get("name").toString();
        name = name.replaceAll( "\"" ,"");
        String unit = s.get("unit").toString();
        unit = unit.replaceAll("\"","");
        ingred.append(name+" : " + dt.format(quantity) +" "+unit+"\n");
    }

}





    }


