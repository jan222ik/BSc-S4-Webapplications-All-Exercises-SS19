function openImage(src) {
    var picture = new Image();
    var myWindow;
    picture.src = src;
    picture.onload = function () {
        var height = picture.height;
        var width = picture.width;
        myWindow = window.open(src, "Window", "height=" + height.toString() + ",width=" + width.toString());
        myWindow.document.write("<img src='"+src+"' alt='Eingang Schützenheim'>" +
            "<input style='position: absolute; right: 10px; bottom: 10px;' type='button' onclick='self.close()' value='Close'>");
    }
}