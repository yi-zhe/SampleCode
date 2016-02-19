package com.liuyang.code.controllers;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/2/9.
 */
public class AsyncTask extends BaseFragment {

    private android.os.AsyncTask task;

    @Override
    protected int layoutId() {
        return R.layout.async_task;
    }

    @Override
    protected void init() {
        task = new MockAsyncTask();
        task.execute(new Object[]{"AsyncTask"});
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (task != null) task.cancel(true);
    }

    private class MockAsyncTask extends android.os.AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            show("pre execute");
        }

        @Override
        protected String doInBackground(String... params) {
            int i = 0;
            try {
                while (i++ < 5) {
                    Thread.sleep(3000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            show(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String result) {
            show("post execute " + result);
        }
    }
}
