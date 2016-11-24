#MarqueeView

#Usage
1.在build.gradle 文件中添加一下依赖包
```
dependencies {
    compile 'me.mvdw.recyclerviewmergeadapter:recyclerviewmergeadapter:2.0.0'
}
```
2.在 Activity onCreate()方法调用一下代码
```
		view = (MarqueeView)findViewById(R.id.view);
        View addView=findViewById(R.id.tv_button_add);
        View deleteView=findViewById(R.id.tv_button_delete);
        students = new ArrayList<>();
        for(int i=0;i<3;i++){
            Student s=new Student("河出伏流，一泻汪洋。鹰隼肆翼，喷薄吸张。旭日东升，其道大光。",23+i);
            students.add(s);
        }

        try {
            view.setOnItemClickListener(new MarqueeView.MarqueesItemClickListener<Student>() {
                @Override
                public void onItemClick(Student o) {
                    if(o!=null) Toast.makeText(MainActivity.this,o.getName(),Toast.LENGTH_SHORT).show();
                }
            });
            view.setNews(students);
            view.startScroll();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

```