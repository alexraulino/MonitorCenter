package com.gatemonitor.db.tabelas;

public enum tipoNotificacoes {

	OPE1("Port�o Abriu"), OPE2("Port�o Fechou"), OPE3("Port�o Est� Aberto"), OPE4(
			"Iniciado Monitoramento"), OPE5("Finalizado Monitoramento"), OPE6(
			"Server Inacess�vel"), OPE7("Iniciado Monitoramento de Aten��o"), OPE8(
			"Finalizado Monitoramento de Aten��o");

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
