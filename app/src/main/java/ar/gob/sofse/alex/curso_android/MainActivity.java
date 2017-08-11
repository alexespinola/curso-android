package ar.gob.sofse.alex.curso_android;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private View btn;
    private final String GREETER = "Hello from the other side";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.botonMain);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // access to segundo activity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("greeter", GREETER);
                startActivity(intent);
            }
        });
    }

}
