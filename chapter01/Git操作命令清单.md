## Git 操作命令清单

> 专用名词的译名

- Workspace 工作区
- Indes/Stage 暂存区
- Local Repository 仓库区或本地仓库
- Remote Repository 远程仓库

#### 一、新建代码库

```bash
# 在当前目录新建一个Git代码库,"初始化的目录"相当于被git管理了，该目录下会生成一个.git隐藏文件
$ git init
# 新建一个目录，将其初始化为Git代码库
$ git init [project-name]
# 查看初始化目录的隐藏.git文件
$ ls -a
# 克隆下载一个项目和它的整个代码历史
$ git clone [url]    # eg:git clone https://github.com/Wing-wsy/bamboo.git
```

#### 二、代码提交/回退操作

```bash
1' 文件在工作区内修改
# modified --> origin 文件位置 : 始终在工作区
# 【还原文件到最新提交的状态】将指定文件 <file> 恢复到最新的提交状态（恢复成跟提交的最新版本一样），丢弃所有未提交(还没add到暂存区)的更改
$ git restore <file>  
# 【还原文件到暂存区的状态】如果已经add到暂存区，但希望撤销这些更改；将文件 <file> 恢复到暂存区的状态，但不影响工作目录中的文件
$ git restore --staged <file>  
# 【还原全部更改】还原所有未提交(还没add到暂存区)的更改，包括工作目录和暂存区的更改,使用这个命令要谨慎，因为它会清除所有未提交的修改
$ git restore . 

--------------------------------------------------------------------------------
2' 文件添加到暂存区
# modified --> staged 文件位置 : 工作区 --> 暂存区
# 提交全部
$ git add .  
# 提交指定目录或文件
$ git add <file/dir> 
# 删除暂存区内容
$ git rm --cached <file/dir>  
# staged --> modified 文件位置 : 暂存区 --> 工作区
$ git reset <file>        * 对于已经被 Git 追踪的文件
$ git reset HEAD <file>   * 对于从来没有被 Git 追踪过，是 new file 的文件
# staged --> origin   文件位置 : 暂存区 --> 工作区
--------------------------------------------------------------------------------
3' 文件添加到本地仓库
# staged --> local    文件位置 : 暂存区 --> 本地仓库
$ git commit -m "description"  # 批量提交 
$ git commit [file1] [file2] ... -m [message] # 指定文件提交
# local --> staged    文件位置 : 本地仓库 --> 暂存区
$ git reset --soft HEAD^      # 如果要撤销本地两次修改，则改成 HEAD^^ 即可
--------------------------------------------------------------------------------
4' 文件推送到远程仓库
# local --> remote    文件位置 : 本地仓库 --> 远程仓库
$ git push origin [remote]    * 推送本地分支到指定远程分支
$ git push [remote] [branch]  * 推送指定本地分支到远程仓库
```

#### 三、查看信息

```bash
【me】$ git status                     # 显示有变更的文件以及所在位置
--------------------------------------------------------------------------------
# 查询版本变动日志log信息
【me】$ git log                        # 显示当前分支的版本历史
$ git log  --pretty=oneline      * 显示单行所有版本历史信息
$ git log -p [file]              * 显示指定文件相关的每一次diff
$ git log -5 --pretty --oneline  * 显示过去5次提交
$ git log --stat                 * 显示commit历史，以及每次commit发生变更的文件
--------------------------------------------------------------------------------
【me】$ git reflog                     # 显示当前分支的最近几次提交（简要信息）
$ git show [commit]              * 显示某次提交的元数据和内容变化 
$ git show --name-only [commit]  * 显示某次提交发生变化的文件
【me】$ git remote show [remote]       # 显示某个远程仓库的信息
--------------------------------------------------------------------------------
# 1.查看已修改，未暂存的内容 2.查看已暂存，未提交的内容 3.查看已提交，未推送的差异 
#    工作区内容                  暂存区内容               本地仓库内容
工作区          暂存区           本地仓库                    远程仓库
    \          /     \          /         \                  /
     \        /       \        /           \                /
 1.$ git diff     2.$ git diff --cache  3.$ git diff origin/master master
```

#### 四、其他

```bash
# 远程仓库拉取/更新到本地仓库
$ git checkout dev          # 切换分支到dev
$ git pull origin master    # 拉取远端origin/master分支并合并到当前分支
$ git pull origin develop   # 拉取远端origin/develop分支并合并到当前分支
--------------------------------------------------------------------------------
【me】$ git remote -v             # 列出当前仓库中已配置的远程仓库，并显示它们的 URL
--------------------------------------------------------------------------------
# 清除未追踪文件
$ git clean -nf             * 查看未追踪untranck文件
$ git clean -f <file>       * 清除指定文件
# 回退所有内容到上一个版本,或指定回退文件
$ git reset HEAD^ <file>
# 回退到某个版本(版本穿梭)
【me】$ git reset 057d 
--------------------------------------------------------------------------------
# 版本分支
$ git branch                       # 查看分支(简单)
$ git branch -v                    # 查看分支(详细)
$ git branch -r                    # 查看远程分支
$ git branch -a                    # 查看本地分支和远程分支 
$ git checkout -b [branch name]    # 使用该命令来创建新分支并立即切换到该分支下，从而在该分支中操作
$ git branch [branch name]         # 创建本地新分支 
$ git checkout [branch name]       # 切换分支
$ git merge [branch name]          # 合并分支，如果想将test分支合并到main分支，那么要保证当前在main分支下，然后branch                                            name写test，git merge test
$ git push <远程主机名> <本地分支名>   # 推送新分支到远程仓库
$ git branch -d [branch name]       # 删除本地分支
$ git push origin -d [branch name]  # 删除远程分支


# 设置代码提交的用户
$ git config --global user.name "runoob"
$ git config --global user.email test@runoob.com
```

#### 五、从本地初始化仓库到推送远程仓库操作

```bash
================================ 开始 ================================
# 查看本地路径
wing@WangShaoYoudeMacBook-Pro architect % pwd
/Users/wing/architect
# 创建目录
wing@WangShaoYoudeMacBook-Pro architect % 【mkdir testGit】
wing@WangShaoYoudeMacBook-Pro architect % cd testGit 
# 初始化仓库，指定目录的方式，目录为 git-demo
wing@WangShaoYoudeMacBook-Pro testGit % 【git init git-demo】
Initialized empty Git repository in /Users/wing/architect/testGit/git-demo/.git/ # 初始化完成
# 进入仓库目录
wing@WangShaoYoudeMacBook-Pro 【testGit % cd git-demo】

# 【在电脑上拷贝东西到仓库目录下，让仓库不为空】
# 查询状态，现在提示有文件了
wing@WangShaoYoudeMacBook-Pro git-demo % git status
On branch main

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)
	.DS_Store
	background/

nothing added to commit but untracked files present (use "git add" to track)
# 将全部数据添加到暂存区
wing@WangShaoYoudeMacBook-Pro git-demo % 【git add .】
# 继续提交到本地仓库
wing@WangShaoYoudeMacBook-Pro git-demo % 【git commit -m "wangsy commit"】

# 查询提交记录，已经有记录了【到这里本地仓库已经ok了】
wing@WangShaoYoudeMacBook-Pro git-demo % git reflog
16e240c (HEAD -> main) HEAD@{0}: commit (initial): first commit

# 下面开始将本地仓库推送到远程仓库，先去GitHub创建个远程仓库
1)New repository -> Repository name:git-demo  #git-demo是远程仓库名

# 到这里本地仓库和远程仓库已经有了，接下来开始推送
# 查询下本地仓库有没保存远程仓库的地址，执行下面命令没有任何返回，说明没有关联
wing@WangShaoYoudeMacBook-Pro git-demo % git remote -v
wing@WangShaoYoudeMacBook-Pro git-demo %
# 开始关联，可能推送时提示需要令牌token方式推送，那接下来去GitHub获取下token
# Settings -- Developer Settings -- Personal access tokens【切记要选择令牌的权限，下面有截图，否则最后push的时候会报错，提示没有权限访】得到token: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（需要保存起来）

# 开始操作（这条命令GitHub 仓库上复制）
 wing@WangShaoYoudeMacBook-Pro git-demo %【git remote add origin https://github.com/Wing-wsy/git-demo.git】
# 使用token方式

// <your_token>：包括<>在内的全部字符替换成你的token
// <USERNAME>：包括<>在内的全部字符替换成你的username
// <REPO>：包括<>在内的全部字符替换成你要访问的仓库名称
git remote set-url origin  https://<your_token>@github.com/<USERNAME>/<REPO>.git

 wing@WangShaoYoudeMacBook-Pro git-demo % 【git remote set-url origin  https://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@github.com/Wing-wsy/git-demo.git】
 # 最后一步，执行推送【到这里完成了将本地文件推送到了github】
 wing@WangShaoYoudeMacBook-Pro git-demo % 【git push origin main 】
 
 ================================ 结束了 ================================
 # 直接在github上打开文件直接修改下，这样的话本地仓库需要拉取远程仓库的最新代码
 wing@WangShaoYoudeMacBook-Pro wang-demo % git pull origin hos-fix


# 上面搞完只有main主分支，现在本地仓库再创建一个分支推送到远程仓库

wing@WangShaoYoudeMacBook-Pro wang-demo % git branch -v
* main 169a949 second commit wansy
wing@WangShaoYoudeMacBook-Pro wang-demo % git branch hos-fix   #创建
wing@WangShaoYoudeMacBook-Pro wang-demo % git branch -v     
  hos-fix 169a949 second commit wansy
* main    169a949 second commit wansy
wing@WangShaoYoudeMacBook-Pro wang-demo % git push origin hos-fix  #推送新分支，刷新GitHub就能看到
```

![](https://pic4.zhimg.com/80/v2-fd40310e9c9ff106f46d3166d7eb3d07_1440w.webp)

#### 六、github对分支进行合并

1、点击这里

<img src="https://img-blog.csdnimg.cn/img_convert/cf967e48310dddc0bf29e69de6c3fa83.png" style="zoom:33%;" />

2、点击这里会看到有更新的分支

<img src="https://img-blog.csdnimg.cn/img_convert/b1edd299acdeffc1d232e76fc5decc25.png" style="zoom:67%;" />





3、可以选择需要合并的分支，关系是把1合并到2上

![](https://img-blog.csdnimg.cn/img_convert/db672506a413313a3531ee9d5dd49714.png)

4、点击进行创建合并

![](https://img-blog.csdnimg.cn/img_convert/9a54fda6c0b9172a69539429e85d8267.png)



5、看到对勾就是没有冲突可以合并，点击按钮合并

![](https://img-blog.csdnimg.cn/img_convert/4cc7ebb32ea1809195ea0f56f6e70832.png)

6、点击confirm按钮提交合并，界面会变成紫色

![](https://img-blog.csdnimg.cn/img_convert/4e2b11f39ba19283b0f7e4e850d45eaa.png)

7、然后去找分支的confirm记录，这时就会看到已经合并好了



#### 	七、删除本地和远程的分支

```bash
# 删除本地分支命令 
git branch -d [branch name]

# 删除github分支命令
git push origin -d [branch name]
```



#### 八、工作代码冲突处理

```bash
# git提交代码和拉取远程代码冲突被拒绝，可以命令行执行下面代码： 
git reset --soft HEAD^

# 还不行就^^（一个表示回到上个版本，两个表示回到上上个版本）
git reset --soft HEAD^^


注意：记得执行上面命令前，要先将本地修改的代码提交到本地仓库
然后重新commit自己的代码，然后Push到远程仓库
```



