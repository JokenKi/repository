
var fn_index = async(ctx,next)=>{
   ctx.render('index.html',{name:'bootstrap'});
};

module.exports = {
    ' GET*index':fn_index
};

