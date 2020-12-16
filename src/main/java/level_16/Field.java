package level_16;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@EqualsAndHashCode
public class Field {
    final static Pattern p = Pattern.compile("^([^:]+): (\\p{N}+)-(\\p{N}+) or (\\p{N}+)-(\\p{N}+)$");
    private String name;
    private int r1l;
    private int r1h;
    private int r2l;
    private int r2h;

    public Field(String descr) {
        Matcher m = p.matcher(descr);
        if (!m.matches()) {
            throw new RuntimeException("Can not parse the rule: " + descr);
        }
        name = m.group(1);
        r1l = Integer.parseInt(m.group(2));
        r1h = Integer.parseInt(m.group(3));
        r2l = Integer.parseInt(m.group(4));
        r2h = Integer.parseInt(m.group(5));
    }

    public boolean validate(int number) {
        return (number >= r1l && number <= r1h) || (number >= r2l && number <= r2h);
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                " " + r1l +
                "-" + r1h +
                " or " + r2l +
                "-" + r2h +
                '}';
    }
}
