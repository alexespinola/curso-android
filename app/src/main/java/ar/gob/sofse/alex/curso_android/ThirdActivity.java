package ar.gob.sofse.alex.curso_android;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText phone;
    private EditText web;
    private ImageButton phoneButton;
    private ImageButton webButton;
    private Button camara;
    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Activar flecha de regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone       = (EditText) findViewById(R.id.phone);
        phoneButton = (ImageButton) findViewById(R.id.phoneButton);
        web         = (EditText) findViewById(R.id.web);
        webButton   = (ImageButton) findViewById(R.id.webButton);
        camara      = (Button) findViewById(R.id.camara);

        //boton para la llamada
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phone.getText().toString();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    //comprobar version actual de android
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // COMPROBAR SI HA HACEPTADO  O NUNCA SE LE HA PREGUNTADO
                        if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                            //ha haceptado
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(i);
                        }else{
                            //no ha haceptado o es la primera vez que se le pregunta
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                // no se le pregunto aun
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                            }else{
                                // ha denegado el permiso
                                Toast.makeText(ThirdActivity.this, "Please enable the permission", Toast.LENGTH_LONG).show();
                                // redirige al usuario a los permisos de la APP
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }
                    } else {
                        OlderVersions(phoneNumber);
                    }
                }else{
                    Toast.makeText(ThirdActivity.this, "Insert a phone number", Toast.LENGTH_LONG).show();
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You declined the access", Toast.LENGTH_LONG).show();
                }

            }


        });

        //boton para la direccion web
        webButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            String url = web.getText().toString();
            if (url != null && !url.isEmpty()) {
                //intent WEB
                Intent intentWeb = new Intent();
                intentWeb.setAction(Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse("http://" + url));
                //startActivity(intentWeb);

                // intent CONTACTOS
                Intent intentContact = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                //startActivity(intentContact);

                //intent E-MAIL
                Intent intentMailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:alexaespinola@gmail.com"));
                //startActivity(intentMailTo);

                //intent E-MAIL COMPLETO
                Intent intentMailCompleto = new Intent(Intent.ACTION_SEND, Uri.parse("alexaespinola@gmail.com"));
                intentMailCompleto.setType("plain/text");
                intentMailCompleto.putExtra(Intent.EXTRA_SUBJECT, "Mail title");
                intentMailCompleto.putExtra(Intent.EXTRA_TEXT, "Este el el cuerpo del mail");
                intentMailCompleto.putExtra(Intent.EXTRA_EMAIL, new String[] {"mail@softwareadvanced.com", "alexaespinola@gmail.com"});
                startActivity(Intent.createChooser(intentMailCompleto, "Elige cliente de correo"));
            }
            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent CAMARA
                Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamara, PICTURE_FROM_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PICTURE_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    // aqui podremos procesar la imagen
                    Toast.makeText(this, "Result:"+result, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "There was an error with the puicture", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result = grantResults[0];
                // comprobar si fue aceptada la peticion de permiso
                if (permission.equals(Manifest.permission.CALL_PHONE))
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //Permiso Concedido
                        String phoneNumbre = phone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumbre));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intentCall);

                    } else {
                        Toast.makeText(ThirdActivity.this, "Permiso denegado por el usuario", Toast.LENGTH_SHORT).show();
                    }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }


    private boolean CheckPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
