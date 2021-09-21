package Server.Commands;

public abstract class AbstractCommand implements Command {

    private String name;
    private String descr;

    public AbstractCommand(String name, String descr) {
        this.name = name;
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public String toString() {
        return name + " (" + descr + ")";
    };

    @Override
    public int hashCode() {
        return name.hashCode() + descr.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && descr.equals(other.descr);
    }
}