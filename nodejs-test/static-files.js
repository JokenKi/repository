const path = require('path');
const mime = require('mime');
const fs = require('mz/fs');

// url: 类似 '/static/'
// dir: 类似 __dirname + '/static'
function statisFiles(url,dir){
    return async(ctx,next)=>{

        let requestPath = ctx.request.path;
       
        // 判断是否以指定的url开头:
        if(!requestPath.startsWith(url)){
           await next();
           return;
        }

        let filePath = path.join(dir,requestPath.substring(url.length));
        console.log('Request FilePath : ' + filePath);

        if(await !fs.exists(filePath)){
            ctx.response.status = 404;
            return;
        }

        ctx.response.type = mime.lookup(filePath);
        ctx.response.body = await fs.readFile(filePath);
    };
};

module.exports = statisFiles;
