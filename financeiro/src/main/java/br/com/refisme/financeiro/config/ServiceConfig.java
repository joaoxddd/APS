package br.com.refisme.financeiro.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.refisme.financeiro.service.CadastroPlanoContaService;

@Configuration
@ComponentScan(basePackageClasses = CadastroPlanoContaService.class)
public class ServiceConfig {

}
