
//加载文件系统
const fs = require('fs');

//加载控制器模块
function loadControllersModule(dir,modulePath,router){
    var state = fs.statSync(dir);
    if(state.isDirectory()){
        var files = fs.readdirSync(dir);
        for(var fileName of files){
            var filePath = dir +'/' + fileName;
            state = fs.statSync(filePath);
            if(state.isDirectory()){
                loadControllersModule(filePath,"/"+fileName,router);
                continue;
            }
            if(fileName.endsWith(".js")){
                let mappings = require(filePath);
                addMapping(mappings,modulePath,router);
            }
        }
    }
}

//添加路由映射
function addMapping(mappings,modulePath,router){
    for(var mapping in mappings){
       // console.log('modulePath : ' + modulePath +' mapping : ' + mapping.trim());
        var paths = mapping.trim().split('*');
        if(paths[0] && paths[1]){
            var path = ((modulePath==="/")?modulePath : (modulePath + "/")) + paths[1];
            console.log('add method:' + paths[0] + " path : " + path);
            addToRouter(paths[0],path,mappings[mapping],router);
        }
    }
}

/**
 * 添加至router
 */
function addToRouter(method,path,fn,router){
    if(method.toLocaleLowerCase() === 'get'){
        router.get(path,fn);
    }else if(method.toLocaleLowerCase() === 'post'){
        router.post(path,fn);
    }else{
        console.log("invalid http method : " + method + " path : " +path);
    }
}

module.exports = function(dir){
    let
        controllersDir = __dirname + "/" + (dir || 'controllers'),
        router = require('koa-router')();
        loadControllersModule(controllersDir,"/",router);
        return router.routes(); 
};