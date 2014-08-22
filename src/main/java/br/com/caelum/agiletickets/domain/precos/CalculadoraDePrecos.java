package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
<<<<<<< HEAD
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

=======
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			
			if(verificarQuantidadeIngressos(sessao, 0.05)) { 
				preco = calculaPrecoSessao(sessao, 0.10);
			} else {
				preco = sessao.getPreco();
			}
			
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			
			if(verificarQuantidadeIngressos(sessao, 0.50)) { 
				preco = calculaPrecoSessao(sessao, 0.20);
			} else {
				preco = sessao.getPreco();
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(multiplicaPrecoSessao(sessao,0.10));
			}
			
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			
			if(verificarQuantidadeIngressos(sessao, 0.50)) { 
				preco = calculaPrecoSessao(sessao, 0.20);
			} else {
				preco = sessao.getPreco();
			}

			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(multiplicaPrecoSessao(sessao,0.10));
			}
			
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal multiplicaPrecoSessao(Sessao sessao, double multiplicador) {
		return sessao.getPreco().multiply(BigDecimal.valueOf(multiplicador));
	}

	private static BigDecimal calculaPrecoSessao(Sessao sessao, double multiplicador) {
		return sessao.getPreco().add(multiplicaPrecoSessao(sessao, multiplicador));
	}

	private static boolean verificarQuantidadeIngressos(Sessao sessao, double limite) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= limite;
	}
>>>>>>> 46d648e68d0b7ae8854a2030129b5db20805dc4c
}