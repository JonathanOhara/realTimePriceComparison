

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.*;

public class Test {

    public static void main(String[] args) {


        List<String> problemINput = List.of("apple,facebook,google", "banana,facebook", "facebook,google,tesla", "intuit,google,facebook");

        Map<String, List<Node>> db = new HashMap<>();

        problemINput.stream().map(commaSeparatedKeyWords -> Arrays.asList(commaSeparatedKeyWords.split(",")))
                .forEach(keywords -> keywords.forEach(key -> {
                    key = key.trim();
                    List<String> otherKeywords = new ArrayList<>(keywords);
                    otherKeywords.remove(key);

                    List<Node> nodeList = Optional.ofNullable(db.get(key)).orElse(new ArrayList<>());

                    nodeList.add(new Node(otherKeywords));

                    db.put(key, nodeList);
                }));

        List<String> example1 = List.of("apple");

        solve(db, example1);
        
        List<String> example2 = List.of("facebook", "google");
        
        solve(db, example2);
        
    }

    private static void solve(Map<String, List<Node>> db, List<String> immutableInput) {
        List<String> input = new ArrayList<>(immutableInput);
        List<Node> nodes = db.get(input.remove(0));

        Set<String> output = new HashSet<>();

        nodes.forEach(node -> {
            output.addAll(node.getOther(immutableInput));
        });


        while(!input.isEmpty()){
            nodes = db.get(input.remove(0));

            List<String> outputPerKeyWord = new ArrayList<>();
            nodes.forEach(node -> {
                outputPerKeyWord.addAll(node.getOther(immutableInput));
            });

            output.retainAll(outputPerKeyWord);
        }

        System.out.println(output);

    }

    private static class Node {
        private List<String> other;

        private List<String> getOther(List<String> inputs){
            List<String> otherWithoutInput = new ArrayList<>(other);
            otherWithoutInput.removeAll(inputs);
            return otherWithoutInput;
        }

        public Node(List<String> other) {
            this.other = other;
        }
    }
}
