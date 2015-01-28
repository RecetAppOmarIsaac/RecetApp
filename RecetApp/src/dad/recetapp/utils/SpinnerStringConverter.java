package dad.recetapp.utils;

import javafx.util.StringConverter;

/**
 *  StringConverter ad-hoc para los ListSpinner de este proyecto.
 */
public class SpinnerStringConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer integer) {
        return integer.toString();
    }

    @Override
    public Integer fromString(String string) {
        if (string.matches("-?\\d+"))
            return Integer.valueOf(string);
        else
            return -1;
    }
}
