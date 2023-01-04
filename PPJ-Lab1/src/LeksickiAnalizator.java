import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeksickiAnalizator {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int lineCounter = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                char[] listOfChars = line.toCharArray();
                int i = 0;

                while(i < listOfChars.length) {
                    if(listOfChars[i] == ' ' || listOfChars[i] == '\n') {
                        if (listOfChars[i] == ' ') {
                            i++;
                        } else {
                            break;
                        }
                        i--;

                    } else if(Character.isLetter(listOfChars[i])) {
                        if(((i + 2 < listOfChars.length && listOfChars[i + 2] == ' ') || (i + 2 == listOfChars.length)) && contains(listOfChars[i])) {
                            if (listOfChars[i] == 'z' && listOfChars[i + 1] == 'a') {
                                System.out.println("KR_ZA " + lineCounter + " za");
                                i++;
                            } else if (listOfChars[i] == 'a' && listOfChars[i + 1] == 'z') {
                                System.out.println("KR_AZ " + lineCounter + " az");
                                i++;
                            } else if (listOfChars[i] == 'o' && listOfChars[i + 1] == 'd') {
                                System.out.println("KR_OD " + lineCounter + " od");
                                i++;
                            } else if (listOfChars[i] == 'd' && listOfChars[i + 1] == 'o') {
                                System.out.println("KR_DO " + lineCounter + " do");
                                i++;
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("IDN ");
                            sb.append(lineCounter).append(" ");

                            while(i < listOfChars.length && (Character.isLetterOrDigit(listOfChars[i]))) {
                                sb.append(listOfChars[i]);
                                i++;
                            }
                            i--;
                            System.out.println(sb);
                        }
                    } else if (Character.isDigit(listOfChars[i])) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("BROJ ");
                        sb.append(lineCounter).append(" ");
                        while(i < listOfChars.length && Character.isDigit(listOfChars[i])) {
                            sb.append(listOfChars[i]);
                            i++;
                        }
                        i--;
                        System.out.println(sb);
                    } else {
                        if(listOfChars[i] == '(') {
                            System.out.println("L_ZAGRADA " + lineCounter + " (");
                        }
                        if(listOfChars[i] == ')') {
                            System.out.println("D_ZAGRADA " + lineCounter + " )");
                        }
                        if(listOfChars[i] == '*') {
                            System.out.println("OP_PUTA " + lineCounter + " *");
                        }
                        if(listOfChars[i] == '/') {
                            if(i + 1 < listOfChars.length && listOfChars[i + 1] == '/') {
                                break;
                            } else {
                                System.out.println("OP_DIJELI " + lineCounter + " /");
                            }
                        }
                        if(listOfChars[i] == '+') {
                            System.out.println("OP_PLUS " + lineCounter + " +");
                        }
                        if(listOfChars[i] == '-') {
                            System.out.println("OP_MINUS " + lineCounter + " -");
                        }
                        if(listOfChars[i] == '=') {
                            System.out.println("OP_PRIDRUZI " + lineCounter + " =");
                        }
                    }
                    i++;
                }
            }
            lineCounter++;
        }
    }

    static boolean contains(char ch) {
        char[] arr = new char[4];
        arr[0] = 'a';
        arr[1] = 'z';
        arr[2] = 'd';
        arr[3] = 'o';
        for (char c : arr) {
            if (ch == c)
                return true;
        }
        return false;
    }
}
