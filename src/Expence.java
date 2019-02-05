import java.io.Serializable;
import java.util.Date;

public class Expence implements Serializable {
    private Long expenceId = System.currentTimeMillis();
    private Long categoryId;
    private Double amount;
    private Date date;
    private String remark;

    public Expence() {
    }

    public Expence(Long categoryId, Double amount, Date date, String remark) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.remark = remark;
    }

    public Long getExpenceId() {
        return expenceId;
    }

    public void setExpenceId(Long expenceId) {
        this.expenceId = expenceId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
