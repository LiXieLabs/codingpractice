import java.util.ArrayDeque;
import java.util.Deque;

public class SumOfSubarrayRanges {

    // Same as 84. Largest Rectangle in Histogram => https://leetcode.com/problems/largest-rectangle-in-histogram/
    // Same as 907. Sum of Subarray Minimums => https://leetcode.com/problems/sum-of-subarray-minimums/
    // Same as 2104. Sum of Subarray Ranges => https://leetcode.com/problems/sum-of-subarray-ranges/

    /************** Solution 1: 2 points embedded for loop **************/
    public long subArrayRanges1(int[] nums) {
        // Java primitive data types
        // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
        // 必须用 long
        long res = 0;
        for (int l = 0; l < nums.length; l++) {
            int curMin = nums[l], curMax = nums[l];
            for (int r = l; r < nums.length; r++) {
                curMin = Math.min(curMin, nums[r]);
                curMax = Math.max(curMax, nums[r]);
                res += (curMax - curMin);
            }
        }
        return res;
    }

    /************** Solution 2: Monotonic Stacks + DP **************/
    public long subArrayRanges(int[] nums) {
        Deque<Integer> ascendStack = new ArrayDeque<>(), descendStack = new ArrayDeque<>();
        long res = 0;
        for (int i = 0; i <= nums.length; i++) {
            while (!ascendStack.isEmpty() && nums[ascendStack.peek()] > (i == nums.length ? Integer.MIN_VALUE : nums[i])) {
                int middle = ascendStack.pop();
                int prevLess = ascendStack.isEmpty() ? -1 : ascendStack.peek();
                res -= (long) nums[middle] * (middle - prevLess) * (i - middle);
            }
            ascendStack.push(i);
            while (!descendStack.isEmpty() && nums[descendStack.peek()] < (i == nums.length ? Integer.MAX_VALUE : nums[i])) {
                int middle = descendStack.pop();
                int prevGreater = descendStack.isEmpty() ? -1 : descendStack.peek();
                res += (long) nums[middle] * (middle - prevGreater) * (i - middle);
            }
            descendStack.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        SumOfSubarrayRanges solution = new SumOfSubarrayRanges();
        System.out.println(solution.subArrayRanges(new int[]{1,2,3}));
        System.out.println(solution.subArrayRanges(new int[]{1,3,3}));
        System.out.println(solution.subArrayRanges(new int[]{-31372,86677,70463,37727,-91683,-41347,-90576,-82174,-84198,
                -5148,-12591,-34156,49770,9666,-77075,-57678,-31101,-47531,-86306,-91337,-89507,-24917,-87692,-39171,98075,
                17787,-42549,34352,-70752,71832,70055,-1026,3784,2190,-36669,959,50619,97129,77088,54049,51707,72052,59230,
                -96834,-14048,-9619,84853,99362,69885,74086,-28737,23060,-63323,13156,-72998,94336,-75409,58266,-86800,-54564,
                80773,40687,-47207,43609,-56556,21192,-48024,-58907,1629,-65561,-68397,31862,-2201,-34966,43542,-59201,-3637,
                -21936,-93559,49435,23249,-54299,70508,-90795,-3620,-33894,43927,10208,-7390,86931,48175,81859,95058,-16614,
                38066,-99361,63621,-99285,-47111,29933,73901,60455,46586,-84117,35256,-89853,33383,-91662,82979,-48835,-93877,
                -80929,-98904,-47773,69451,85183,-14449,-51496,75765,35062,12456,35254,-16363,80792,-3414,9244,62961,-52057,
                56344,-50277,-26870,-63323,54993,75596,-93637,-78526,-3058,-30560,82233,-50795,-5290,-641,-83040,13524,86725,
                23735,29280,43938,-43995,-8992,-83717,-62090,74538,58682,-56550,-8638,61528,-87974
        }));
        System.out.println(solution.subArrayRanges(new int[]{
                44100,-56253,-87825,85180,9757,38400,-79790,70906,-84011,-47443,7591,51955,82857,88601,-40488,50522,67401,52187,
                -95259,29098,-90134,89896,-77967,-22428,-28532,-90237,-99696,-62447,87981,-12925,7985,39797,-47714,-63656,-74771,
                22170,-23500,93592,-4531,84380,-6001,85246,-97211,-59961,94258,61193,5647,50267,-84298,-74537,93428,-65618,65642,
                13034,29918,71695,-73036,-77551,534,-89410,-48966,75211,-58026,80627,15452,36970,8639,24721,-69696,53800,-31659,
                57435,-48166,-20745,45691,27731,-58085,84151,68345,89265,-86124,-62665,3042,-91274,778,13232,14311,16015,-53543,
                66854,-71929,84591,-12147,-92987,14605,48389,-58397,-77052,93904,-18773,-25516,-41432,82123,-21665,-54708,-12475,
                30278,-67332,-26924,-15315,8680,49804,19357,-90558,80541,21961,-54950,91191,82745,-81144,49588,49217,54596,-618,
                78471,-18303,78836,48380,-34052,83040,27224,93503,94966,-53188,-96363,22665,-53675,-36581,72081,90508,-18263,-84327,
                86545,-11804,-3786,78317,96389,68162,-14903,-62461,-55365,-66125,-82587,-4308,93327,-76806,17561,53395,-64928,-42247,
                21804,47261,-32844,-24622,-45726,34754,29110,15750,38051,-12767,85394,18749,59426,-23824,-49666,-1690,24342,-31252,
                -3506,-63185,-92805,-16740,-51909,-80956,-28454,-59771,42745,-38088,20909,40377,54491,6580,-97200,-56717,55680,-22376,
                70854,65247,-12746,82514,56879,65217,-49417,5355,53319,63307,78552,49928,-21622,85397,-3109,-34472,-88532,37545,-87396,
                -22167,-78133,-69524,76682,-10739,-13888,-92714,57196,-99809,62781,-34925,24895,-6503,37424,75340,53525,-40106,28872,
                75499,42659,99828,-81267,-76421,-91194,-71986,15377,-24108,23000,9246,-87219,-46664,-39120,2680,54553,86907,-44211,37793,
                -52369,40984,-83117,89487,-95440,90367,94347,-35235,-31538,-38342,-65860,67173,-60069,-40497,-77873,42434,90335,22860,
                28309,-25074,-48919,64655,98456,-76009,-42841,94788,-1502,-8159,-33158,20468,32838,-70461,62674,-36618,-94199,-10011,
                -65259,-47358,84651,9074,-35506,15918,-36663,65790,78262,-72474,-87275,-9757,-4125,64710,85251,-15492,24729,53548,9289,
                50720,34291,-76939,-8976,84142,53871,-57634,-81699,-49082,52811,8794,-20580,-76504,90150,-83198,-32686,49607,74307,46813,
                -98412,-43808,58346,18950,72143,-95992,-99571,17346,2416,56359,9009,-81938,98957,-34143,-53695,-42578,-29657,-46722,31255,
                59060,69214,-64971,66461,-38996,-4668,-13508,-49576,24564,48014,925,79429,59323,86294,22145,64718,-10480,83072,-34638,
                21766,60961,67618,82348,89014,24046,-89014,18861,-30218,-7192,19708,-18537,25481,-21690,-23903,35083,34470,-29076,88710,
                -65080,-69025,84659
        }));
    }
}