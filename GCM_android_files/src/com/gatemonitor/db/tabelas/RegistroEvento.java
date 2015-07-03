package com.gatemonitor.db.tabelas;

public class RegistroEvento extends RegistroAbstract {

	private String data;
	private int tipoNotificacao;
	private String info;

	public RegistroEvento(int id, String data, int tipoNotificacao, String info) {
		super(id);
		this.data = data;
		this.tipoNotificacao = tipoNotificacao;
		this.info = info;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(int tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
