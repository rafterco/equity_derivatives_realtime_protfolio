package jdc.sim.md;

public class KthTest2 {

    public static void main(String[] args) {
        String input = "21462675756";
        StringBuilder output = new StringBuilder();

        char[] chars = input.toCharArray();

        for(int i = 0; i < chars.length; i++) {
            int value = Character.getNumericValue(chars[i]);
            output.append(value);

            if (i + 1 < chars.length) {
                int secondValue = Character.getNumericValue(chars[i + 1]);
                if (value % 2 == 0 && secondValue % 2 == 0) {
                    output.append("*");
                } else if (value % 2 != 0 && secondValue % 2 != 0) {
                    output.append("-");
                }
            }
        }
        System.out.println(output.toString());
    }
}
