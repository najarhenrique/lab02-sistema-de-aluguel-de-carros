package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Cliente;
import br.edu.pucminas.lab02.aluguel_de_carros.model.RendimentoEmpregadora;
import br.edu.pucminas.lab02.aluguel_de_carros.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        adicionarFormulario(model, new Cliente());
        return "clientes/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable String id, Model model) {
        adicionarFormulario(model, service.buscar(id));
        return "clientes/formulario";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result,
                        Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            adicionarFormulario(model, cliente);
            return "clientes/formulario";
        }
        try {
            service.salvar(cliente);
            attributes.addFlashAttribute("sucesso", "Cliente cadastrado com sucesso.");
            return "redirect:/clientes";
        } catch (ClienteService.CpfDuplicadoException exception) {
            result.rejectValue("cpf", "duplicado", exception.getMessage());
            adicionarFormulario(model, cliente);
            return "clientes/formulario";
        } catch (ClienteService.CpfInvalidoException exception) {
            result.rejectValue("cpf", "invalido", exception.getMessage());
            adicionarFormulario(model, cliente);
            return "clientes/formulario";
        }
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable String id, @Valid @ModelAttribute("cliente") Cliente cliente,
                            BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            adicionarFormulario(model, cliente);
            return "clientes/formulario";
        }
        try {
            service.atualizar(id, cliente);
            attributes.addFlashAttribute("sucesso", "Cliente atualizado com sucesso.");
            return "redirect:/clientes";
        } catch (ClienteService.CpfDuplicadoException exception) {
            result.rejectValue("cpf", "duplicado", exception.getMessage());
            adicionarFormulario(model, cliente);
            return "clientes/formulario";
        } catch (ClienteService.CpfInvalidoException exception) {
            result.rejectValue("cpf", "invalido", exception.getMessage());
            adicionarFormulario(model, cliente);
            return "clientes/formulario";
        }
    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable String id, RedirectAttributes attributes) {
        service.excluir(id);
        attributes.addFlashAttribute("sucesso", "Cliente excluido com sucesso.");
        return "redirect:/clientes";
    }

    private void adicionarFormulario(Model model, Cliente cliente) {
        while (cliente.getRendimentos().size() < 3) {
            cliente.getRendimentos().add(new RendimentoEmpregadora());
        }
        model.addAttribute("cliente", cliente);
    }
}