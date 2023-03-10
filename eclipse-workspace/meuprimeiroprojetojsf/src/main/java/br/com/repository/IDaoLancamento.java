package br.com.repository;

import java.util.Date;
import java.util.List;

import br.com.entidades.Lancamento;

public interface IDaoLancamento {
List<Lancamento> consultar(Long codUser);
List<Lancamento> consultarLimite10(Long codUser);
List<Lancamento> relatorioLancamento(String nota, Date dataIni, Date dataFim);
}
