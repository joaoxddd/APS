package br.com.refisme.financeiro.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.refisme.financeiro.model.Usuario;

@Component
public class UsuarioValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "funcionario.id", "", "Selecione um funcionário na pesquisa rápida");

		Usuario usuario = (Usuario) target;

		if (!analiseEmail(usuario.getEmail())) {
			errors.rejectValue("email", "", "E-mail inválido");
		}
		//
		// if (usuario.getEmail().isEmpty() == usuario.getEmail()
		// .matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
		// {
		// errors.rejectValue("email","", "E-mail inválido");
		// }

		lengthValidator(errors, usuario);

		if (usuario.isNovo()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmacaoSenha", "",
					"Confirmação de senha é obrigatória");

			if (!confirmarSenha(usuario.getSenha(), usuario.getConfirmacaoSenha())) {
				errors.rejectValue("confirmacaoSenha", "", "Senha não confere");
			}
		} else {
			ValidationUtils.rejectIfEmpty(errors, "permissaoAcesso.id", "",
					"Selecione uma permissão de acesso na pesquisa rápida");
		}
	}

	private void lengthValidator(Errors errors, Usuario usuario) {
		if (usuario.getEmail().length() > 50 && !usuario.getEmail().isEmpty()) {
			errors.rejectValue("email", "", "E-mail poderá ter no máximo 50 caracteres");
		}

		if (usuario.getSenha().length() > 10 && !usuario.getSenha().isEmpty()) {
			errors.rejectValue("senha", "", "Senha poderá ter no máximo 10 caracteres");
		}

	}

	private boolean analiseEmail(String email) {
		if (email.length() < 4) {
			return false;
		}

		for (int indice = 0; indice < email.length(); indice++) {
			if (email.charAt(indice) == " ".charAt(0)) {
				return false;
			}

			if (email.charAt(indice) != "@".charAt(0)) {
				continue;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean confirmarSenha(String senha, String confirmacaoSenha) {
		return senha.equals(confirmacaoSenha);
	}
}
