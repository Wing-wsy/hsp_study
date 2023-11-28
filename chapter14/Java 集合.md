#### 一、Java集合

##### 1 集合的理解和好处

###### 1.1 数组的不足之处

- 长度开始时必须指定，而且一旦指定，不能更改。
- 保存的必须为同一类型的元素。
- 使用数组进行增加元素的示意代码比较麻烦

> 数组扩容，不灵活，比较麻烦，实例如下:

`（见代码 com.hspedu.collection_.ArrayExample ）`

###### 1.2 集合的好处

- 可以动态保存任意多个对象，使用比较方便
- 提供了一系列方便的操作对象的方法:add、remove、set、get等
- 使用集合添加，删除新元素更加简洁

###### 1.3 Collection 接口图

![](https://img-blog.csdnimg.cn/00d7515347a441458ca659fbfe7c2629.png#pic_center)

###### 1.4 Map 接口图

![](https://img-blog.csdnimg.cn/d1241df8097d40cdbc7824f0e8c8fb87.png#pic_center)

```markdown
1.集合主要是两组(单列集合、双列集合)
2.Collection接口有两个重要的子接口List Set 它们的实现子类都是单列集合
3.Map接口的实现子类 是双列集合，存放的 Key-Value
```

#### 二 、Collection接口实现类的特点

- Collection实现子类可以存放多个元素,每个元素可以是Object
- 有些Collection的实现类，可以存放重复的元素，有些不可以
- 有些Collection的实现类是有序的(List),有些是无序的(Set)–这里说的有序和无序是指取出的顺序是否和放入顺序一致
- Collection接口没有直接的实现子类，是通过它的子接口Set和List来实现的

`演示示例见代码：com.hspedu.collection_.CollectionMethod`

##### 1 Collection接口的遍历形式

> 使用迭代器Iterator

- Iterator对象称为迭代器，用于遍历Collection集合中的元素。
- 所有实现了Collection接口的集合类都有一个Iterator()方法，用以返回一个实现了Iterator接口的对象，即可以返回一个迭代器
- Iterator仅用于遍历集合，Iterator本身并不存放对象

`迭代器使用见代码：com.hspedu.collection_.CollectionIterator `



> for循环增强遍历

增强for循环，可以替代itrator迭代器，特点:增强for就是简化版的iterator，本质一样。只能用于遍历集合或者数组

#### 三 、List接口和常用方法

##### 1 List接口是Collection接口的子接口

- List集合类中元素有序(即添加顺序和取出顺序一致)、并且可以重复
- List集合中的每个元素都有其对应的顺序索引，即支持索引
- List容器中的元素都对应一个整数型的序号记载其在容器中的位置，可以根据序号存取容器中的元素。
- List接口的常用实现类有:ArrayList、LinkedList和Vector



> List接口和常用方法

- add 在index位置插入元素

- addAll 从index位置开始将所有元素添加进来

- get 获取指定index位置的元素

- indexOf 返回在集合中首次出现的位置

- lastIndexOf 返回在当前集合中末次出现的位置

- remove 移除index位置的元素，并返回此元素

- set 设置指定index位置的元素(替换)

- subList 返回一个范围位置中的子集合

  

##### 2 List的三种遍历方式

##### 3 ArrayList底层接口和源码分析

##### 4 LinkList底层结构

##### 5 LinkList底层操作机制

##### 6 LinkLis和ArrayList的区别
