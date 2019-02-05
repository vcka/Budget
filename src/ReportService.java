import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ReportService {
    Repository repo = Repository.getRepository();

    public Map<String, Double>  calculateMonthlyTotal(){
        Map<String, Double> m = new TreeMap<>();
        for (Expence expence : repo.expenceList) {
            Date expDate = expence.getDate();
            String yearMonth = DateUtil.getYearAndMonth(expDate);
            if (m.containsKey(yearMonth)){
                //then expence is allready present for a month
                Double total =  m.get(yearMonth);
                total = total + expence.getAmount();
                m.put(yearMonth, total);
            }else {
                //this month is not added, add here
                m.put(yearMonth, expence.getAmount());
            }
        }
        return m;
    }

    public Map<Integer, Double>  calculateYearlyTotal(){
        Map<Integer, Double> m = new TreeMap<>();
        for (Expence expence : repo.expenceList) {
            Date expDate = expence.getDate();
            Integer year = DateUtil.getYear(expDate);
            if (m.containsKey(year)){
                //then expence is allready present for a year
                Double total =  m.get(year);
                total = total + expence.getAmount();
                m.put(year, total);
            }else {
                //this year is not added, add here
                m.put(year, expence.getAmount());
            }
        }
        return m;
    }

    public Map<String, Double>  calculateCategorysTotal(){
        Map<String, Double> m = new TreeMap<>();
        for (Expence expence : repo.expenceList) {
            Long categoryID= expence.getCategoryId();
            String categoryName = getCategoryNameByID(categoryID);
            if (m.containsKey(categoryName)){
                //then expence is allready present for a category
                Double total =  m.get(categoryName);
                total = total + expence.getAmount();
                m.put(categoryName, total);
            }else {
                //this category is not added, add here
                m.put(categoryName, expence.getAmount());
            }
        }
        return m;
    }
    public String getCategoryNameByID(Long categoryId) {
        for (Category c : repo.categoryList) {
            if (c.getCategoryId().equals(categoryId)) {
                return c.getName();
            }
        }
        return null;
    }

}
