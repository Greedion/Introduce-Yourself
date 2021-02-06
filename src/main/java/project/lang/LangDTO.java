package project.lang;

public class LangDTO {
    Integer id;
    String code;

    public LangDTO() {
    }

    public LangDTO(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public LangDTO(Lang lang){
        this.id = lang.getId();
        this.code = lang.getCode();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
