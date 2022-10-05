package br.com.refisme.financeiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping("/")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("index/PaginaInicial");
		return mv;
	}	

    @GetMapping(path="/cadastros")
    public ModelAndView cadastros() {
        ModelAndView mv = new ModelAndView("index/Cadastro");
        return mv;
    }
    
    @GetMapping(path="/movimentos")
    public ModelAndView movimentos() {
        ModelAndView mv = new ModelAndView("index/Movimento");
        return mv;
    }
    
    @GetMapping(path="/relatorios")
    public ModelAndView relatorios() {
        ModelAndView mv = new ModelAndView("index/Relatorio");
        return mv;
    }
    
    @GetMapping(path="/impressoes")
    public ModelAndView impressoes() {
        ModelAndView mv = new ModelAndView("index/Impressao");
        return mv;
    }

}
