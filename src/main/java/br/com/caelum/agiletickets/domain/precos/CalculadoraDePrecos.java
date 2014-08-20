package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		double aliquota = 0.0;
		double lotacao = 0.0;
		boolean temAdicionalTempo = false;
		int tempoExcedenteEspetaculo = 60;
		
		double aliquotaTempoExcedente = 0.10;
		switch(sessao.getEspetaculo().getTipo()){
		case CINEMA:
		case SHOW:
			lotacao = 0.05;
			aliquota = 0.10;
			break;
		case BALLET:
		case ORQUESTRA:
			lotacao = 0.50;
			aliquota = 0.20;
			temAdicionalTempo = true;			
		}
		
		preco = sessao.getPreco();
		
		if(aplicaAcrescimo(sessao, lotacao)) { 
			preco = preco.add(getAcrescimo(sessao, aliquota));
		}
		
		if(temAdicionalTempo){
			
			if(sessao.getDuracaoEmMinutos() > tempoExcedenteEspetaculo){
				preco = preco.add(getAcrescimo(sessao, aliquotaTempoExcedente));
			}
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal getAcrescimo(Sessao sessao, double aliquota) {
		return sessao.getPreco().multiply(BigDecimal.valueOf(aliquota));
	}

	private static boolean aplicaAcrescimo(Sessao sessao, double lotacao) {
		return lotacao != 0.0  && taxaOcupacao(sessao) <= lotacao;
	}

	private static double taxaOcupacao(Sessao sessao) {
		return getLugaresVagos(sessao) / sessao.getTotalIngressos().doubleValue();
	}

	private static int getLugaresVagos(Sessao sessao) {
		return sessao.getTotalIngressos() - sessao.getIngressosReservados();
	}

}