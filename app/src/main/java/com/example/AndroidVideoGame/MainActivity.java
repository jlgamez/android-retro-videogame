package com.example.AndroidVideoGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private String[] dificultiesJgh = {"Easy", "Normal", "Difficult"};
    public String GAME_LEVEL = "GAME_LEVEL";
    private Button playBtnJgh;
    private Spinner difficultySpinnerJgh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        difficultySpinnerJgh = (Spinner) findViewById(R.id.difficulty_spinner);
        playBtnJgh = (Button) findViewById(R.id.play_button);

        // rellenar el spinner de la dificultad con los niveles
        ArrayAdapter<String> adapterJgh = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                dificultiesJgh);
        difficultySpinnerJgh.setAdapter(adapterJgh);
    }

    public void startGame(View view) {
        // obtener nivel dificultad
        String chosenLevelJgh = String.valueOf(difficultySpinnerJgh.getSelectedItem());
        Intent intentJgh = new Intent(this, GameActivity.class);
        intentJgh.putExtra(GAME_LEVEL, chosenLevelJgh);
        // comenzar juego
        startActivity(intentJgh);
    }
}