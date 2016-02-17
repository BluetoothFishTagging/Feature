/**
 * Created by jamiecho on 2/10/16.
 */
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var logger = require('morgan');
var multer = require('multer');

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, './photo'); //"photo' directory must exist in prior.
    },
    filename: function (req, file, cb) {
        var ext = '';
        console.log(cb);
        var s = ''+file.mimetype;
        console.log(s);
        if(s.search('image') != -1){
            ext = s.substr(s.search('/')+1);
        }
        console.log(':)');
        console.log(ext);
        cb(null, '' + Date.now() + '.' + ext); //time stamp
    }
});

var upload = multer({storage:storage});
var path = require('path');

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:false}));
app.use(express.static(path.join(__dirname,'public'))); //static resources like images.
app.set('view engine','jade'); //default setting, though.



app.get('/',function(req,res){
    res.render('tag');
});

app.post('/',upload.single('photo'),function(req,res,next){
    //req.body will contain latitude, longitude, location, weight-size of fish, et cetera.
    console.log(req.body); // form fields
    //file is auto-saved to directory in server.
    console.log(req.file); // form file (file, since single)
    //if no file, req.file is undefined --> no photo submission
    res.render('tag'); //back to main page... essentially.
});

app.listen(8000); //arbitrary port