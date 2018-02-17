package craftedcart.smbrelquickpatcher;

public class Stage {
    public int id;
    public EnumStageTheme theme;

    @Override
    public String toString() {
        return String.format("%d [0x%02X %s]", id, theme.ordinal(), theme.toString());
    }
}
