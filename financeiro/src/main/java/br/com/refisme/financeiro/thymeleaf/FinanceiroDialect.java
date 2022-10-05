package br.com.refisme.financeiro.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.refisme.financeiro.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.refisme.financeiro.thymeleaf.processor.MenuAttributeTagProcessor;
import br.com.refisme.financeiro.thymeleaf.processor.OrderElementTagProcessor;
import br.com.refisme.financeiro.thymeleaf.processor.PaginationElementTagProcessor;

public class FinanceiroDialect extends AbstractProcessorDialect{

	public FinanceiroDialect() {
		super("NPI financeiro", "financeiro", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		return processadores;
	}

}
