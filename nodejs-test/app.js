"use strict;"
//导入koa
const Koa = require('koa');

// 创建app对象
const app = new Koa();

// 控制器模块
const controller = require('./controller');

// 解析post请求函数
const bodyParser = require('koa-bodyparser');

// 静态文件处理模块
let staticFiles = require('./static-files');
// 静态文件存放目录
const staticFilePath = '/static/';

// 模板模块
const template = require('./template');

const isProduction = process.env.NODE_ENV ==='production';

app.use(async(ctx,next)=>{
    console.log(`Process ${ctx.request.method} ${ctx.request.url}...`);
    var
        start = new Date().getTime(),
        execTime;
    await next();
    execTime = new Date().getTime() - start;
    ctx.response.set('X-Response-Time', `${execTime}ms`);
    console.log(`Process execTime ${execTime}...`);
});

if(!isProduction){
    app.use(staticFiles(staticFilePath,__dirname + staticFilePath));
}

app.use(bodyParser());

app.use(template('views',{
    noCache : !isProduction,
    watch : !isProduction
}));

app.use(controller());

app.listen(3000);

console.log('app started at port 3000');