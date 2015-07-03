package com.gatemonitor.gcm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gatemonitor.db.DBGateMonitor;
import com.gatemonitor.db.tabelas.RegistroConfiguracao;
import com.gatemonitor.db.tabelas.TabelaConfiguracoes;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {
	// label to display gcm messages
	// TextView lblMessage;
	Controller aController;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	public static String name;
	public static String email;
	public static String senha;
	public static boolean registered;
	public static String regId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get Global Controller Class object (see application tag in
		// AndroidManifest.xml)
		aController = (Controller) getApplicationContext();

		// Check if Internet present
		if (!aController.isConnectingToInternet()) {

			// Internet Connection is not present
			aController.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			return;
		}

		DBGateMonitor.setContext(getApplicationContext());
		DBGateMonitor db = DBGateMonitor.getInstanceDB();
		Intent i = getIntent();
		name = i.getStringExtra("name");
		email = i.getStringExtra("email");
		senha = i.getStringExtra("senha");
		registered = i.getBooleanExtra("registered", false);
		regId = i.getStringExtra("regId");

		if ((name == null) || (email == null) || (senha == null)) {

			if (db.getTabelaConfiguracoes().registros.isEmpty()) {
				Intent x = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(x);
				finish();
				return;
			} else {
				RegistroConfiguracao regi = (RegistroConfiguracao) db
						.getTabelaConfiguracoes().registros.values().toArray()[0];
				name = regi.getNameUser();
				email = regi.getEmailUser();
				senha = regi.getPasswordUser();
				GCMRegistrar.checkDevice(this);
				// Make sure the manifest permissions was properly set
				GCMRegistrar.checkManifest(this);

				// Register custom Broadcast receiver to show messages on
				// activity
				registerReceiver(mHandleMessageReceiver, new IntentFilter(
						Config.DISPLAY_MESSAGE_ACTION));
				// Get GCM registration id
				regId = GCMRegistrar.getRegistrationId(this);
				registered = true;
			}

		}

		if (registered) {

		} else {
			// Getting name, email from intent

			// Make sure the device has the proper dependencies.
			GCMRegistrar.checkDevice(this);
			// Make sure the manifest permissions was properly set
			GCMRegistrar.checkManifest(this);

			// Register custom Broadcast receiver to show messages on activity
			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					Config.DISPLAY_MESSAGE_ACTION));
			// Get GCM registration id
			regId = GCMRegistrar.getRegistrationId(this);
			// Check if regid already presents
			if (regId.equals("")) {

				// Register with GCM
				GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);

			} else {

				// Device is already registered on GCM Server
				if (GCMRegistrar.isRegisteredOnServer(this)) {

					TabelaConfiguracoes tbConfiguracoes = DBGateMonitor
							.getInstanceDB().getTabelaConfiguracoes();
					RegistroConfiguracao regi = new RegistroConfiguracao(1,
							name, email, senha, "0");
					tbConfiguracoes.limparTabela();
					tbConfiguracoes.inserirRegistro(regi);

					// Skips registration.
					Toast.makeText(getApplicationContext(),
							"Already registered with GCM Server",
							Toast.LENGTH_LONG).show();

				} else {

					final Context context = this;
					mRegisterTask = new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {

							// Register on our server
							// On server creates a new user
							aController.register(context, name, email, regId,
									senha);
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mRegisterTask = null;
						}
					};

					// execute AsyncTask
					mRegisterTask.execute(null, null, null);
				}
			}
		}
	}

	// Create a broadcast receiver to get message and show on screen
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String newMessage = intent.getExtras().getString(
					Config.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());

			// Display message on the screen
			// lblMessage.append(newMessage + "\n");

			Toast.makeText(getApplicationContext(),
					"Got Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};

	@Override
	protected void onDestroy() {
		// Cancel AsyncTask
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			// Unregister Broadcast Receiver
			unregisterReceiver(mHandleMessageReceiver);

			// Clear internal resources.
			GCMRegistrar.onDestroy(this);

		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_sobre:
			Intent i = new Intent(getApplicationContext(), AboutActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
