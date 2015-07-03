package com.gatemonitor.db.tabelas;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gatemonitor.db.Operacao;

public abstract class DataAbstract {
	protected final String nameTable;
	protected final String comandoSqlCreate;

	protected Cursor cursor;

	public final HashMap<Integer, RegistroAbstract> registros = new HashMap<Integer, RegistroAbstract>();

	protected SQLiteDatabase db;

	public String getComandoSqlCreate() {
		return comandoSqlCreate;
	}

	protected abstract ArrayList<ContentValues> montarValores(
			RegistroAbstract registro);

	protected abstract void loadRegistroFromCursor();

	public String getNome() {
		return nameTable;
	}

	public Cursor getCursor(String filtro) {
		return this.db.query(nameTable, null, filtro, null, null, null, null);
	}

	public Cursor getCursor() {
		return cursor;
	}

	public DataAbstract(String name, String comandoSqlCreate) {
		super();
		this.nameTable = name;
		this.comandoSqlCreate = comandoSqlCreate;
	}

	public void carregarTabela(SQLiteDatabase db) {
		this.db = db;
		cursor = this.db.query(nameTable, null, null, null, null, null, null);
		loadRegistroFromCursor();
	}

	public void recarregarTabela() {
		carregarTabela(db);
	}

	public void limparTabela() {
		executarComandoEspecifico(Operacao.DELETE, this.nameTable, null, null);
	}

	public void inserirRegistro(RegistroAbstract registro) {
		executarComando(registro, Operacao.INSERT);
	}

	public void alterarRegistro(RegistroAbstract registro) {
		executarComando(registro, Operacao.UPDATE);
	}

	public void excluirRegistro(RegistroAbstract registro) {
		executarComando(registro, Operacao.DELETE);
	}

	private long executarComando(RegistroAbstract registro, Operacao operacao) {
		ArrayList<ContentValues> values = montarValores(registro);

		for (ContentValues value : values) {
			String tabela = value.getAsString("tabela");
			String where = value.getAsString("where");

			// Remove da lista para deixar apenas os valores dos campos a serem
			// manipulados
			value.remove("tabela");
			value.remove("where");

			executarComandoEspecifico(operacao, tabela, where, value);
		}
		return 1;
	}

	private void executarComandoEspecifico(Operacao operacao, String tabela,
			String where, ContentValues values) {
		switch (operacao) {
		case INSERT:
			this.db.insert(tabela, null, values);
			break;

		case UPDATE:
			this.db.update(tabela, values, where, null);
			break;

		case DELETE:
			this.db.delete(tabela, where, null);
			Log.w("Delete: ", tabela + " - " + where);
			break;
		}
	}
}
