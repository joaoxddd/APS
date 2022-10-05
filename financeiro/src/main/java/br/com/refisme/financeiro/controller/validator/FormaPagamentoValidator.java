package br.com.refisme.financeiro.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.refisme.financeiro.model.FormaPagamento;

@Component
public class FormaPagamentoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return FormaPagamento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descricao", "", "Descrição é obrigatória");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sigla", "", "Sigla é obrigatória");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "parcela", "", "Quantidade de parcelas é obrigatória");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "diaCredito", "", 
				"Quantidade de dias de crédito é obrigatória");
		ValidationUtils.rejectIfEmpty(errors, "tipo", "", "Tipo é obrigatório");
		
		FormaPagamento formaPagamento = (FormaPagamento) target;
		
		if(formaPagamento.getDescricao().length() > 50)
			errors.rejectValue("descricao", "", "Descrição deve ter até 50 caracteres");
		
		if(formaPagamento.getSigla().length() > 10)
			errors.rejectValue("sigla", "", "Sigla deve ter até 10 caracteres");
		
		if(formaPagamento.getParcela() < 1)
			errors.rejectValue("parcela", "", "Parcela deve ser maior que 0");
		
		if(formaPagamento.getDiaCredito() < 0)
			errors.rejectValue("diaCredito", "", 
					"Quantidade de dias de crédito deve ser maior ou igual a 0");
	}

}
