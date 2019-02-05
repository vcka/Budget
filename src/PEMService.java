import java.io.*;
import java.util.*;

public class PEMService {

    private Repository repo = Repository.getRepository();
    private ReportService reportService = new ReportService();

    private Scanner in = new Scanner(System.in);
    private int choice;

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
            System.out.println("No such expense.");
        }
    }


    private void onExpenceEntry() {
        System.out.println("Please input expense details...");
        onCategoryList();

        System.out.print("Choose category ");
        int catChoice = in.nextInt();
        Category selectedCategory = repo.categoryList.get(catChoice - 1);
        System.out.println("You chose: " + selectedCategory.getName());

        System.out.print("Please enter the amount: ");
        Double amount = in.nextDouble();
        System.out.print("Please write description: ");
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
        System.out.println("Your expense added.");
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

    private void printMenu() {
        System.out.println("------------Menu-----------");
        System.out.println("1. Add category");
        System.out.println("2. Category list");
        System.out.println("3. Expense entry");
        System.out.println("4. Expense list");
        System.out.println("5. Monthly expense list");
        System.out.println("6. Yearly expense list");
        System.out.println("7. Categorized expense list");
        System.out.println("8. Delete expense by nr");
        System.out.println("9. Delete category by nr");
        System.out.println("0. Exit");
        System.out.println("---------------------------");
        System.out.println("Enter your choice: ");
        choice = in.nextInt();
    }

    private void pressAnyKeyToContinue() {
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onExit() {
        try {
            repo.categorySave();
            repo.expenceSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void showMenu() {
        try {
            repo.categoryLoad();
            repo.expenceLoad();
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
}
