package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			if(verificarQuantidadeIngressos(sessao, 0.05)) { 
				preco = calculaPreco(sessao, 0.10);
			} else {
				preco = sessao.getPreco();
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			if(verificarQuantidadeIngressos(sessao, 0.50)) { 
				preco = calculaPreco(sessao, 0.20);
			} else {
				preco = sessao.getPreco();
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			if(verificarQuantidadeIngressos(sessao, 0.50)) { 
				preco = calculaPreco(sessao, 0.20);
			} else {
				preco = sessao.getPreco();
			}

			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPreco(Sessao sessao, double multiplicador) {
		return sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(multiplicador)));
	}

	private static boolean verificarQuantidadeIngressos(Sessao sessao, double limite) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= limite;
	}
}