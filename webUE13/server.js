const express = require('express');
const fs = require('fs');
const path = require('path');
const app = express();

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', function (req, res) {
    res.sendFile(path.join(__dirname + '/index.html'))
});

app.get('/video', function (req, res) {
    console.log(req.url);
    let param = req.query.vid;
    console.log(`Param: ${param}`);
    let path;
    if (param !== undefined) {
        if (param === 'yellifish' || param === 'yelli' || param === 'y') {
            path = 'public/images/yellifish.mp4';
            console.log("yellyVID");
        } else {
            if (param === 'turtle' || param === 't') {
                path = 'public/images/turtle.mp4';
                console.log("turtleVID");
            } /*ADD ELSE IF FOR OTHER VIDEOS HERE*/
        }
    }
    console.log(path);
    if (path !== undefined) {
        const stat = fs.statSync(path);
        const fileSize = stat.size;
        const range = req.headers.range;
        console.log(range);

        if (range) {
            const parts = range.replace(/bytes=/, "").split("-");
            const start = parseInt(parts[0], 10);
            const end = parts[1] ? parseInt(parts[1], 10) : fileSize - 1;
            const chunksize = (end - start) + 1;
            const file = fs.createReadStream(path, {start, end});
            const head = {
                'Content-Range': `bytes ${start}-${end}/${fileSize}`,
                'Accept-Ranges': 'bytes',
                'Content-Length': chunksize,
                'Content-Type': 'video/mp4',
            };

            res.writeHead(206, head);
            file.pipe(res);
        } else {
            const head = {
                'Content-Length': fileSize,
                'Content-Type': 'video/mp4',
            };
            res.writeHead(200, head);
            fs.createReadStream(path).pipe(res);
        }
    }
});

app.listen(3000, function () {
    console.log('Listening on port 3000!');
});