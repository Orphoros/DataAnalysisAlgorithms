package nl.cds.model.records;

public record Incident(String id, String category, String priority, int resolveDuration) implements Comparable<Incident>{

    @Override
    public String toString() {
        return String.format("Incident #%s: (%s category) prioritised as %s, ~%s minutes",id, category, priority, resolveDuration);
    }

    @Override
    public int compareTo(Incident that) {
        //Assuming an incident's id always starts with an 'I' and then followed by numbers
        return Integer.parseInt(this.id.substring(1)) - Integer.parseInt(that.id.substring(1));
    }
}
