package com.gatemonitor.gcm;

public interface Config {

	// CONSTANTS
	static final String YOUR_SERVER_URL = "https://gatemonito.000webhostapp.com/GCM/register.php";
	// YOUR_SERVER_URL : Server url where you have placed your server files
	// Google project id
	static final String GOOGLE_SENDER_ID = "745146829393"; // Place here your
															// Google project id

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "Gate Monitor";

	static final String DISPLAY_MESSAGE_ACTION = "com.gatemonitor.gcm.DISPLAY_MESSAGE";

	static final String EXTRA_MESSAGE = "message";

}
