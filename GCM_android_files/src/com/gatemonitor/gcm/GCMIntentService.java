package com.gatemonitor.gcm;

import com.gatemonitor.db.DBGateMonitor;
import com.gatemonitor.db.tabelas.RegistroEvento;
import com.gatemonitor.db.tabelas.TipoNotificacoes;
import com.google.android.gcm.GCMBaseIntentService;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

	private Controller aController = null;

	public GCMIntentService() {
		// Call extended class Constructor GCMBaseIntentService
		super(Config.GOOGLE_SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {

		// Get Global Controller Class object (see application tag in
		// AndroidManifest.xml)
		if (aController == null)
			aController = (Controller) getApplicationContext();

		Log.i(TAG, "Device registered: regId = " + registrationId);
		aController.displayMessageOnScreen(context,
				"Your device registred with GCM");
		Log.d("NAME", MainActivity.name);
		aController.register(context, MainActivity.name, MainActivity.email,
				registrationId, MainActivity.senha);
	}

	/**
	 * Method called on device unregistred
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		if (aController == null)
			aController = (Controller) getApplicationContext();
		Log.i(TAG, "Device unregistered");
		aController.displayMessageOnScreen(context,
				getString(R.string.gcm_unregistered));
		aController.unregister(context, registrationId);
	}

	/**
	 * Method called on Receiving a new message from GCM server
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		Log.i(TAG, "Received message");
		String message = intent.getExtras().getString("message");

		aController.displayMessageOnScreen(context, message);
		// notifies user
		generateNotification(context, message);
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		aController.displayMessageOnScreen(context, message);
		// notifies user
		generateNotification(context, message);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		Log.i(TAG, "Received error: " + errorId);
		aController.displayMessageOnScreen(context,
				getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		aController.displayMessageOnScreen(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Create a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String message) {
		
		int msg = Integer.valueOf(message.split("=")[0]);
		String data = message.split("=")[1];		
		
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(TipoNotificacoes.getTipoNotificacao(msg).getDescricao());
        notificationManager.notify(1, mBuilder.build());
        
        
		DBGateMonitor.setContext(context);
		DBGateMonitor db = DBGateMonitor.getInstanceDB();
		RegistroEvento rg = new RegistroEvento(0, data, msg, "");
		db.getTabelaEventos().inserirRegistro(rg);
		db.getTabelaEventos().recarregarTabela();
	}

}
