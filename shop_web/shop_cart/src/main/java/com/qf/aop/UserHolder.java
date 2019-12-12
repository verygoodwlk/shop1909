package com.qf.aop;

import com.qf.entity.User;

public class UserHolder {

    private static ThreadLocal<User> user = new ThreadLocal<>();

    public static boolean isLogin(){
        return user.get() != null;
    }

    public static void setUser(User user){
        UserHolder.user.set(user);
    }

    public static User getUser(){
        return user.get();
    }

// Map<ThreadName, xxxx>
//    public static void main(String[] args) {
//
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//        threadLocal.set("Hello!");
//
//        new Thread(){
//            @Override
//            public void run() {
//                threadLocal.set("World!");
//                System.out.println(threadLocal.get());
//            }
//        }.start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(threadLocal.get());
//    }

}
