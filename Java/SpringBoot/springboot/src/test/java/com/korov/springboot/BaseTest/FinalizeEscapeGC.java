package com.korov.springboot.BaseTest;

public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println(" yes, i am still alive:)");
    }

    public void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize mehtod executed!");
        SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        // 对象第一次成功拯救自己
        SAVE_HOOK=null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no, i am dead:(");
        }
        /**
         * 下面这段代码与上面完全相同，但是自救失败了
         *  原因：因为第一次筛选的时候已经执行过finalize方法，所以直接被回收没有放到F-Queue中
         */
        SAVE_HOOK=null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no, i am dead:(");
        }
    }
}
