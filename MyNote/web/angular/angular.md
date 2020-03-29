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
// 添加ng-zorro-antd
ng add ng-zorro-antd
// 创建一个module，--routing表示创建的时候带一个路由
ng generate module pages/monitor --routing
// 在app中的pages中创建一个welcome组件，
ng generate component pages/monitor --module=monitor
ng generate route
```

## 服务端渲染

一种在服务器端将页面渲染好然后发送给浏览器的基数

```
#安装依赖
ng add @nguniversal/express-engine --clientProject <project-name>
```



## 编译以及运行项目

```bash
// 编译项目
ng build
// 启动项目
ng serve --port 4200 --open
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

# 基本原理

## angular的基本概念

### 基本概念简介

angular的基本构造模块是NgModule，它为*组件*提供了编译的上下文环境。 NgModule 会把相关的代码收集到一些功能集中。Angular 应用就是由一组 NgModule 定义出的。 应用至少会有一个用于引导应用的*根模块*，通常还会有很多*特性模块*。

- 组件定义*视图*。视图是一组可见的屏幕元素，Angular 可以根据你的程序逻辑和数据来选择和修改它们。 每个应用都至少有一个根组件。
- 组件使用*服务*。服务会提供那些与视图不直接相关的功能。服务提供商可以作为*依赖*被*注入*到组件中， 这能让你的代码更加模块化、更加可复用、更加高效。

组件和服务都是简单的类，这些类使用*装饰器*来标出它们的类型，并提供元数据以告知 Angular 该如何使用它们。

- 组件类的元数据将组件类和一个用来定义视图的*模板*关联起来。 模板把普通的 HTML 和 Angular *指令*与*绑定标记（markup）*组合起来，这样 Angular 就可以在呈现 HTML 之前先修改这些 HTML。
- 服务类的元数据提供了一些信息，Angular 要用这些信息来让组件可以通过*依赖注入（DI）*使用该服务。

应用的组件通常会定义很多视图，并进行分级组织。 Angular 提供了 `Router` 服务来帮助你定义视图之间的导航路径。 路由器提供了先进的浏览器内导航功能。

#### 模块

Angular 定义了 `NgModule`，它和 JavaScript（ES2015） 的模块不同而且有一定的互补性。 NgModule 为一个组件集声明了编译的上下文环境，它专注于某个应用领域、某个工作流或一组紧密相关的能力。 NgModule 可以将其组件和一组相关代码（如服务）关联起来，形成功能单元。

每个 Angular 应用都有一个*根模块*，通常命名为 `AppModule`。根模块提供了用来启动应用的引导机制。 一个应用通常会包含很多特性模块。

像 JavaScript 模块一样，NgModule 也可以从其它 NgModule 中导入功能，并允许导出它们自己的功能供其它 NgModule 使用。 比如，要在你的应用中使用路由器（Router）服务，就要导入 `Router` 这个 NgModule。

把你的代码组织成一些清晰的特性模块，可以帮助管理复杂应用的开发工作并实现可复用性设计。 另外，这项技术还能让你获得*惰性加载*（也就是按需加载模块）的优点，以尽可能减小启动时需要加载的代码体积。

#### 组件

每个 Angular 应用都至少有一个组件，也就是*根组件*，它会把组件树和页面中的 DOM 连接起来。 每个组件都会定义一个类，其中包含应用的数据和逻辑，并与一个 HTML *模板*相关联，该模板定义了一个供目标环境下显示的视图。

`@Component()` 装饰器表明紧随它的那个类是一个组件，并提供模板和该组件专属的元数据。

#### 模板、指令和数据绑定

模板会把 HTML 和 Angular 的标记（markup）组合起来，这些标记可以在 HTML 元素显示出来之前修改它们。 模板中的*指令*会提供商逻辑，而*绑定标记*会把你应用中的数据和 DOM 连接在一起。 有两种类型的数据绑定：

- *事件绑定*让你的应用可以通过更新应用的数据来响应目标环境下的用户输入。
- *属性绑定*让你将从应用数据中计算出来的值插入到 HTML 中。

在视图显示出来之前，Angular 会先根据你的应用数据和逻辑来运行模板中的指令并解析绑定表达式，以修改 HTML 元素和 DOM。 Angular 支持*双向数据绑定*，这意味着 DOM 中发生的变化（比如用户的选择）同样可以反映回你的程序数据中。

你的模板也可以用*管道*转换要显示的值以增强用户体验。比如，可以使用管道来显示适合用户所在语言环境的日期和货币格式。 Angular 为一些通用的转换提供了预定义管道，你还可以定义自己的管道。

#### 服务与依赖注入

对于与特定视图无关并希望跨组件共享的数据或逻辑，可以创建*服务*类。 服务类的定义通常紧跟在 “@Injectable()” 装饰器之后。该装饰器提供的元数据可以让你的服务作为依赖*被注入到*客户组件中。

*依赖注入*（或 DI）让你可以保持组件类的精简和高效。有了 DI，组件就不用从服务器获取数据、验证用户输入或直接把日志写到控制台，而是会把这些任务委托给服务。

#### 路由

Angular 的 `Router` 模块提供了一个服务，它可以让你定义在应用的各个不同状态和视图层次结构之间导航时要使用的路径。 它的工作模型基于人们熟知的浏览器导航约定：

- 在地址栏输入 URL，浏览器就会导航到相应的页面。
- 在页面中点击链接，浏览器就会导航到一个新页面。
- 点击浏览器的前进和后退按钮，浏览器就会在你的浏览历史中向前或向后导航。

不过路由器会把类似 URL 的路径映射到视图而不是页面。 当用户执行一个动作时（比如点击链接），本应该在浏览器中加载一个新页面，但是路由器拦截了浏览器的这个行为，并显示或隐藏一个视图层次结构。

如果路由器认为当前的应用状态需要某些特定的功能，而定义此功能的模块尚未加载，路由器就会按需*惰性加载*此模块。

路由器会根据你应用中的导航规则和数据状态来拦截 URL。 当用户点击按钮、选择下拉框或收到其它任何来源的输入时，你可以导航到一个新视图。 路由器会在浏览器的历史日志中记录这个动作，所以前进和后退按钮也能正常工作。

要定义导航规则，你就要把*导航路径*和你的组件关联起来。 路径（path）使用类似 URL 的语法来和程序数据整合在一起，就像模板语法会把你的视图和程序数据整合起来一样。 然后你就可以用程序逻辑来决定要显示或隐藏哪些视图，以根据你制定的访问规则对用户的输入做出响应。

### NgModule 简介

虽然小型的应用可能只有一个 NgModule，不过大多数应用都会有很多*特性模块*。应用的*根模块*之所以叫根模块，是因为它可以包含任意深度的层次化子模块。

#### `@NgModule` 元数据

NgModule 是一个带有 `@NgModule()` 装饰器的类。`@NgModule()` 装饰器是一个函数，它接受一个元数据对象，该对象的属性用来描述这个模块。其中最重要的属性如下。

- `declarations`（可声明对象表） —— 那些属于本 NgModule 的[组件](https://angular.cn/guide/architecture-components)、*指令*、*管道*。
- `exports`（导出表） —— 那些能在其它模块的*组件模板*中使用的可声明对象的子集。
- `imports`（导入表） —— 那些导出了*本*模块中的组件模板所需的类的其它模块。
- `providers` —— 本模块向全局服务中贡献的那些[服务](https://angular.cn/guide/architecture-services)的创建器。 这些服务能被本应用中的任何部分使用。（你也可以在组件级别指定服务提供商，这通常是首选方式。）
- `bootstrap` —— 应用的主视图，称为*根组件*。它是应用中所有其它视图的宿主。只有*根模块*才应该设置这个 `bootstrap` 属性。