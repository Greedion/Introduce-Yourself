package project.lang;

import java.util.ArrayList;
import java.util.List;

public class LangService {

    private final LangRepository repository;

    public LangService() {
        this(new LangRepository());
    }

    public LangService(LangRepository repository) {
        this.repository = repository;
    }

    private LangDTO parseFromLangToLangDTO(Lang lang){
        return new LangDTO(lang.getId(),lang.getCode());
    }

    List<LangDTO> findAll(){
        List<LangDTO> langList = new ArrayList<>();
        List<Lang> entityList  = repository.findAll();
        for (Lang x:entityList
             ) {
            langList.add(parseFromLangToLangDTO(x));
        }
        return langList;
    }
}
