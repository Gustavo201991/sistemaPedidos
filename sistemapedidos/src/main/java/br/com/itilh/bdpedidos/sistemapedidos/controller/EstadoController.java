package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;







@RestController

public class EstadoController {

    
    private final EstadoRepository repositorio;

    public EstadoController(EstadoRepository repositorio) {
        this.repositorio = repositorio;

    }

    @GetMapping("/estados")
    public List<Estado> getTodos() {
        return (List<Estado>) repositorio.findAll();  
            
    }
    @SuppressWarnings("unlikely-arg-type")
    @GetMapping("/estados/nome/{nome}")
    public List<Estado> getEstadosPorNome(@PathVariable String nome,
    @RequestParam(required=true) String modoBusca) {
        if(modoBusca.equals(ModoBusca.EXATO)){
            return repositorio.findByNome(nome);
        }else if (modoBusca.equals(ModoBusca.INICIADO)){
            return repositorio.findByNomeContainingIgnoreCase(nome);
        }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
            return repositorio.findByNomeContainingIgnoreCase(nome);
        }else{
                return repositorio.findByNomeContainingIgnoreCase(nome);
        }        
    }

    @GetMapping("/estado/{id}")
      public Estado getPorId(@PathVariable BigInteger id) throws Exception {
          return repositorio.findById(id).orElseThrow(
        () -> new Exception("ID inválido."));
   }
   
   
    //funcionando porem sem tratamentos de erros    
    //@PostMapping("/estado")
   // public Estado criarEstado(@RequestBody Estado entity) {
    //    return repositorio.save(entity);
    //}
            
    @PostMapping("/estado")
    public Estado criarEstado(@RequestBody Estado entity) throws Exception { 
        try{               
            return repositorio.save(entity);
        }catch(Exception e){
            throw new Exception("Erro ao salvar o estado.");
        }
    }     

    
    @PutMapping("/estado/{id}")
    public Estado alterarEstado(@PathVariable BigInteger id, @RequestBody Estado novosDados) throws Exception {

        Optional<Estado> estadoAmazenado = repositorio.findById(id);
        if(estadoAmazenado.isPresent()){
            //Atribuir novo nome ao objeto já existem no banco de dados
            estadoAmazenado.get().setNome(novosDados.getNome());
           
            return repositorio.save(estadoAmazenado.get());}
        return novosDados;
        }        
    
@DeleteMapping("/estado/{id}")
public String deletePorId(@PathVariable BigInteger id) throws Exception {

    Optional<Estado> estadoAmazenado = repositorio.findById(id);
    if(estadoAmazenado.isPresent()){
        repositorio.delete(estadoAmazenado.get());
        return "Excluído";
    }
    throw new Exception("Id não econtrado para a exclusão");
}



}
