package com.gatemonitor.db.tabelas;

public enum tipoNotificacoes {

	OPE1("Portão Abriu"), OPE2("Portão Fechou"), OPE3("Portão Está Aberto"), OPE4(
			"Iniciado Monitoramento"), OPE5("Finalizado Monitoramento"), OPE6(
			"Server Inacessível"), OPE7("Iniciado Monitoramento de Atenção"), OPE8(
			"Finalizado Monitoramento de Atenção");

	private String desc;

	tipoNotificacoes(String desc) {
		this.desc = desc;
	}

	public String getDescricao() {
		return desc;
	}

	public static tipoNotificacoes getTipoNotificacao(int tipo) {
		return tipoNotificacoes.values()[tipo];
	}

}
