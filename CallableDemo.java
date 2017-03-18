import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(30);

        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(
                executorService);

        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
        //提交任务

        for (Integer i = 0; i < 100; i++) {
            MyThread3 mm = new MyThread3(i);
//            Future f3 = completionService.submit(mm);
            Future f3 = pool.submit(mm);

            futureList.add(f3);
        }
        int sum = 0;

        for (int i = 0; i < 100; i++) {
//            int temp = Integer.valueOf(completionService.take().get().toString());
            int temp = Integer.valueOf(futureList.get(i).get().toString());
            sum += temp;
            System.out.println(temp);
        }
        System.out.print("1到100的和:" + sum + "\t");
        //按计算结果依次获取


        executorService.shutdown();


    }
}


class MyThread3 implements Callable {

    private int name;

    MyThread3(int name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(100);
        return name;
    }
}
