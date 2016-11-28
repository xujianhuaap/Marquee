#MarqueeView

#Usage
1.��build.gradle �ļ������һ��������
```
dependencies {
    compile 'me.mvdw.recyclerviewmergeadapter:recyclerviewmergeadapter:2.0.0'
}
```
2.�� Activity onCreate()��������һ�´���
```
		view = (MarqueeView)findViewById(R.id.view);
        View addView=findViewById(R.id.tv_button_add);
        View deleteView=findViewById(R.id.tv_button_delete);
        students = new ArrayList<>();
        for(int i=0;i<3;i++){
            Student s=new Student("�ӳ�������һк����ӥ�������籡���š����ն����������⡣",23+i);
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

3. ����ʵ����

```
	public class Student {
		@MsgField("Student") //Ҫ��ʾ���ֶ�
		public String name;//���ֶα�����public ����Ȩ��
		public int age;

		public Student(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		@Override
		public String toString() {
			return "name\t"+name+"\tage\t"+age;
		}
	}
```