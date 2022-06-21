package jdc.sim.md;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class KthTest {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        int paramCount = 0;
        List<Integer> outputList = new ArrayList<>();

        int numberOfCourses;
        Map<Integer, List<Integer>> courseMap = new HashMap<>();

        for (Map.Entry<Integer, List<Integer>> entry : courseMap.entrySet()) {
            entry.getValue().stream().forEach(integer -> {
                if (!outputList.contains(integer)) {

                }
                outputList.add(entry.getKey());
            });
        }

        Set<Integer> myset = new HashSet<>();

        String line;
        List<String> inputs = new ArrayList<>();
        while (!(line = in.readLine()).equals("zzz")) {
            if(paramCount == 0) {
                numberOfCourses = Integer.parseInt(line);
            } else {
                String[] s = line.split(" ");
                for(int i = 0; i < line.split(" ").length; i++) {

                }

            }
            inputs.add(line);
            break;



        }





    }
}
