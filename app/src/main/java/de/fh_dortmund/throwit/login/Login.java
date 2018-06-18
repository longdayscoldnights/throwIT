package de.fh_dortmund.throwit.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.fh_dortmund.throwit.R;
import de.fh_dortmund.throwit.menu.Menu;

/**
 * @author Bijan Riesenberg
 */
public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = this.findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMenu();
            }
        });

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button blogin = (Button) findViewById(R.id.btn_login);
        final TextView registerLink = (TextView) findViewById(R.id.registerhere);


        registerLink.setOnClickListener(new View.OnClickListener(){ // Creating a listener which leads you from the start page to the register page
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(Login.this, RegisterActivity.class);
                Login.this.startActivity(registerIntent);
            }
        });
    }


    /**
     * Send Intent to Start Menu Activity.
     */
    private void launchMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

}