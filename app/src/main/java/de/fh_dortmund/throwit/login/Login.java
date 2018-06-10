package de.fh_dortmund.throwit.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }


    /**
     * Send Intent to Start Menu Activity.
     */
    private void launchMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

}