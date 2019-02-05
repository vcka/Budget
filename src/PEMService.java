import java.io.*;
import java.util.*;

public class PEMService {

    private Repository repo = Repository.getRepository();
    private ReportService reportService = new ReportService();

    private Scanner in = new Scanner(System.in);
    private int choice;

    public void showMenu() {
        try {
            categoryLoad();
            expenceLoad();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No data loaded.");
        }

        while (true) {
            printMenu();
            switch (choice) {
                case 1:
                    onAddCategory();
                    pressAnyKeyToContinue();
                    break;
                case 2:
                    onCategoryList();
                    pressAnyKeyToContinue();
                    break;
                case 3:
                    onExpenceEntry();
                    pressAnyKeyToContinue();
                    break;
                case 4:
                    onExpenceList();
                    pressAnyKeyToContinue();
                    break;
                case 5:
                    onMonthlyExpenceList();
                    pressAnyKeyToContinue();
                    break;
                case 6:
                    onYearlyExpenceList();
                    pressAnyKeyToContinue();
                    break;
                case 7:
                    onCategorizedExpenceList();
                    pressAnyKeyToContinue();
                    break;
                case 8:
                    onExpenceDelete();
                    pressAnyKeyToContinue();
                    break;
                case 9:
                    onCategoryDelete();
                    pressAnyKeyToContinue();
                    break;
                case 0:
                    onExit();
                    break;
            }
        }
    }

    private void onCategorizedExpenceList() {
        System.out.println("Categorized expence list");
        Map<String, Double> resultMap = reportService.calculateCategorysTotal();
        Set<String> categorys = resultMap.keySet();
        Double total = 0.0D;
        for (String categoryName : categorys) {
            total = resultMap.get(categoryName) + total;
            System.out.println(categoryName + " : " + resultMap.get(categoryName));
        }
        System.out.println("Category total: " + total);

    }

    private void onYearlyExpenceList() {
        System.out.println("Yearly expence list");
        Map<Integer, Double> resultMap = reportService.calculateYearlyTotal();
        Set<Integer> years = resultMap.keySet();
        Double total = 0.0D;
        for (Integer year : years) {
            total += resultMap.get(year);
            System.out.println(year + " : " + resultMap.get(year));
        }
        System.out.println("All times total: " + total);
    }

    private void onMonthlyExpenceList() {
        System.out.println("Monthly expence list");
        Map<String, Double> resultMap = reportService.calculateMonthlyTotal();
        Set<String> keys = resultMap.keySet();
        for (String yearMonth : keys) {
            System.out.println(yearMonth + " : " + resultMap.get(yearMonth));
        }
    }

    private void onExpenceList() {
        System.out.println("Expence list");
        List<Expence> expenceList = repo.expenceList;
        for (int i = 0; i < expenceList.size(); i++) {
            Expence expence = expenceList.get(i);
            String catName = reportService.getCategoryNameByID(expence.getCategoryId());
            String dateString = DateUtil.dataToString(expence.getDate());
            System.out.println((i + 1) + " " + catName + " " + expence.getAmount() + ", " + expence.getRemark() + ", " + dateString + ", " + expence.getCategoryId());
        }
    }

    private void onCategoryDelete() {
        onCategoryList();
        System.out.print("Please enter the category to remove: ");
        int nr = in.nextInt();

        if (nr <= repo.categoryList.size()) {
            System.out.println("Category " + repo.categoryList.get(nr - 1).getName() + " will be removed.");
            repo.expenceList.removeIf(obj -> obj.getCategoryId().equals(repo.categoryList.get(nr - 1).getCategoryId()));
            repo.categoryList.remove(nr - 1);
        } else {
            System.out.println("No such category.");
        }
    }

    private void onExpenceDelete() {
        onExpenceList();
        System.out.print("Please enter the expence to remove: ");
        int nr = in.nextInt();

        if (nr <= repo.expenceList.size()) {
            System.out.println("Expence " + repo.expenceList.get(nr - 1).getRemark() + " will be removed.");
            repo.expenceList.remove(nr - 1);
        } else {
            System.out.println("No such expence.");
        }
    }


    private void onExpenceEntry() {
        System.out.println("Please input expence details...");
        onCategoryList();

        System.out.print("Choose category ");
        int catChoice = in.nextInt();
        Category selectedCategory = repo.categoryList.get(catChoice - 1);
        System.out.println("You chose: " + selectedCategory.getName());

        System.out.print("Please enter the amount: ");
        Double amount = in.nextDouble();
        System.out.print("Please enter the remark: ");
        in.nextLine();
        String remark = in.nextLine();


        System.out.print("Enter date (DD/MM/YYYY): ");
        String dateAsString = in.nextLine();
        Date date = DateUtil.stringToData(dateAsString);

        Expence expence = new Expence();
        expence.setCategoryId(selectedCategory.getCategoryId());
        expence.setAmount(amount);
        expence.setRemark(remark);
        expence.setDate(date);
        //Store expence
        repo.expenceList.add(expence);
        System.out.println("Your expence added.");
    }

    private void onCategoryList() {
        System.out.println("Listing categories");
        List<Category> clist = repo.categoryList;
        for (int i = 0; i < clist.size(); i++) {
            Category c = clist.get(i);
            System.out.println((i + 1) + ". " + c.getName() + ", " + c.getCategoryId());
        }
    }

    private void onAddCategory() {
        in.nextLine();//Unu1sed input
        System.out.print("Please name category: ");
        String catName = in.nextLine();
        Category cat = new Category(catName);
        repo.categoryList.add(cat);
        System.out.println("Category created.");
    }

    public void printMenu() {
        System.out.println("------------Menu-----------");
        System.out.println("1. Add category");
        System.out.println("2. Category list");
        System.out.println("3. Expence entry");
        System.out.println("4. Expence list");
        System.out.println("5. Monthly expence list");
        System.out.println("6. Yearly expence list");
        System.out.println("7. Categorized expence list");
        System.out.println("8. Delete expence by nr");
        System.out.println("9. Delete category by nr");
        System.out.println("0. Exit");
        System.out.println("---------------------------");
        System.out.println("Enter your choice: ");
        choice = in.nextInt();
    }

    public void categorySave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Categorys.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(repo.categoryList);
        oos.close();
    }

    public void categoryLoad() throws IOException {
        FileInputStream fis = new FileInputStream("Categorys.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            repo.setCategoryList((List<Category>) ois.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ois.close();
    }

    public void expenceSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Expence.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(repo.expenceList);
        oos.close();
    }

    public void expenceLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Expence.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        repo.setExpenceList((List<Expence>) ois.readObject());
        ois.close();
    }

    public void pressAnyKeyToContinue() {
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onExit() {
        try {
            //save("failas");
            categorySave();
            expenceSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


}
