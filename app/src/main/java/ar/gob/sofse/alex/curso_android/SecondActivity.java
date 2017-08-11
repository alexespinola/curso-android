package ar.gob.sofse.alex.curso_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView =  (TextView) findViewById(R.id.textViewMain);
        btnNext = (Button) findViewById(R.id.buttonGoSharing);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String greeter = bundle.getString("greeter");
            Toast.makeText(SecondActivity.this, greeter, Toast.LENGTH_SHORT).show();
            textView.setText(greeter);
        }else{
            Toast.makeText(SecondActivity.this, "No llego el mensaje", Toast.LENGTH_SHORT).show();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });


    }
}
