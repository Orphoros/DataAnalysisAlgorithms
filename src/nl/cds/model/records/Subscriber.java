package nl.cds.model.records;

public record Subscriber(String lastName, String initials, String city) implements Comparable<Subscriber>{

    @Override
    public int compareTo(Subscriber s) {
        int compareResult = this.lastName.compareTo(s.lastName);
        if (compareResult == 0) compareResult = this.initials.compareTo(s.initials);
        return compareResult;
    }

    @Override
    public String toString() {
        return String.format("Customer: %s %s (living in %s) ", lastName, initials, city);
    }
}
