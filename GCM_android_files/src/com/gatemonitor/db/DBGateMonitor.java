package com.gatemonitor.db;

import java.util.Collection;
import java.util.HashMap;

import com.gatemonitor.db.tabelas.DataAbstract;
import com.gatemonitor.db.tabelas.TabelaConfiguracoes;
import com.gatemonitor.db.tabelas.TabelaEventos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBGateMonitor {
	private static final String DATABASE_NAME = "GateMonitor.db";
	private static final int DATABASE_VERSION = 2;

	private final HashMap<String, DataAbstract> tabelas = new HashMap<String, DataAbstract>();
	private static Context context;
	private final SQLiteDatabase db;

	private static DBGateMonitor dbInstance = null;

	public static DBGateMonitor getInstanceDB() {

		if (dbInstance == null) {
			dbInstance = new DBGateMonitor();
		}
		return dbInstance;
	}

	public static void setContext(Context acontext) {
		context = acontext;
	}

	private DBGateMonitor() {
		tabelas.put("configuracoes", new TabelaConfiguracoes());
		tabelas.put("eventos", new TabelaEventos());

		OpenHelper openHelper = new OpenHelper(context, tabelas.values());
		this.db = openHelper.getWritableDatabase();

		carregarTabelas();

	}

	public TabelaConfiguracoes getTabelaConfiguracoes() {
		return (TabelaConfiguracoes) tabelas.get("configuracoes");
	}

	public TabelaEventos getTabelaEventos() {
		return (TabelaEventos) tabelas.get("eventos");
	}

	private void carregarTabelas() {
		for (DataAbstract tabela : tabelas.values()) {
			tabela.carregarTabela(db);
		}
	}

	public void recarregarTabelas() {
		for (DataAbstract tabela : tabelas.values()) {
			tabela.recarregarTabela();
		}
	}

	public void limparBase() {
		for (DataAbstract tabela : tabelas.values()) {
			tabela.limparTabela();
		}
		recarregarTabelas();
	}

	private static class OpenHelper extends SQLiteOpenHelper {
		private final Collection<DataAbstract> tabelas;

		OpenHelper(Context context, Collection<DataAbstract> tabelas) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.tabelas = tabelas;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			for (DataAbstract tabela : tabelas) {
				db.execSQL(tabela.getComandoSqlCreate());
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			for (DataAbstract tabela : tabelas) {
				db.execSQL("DROP TABLE IF EXISTS " + tabela.getNome());
			}
			onCreate(db);
		}
	}
}