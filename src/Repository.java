import java.io.*;
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

    public void categorySave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Categorys.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(categoryList);
        oos.close();
    }

    public void categoryLoad() throws IOException {
        FileInputStream fis = new FileInputStream("Categorys.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            setCategoryList((List<Category>) ois.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ois.close();
    }

    public void expenceSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Expence.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(expenceList);
        oos.close();
    }

    public void expenceLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Expence.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setExpenceList((List<Expence>) ois.readObject());
        ois.close();
    }
}
