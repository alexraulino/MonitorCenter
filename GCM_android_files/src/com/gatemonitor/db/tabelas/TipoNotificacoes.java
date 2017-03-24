package com.gatemonitor.db.tabelas;

public enum TipoNotificacoes {

	tnPortaoAbriu("Port�o Abriu"), 
	tnPortaoFechou("Port�o Fechou"), 
	tnPortaoEstaAberto("Port�o Est� Aberto"), 
	tnIniciadoMonitoriamento("Iniciado Monitoramento"), 
	tnFinalizadoMonitoranmento("Finalizado Monitoramento"), 
	tnServerInacessivel("Server Inacess�vel"), 
	tnIniciandoMonitoramentoAtencao("Iniciado Monitoramento de Aten��o"), 
	tnFinalizandoMonitoramentoAtencao("Finalizado Monitoramento de Aten��o"), 
	tnNotebookLigou("Notebook ligou"), 
	tnNotebookDesligou("Notebook Desligou");

	private String desc;

	TipoNotificacoes(String desc) {
		this.desc = desc;
	}

	public String getDescricao() {
		return desc;
	}

	public static TipoNotificacoes getTipoNotificacao(int tipo) {
		return TipoNotificacoes.values()[tipo];
	}

}
