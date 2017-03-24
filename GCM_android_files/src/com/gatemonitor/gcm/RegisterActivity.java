package com.gatemonitor.gcm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	// UI elements
	EditText txtName;
	EditText txtEmail;
	EditText txtSenha;

	// Register button
	Button btnRegister;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// Get Global Controller Class object (see application tag in
		// AndroidManifest.xml)
		final Controller aController = (Controller) getApplicationContext();

		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtSenha = (EditText) findViewById(R.id.txtSenha);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		// txtName.setText("alexraulino");
		// txtEmail.setText("alex.alex.raulino@gmail.com");
		// txtSenha.setText("123");

		// Click event on Register button
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get data from EditText
				String name = txtName.getText().toString();
				String email = txtEmail.getText().toString();
				String senha = txtSenha.getText().toString();

				// Check if user filled the form
				if (name.trim().length() > 0 && email.trim().length() > 0) {

					// Launch Main Activity
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);

					// Registering user on our server
					// Sending registraiton details to MainActivity
					i.putExtra("name", name);
					i.putExtra("email", email);
					i.putExtra("senha", senha);
					i.putExtra("registered", false);
					startActivity(i);
					finish();
				} else {
					// user doen't filled that data
					aController.showAlertDialog(RegisterActivity.this,
							"Registration Error!", "Please enter your details",
							false);
				}
			}
		});		
	}

}
