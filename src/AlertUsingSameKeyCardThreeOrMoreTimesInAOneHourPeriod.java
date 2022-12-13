import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 1604. Alert Using Same Key-Card Three or More Times in a One Hour Period (https://leetcode.com/problems/alert-using-same-key-card-three-or-more-times-in-a-one-hour-period/)
 */
public class AlertUsingSameKeyCardThreeOrMoreTimesInAOneHourPeriod {

    /******** Solution 1: Group times by name + Scan size = 3 windows ***************/
    /**
     * Time: O(N + NlogN) = O(NlogN)   Space: O(N)
     */
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        // group times by name
        Map<String, List<Integer>> nameToTime = new HashMap<>();
        for (int i = 0; i < keyName.length; i++) {
            nameToTime.putIfAbsent(keyName[i], new ArrayList<>());
            nameToTime.get(keyName[i]).add(translate(keyTime[i]));
        }
        // for each name, sort times and check size = 3 windows
        Set<String> res = new TreeSet<>(); // keep result in sorted order!!!
        for (String name : nameToTime.keySet()) {
            List<Integer> times = nameToTime.get(name);
            if (times.size() < 3) continue;
            Collections.sort(times);
            for (int i = 2; i < times.size() && !res.contains(name); i++) {
                if (times.get(i) - times.get(i - 2) <= 60) res.add(name);
            }
        }
        return new ArrayList<>(res);
    }

    private int translate(String time) {
        String hour = time.substring(0, 2);
        String minute = time.substring(3, 5);
        return Integer.parseInt(hour) * 60 + Integer.parseInt(minute);
    }

    public static void main(String[] args) {
        AlertUsingSameKeyCardThreeOrMoreTimesInAOneHourPeriod solution = new AlertUsingSameKeyCardThreeOrMoreTimesInAOneHourPeriod();
        // TC1
        System.out.println(solution.alertNames(
                new String[]{"daniel","daniel","daniel","luis","luis","luis","luis"},
                new String[]{"10:00","10:40","11:00","09:00","11:00","13:00","15:00"}
        ));
        // TC2
        System.out.println(solution.alertNames(
                new String[]{"alice","alice","alice","bob","bob","bob","bob"},
                new String[]{"12:01","12:00","18:00","21:00","21:20","21:30","23:00"}
        ));
        // TC3
        System.out.println(solution.alertNames(
                new String[]{"a","a","a","a","a","a","a","b","b","b","b","b","b","b","b","c","c","c","c","c","d","d","d","d","d","e","e","e","e","e","e","f","f","f","f","f","g","g","g","g","g","h","h","h","h","h","h","h","h","i","i","i","i","i","j","j","j","j","j","j","j","j","k","k","k","k","k","k","k","k","l","l","l","l","l","l","m","m","m","m","m","n","n","n","n","n","n","n","n","o","o","o","o","o","o","p","p","p","p","p","p","p","p","q","q","q","q","q","q","q","q","r","r","r","r","r","r","r","r","s","s","s","s","s","s","s","s","t","t","t","t","t"},
                new String[]{"20:31","00:29","13:38","12:35","12:51","19:42","02:57","00:10","21:35","11:46","17:38","20:00","16:01","03:07","21:55","06:47","18:37","02:45","17:12","08:38","17:18","19:52","18:45","00:43","19:30","13:34","14:22","07:45","17:28","22:49","23:26","07:39","11:20","00:45","00:36","03:34","05:36","00:33","04:34","12:07","07:37","12:58","22:18","04:10","00:51","22:50","12:17","09:21","22:24","21:32","23:25","15:12","11:30","21:00","22:01","16:01","03:20","18:54","06:50","08:34","13:11","05:53","08:55","04:35","02:19","04:23","09:02","01:44","14:29","08:34","09:41","23:56","04:41","17:27","11:16","03:17","05:11","11:11","06:37","14:17","02:30","18:11","07:57","02:40","21:15","03:03","06:45","09:12","01:48","04:00","17:17","20:42","05:30","04:39","03:31","05:42","21:36","19:09","12:38","03:15","20:11","03:49","17:21","20:41","06:21","02:34","01:37","06:47","09:07","10:57","03:14","16:50","07:34","19:31","14:00","01:07","14:16","02:51","06:30","05:14","19:30","20:13","19:24","12:56","23:37","12:26","00:48","14:15","09:30","19:57","04:53","10:20"}
        ));
    }
}
