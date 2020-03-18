# 创建项目

## 安装

```bash
yarn add @angular/cli
```

## 创建新项目

```bash
// 创建项目并跳过安装依赖的环节
ng new project_name --skip-install
// 如果安装时间过程可以停止，之后使用cnpm安装依赖
yarn install
```

## 编译以及运行项目

```bash
// 编译项目
ng build
// 启动项目
ng serve --open
```

# 目录结构

## 目录说明

主要关注package.json和src文件夹。

### e2e

端到端测试

### node_modules

安装的第三方模块都在这，package.json中定义的依赖都会安装到这里

### src

项目中的所有文件都放在这里

#### app

组建以及根模块

其中的`app.module.ts`是项目的根模块。

```typescript
/*这是angular的根模块，告诉angular如何组装应用*/

// 浏览器的解析模块
import {BrowserModule} from '@angular/platform-browser';
// angular的核心模块
import {NgModule} from '@angular/core';
// 根路由模块
import {AppRoutingModule} from './app-routing.module';
// 根组件
import {AppComponent} from './app.component';
// @NgModule装饰器，@NgModule接受一个元数据对象，告诉angular如何便器和启动应用
@NgModule({
  // 配置当前项目运行的组件
  declarations: [
    AppComponent
  ],
  // 配置当前模块运行时依赖的其他模块
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  // 配置当前项目所需要的服务
  providers: [],
  // 指定应用的主视图（称为根组件），通过引导根AppModule来启动应用
  bootstrap: [AppComponent]
})
// 导出，根模块不需要导出任何东西，因为其他组件不需要导入根模块
export class AppModule {
}
```

根组件包含三个app.component.ts，app.component.html，app.component.less文件，

```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'app-root', // 组件的名称
  templateUrl: './app.component.html',//组件的html
  styleUrls: ['./app.component.less']// 组件的样式文件
})
// 导出
export class AppComponent {
  title = 'demo5'; // 属性
  constructor() {// 构造函数
  }
}
```



#### assets

静态资源

#### environments

其中包括为各个目标环境准备的文件

#### index.html

主页面

#### main.ts

应用的主要入口点

#### polyfills.ts

填充库能帮我们把这些不同点进行标准化

#### styles.less

全局样式

#### test.ts

单元测试的主要入口

### .editorconfig

给编辑器看的一个简单配置文件

### angular.json

### broweserslist

支持浏览器的配置文件

### karma.conf.js

给karma的单元测试配置

### package.json

npm配置文件

### tsconfig.json

TypeScript编辑器的配置。

### tsline.json

给TSLint和CodeLyzer用的配置信息，Lint功能可以帮你保持代码风格的统一。

# 组件以及组件中的模板

```bash
ng generate component component/news
```

会在component文件下生成一个news组件，并在app.module.ts中引入此组件，并声明。

## 绑定属性

news.component.ts

```typescript
import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-news',
    templateUrl: './news.component.html',
    styleUrls: ['./news.component.less']
})
export class NewsComponent implements OnInit {

    msg: string;
    content: any = '<h2>hahah</h2>';


    constructor() {
        this.setMsg('Hello World!');
    }

    ngOnInit(): void {
    }

    setMsg(msg: string) {
        this.msg = msg;
    }

    changeMsg() {
        this.msg = 'new MSG';
    }
}
```

news.component.html

```html
<p>news works!</p>
<h1>{{msg}}</h1>
<button (click)="changeMsg()"></button>

<div [title]="msg">
    demo
</div>

<span [innerHTML]="content"></span>
```

## ngstyle、ngclass

```html
// 基本用法
<div [ngStyle]="{'background-color':'green'}"></<div>
// 增加判断
<div [ngStyle]="{'background-color':username === 'zxc' ? 'green' : 'red' }"></<div>
// 基本用法
[ngClass]="{'text-success':true}"
// 增加判断
[ngClass]="{'text-success':username == 'zxc'}"
[ngClass]="{'text-success':index == 0}"
```

## 管道

```bash
ng generate pipe pipes/stringpipe
```

创建管道。

# 路由

## get传值

## 动态路由

## js跳转

## js动态跳转

## 父子嵌套路由