import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SemantickiAnalizator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        ArrayList<String> OPS = new ArrayList<>();
        OPS.add("OP_PRIDRUZI");
        OPS.add("KR_AZ");
        OPS.add("IDN");
        OPS.add("KR_ZA");

        while (sc.hasNext()) {
            String input = sc.nextLine();
            list.add(input);
        }

        list = list.stream().map(String::strip)
                .filter(s -> (!s.startsWith("<") && !s.endsWith(">") && !s.contains("$")))
                .collect(Collectors.toList());

        ArrayList<HashMap<String, Integer>> ahash = new ArrayList<>();
        ahash.add(new HashMap<>());

        int here = 0;
        while (here < list.size()) {
            String[] parts = list.get(here).split(" ");
            if (parts[0].equals(OPS.get(0))) {
                here++;
                continue;
            }
            if (parts[0].equals(OPS.get(1))) {
                ahash.remove(ahash.size() - 1);
                here++;
                continue;
            }
            String[] parts2 = {"", "", ""};
            if ( here + 1 < list.size() )
                parts2 = list.get(here + 1).split(" ");
            if (parts[0].equals(OPS.get(2)) && parts2[0].equals(OPS.get(0))) {
                HashMap<String, Integer> last = ahash.get(ahash.size() - 1);
                if (last.containsKey(parts[2])) {
                    int min = Math.min(Integer.parseInt(parts[1]), last.get(parts[2]));
                    last.put(parts[2], min);
                } else
                    last.put(parts[2], Integer.parseInt(parts[1]));

                ahash.set(ahash.size() - 1, last);
                here += 1;
                continue;
            }
            if (parts[0].equals(OPS.get(2))) {
                int index = -1;
                for (int i = ahash.size() - 1; i >= 0; i--) {
                    if (ahash.get(i).containsKey(parts[2])) {
                        index = i;
                        break;
                    }
                }

                if (index == -1 || ahash.get(index).get(parts[2]) >= Integer.parseInt(parts[1])) {
                    System.out.println("err " + parts[1] + " " + parts[2]);
                    break;
                }
                System.out.println(parts[1] + " " + ahash.get(index).get(parts[2]) + " " + parts[2]);
            }


            if (parts[0].equals(OPS.get(3))) {
                ahash.add(new HashMap<>());
                ahash.get(ahash.size() - 1).putAll(ahash.get(ahash.size() - 2));
                ahash.get(ahash.size() - 1).put(parts2[2], Integer.parseInt(parts2[1]));
                here += 3;
                while (here < list.size() && Integer.parseInt(list.get(here).split(" ")[1]) == Integer.parseInt(parts[1])) {
                    String[] parts3 = list.get(here).split(" ");
                    if (parts3[0].equals(OPS.get(2))) {
                        int index = -1;
                        for (int i = ahash.size() - 1; i >= 0; i--) {
                            if (ahash.get(i).containsKey(parts3[2])) {
                                index = i;
                                break;
                            }
                        }

                        if (index == -1 || ahash.get(index).get(parts3[2]) >= Integer.parseInt(parts3[1])) {
                            System.out.println("err " + parts3[1] + " " + parts3[2]);
                            System.exit(0);
                        }

                        System.out.println(parts3[1] + " " + ahash.get(index).get(parts3[2]) + " " + parts3[2]);
                    }
                    here++;
                }
                continue;
            }
            here++;
        }
    }
}