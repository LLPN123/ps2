package ps2.restapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
class DisciplinaController{
    private List<Disciplina> d;

    public DisciplinaController(){
        this.d = new ArrayList<>();
        
    }
    @GetMapping("/api/disciplinas")
	Iterable<Disciplina> getDisciplina() {
		return this.d;
	}
	
	@GetMapping("/api/disciplinas/{id}")
	Optional<Disciplina> getDisciplina(@PathVariable long id) {
		for(Disciplina dis: d){
            if(dis.getId()==id){
                return Optional.of(dis);
            }
        }
        return Optional.empty();
	}
	
	@PostMapping("/api/disciplinas")
	Disciplina createDisciplina(@RequestBody Disciplina di) {
        long maxId = 1;
        for(Disciplina dis: d){
            if(dis.getId()>maxId){
                maxId = dis.getId();
            }
        }
		di.setId(maxId+1);
        d.add(di);
        return di;
	}
	
	@PutMapping("/api/disciplinas/{dispId}")
	Optional<Disciplina> updateDisciplina(@RequestBody Disciplina dispRequest, @PathVariable long dispId) {
		Optional<Disciplina> opt = this.getDisciplina(dispId);
		if (opt.isPresent()) {
			Disciplina disc = opt.get();
            disc.setNome(dispRequest.getNome());
            disc.setSigla(dispRequest.getSigla());
            disc.setCurso(dispRequest.getCurso());
            disc.setSemestre(dispRequest.getSemestre());
		}
        return opt;
	}	
	
	@DeleteMapping(value = "/api/disciplinas/{id}")
	void deleteDisciplina(@PathVariable long id) {
		d.removeIf(di -> di.getId() == id);
	}		
}