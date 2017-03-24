package com.gatemonitor.db.tabelas;

public enum TipoNotificacoes {

	tnPortaoAbriu("Portão Abriu"), 
	tnPortaoFechou("Portão Fechou"), 
	tnPortaoEstaAberto("Portão Está Aberto"), 
	tnIniciadoMonitoriamento("Iniciado Monitoramento"), 
	tnFinalizadoMonitoranmento("Finalizado Monitoramento"), 
	tnServerInacessivel("Server Inacessível"), 
	tnIniciandoMonitoramentoAtencao("Iniciado Monitoramento de Atenção"), 
	tnFinalizandoMonitoramentoAtencao("Finalizado Monitoramento de Atenção"), 
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
