package br.com.refisme.financeiro.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.refisme.financeiro.model.PlanoConta;

@Component
public class PlanoContaValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return PlanoConta.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descricao", "", "Descrição é obrigatória");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ordem", "", "Ordem é obrigatória");
		ValidationUtils.rejectIfEmpty(errors, "acesso", "", "Acesso é obrigatório");
		ValidationUtils.rejectIfEmpty(errors, "modelo", "", "Modelo é obrigatório");
		ValidationUtils.rejectIfEmpty(errors, "tipo", "", "Tipo é obrigatório");
		ValidationUtils.rejectIfEmpty(errors, "nivel", "", "Nível é obrigatório");
		
		PlanoConta planoConta = (PlanoConta) target;
		
		if(planoConta.getDescricao().length() > 45)
			errors.rejectValue("descricao", "", "Descrição poderá ter no máximo 45 caracteres");
		
		if(planoConta.getOrdem().length() > 15)
			errors.rejectValue("ordem", "", "Ordem poderá ter no máximo 15 caracteres");
		
	}
}
