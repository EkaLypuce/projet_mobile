package com.example.projet_marmiton_20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Recette extends AppCompatActivity {

    private String nomRecette;
    private String imageRecette;
    private int tempPreparation;

    public Recette(String titre, String img, int tps){
        this.nomRecette=titre;
        this.imageRecette=img;
        this.tempPreparation=tps;
    }

    public String getNomRecette() {
        return nomRecette;
    }

    public void setNomRecette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public String getImageRecette() {
        return imageRecette;
    }

    public void setImageRecette(String imageRecette) {
        this.imageRecette = imageRecette;
    }

    public int getTempPreparation() {
        return tempPreparation;
    }

    public void setTempPreparation(int tempPreparation) {
        this.tempPreparation = tempPreparation;
    }

    @Override
    public String toString() {
        return "Recette{" +
                "nomRecette='" + nomRecette + '\'' +
                ", imageRecette='" + imageRecette + '\'' +
                ", tempPreparation=" + tempPreparation +
                '}';
    }
}
