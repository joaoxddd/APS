var Financeiro = Financeiro || {}

// Máscarar de valores.
Financeiro.MaskMoney = (function() {
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
	}
	
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
	}
	
	return MaskMoney;
	
}());

// Máscarar para cep.
Financeiro.MaskCep = (function() {
	
	function MaskCep() {
		this.inputCep = $('.js-cep'); 
	}
	
	MaskCep.prototype.enable = function() {
		this.inputCep.mask('00.000-000');		
	}
	
	return MaskCep;
	
}());


// Mascara para DDD.
Financeiro.MaskDDD = (function() {
	function MaskDDD() {
		this.inputDDD = $('.js-ddd');
	}
	
	MaskDDD.prototype.enable = function() {
		this.inputDDD.mask('00');
	}
	
	return MaskDDD;
}());

//Máscara de celular.
Financeiro.MaskCelular = (function() {
	
	function MaskCelular() {
		this.inputCelular = $('.js-celular');
	}
	
	MaskCelular.prototype.enable = function() {
		var maskBehavior = function (val) {
		  return val.replace(/\D/g, '').length === 9 ? '00000-0000' : '0000-00009';
		};
		
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		    }
		};
		
		this.inputCelular.mask(maskBehavior, options);
	}
	
	return MaskCelular;
	
}());

// Máscara de telefone.
Financeiro.MaskTelefone = (function () {
	function MaskTelefone() {
		this.inputTelefone = $('.js-telefone');
	}
	
	MaskTelefone.prototype.enable = function() {
		this.inputTelefone.mask('0000-0000');
	}
	
	return MaskTelefone;
}());

// Máscara do CPF.
Financeiro.MaskCpf = (function() {
	function MaskCpf() {
		this.inputCpf = $('.js-cpf');
	}
	
	MaskCpf.prototype.enable = function() {
		this.inputCpf.mask('000.000.000-00');
	}
	
	return MaskCpf;
}());

//Máscara do CNPJ.
Financeiro.MaskCnpj = (function() {
	function MaskCnpj() {
		this.inputCnpj = $('.js-cnpj');
	}
	
	MaskCnpj.prototype.enable = function() {
		this.inputCnpj.mask('00.000.000/0000-00');
	}
	
	return MaskCnpj;
}());

//Máscara do RG.
Financeiro.MaskRg = (function() {
	function MaskRg() {
		this.inputRg = $('.js-rg');
	}
	
	MaskRg.prototype.enable = function() {
		this.inputRg.mask('0000000000-0');
	}
	
	return MaskRg;
}());

//Máscara do RG com 15 caracteres.
Financeiro.MaskRg15 = (function() {
	function MaskRg15() {
		this.inputRg = $('.js-rg-15');
	}
	
	MaskRg15.prototype.enable = function() {
		this.inputRg.mask('000000000000000');
	}
	
	return MaskRg15;
}());

//Máscara do PIS.
Financeiro.MaskPis = (function() {
	function MaskPis() {
		this.inputPis = $('.js-pis');
	}
	
	MaskPis.prototype.enable = function() {
		this.inputPis.mask('000.00000.00-0');
	}
	
	return MaskPis;
}());

//Máscara de Data.
Financeiro.MaskData = (function() {
	
	function MaskData() {
		this.inputDate = $('.js-data');
	}
	
	MaskData.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		})
	}
	
	return MaskData;
	
}());

//Máscara de Data (Mês e Ano).
Financeiro.MaskDataMesAno = (function() {
	
	function MaskDataMesAno() {
		this.inputDate = $('.js-mes-ano');
	}
	
	MaskDataMesAno.prototype.enable = function() {
		this.inputDate.datepicker({
			startView: 1,
			minViewMode: 1,
			maxViewMode: 3,
			format: "mm/yyyy",
			changeMonth: true,
			changeYear: true
		})
	}
	
	return MaskDataMesAno;
	
}());

//Máscara de Mês e Ano.
Financeiro.MaskMes = (function() {
	
	function MaskMes() {
		this.inputAno = $('.js-ano');
	}
	
	MaskMes.prototype.enable = function() {
		this.inputAno.mask('00/0000');
	}
	
	return MaskMes;
	
}());

// Validação para o código de município.
Financeiro.MaskCod = (function() {
	
	function MaskCod() {
		this.inputCod = $('.js-cod'); 
	}
	
	MaskCod.prototype.enable = function() {
		this.inputCod.mask('0000000');		
	}
	
	return MaskCod;
	
}());

//Token.
//Financeiro.Security = (function() {
//	
//	function Security() {
//		this.token = $('input[name=_csrf]').val();
//		this.header = $('input[name=_csrf_header]').val();
//	}
//	
//	Security.prototype.enable = function() {
//		$(document).ajaxSend(function(event, jqxhr, settings) {
//			jqxhr.setRequestHeader(this.header, this.token);
//		}.bind(this));
//	}
//	
//	return Security;
//	
//}());

$(function() {		
	// Máscarar de valores.
	var maskMoney = new Financeiro.MaskMoney();
	maskMoney.enable();
	
	// Máscarar para cep.
	var maskCep = new Financeiro.MaskCep();
	maskCep.enable();
	
	// Mascara para DDD.
	var maskDDD = new Financeiro.MaskDDD();
	maskDDD.enable();
	
	//Máscara de celular.
	var maskCelular = new Financeiro.MaskCelular();
	maskCelular.enable();
	
	// Máscara de telefone.
	var maskTelefone = new Financeiro.MaskTelefone();
	maskTelefone.enable();
	
	// Máscara de CPF.
	var maskCpf = new Financeiro.MaskCpf();
	maskCpf.enable();
	
	// Máscara de CNPJ.
	var maskCnpj = new Financeiro.MaskCnpj();
	maskCnpj.enable();
	
	//Máscara de RG.
	var maskRg = new Financeiro.MaskRg();
	maskRg.enable();
	
	//Máscara de RG 15 caracteres.
	var maskRg15 = new Financeiro.MaskRg15();
	maskRg15.enable();
	
	//Máscara do PIS.
	var maskPis = new Financeiro.MaskPis();
	maskPis.enable();
	
	//Máscara de Data.
	var maskData = new Financeiro.MaskData();
	maskData.enable();
	
	//Máscara de Data (Mês e Ano).
	var maskDataMesAno = new Financeiro.MaskDataMesAno();
	maskDataMesAno.enable();
	
	//Máscara de Mês e Ano.
	var maskMes = new Financeiro.MaskMes();
	maskMes.enable();
	
	// Validação para o código de município.
	var maskcod = new Financeiro.MaskCod();
	maskcod.enable();
	
	// Token.
//	var security = new Financeiro.Security();
//	security.enable();
});