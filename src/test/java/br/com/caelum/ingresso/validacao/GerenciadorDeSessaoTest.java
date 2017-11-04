package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {

	@Test
	public void NaoPermitirNoMesmoHorario(){
		
		Filme filme = new Filme("RogueOne", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("", BigDecimal.ONE);
		
		Sessao sessao = new Sessao(horario, filme, sala);
		List<Sessao> sessoes = Arrays.asList(sessao);
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
		
	}
	
	@Test
	public void NaoDevePermitirDentroDoHorarioJaExistente(){
		
		Filme filme = new Filme("RogueOne", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("", BigDecimal.ONE);
		
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		
		Sessao sessao = new Sessao(horario.minusHours(1), filme, sala);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));		
		
	}
	
	@Test
	public void NaoDevePermitirIniciandoDentroDoHorarioJaExistente(){
		
		Filme filme = new Filme("RogueOne", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("", BigDecimal.ONE);
		
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		
		Sessao sessao = new Sessao(horario.plusHours(1),filme, sala);
		Assert.assertFalse(gerenciador.cabe(sessao));		
		
	}
	
	@Test
	public void DevePermitirUmaInsercaoEntreDoisFilmes(){
		
		Sala sala = new Sala("", BigDecimal.ONE);
		Filme filme1 = new Filme("Rogue One", Duration.ofMinutes(90), "SCI-FI", BigDecimal.ONE);
		LocalTime dezHoras = LocalTime.parse("10:00:00");
		Sessao sessaoDasDez = new Sessao(dezHoras, filme1, sala);
		
		Filme filme2 = new Filme("RogueOne", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		LocalTime dezoitoHoras = LocalTime.parse("18:00:00");
		Sessao sessaoDasDezoitoHoras = new Sessao(dezoitoHoras, filme2, sala);
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoitoHoras);
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessao = new Sessao(LocalTime.parse("13:00:00"), filme1, sala);
		
		Assert.assertTrue(gerenciador.cabe(sessao));
		
	}
}

