package com.gatemonitor.db.tabelas;

import java.util.ArrayList;

import android.content.ContentValues;

public class TabelaEventos extends DataAbstract {

	public TabelaEventos() {
		super("eventos", getSqlCreate());
		// TODO Auto-generated constructor stub
	}

	public static String getSqlCreate() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE eventos ( ");
		sql.append("	    id             			INTEGER 		PRIMARY KEY AUTOINCREMENT NOT NULL,");
		sql.append("	    data varchar(100) NOT NULL,");
		sql.append("	    tipo_notificacao int(11) NOT NULL,");
		sql.append("	    info varchar(100) NOT NULL");
		sql.append("	);");
		return sql.toString();
	}

	@Override
	protected ArrayList<ContentValues> montarValores(RegistroAbstract registro) {
		RegistroEvento evento = (RegistroEvento) registro;
		ContentValues values = new ContentValues();

		values.put("tabela", nameTable);
		values.put("data", evento.getData());
		values.put("tipo_notificacao", evento.getTipoNotificacao());
		values.put("info", evento.getInfo());
		values.put("where", "id = " + evento.getId());

		ArrayList<ContentValues> lista = new ArrayList<ContentValues>();
		lista.add(values);

		return lista;
	}

	@Override
	protected void loadRegistroFromCursor() {
		registros.clear();
		if (cursor.moveToFirst()) {
			do {
				RegistroEvento rc = new RegistroEvento(cursor.getInt(cursor
						.getColumnIndex("id")), cursor.getString(cursor
						.getColumnIndex("data")), cursor.getInt(cursor
						.getColumnIndex("tipo_notificacao")),
						cursor.getString(cursor.getColumnIndex("info")));
				registros.put(rc.getId(), rc);

			} while (cursor.moveToNext());
		}

	}

}
