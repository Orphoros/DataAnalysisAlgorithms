package nl.cds.model.records;

public record Employee(String firstName, String lastName, String function) implements Comparable<Employee>{

    @Override
    public int compareTo(Employee s) {
        int compareResult = this.lastName.compareTo(s.lastName);
        if (compareResult == 0){
            compareResult = this.firstName.compareTo(s.firstName);
            if (compareResult == 0) compareResult = this.function.compareTo(s.function);
        }
        return compareResult;
    }

    @Override
    public String toString() {
        return String.format("Employee: %s %s (%s) ",firstName, lastName, function);
    }
}
