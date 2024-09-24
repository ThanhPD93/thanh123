package mockProject.team3.Vaccination_20.dto.report;

public class InjectionResultStats {

    private String monthName;
    private Long count;

    public InjectionResultStats(int month, Long count) {
        this.monthName = getMonthName(month);
        this.count = count;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    private String getMonthName(int month) {
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return months[month - 1];
    }
}
