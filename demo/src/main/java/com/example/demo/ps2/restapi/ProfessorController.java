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
class ProfessorController{
    private List<Professor> p;

    public ProfessorController(){
        this.p = new ArrayList<>();
        
    }
    @GetMapping("/api/professores")
	Iterable<Professor> getProfessores() {
		return this.p;
	}
	
	@GetMapping("/api/professores/{id}")
	Optional<Professor> getProfessor(@PathVariable long id) {
		for(Professor pro: p){
            if(pro.getId()==id){
                return Optional.of(pro);
            }
        }
        return Optional.empty();
	}
	
	@PostMapping("/api/professores")
	Professor createProfessor(@RequestBody Professor pr) {
        long maxId = 1;
        for(Professor prof: p){
            if(prof.getId()>maxId){
                maxId = prof.getId();
            }
        }
		pr.setId(maxId+1);
        p.add(pr);
        return pr;
	}
	
	@PutMapping("/api/professores/{professorId}")
	Optional<Professor> updateProfessor(@RequestBody Professor professorRequest, @PathVariable long professorId) {
		Optional<Professor> opt = this.getProfessor(professorId);
		if (opt.isPresent()) {
			Professor profe = opt.get();
            profe.setNome(professorRequest.getNome());
            profe.setMatricula(professorRequest.getMatricula());
            profe.setArea(professorRequest.getArea());
		}
        return opt;
	}	
	
	@DeleteMapping(value = "/api/professores/{id}")
	void deleteProfessor(@PathVariable long id) {
		p.removeIf(pr -> pr.getId() == id);
	}		
}