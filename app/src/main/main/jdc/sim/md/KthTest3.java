package jdc.sim.md;

public class KthTest3 {
    public static void main(String[] args) {
        String[] input = {"AnNiruddHA Routh", "LOVES", "to", "COdE everyDAY"};
        StringBuilder answer = new StringBuilder();

        for (String token : input) {
            token = token.toLowerCase();
            char[] chars = token.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (j == 0 ) {
                    answer.append(String.valueOf(chars[j]).toUpperCase());
                } else if (chars[j] == ' ') {
                    answer.append(" ");
                    answer.append(String.valueOf(chars[j+1]).toUpperCase());
                    j++;

                } else {
                    answer.append(chars[j]);
                }
            }
            answer.append(" ");
        }
        System.out.println(answer.toString());
    }
}
