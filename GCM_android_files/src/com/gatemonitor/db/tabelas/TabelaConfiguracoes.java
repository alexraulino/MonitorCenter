package com.gatemonitor.db.tabelas;

import java.util.ArrayList;

import android.content.ContentValues;

public class TabelaConfiguracoes extends DataAbstract {

	public TabelaConfiguracoes() {
		super("configuracoes", getSqlCreate());
	}

	public static String getSqlCreate() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE configuracoes ( ");
		sql.append("	    id             			INTEGER 		PRIMARY KEY AUTOINCREMENT NOT NULL,");
		sql.append("	    nameUser varchar(50) NOT NULL,");
		sql.append("	    emailUser varchar(255) NOT NULL,");
		sql.append("	    passwordUser varchar(10) NOT NULL,");
		sql.append("	    adminUser varchar(10) NOT NULL");
		sql.append("	);");
		return sql.toString();
	}

	@Override
	protected ArrayList<ContentValues> montarValores(RegistroAbstract registro) {
		RegistroConfiguracao configuracoes = (RegistroConfiguracao) registro;
		ContentValues values = new ContentValues();

		values.put("tabela", nameTable);
		values.put("nameUser", configuracoes.getNameUser());
		values.put("emailUser", configuracoes.getEmailUser());
		values.put("passwordUser", configuracoes.getPasswordUser());
		values.put("adminUser", configuracoes.getAdminUser());
		values.put("where", "id = " + configuracoes.getId());

		ArrayList<ContentValues> lista = new ArrayList<ContentValues>();
		lista.add(values);

		return lista;
	}

	@Override
	protected void loadRegistroFromCursor() {
		registros.clear();
		if (cursor.moveToFirst()) {
			do {
				RegistroConfiguracao rc = new RegistroConfiguracao(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("nameUser")),
						cursor.getString(cursor.getColumnIndex("emailUser")),
						cursor.getString(cursor.getColumnIndex("passwordUser")),
						cursor.getString(cursor.getColumnIndex("adminUser")));
				registros.put(rc.getId(), rc);

			} while (cursor.moveToNext());
		}

	}
}
