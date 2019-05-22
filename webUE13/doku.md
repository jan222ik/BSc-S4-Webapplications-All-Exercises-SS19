Dokumentation UE 13<br/>
GET auf Path: /video mit Parameter ?vid=‘name‘
1)	Video identifizieren
     ```javascript
     /* Mithilfe von simplem Entscheidungsbaum. */
     if (param !== undefined) {
         if (param === 'yellifish' || param === 'yelli' || param === 'y') {
             path = 'public/images/yellifish.mp4';   //Video über Quallen
     } else {
         if (param === 'turtle' || param === 't') {
             path = 'public/images/turtle.mp4';      //Video über Schildkröten
         } /*ADD ELSE IF FOR OTHER VIDEOS HERE*/
     }
     ```
2)  Pfad Existiert <br/>
       1) Größe des Files in ```fileSize``` Laden 
       2) Aus Request die Range der Bytes aus Header ```const range = req.headers.range``` abfragen. (Fromat Range: ```bytes=%d-%d```, erste Zahl: Start Byte, Zweite Zahl: Bis Byte x maximal laden)
       3) Range r mit ?
           1) ```r[0,_[```: 
               1) Stringmanipulation von Range-ObjectString
               2) Byterangestart parsen zu Integer(Radix 10)
               3) Byterangeende parsen zu Integer(Radix 10)
                   ```{javascript 1.8}
                   /*Schritt a-c*/
                   const parts = range.replace(/bytes=/, "").split("-");
                   const start = parseInt(parts[0], 10);
                   const end = parts[1] ? parseInt(parts[1], 10) : fileSize - 1;
                   ```
               4) Chunkgröße ermitteln ```chunksize = (end - start) + 1;```
               5) Dateisector laden ```file = fs.createReadStream(path, {start, end})```
               6) Response-Header erzeugen
                   ```
                   const head = {
                        /*
                            Angaben in Bytes über die Übermittelten Daten: <Start>-<Ende>/<FileSizeGesamt>
                        */
                        'Content-Range': `bytes ${start}-${end}/${fileSize}`,
                        'Accept-Ranges': 'bytes',
                        /*
                            Größe des Chunkes ^= end - start + 1 (+ 1 um Array 0 Indexing auszugleichen)
                        */
                        'Content-Length': chunksize,
                        /*
                            Content-Type hard-coded auf mp4, falls andere Videoformate verfügbar,
                            muss dynamisch sein/von File ableiten
                        */
                        'Content-Type': 'video/mp4',
                   };
                   ```
               7) Response senden<br/>
                Header mit Code 206 auf Stream schreiben. Code 206 bedeutet, dass es sich partial content handelt und der Client den Rest noch anfragen muss. Das File wird anschließend in den Responsebody geschrieben.
                   ```
                       res.writeHead(206, head);
                       file.pipe(res);
                   ```
           2) ```r[x,_[```:
               1) Response-Header erzeugen<br/>
                   Header mit Filesize, da das File auf einmal geladen wird. Content-Type hard-coded auf mp4, falls andere Videoformate verfügbar, muss dynamisch von File ableitent werden.                    
                   ```
                       const head = {
                            'Content-Length': fileSize,
                            'Content-Type': 'video/mp4',
                       []() };
                   ```
               2) Response senden<br/>
                   Header mit Code 200, da alles übermittelt wird. 
                   ```
                       res.writeHead(200, head);
                       fs.createReadStream(path).pipe(res);
                   ```           