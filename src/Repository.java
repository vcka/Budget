import java.util.ArrayList;
import java.util.List;

public class Repository {
    public List<Expence> expenceList = new ArrayList<>();
    public List<Category> categoryList = new ArrayList<>();
    private static Repository repository;

    private Repository(){
    }

    public static Repository getRepository(){
        if (repository==null){
            repository = new Repository();
        }
        return repository;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setExpenceList(List<Expence> expenceList) {
        this.expenceList = expenceList;
    }
}
